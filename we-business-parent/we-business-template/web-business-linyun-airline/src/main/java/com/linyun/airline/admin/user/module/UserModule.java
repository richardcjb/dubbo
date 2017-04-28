package com.linyun.airline.admin.user.module;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.area.service.AreaViewService;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.form.TSalaryIncreaseAddForm;
import com.linyun.airline.admin.user.form.TSalaryIncreaseUpdateForm;
import com.linyun.airline.admin.user.form.TUserSqlForm;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TUserAddForm;
import com.linyun.airline.forms.TUserModForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 
* @ClassName: UserModule
* @author 崔建斌 
* @date 2016年11月18日 上午9:47:20
 */
@IocBean
@At("/admin/user")
public class UserModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private SqlManager sqlManager;

	@Inject
	private UserViewService userViewService;

	@Inject
	private AreaViewService areaViewService;

	/**
	 * 列表展示
	 * @param sqlForm
	 * @param pager
	 */

	@At
	@Ok("jsp")
	public Object list(@Param("..") final TUserSqlForm sqlForm, @Param("..") final Pager pager,
			final HttpSession session) {
		Map<String, Object> map = MapUtil.map();
		List<Record> deplist = userViewService.getDeptNameSelect(sqlManager, session);
		map.put("deplist", deplist);
		return map;
	}

	/**
	 * @param sqlForm
	 * @param pager
	 * 个人信息
	 */
	@At
	@Ok("jsp")
	public Object personalInfoList(final HttpSession session) {
		return userViewService.personalInfo(session);
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TUserSqlForm sqlForm, final HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Long adminId = company.getAdminId();//得到公司管理员id
		sqlForm.setComId(companyId);
		//通过session获取当前登录用户的id
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userType = user.getUserType();//得到用户类型
		if (UserTypeEnum.UPCOM.intKey() == userType || UserTypeEnum.AGENT.intKey() == userType
				|| UserTypeEnum.UP_MANAGER.intKey() == userType || UserTypeEnum.AGENT_MANAGER.intKey() == userType) {
			//如果当前用户是普通用户,登录到系统中只能显示出自己的数据，不可以看到管理员的账户
			sqlForm.setAdminId(adminId);
		}
		return userViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(final HttpSession session) {
		return userViewService.queryDept(session);
	}

	/**
	 * 添加操作
	 */
	@At
	@POST
	public Object add(@Param("..") final TUserAddForm addForm, @Param("..") final TSalaryIncreaseAddForm addSalaryForm,
			@Param("areaId") final Long areaId, @Param("jobId") final Long jobId, final HttpSession session) {
		userViewService.saveEmployeeData(addForm, addSalaryForm, areaId, jobId, session);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 添加时查询出部门联动带出职位
	 */
	@At
	public Object selectDeptName(@Param("deptId") final Long deptId) {
		//根据前端传过来的部门id查询出职位
		return dbDao.query(TJobEntity.class, Cnd.where("deptId", "=", deptId), null);
	}

	/**
	 * 密码初始化
	 */
	@At
	public Object passwordInit(@Param("..") final TUserModForm setForm) {
		//根据前端传过来的用户id设置该用户的密码
		TUserEntity singleUser = dbDao.fetch(TUserEntity.class, setForm.getId());
		if (!Util.isEmpty(singleUser)) {
			singleUser.setPassword(CommonConstants.INITIAL_PASSWORD);
			userViewService.updateIgnoreNull(singleUser);
			return JsonResult.success("密码初始化成功!");
		}
		return JsonResult.error("用户不存在!");
	}

	/**
	 * 修改密码页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updatePassword(final HttpSession session) {
		return userViewService.openUpdatePassword(session);
	}

	/**
	 * @param PassForm
	 * @param session
	 * 执行密码修改操作
	 */
	@At
	@POST
	public Object updatePassword(@Param("..") final TUserModForm PassForm, @Param("id") final Long userId) {
		try {
			userViewService.updatePassData(PassForm, userId);
		} catch (Exception e) {
			return JsonResult.error(e.getMessage());
		}
		return JsonResult.success("密码修改成功!");
	}

	/**
	 * @param userId
	 * 验证原密码输入是否正确
	 */
	/*public Object checkOldPass(@Param("..") final TUserModForm PassForm, @Param("id") final Long userId) {
		return userViewService.checkOldPass(PassForm, userId);
	}*/

	/**
	 * @param userId
	 * @param session
	 * 编辑个人信息页面回显数据
	 */
	@At
	@Ok("jsp")
	public Object updatePersonal(@Param("id") final Long userId, final HttpSession session) {
		return userViewService.editPersonal(userId, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object updatePersonal(@Param("..") final TUserModForm updatePerForm, @Param("id") final Long userId) {
		userViewService.updatePersonalData(updatePerForm, userId);
		return JsonResult.success("修改成功!");
	}

	/**
	 * @param dictAreaName
	 * 区域select2查询
	 */
	@At
	@POST
	public Object areaSelect2(@Param("area") final String dictAreaName,
			@Param("selectedAreaIds") final String selectedAreaIds, final HttpSession session) {
		return areaViewService.areaSelect2(dictAreaName, selectedAreaIds, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final Long userId, final HttpSession session) {
		return userViewService.updateUserinfo(userId, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final TUserModForm updateForm,
			@Param("..") final TSalaryIncreaseUpdateForm salUpdateForm, final HttpSession session) {
		userViewService.updateData(updateForm, salUpdateForm, session);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("userId") final Long userId) {
		userViewService.deleteUserInfo(userId);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TUserEntity.class, ids);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 校验用户姓名唯一性
	 */
	/*@At
	@POST
	public Object checkUserNameExist(@Param("fullName") final String fullName, @Param("id") final Long userId) {
		return userViewService.checkDeptNameExist(fullName, userId);
	}*/
	/**
	 * 校验联系电话
	 */
	@At
	@POST
	public Object checkTelephoneExist(@Param("telephone") final String telephone, @Param("id") final Long userId) {
		return userViewService.checkTelephoneExist(telephone, userId);
	}
}