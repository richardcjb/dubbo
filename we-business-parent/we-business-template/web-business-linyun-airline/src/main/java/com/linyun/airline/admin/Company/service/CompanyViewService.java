package com.linyun.airline.admin.Company.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.linyun.airline.admin.authority.authoritymanage.publicservice.AuthorityPublicService;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.TAgentEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyJobEntity;
import com.linyun.airline.entities.TDepartmentEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserJobEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.linyun.airline.forms.TUserAddForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {

	//管理员所在的部门
	private static final String MANAGE_DEPART = "公司管理部";
	//管理员职位
	private static final String MANAGE_POSITION = "公司管理员";
	//公司管理员账号初始密码
	private static final String MANAGE_PASSWORD = "000000";

	@Inject
	private AuthorityPublicService authorityPublicService;

	/**
	 * 
	 * 获取上游公司和代理商的数量
	 * <p>
	 * TODO
	 *
	 * @param sqlManagerm
	 * @return 返回上游公司、代理商、总数 封装到map中
	 */
	public Map<String, Object> getUpCompanyAndAgentCount(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> map = MapUtil.map();
		long upconpany = getCompanyCount(CompanyTypeEnum.UPCOMPANY.intKey(), company.getId());
		long agent = getCompanyCount(CompanyTypeEnum.AGENT.intKey(), company.getId());
		map.put("upconpany", upconpany);
		map.put("agent", agent);
		map.put("totalcompany", upconpany + agent);
		map.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return map;
	}

	/**
	 * 
	 * 获取相应公司的数量
	 * <p>
	 * TODO
	 *
	 * @param comType 公司类型id
	 * @return 返回相应数量
	 */
	@SuppressWarnings("unused")
	private long getCompanyCount(int comType, Long logincompanyid) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		cnd.and("id", "!=", logincompanyid);
		sql.setCondition(cnd);
		return Daos.queryCount(nutDao, sql.toString());
	}

	/**
	 * 
	 * 获取公司下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comType 公司类型（枚举）
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getCompanyList(int comType) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 
	 * 获取客户公司下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comType 公司类型（枚举）
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getCompanyList(int comType, String comName) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		cnd.and("comName", "like", Strings.trim(comName) + "%");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 
	 * 获取公司部门下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlManager
	 * @param departmentForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("hiding")
	public List<Record> getCompanyDepartment(long comId) {
		String sqlString = sqlManager.get("company_department_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("td.comId", "=", comId);
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 添加公司信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @param userAddForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCompany(TCompanyAddForm addForm, TUserAddForm userAddForm) {
		//添加管理员信息数据
		userAddForm.setPassword(MANAGE_PASSWORD);
		userAddForm.setUserName(addForm.getTelephone());
		userAddForm.setFullName(addForm.getTelephone());
		userAddForm.setTelephone("");
		userAddForm.setStatus(1);
		//设置上游公司或代理商的管理员用户类型
		if (addForm.getComType() == CompanyTypeEnum.UPCOMPANY.intKey()) {
			userAddForm.setUserType(UserTypeEnum.UP_MANAGER.intKey());
		} else if (addForm.getComType() == CompanyTypeEnum.AGENT.intKey()) {
			userAddForm.setUserType(UserTypeEnum.AGENT_MANAGER.intKey());
		}
		TUserEntity userEntity = FormUtil.add(dbDao, userAddForm, TUserEntity.class);
		//添加公司信息数据
		addForm.setCreatetime(new Date());
		addForm.setLastupdatetime(new Date());
		addForm.setAdminId(userEntity.getId());
		TCompanyEntity company = this.add(addForm);
		//添加管理员所在的部门信息
		TDepartmentEntity depart = new TDepartmentEntity();
		depart.setComId(company.getId());
		depart.setDeptName(MANAGE_DEPART);
		TDepartmentEntity department = dbDao.insert(depart);
		//添加公司管理员的职位信息
		TJobEntity jobEntity = new TJobEntity();
		jobEntity.setCreateTime(new Date());
		jobEntity.setDeptId(department.getId());
		jobEntity.setName(MANAGE_POSITION);
		TJobEntity job = dbDao.insert(jobEntity);
		//在公司职位表中添加管理员的公司职位信息
		TCompanyJobEntity companyJobEntity = new TCompanyJobEntity();
		companyJobEntity.setComId(company.getId());
		companyJobEntity.setPosid(job.getId());
		TCompanyJobEntity companyJob = dbDao.insert(companyJobEntity);
		//添加用户就职表
		TUserJobEntity userJobEntity = new TUserJobEntity();
		userJobEntity.setCompanyJobId(companyJob.getId());
		userJobEntity.setHireDate(new Date());
		userJobEntity.setStatus(userEntity.getStatus());
		userJobEntity.setUserid(userEntity.getId());
		//添加上游公司代理商关系表
		if (addForm.getComType() == CompanyTypeEnum.UPCOMPANY.intKey()) {
			TUpcompanyEntity upcompany = new TUpcompanyEntity();
			upcompany.setComId(company.getId());
			dbDao.insert(upcompany);
			//新增完上游公司自动配上权限
			authorityPublicService.companyFunction(addForm);
		} else {
			TAgentEntity agent = new TAgentEntity();
			agent.setComId(company.getId());
			dbDao.insert(agent);
			//新增完代理商公司自动配上权限
			authorityPublicService.DlsCompanyFunction(addForm);
		}
		return dbDao.insert(userJobEntity);
	}

	/**
	 * 为修改页面准备信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity companyEntity = this.fetch(id);
		//准备数据
		obj.put("company", companyEntity);
		//准备用户名
		obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//准备下拉框
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	public Object updateCompany(TCompanyUpdateForm updateForm) {
		//修改管理员用户名
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, updateForm.getAdminId());
		userEntity.setUserName(updateForm.getTelephone());
		userEntity.setFullName(updateForm.getTelephone());
		//设置上游公司或代理商的管理员用户类型
		if (updateForm.getComType() == CompanyTypeEnum.UPCOMPANY.intKey()) {
			userEntity.setUserType(UserTypeEnum.UP_MANAGER.intKey());
		} else if (updateForm.getComType() == CompanyTypeEnum.AGENT.intKey()) {
			userEntity.setUserType(UserTypeEnum.AGENT_MANAGER.intKey());
		}
		dbDao.update(userEntity);
		//上游公司表的信息
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", updateForm.getId()));
		//代理商表信息
		TAgentEntity agent = dbDao.fetch(TAgentEntity.class, Cnd.where("comId", "=", updateForm.getId()));
		//更新上游公司、代理商表信息
		if (updateForm.getComType() == CompanyTypeEnum.UPCOMPANY.intKey()) {
			if (Util.isEmpty(upcompany)) {
				TUpcompanyEntity upcompanyinsert = new TUpcompanyEntity();
				upcompanyinsert.setComId(updateForm.getId());
				dbDao.insert(upcompanyinsert);
			}
			if (!Util.isEmpty(agent)) {
				dbDao.delete(agent);
			}
		} else {
			if (Util.isEmpty(agent)) {
				TAgentEntity agentinsert = new TAgentEntity();
				agentinsert.setComId(updateForm.getId());
				dbDao.insert(agentinsert);
			}
			if (!Util.isEmpty(upcompany)) {
				dbDao.delete(upcompany);
			}
		}
		TCompanyEntity company = this.fetch(updateForm.getId());
		//修改公司信息
		updateForm.setCreatetime(company.getCreatetime());
		updateForm.setLastupdatetime(new Date());

		//查询上游公司(代理商公司)之前的功能
		Integer comType = updateForm.getComType();//得到公司类型
		if (!Util.isEmpty(comType) && comType == CompanyTypeEnum.UPCOMPANY.intKey()) {
			authorityPublicService.companyUpdateFunction(updateForm);
		} else if (!Util.isEmpty(comType) && comType == CompanyTypeEnum.AGENT.intKey()) {
			authorityPublicService.DlscompanyUpdateFunction(updateForm);
		}
		return this.update(updateForm);
	}

	/**
	 * 
	 * 验证公司名称唯一
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comName
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> validatorCompanyName(String comName) {
		Map<String, Object> result = new HashMap<String, Object>();
		TCompanyEntity companyEntity = dbDao.fetch(TCompanyEntity.class, Cnd.where("comName", "=", comName));
		if (Util.isEmpty(companyEntity)) {
			result.put("valid", true);
		} else {
			result.put("valid", false);
		}
		// TODO Auto-generated method stub
		return result;

	}

	/**
	 * 移除员工
	 * 使员工离职
	 *
	 * @param id
	 * @return 使员工离职
	 */
	public Object removeUser(long id) {
		TUserEntity user = dbDao.fetch(TUserEntity.class, id);
		user.setStatus(2);
		return dbDao.update(user);

	}
}
