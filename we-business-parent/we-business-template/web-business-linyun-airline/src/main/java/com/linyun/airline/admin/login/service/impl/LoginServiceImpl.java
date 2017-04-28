package com.linyun.airline.admin.login.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.authority.authoritymanage.service.AuthorityViewService;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.log.service.SLogService;
import com.linyun.airline.admin.login.form.LoginForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.common.util.IpUtil;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean(name = "loginService")
public class LoginServiceImpl extends BaseService<TUserEntity> implements LoginService {

	@Inject
	private UserViewService userService;

	@Inject
	private SLogService sLogService;

	@Inject
	private AuthorityViewService authorityViewService;

	@Override
	public boolean login(final LoginForm form, final HttpSession session, final HttpServletRequest req) {
		form.setReturnUrl("jsp:admin.login");
		String loginName = form.getLoginName();
		if (Util.isEmpty(loginName)) {
			form.setErrMsg("用户名不能为空");
			return false;
		}

		String password = form.getPassword();
		if (Util.isEmpty(password)) {
			form.setErrMsg("密码不能为空");
			return false;
		}

		String recode = (String) session.getAttribute(CommonConstants.CONFIRMCODE);
		if (Util.isEmpty(recode)) {
			form.setErrMsg(null);
			return false;
		}

		String vCode = form.getValidateCode();
		if (Util.isEmpty(vCode) || !recode.equalsIgnoreCase(vCode)) {
			form.setErrMsg("验证码不正确");
			return false;
		}

		String passwd = MD5.sign(form.getPassword(), AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
		TUserEntity user = userService.findUser(form.getLoginName(), passwd);
		if (user == null) {
			form.setErrMsg("用户名或密码错误");
			return false;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getStatus()) {
				form.setErrMsg("账号未激活");
				return false;
			}
			addLoginlog(user, req);
			int userType = user.getUserType();

			//查询当前用户的公司
			Sql companySql = Sqls.create(sqlManager.get("login_select_company"));
			companySql.params().set("userId", user.getId());
			companySql.params().set("jobStatus", UserJobStatusEnum.ON.intKey());
			List<TCompanyEntity> companyLst = DbSqlUtil.query(dbDao, TCompanyEntity.class, companySql);

			if (UserTypeEnum.PLAT.intKey() != userType && Util.isEmpty(companyLst) && companyLst.size() != 1) {
				throw new IllegalArgumentException("用户必须且只能在一家公司就职");
			}
			TCompanyEntity company = companyLst.get(0);
			session.setAttribute(USER_COMPANY_KEY, company); //公司

			List<TFunctionEntity> allUserFunction = Lists.newArrayList();
			if (!Util.isEmpty(user) && CommonConstants.SUPER_ADMIN.equals(user.getUserName())) {
				allUserFunction = dbDao.query(TFunctionEntity.class, null, null);
			} else if (UserTypeEnum.UP_MANAGER.intKey() == userType || UserTypeEnum.AGENT_MANAGER.intKey() == userType) {
				allUserFunction = authorityViewService.getCompanyFunctions(session);
			} else {
				allUserFunction = userService.findUserFunctions(user.getId());
			}
			if (UserTypeEnum.PLAT.intKey() != userType || UserTypeEnum.UP_MANAGER.intKey() == userType
					|| UserTypeEnum.AGENT_MANAGER.intKey() == userType) {
				List<TFunctionEntity> meunSingle = dbDao
						.query(TFunctionEntity.class,
								Cnd.where("id", "=", CommonConstants.PERSON_ID).or("id", "=",
										CommonConstants.DESKTOP_ID), null);
				for (TFunctionEntity tf : meunSingle) {
					if (!allUserFunction.contains(tf)) {
						allUserFunction.add(tf);
					}
				}
			}
			//1级菜单
			List<TFunctionEntity> menus = new ArrayList<TFunctionEntity>();
			//根据菜单取功能的map
			Map<Long, List<TFunctionEntity>> functionMap = new HashMap<Long, List<TFunctionEntity>>();
			for (TFunctionEntity f : allUserFunction) {
				if (1 == f.getLevel()) {
					menus.add(f);
				}

				if (2 == f.getLevel()) {
					List<TFunctionEntity> subList = functionMap.get(f.getParentId());
					if (null == subList) {
						subList = new ArrayList<TFunctionEntity>();
						subList.add(f);
						functionMap.put(f.getParentId(), subList);
					} else {
						subList.add(f);
					}
				}
			}

			//一级菜单排序
			if (!Util.isEmpty(menus)) {
				Collections.sort(menus);
			}

			//排序functionMap
			Set<Long> keySet = functionMap.keySet();
			for (Long key : keySet) {
				List<TFunctionEntity> list = functionMap.get(key);
				if (!Util.isEmpty(list)) {
					Collections.sort(list);
				}
			}

			//将用户权限保存到session中
			session.setAttribute(FUNCTION_MAP_KEY, functionMap); //功能
			session.setAttribute(MENU_KEY, menus); //菜单
			session.setAttribute(AUTHS_KEY, allUserFunction); //所有功能
			session.setAttribute(LOGINUSER, user);
			session.setAttribute(IS_LOGIN_KEY, true);
			session.setAttribute("currentPageIndex", 0);
		}
		if ("phone".equalsIgnoreCase(form.getFlag())) {
			boolean flag = false;
			String sqlString = sqlManager.get("login_test_authority");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("uj.userid", "=", user.getId());
			List<Record> list = dbDao.query(sql, cnd, null);
			for (Record record : list) {
				if ("/admin/applyapproval/list.html".equalsIgnoreCase(record.getString("url"))) {
					flag = true;
					break;
				}
			}
			if (flag) {

				form.setReturnUrl(">>:/admin/applyapproval/list.html?phone=phone");
				return true;
			} else {
				form.setErrMsg("权限不足，请联系管理员");
				return false;
			}
		}
		if (UserTypeEnum.PLAT.intKey() == user.getUserType()) {
			form.setReturnUrl(">>:/admin/Company/list.html");
		} else if (UserTypeEnum.UPCOM.intKey() == user.getUserType()
				|| UserTypeEnum.UP_MANAGER.intKey() == user.getUserType()) {
			form.setReturnUrl(">>:/admin/operationsArea/desktop.html");
		} else if (UserTypeEnum.AGENT.intKey() == user.getUserType()
				|| UserTypeEnum.AGENT_MANAGER.intKey() == user.getUserType()) {
			form.setReturnUrl(">>:/admin/operationsArea/desktop.html");
			/*form.setReturnUrl(">>:/admin/user/list.html");*/
		} else {
			form.setReturnUrl("jsp:main");
		}
		return true;
	}

	@Override
	public void logout(HttpSession session) {
		session.removeAttribute(USER_COMPANY_KEY);
		session.removeAttribute(FUNCTION_MAP_KEY);
		session.removeAttribute(MENU_KEY);
		session.removeAttribute(AUTHS_KEY);
		session.removeAttribute(LOGINUSER);
		session.removeAttribute(IS_LOGIN_KEY);
		session.invalidate();
	}

	@Override
	public Boolean isLogin(HttpSession session) {
		return !Util.isEmpty(session.getAttribute(IS_LOGIN_KEY));
	}

	/**添加登录日志*/
	private void addLoginlog(TUserEntity user, HttpServletRequest req) {
		TFunctionEntity function = new TFunctionEntity();
		function.setId(-1);
		function.setName("登录");
		function.setUrl("/admin/login.html");

		String ip = IpUtil.getIpAddr(req);

		sLogService.addSyslog(function, user, ip);
	}

}
