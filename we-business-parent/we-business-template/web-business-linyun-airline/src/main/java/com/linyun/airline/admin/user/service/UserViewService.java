package com.linyun.airline.admin.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.area.service.AreaViewService;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.entity.TSalaryIncreaseEntity;
import com.linyun.airline.admin.user.enums.SalaryEnum;
import com.linyun.airline.admin.user.form.TSalaryIncreaseAddForm;
import com.linyun.airline.admin.user.form.TSalaryIncreaseUpdateForm;
import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.common.enums.UserDisableStatusEnum;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.common.enums.UserStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyJobEntity;
import com.linyun.airline.entities.TDepartmentEntity;
import com.linyun.airline.entities.TUserAreaMapEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserJobEntity;
import com.linyun.airline.forms.TUserAddForm;
import com.linyun.airline.forms.TUserModForm;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "userViewService")
public class UserViewService extends BaseService<TUserEntity> {

	@Inject
	private AreaViewService areaViewService;//区域

	/**
	 * 
	 * 根据部门名称进行筛选
	 * @param sqlManager
	 */
	@SuppressWarnings("all")
	public List<Record> getDeptNameSelect(SqlManager sqlManager, final HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		String sqlString = EntityUtil.entityCndSql(TDepartmentEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comId", "=", companyId);
		cnd.and("deptName", "!=", "公司管理部");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 获取区域名称下拉框
	 * @param dictName
	 */
	public List<Record> getAreaNameList(String areaName, final String selectedAreaIds, final HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		String sqlString = sqlManager.get("employee_area_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("ta.areaName", "like", Strings.trim(areaName) + "%");
		if (!Util.isEmpty(selectedAreaIds)) {
			cnd.and("ta.id", "NOT IN", selectedAreaIds);
		}
		if (!Util.isEmpty(comId)) {
			cnd.and("ta.comId", "=", comId);
		}
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * @param addForm
	 * @param areaId
	 * @param jobId
	 * @param session
	 * 员工管理表单数据保存
	 */
	@Aop("txDb")
	public Map<String, String> saveEmployeeData(TUserAddForm addForm, TSalaryIncreaseAddForm addSalaryForm,
			Long areaId, Long jobId, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		String comType = company.getComType();//公司类型(1、上游公司;2、代理商)
		//查询出如果本公司下的某个员工离职了，再此入职让他的状态改为入职状态即可
		Sql sqlUser = Sqls.create(sqlManager.get("employee_add_data_update_old_user"));
		Cnd cnd = Cnd.NEW();
		cnd.and("cj.comId", "=", companyId);
		cnd.and("u.status", "=", UserJobStatusEnum.OFF.intKey());//离职
		cnd.and("uj.status", "=", UserJobStatusEnum.OFF.intKey());//离职
		cnd.and("u.telephone", "=", addForm.getTelephone());//用户名
		List<Record> before = dbDao.query(sqlUser, cnd, null);
		Long userId = null;
		for (Record record : before) {
			if (!Util.isEmpty(before)) {
				userId = (long) record.getInt("id");
			}
		}
		if (Util.isEmpty(before)) {
			//先添加用户数据
			TUserEntity userEntity = new TUserEntity();
			userEntity.setFullName(addForm.getFullName());//用户姓名
			userEntity.setUserName(addForm.getTelephone());//用户名
			userEntity.setTelephone(addForm.getTelephone());//手机号
			userEntity.setPassword(CommonConstants.INITIAL_PASSWORD);
			userEntity.setQq(addForm.getQq());
			if (!Util.isEmpty(comType) && comType.equals(CompanyTypeEnum.UPCOMPANY.intKey())) {
				userEntity.setUserType(UserTypeEnum.UPCOM.intKey());//上游公司普通用户
			} else if (!Util.isEmpty(comType) && comType.equals(CompanyTypeEnum.AGENT.intKey())) {
				userEntity.setUserType(UserTypeEnum.AGENT.intKey());//代理商公司普通用户
			}
			userEntity.setLandline(addForm.getLandline());
			userEntity.setEmail(addForm.getEmail());
			userEntity.setDisableStatus(UserDisableStatusEnum.YES.intKey());//没有禁用
			userEntity.setRemark(addForm.getRemark());
			userEntity.setStatus(UserJobStatusEnum.ON.intKey());//在职
			userEntity.setCreateTime(new Date());
			userEntity = dbDao.insert(userEntity);
			//获取到用户id
			Long userIds = userEntity.getId();
			//根据公司id和职位id查询出公司职位表的id
			TCompanyJobEntity comJob = dbDao.fetch(TCompanyJobEntity.class,
					Cnd.where("comId", "=", companyId).and("posid", "=", jobId));
			Long companyJobId = comJob.getId();//得到公司职位id
			//往用户就职表中填入数据
			Sql sql1 = Sqls.create(sqlManager.get("employee_add_data"));
			sql1.params().set("userId", userId);
			sql1.params().set("statusId", UserJobStatusEnum.ON.intKey());//在职
			sql1.params().set("companyJobId", companyJobId);
			TUserJobEntity newUser = DbSqlUtil.fetchEntity(dbDao, TUserJobEntity.class, sql1);
			if (Util.isEmpty(newUser)) {
				newUser = new TUserJobEntity();
				newUser.setUserid(userIds);
				newUser.setCompanyJobId(companyJobId);
				newUser.setStatus(UserJobStatusEnum.ON.intKey());//在职
				newUser.setHireDate(new Date());
				newUser.setLeaveDate(new Date());
				newUser = dbDao.insert(newUser);
			}
			//往用户区域表中填数据
			Iterable<String> areaIds = Splitter.on(",").split(addForm.getSelectedAreaIds());
			List<TUserAreaMapEntity> areaEntities = new ArrayList<TUserAreaMapEntity>();
			for (String dictInfoId : areaIds) {
				TUserAreaMapEntity userAreaMapEntity = new TUserAreaMapEntity();
				userAreaMapEntity.setAreaId(Long.valueOf(dictInfoId));
				//判断是否为空
				if (!Util.isEmpty(dictInfoId) && Long.valueOf(dictInfoId) != 0) {
					userAreaMapEntity.setUserId(userIds);
				}
				areaEntities.add(userAreaMapEntity);
			}
			dbDao.insert(areaEntities);
			//向员工的工资表中添加数据
			TSalaryIncreaseEntity insertSalary = new TSalaryIncreaseEntity();
			insertSalary.setComId(companyId);//公司id
			insertSalary.setUserId(userIds);
			insertSalary.setBaseWages(addSalaryForm.getBaseWages());//基本工资
			insertSalary.setWuXianYiJin(addSalaryForm.getWuXianYiJin());//五险一金
			insertSalary.setBonus(addSalaryForm.getBonus());//奖金
			insertSalary.setCommission(addSalaryForm.getCommission());//提成
			insertSalary.setCreateTime(new Date());
			insertSalary.setForfeit(addSalaryForm.getForfeit());//罚款
			insertSalary.setRatepaying(addSalaryForm.getRatepaying());//纳税
			insertSalary.setRemark(addSalaryForm.getRemark());//
			insertSalary.setStatus(SalaryEnum.ALREADY_WAGES.intKey());//已发工资
			dbDao.insert(insertSalary);
		}
		//若此员工在本公司离职，但是又想入职，查询出他的信息之后更新此员工的状态即可
		if (!Util.isEmpty(before)) {
			dbDao.update(TUserJobEntity.class, Chain.make("status", UserJobStatusEnum.ON.intKey()),
					Cnd.where("userid", "=", userId));
			dbDao.update(TUserEntity.class, Chain.make("status", UserJobStatusEnum.ON.intKey()),
					Cnd.where("id", "=", userId));
		}
		return JsonResult.success("添加成功!");
	}

	/**
	 * @param userId
	 * 修改时回显用户数据
	 */
	public Object updateUserinfo(Long userId, final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		Sql sql = Sqls.create(sqlManager.get("employee_update_data"));
		sql.params().set("userId", userId);
		Record updateUser = dbDao.fetch(sql);
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//获得公司id
		Sql sql1 = Sqls.create(sqlManager.get("employee_select_dept_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("comId", "=", comId);
		cnd.and("deptName", "!=", "公司管理部");
		List<Record> userDeptList = dbDao.query(sql1, cnd, null);
		//查询区域
		Sql sql2 = Sqls.create(sqlManager.get("employee_update_area"));
		Cnd cnd2 = Cnd.NEW();
		cnd2.and("a.comId", "=", comId);
		cnd2.and("am.userId", "=", userId);
		sql2.setCondition(cnd2);
		List<TAreaEntity> areaList = DbSqlUtil.query(dbDao, TAreaEntity.class, sql2);
		//区域id 拼串
		String areaIds = "";
		for (TAreaEntity tAreaEntity : areaList) {
			areaIds += tAreaEntity.getId() + ",";
		}
		if (areaIds.length() > 0) {
			areaIds = areaIds.substring(0, areaIds.length() - 1);
		}
		//查询用户工资
		List<TSalaryIncreaseEntity> salaryList = dbDao.query(TSalaryIncreaseEntity.class, Cnd
				.where("comId", "=", comId).and("userId", "=", userId), null);
		obj.put("salaryList", salaryList);
		obj.put("areaIds", areaIds);
		obj.put("userInfo", updateUser);//用户信息
		obj.put("deptInfo", userDeptList);//部门职位信息
		obj.put("areaInfo", Lists.transform(areaList, new Function<TAreaEntity, Select2Option>() {
			@Override
			public Select2Option apply(TAreaEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());//区域id
				op.setText(record.getAreaName());//区域名称
				return op;
			}
		}));
		return obj;
	}

	/**
	 * @param userId
	 * 删除用户信息
	 */
	@Aop("txDb")
	public Object deleteUserInfo(final Long userId) {
		TUserEntity singleUser = dbDao.fetch(TUserEntity.class, userId);
		//校验,如果用户表用户状态是处于在职,那么更新用户表中用户的状态为离职
		if (!Util.isEmpty(singleUser)) {
			//更新用户表中状态
			nutDao.update(TUserEntity.class, Chain.make("status", UserJobStatusEnum.OFF.intKey()),
					Cnd.where("id", "=", userId));//离职
			//更新用户就职表中的状态为离职
			nutDao.update(TUserJobEntity.class, Chain.make("status", UserJobStatusEnum.OFF.intKey()),
					Cnd.where("userid", "=", userId));//离职
			return JsonResult.success("删除成功");
		}
		return JsonResult.error("删除失败");
	}

	/**
	 * @param form
	 * 修改保存
	 */
	public Object updateData(TUserModForm updateForm, TSalaryIncreaseUpdateForm salUpdateForm, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Map<String, Object> obj = Maps.newHashMap();
		if (!Util.isEmpty(updateForm)) {
			updateForm.setStatus(UserJobStatusEnum.ON.intKey());
			updateForm.setUpdateTime(new Date());
		}
		TUserEntity user = new TUserEntity();
		BeanUtil.copyProperties(updateForm, user);
		this.updateIgnoreNull(user);//更新用户表中的数据
		//根据公司id和职位id查询出公司职位表的id
		TCompanyJobEntity comJob = dbDao.fetch(TCompanyJobEntity.class,
				Cnd.where("comId", "=", companyId).and("posid", "=", updateForm.getJobId()));
		Long companyJobId = comJob.getId();//得到公司职位id
		//更新部门职位
		dbDao.update(TCompanyJobEntity.class, Chain.make("posid", updateForm.getJobId()),
				Cnd.where("id", "=", companyJobId));
		dbDao.update(TUserJobEntity.class, Chain.make("companyJobId", companyJobId),
				Cnd.where("userid", "=", updateForm.getId()));

		//更新员工工资数据
		if (!Util.isEmpty(salUpdateForm)) {
			salUpdateForm.setUpdateTime(new Date());
		}
		Chain make = Chain.make("updateTime", new Date());
		if (!Util.isEmpty(salUpdateForm.getBaseWages())) {
			make.add("baseWages", salUpdateForm.getBaseWages());
		}
		if (!Util.isEmpty(salUpdateForm.getCommission())) {
			make.add("commission", salUpdateForm.getCommission());
		}
		make.add("wuXianYiJin", salUpdateForm.getWuXianYiJin());
		make.add("bonus", salUpdateForm.getBonus());
		make.add("forfeit", salUpdateForm.getForfeit());
		make.add("ratepaying", salUpdateForm.getRatepaying());
		make.add("remark", salUpdateForm.getRemark());
		make.add("status", salUpdateForm.getStatus());
		dbDao.update(TSalaryIncreaseEntity.class, make,
				Cnd.where("comId", "=", companyId).and("userId", "=", updateForm.getId()));
		//查询出用户区域关系表之前添加好的数据
		List<TUserAreaMapEntity> before = dbDao.query(TUserAreaMapEntity.class,
				Cnd.where("userId", "=", updateForm.getId()), null);
		//用户职位关联表欲更新为
		List<TUserAreaMapEntity> after = Lists.newArrayList();
		//获取到区域的ids
		String areaIds = updateForm.getSelectedAreaIds();
		if (!Util.isEmpty(areaIds)) {
			Iterable<String> areaIdIter = Splitter.on(",").trimResults(CharMatcher.anyOf("0")).omitEmptyStrings()
					.split(areaIds);

			for (String areaId : areaIdIter) {
				TUserAreaMapEntity tuam = new TUserAreaMapEntity();
				tuam.setUserId(updateForm.getId());
				tuam.setAreaId(Long.valueOf(areaId));
				after.add(tuam);
			}
		}
		dbDao.updateRelations(before, after);
		return obj;
	}

	/**更新用户的职位*/
	public void updateUserJobMap(TUserModForm userForm) {
		List<TUserJobEntity> mapList = userForm.getMap();
		Long userId = userForm.getId();
		//根据用户查询用户职位关系
		List<TUserJobEntity> before = dbDao.query(TUserJobEntity.class, Cnd.where("userId", "=", userId), null);

		if (!Util.isEmpty(mapList)) {
			for (TUserJobEntity map : mapList) {
				//职位id为空则不处理
				if (Util.isEmpty(map.getUserid())) {
					continue;
				}
				map.setUserid(userId);
			}
		}
		dbDao.updateRelations(before, mapList);
	}

	public TUserEntity findUser(String loginName, String passwd) {
		TUserEntity user = dbDao.fetch(
				TUserEntity.class,
				Cnd.where("userName", "=", loginName).and("password", "=", passwd)
						.and("status", "=", UserStatusEnum.VALID.intKey()));

		if (Util.isEmpty(user)) {
			user = dbDao.fetch(TUserEntity.class, Cnd.where("telephone", "=", loginName).and("password", "=", passwd)
					.and("status", "=", UserStatusEnum.VALID.intKey()));
		}
		return user;
	}

	public List<TFunctionEntity> findUserFunctions(long userId) {
		Sql sql = Sqls.create(sqlManager.get("user_function_all"));
		sql.params().set("userId", userId);
		sql.params().set("status", UserJobStatusEnum.ON.intKey());//在职
		return DbSqlUtil.query(dbDao, TFunctionEntity.class, sql, null);
	}

	//根据公司id查询出部门
	public Object queryDept(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//获得公司id
		Sql sql = Sqls.create(sqlManager.get("employee_select_dept_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("comId", "=", comId);
		cnd.and("deptName", "!=", "公司管理部");
		List<Record> queryList = dbDao.query(sql, cnd, null);
		obj.put("queryList", queryList);
		return obj;
	}

	//用户名称唯一性校验
	/*public Object checkDeptNameExist(final String fullName, final Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(userId)) {
			//添加时校验
			count = nutDao.count(TUserEntity.class, Cnd.where("fullName", "=", fullName));
		} else {
			//更新时校验
			count = nutDao.count(TUserEntity.class, Cnd.where("fullName", "=", fullName).and("id", "!=", userId));
		}
		map.put("valid", count <= 0);
		return map;
	}*/

	//联系电话唯一性校验
	public Object checkTelephoneExist(final String telephone, final Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(userId)) {
			//添加时校验
			count = nutDao.count(TUserEntity.class,
					Cnd.where("telephone", "=", telephone).and("status", "=", UserJobStatusEnum.ON.intKey()));
		} else {
			//更新时校验
			count = nutDao.count(
					TUserEntity.class,
					Cnd.where("telephone", "=", telephone).and("id", "!=", userId)
							.and("status", "=", UserJobStatusEnum.ON.intKey()));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//个人信息列表展示
	public Object personalInfo(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//通过session获取用户的id
		TUserEntity personal = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = personal.getId();//得到用户的id
		Sql sql = Sqls.create(sqlManager.get("employee_personalInfo_list"));
		sql.params().set("userId", userId);
		List<Record> personalList = dbDao.query(sql, null, null);
		personalList.get(0).getString("fullName");
		personalList.get(0).getString("telephone");
		personalList.get(0).getString("landline");
		personalList.get(0).getString("qq");
		personalList.get(0).getString("email");
		personalList.get(0).getString("deptName");//部门
		personalList.get(0).getString("jobName");//职位
		personalList.get(0).getString("areaName");//区域
		obj.put("personalInfo", personalList);//个人信息所要展现的数据
		return obj;
	}

	/**
	 * @param session
	 * 打开修改密码页面时准备数据
	 */
	public Object openUpdatePassword(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//通过session获取用户的id
		TUserEntity personal = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = personal.getId();//得到用户的id
		Sql sql = Sqls.create(sqlManager.get("employee_personalInfo_list"));
		sql.params().set("userId", userId);
		List<Record> personalList = dbDao.query(sql, null, null);
		personalList.get(0).getString("password");
		//通过查询将user表中的数据放在put中，前端通过key值可以取到id,在执行修改密码操作时可以将userId通过隐藏域传到后台
		obj.put("personalInfo", personalList);
		return obj;
	}

	/**
	 * @param PassForm
	 * @param session
	 * 修改密码执行操作
	 */
	public Object updatePassData(final TUserModForm PassForm, final Long userId) {
		TUserEntity userPass = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", userId));
		String oldPass = userPass.getPassword();//获得数据库中存在的原密码
		//前端输入的原密码
		String frontPass = PassForm.getPassword();
		String newPass = MD5.sign(PassForm.getNewPass(), AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
		//判断数据库中已存在的密码和前端输入传过来的原密码是否一致
		if (!oldPass.equals(frontPass)) {
			JsonResult.error("输入原密码错误,请重新输入!");
		} else {//如果原密码输入正确
			userPass.setPassword(newPass);
			//将原密码更新为新密码
			dbDao.update(TUserEntity.class, Chain.make("password", newPass), Cnd.where("id", "=", userId));
		}
		return JsonResult.success("密码修改成功!");
	}

	/*public Object checkOldPass(TUserModForm PassForm, final Long userId) {
		Map<String, Object> obj = Maps.newHashMap();
		TUserEntity userPass = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", userId));

		return obj;
	}*/

	/**
	 * @param userId
	 * @param session
	 * 个人信息编辑回显数据
	 */
	public Object editPersonal(Long userId, final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		Sql sql = Sqls.create(sqlManager.get("employee_personalInfo_list"));
		sql.params().set("userId", userId);
		List<Record> personalList = dbDao.query(sql, null, null);
		obj.put("personalInfo", personalList);//编辑页面所要展现的数据
		return obj;
	}

	/**
	 * @param updatePerForm
	 * @param userId
	 * 修改个人信息操作
	 */
	public Object updatePersonalData(final TUserModForm updatePerForm, Long userId) {
		Map<String, Object> obj = Maps.newHashMap();
		TUserEntity singlePerson = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", userId));
		if (!Util.isEmpty(singlePerson)) {
			singlePerson.setLandline(updatePerForm.getLandline());
			singlePerson.setQq(updatePerForm.getQq());
			singlePerson.setEmail(updatePerForm.getEmail());
		}
		this.updateIgnoreNull(singlePerson);//更新用户表中的数据
		return obj;
	}
}
