package com.linyun.airline.admin.authority.authoritymanage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.authority.authoritymanage.form.JobDto;
import com.linyun.airline.admin.authority.authoritymanage.form.JobZnode;
import com.linyun.airline.admin.authority.authoritymanage.form.ZTreeNode;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.authority.job.form.DeptJobForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.entities.TComFunPosMapEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyFunctionMapEntity;
import com.linyun.airline.entities.TCompanyJobEntity;
import com.linyun.airline.entities.TDepartmentEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * @author   崔建斌
 * @Date	 2016年12月8日 	 
 */
@IocBean(name = "authorityViewService")
public class AuthorityViewService extends BaseService<DeptJobForm> {

	/**
	 * 新增部门职位或者修改部门职位时查询该公司的全部功能，如果传递了职位id，则查询职位的功能并设置选中
	 * @throws CloneNotSupportedException 
	 */
	public Map<String, Object> findCompanyFunctions(long jobId, final HttpSession session)
			throws CloneNotSupportedException {
		Map<String, Object> obj = new HashMap<String, Object>();

		List<TFunctionEntity> allModule = getCompanyFunctions(session);

		DeptJobForm deptJobForm = new DeptJobForm();
		if (jobId > 0) {
			allModule = getJobFunctionInfos(jobId, allModule);
			Sql sql = Sqls.create(sqlManager.get("deptJob_select"));
			sql.params().set("jobId", jobId);
			deptJobForm = DbSqlUtil.fetchEntity(dbDao, DeptJobForm.class, sql);
		}
		obj.put("list", allModule);
		obj.put("dept", deptJobForm);
		return obj;
	}

	public List<TFunctionEntity> getCompanyFunctions(final HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql comFunSql = Sqls.fetchEntity(sqlManager.get("company_function"));
		comFunSql.params().set("comId", companyId);
		List<TFunctionEntity> allModule = DbSqlUtil.query(dbDao, TFunctionEntity.class, comFunSql);
		//排序functionMap
		Collections.sort(allModule, new Comparator<TFunctionEntity>() {
			@Override
			public int compare(TFunctionEntity tf1, TFunctionEntity tf2) {
				if (!Util.isEmpty(tf1.getSort()) && !Util.isEmpty(tf2.getSort())) {
					if (tf1.getSort() > tf2.getSort()) {
						return 1;
					} else if (tf1.getSort() == tf2.getSort()) {
						return 0;
					} else if (tf1.getSort() < tf2.getSort()) {
						return -1;
					}
				}
				return 0;
			}
		});
		return allModule;
	}

	private List<TFunctionEntity> getJobFunctionInfos(long jobId, final List<TFunctionEntity> allModule) {
		Sql comFuncSql = Sqls.create(sqlManager.get("job_function"));
		comFuncSql.params().set("jobId", jobId);

		//每一个职位的功能不一样，每次必须是一个新的功能集合
		List<TFunctionEntity> newFunctions = new ArrayList<TFunctionEntity>();
		for (TFunctionEntity srcFunc : allModule) {
			TFunctionEntity dest = new TFunctionEntity();
			BeanUtil.copyProperties(srcFunc, dest);
			newFunctions.add(dest);
		}

		//根据职位查询关系
		List<TFunctionEntity> jobFuncs = DbSqlUtil.query(dbDao, TFunctionEntity.class, comFuncSql);
		//如果职位有功能
		if (!Util.isEmpty(jobFuncs)) {
			for (TFunctionEntity f : newFunctions) {
				if (jobFuncs.contains(f)) {
					f.setChecked("true");
				}
			}
		}
		return newFunctions;
	}

	/**更新部门职位权限的数据信息*/
	@Aop("txDb")
	public Object updateJobFunctions(DeptJobForm updateForm, Long deptId, final HttpSession session) {
		//校验
		TDepartmentEntity dept = dbDao.fetch(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		if (Util.isEmpty(dept)) {
			return JsonResult.error("部门不存在!");
		}

		//修改部门名称
		dbDao.update(TDepartmentEntity.class, Chain.make("deptName", updateForm.getDeptName()),
				Cnd.where("id", "=", updateForm.getDeptId()));

		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		String jobJson = updateForm.getJobJson();
		JobDto[] jobJsonArray = Json.fromJsonAsArray(JobDto.class, jobJson);
		//根据部门id查出此部门下面职位之前的数据
		String jobIds = "";
		List<TJobEntity> beforeJob = dbDao.query(TJobEntity.class, Cnd.where("deptId", "=", deptId), null);
		for (TJobEntity tJoblst : beforeJob) {
			Long jobId = tJoblst.getId();
			jobIds += String.valueOf(jobId) + ",";
		}
		if (!Util.isEmpty(jobIds)) {
			jobIds = jobIds.substring(0, jobIds.length() - 1);
		}
		//职位表欲更新为
		String afterjobIds = "";
		if (jobJsonArray.length >= 1) {
			for (JobDto jobDto : jobJsonArray) {
				Long jobId = jobDto.getJobId();
				afterjobIds += String.valueOf(jobId) + ",";
			}
		}
		if (!Util.isEmpty(afterjobIds)) {
			afterjobIds = afterjobIds.substring(0, afterjobIds.length() - 1);
		}
		List<TJobEntity> afterJob = null;
		if (!Util.isEmpty(afterjobIds)) {
			afterJob = dbDao.query(TJobEntity.class, Cnd.where("id", "in", afterjobIds), null);
		}
		dbDao.updateRelations(beforeJob, afterJob);

		//根据职位id查询出公司功能职位表之前的数据
		List<TComFunPosMapEntity> beforeComfunPos = dbDao.query(TComFunPosMapEntity.class,
				Cnd.where("jobId", "in", jobIds), null);
		//公司功能职位表欲更新为
		List<TComFunPosMapEntity> afterComfunPos = dbDao.query(TComFunPosMapEntity.class,
				Cnd.where("jobId", "in", afterjobIds), null);
		dbDao.updateRelations(beforeComfunPos, afterComfunPos);
		List<TCompanyJobEntity> beforeComJob = dbDao.query(TCompanyJobEntity.class, Cnd.where("comId", "=", companyId)
				.and("posid", "in", jobIds), null);
		//欲更新为
		List<TCompanyJobEntity> afterComJob = dbDao.query(TCompanyJobEntity.class, Cnd.where("comId", "=", companyId)
				.and("posid", "in", afterjobIds), null);
		dbDao.updateRelations(beforeComJob, afterComJob);
		if (!Util.isEmpty(jobJsonArray)) {
			for (JobDto jobDto : jobJsonArray) {
				saveOrUpdateSingleJob(dept.getId(), jobDto.getJobId(), companyId, jobDto.getJobName(),
						jobDto.getFunctionIds());
			}
		}

		return JsonResult.success("更新成功");
	}

	//保存数据
	@Aop("txDb")
	public Map<String, String> saveDeptJobData(DeptJobForm addForm, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		String jobJson = addForm.getJobJson();

		//1,先添加部门，拿到部门id
		Sql sql1 = Sqls.create(sqlManager.get("authoritymanage_companyDept"));
		sql1.params().set("deptName", addForm.getDeptName());
		sql1.params().set("comId", companyId);
		TDepartmentEntity newDept = DbSqlUtil.fetchEntity(dbDao, TDepartmentEntity.class, sql1);
		if (Util.isEmpty(newDept)) {
			newDept = new TDepartmentEntity();
			newDept.setComId(companyId);
			newDept.setDeptName(addForm.getDeptName());
			newDept = dbDao.insert(newDept);
		}
		//获取到部门id
		Long deptId = newDept.getId();
		JobDto[] jobJsonArray = Json.fromJsonAsArray(JobDto.class, jobJson);

		if (!Util.isEmpty(jobJsonArray)) {
			for (JobDto jobDto : jobJsonArray) {
				saveOrUpdateSingleJob(deptId, null, companyId, jobDto.getJobName(), jobDto.getFunctionIds());
			}
		}
		return JsonResult.success("添加成功!");
	}

	private void saveOrUpdateSingleJob(Long deptId, Long jobId, Long companyId, String jobName, String functionIds) {
		//1，判断操作类型，执行职位新增或者修改
		if (Util.isEmpty(jobId) || jobId <= 0) {
			//添加操作
			Sql sql = Sqls.create(sqlManager.get("authoritymanage_companyJob"));
			sql.params().set("comId", companyId);
			sql.params().set("jobName", jobName);
			TJobEntity newJob = DbSqlUtil.fetchEntity(dbDao, TJobEntity.class, sql);

			if (Util.isEmpty(newJob)) {
				//职位不存在则新增
				newJob = new TJobEntity();
				newJob.setName(jobName);
				newJob.setDeptId(deptId);
				newJob = dbDao.insert(newJob);
				jobId = newJob.getId();//得到职位id
				//该公司添加新的职位
				TCompanyJobEntity newComJob = new TCompanyJobEntity();
				newComJob.setComId(companyId);
				newComJob.setPosid(jobId);
				dbDao.insert(newComJob);
			} else {
				//如果职位名称已存在
				throw new IllegalArgumentException("该公司此职位已存在,无法添加,职位名称:" + jobName);
			}

		} else {
			//更新操作
			TJobEntity newJob = dbDao.fetch(TJobEntity.class, Cnd.where("id", "=", jobId));
			if (Util.isEmpty(newJob)) {
				throw new IllegalArgumentException("欲更新的职位不存在,jobId:" + jobId);
			}

			//判断该公司是否存在同名的其他职位
			Sql sql = Sqls.create(sqlManager.get("authoritymanage_companyJob_update"));
			sql.params().set("comId", companyId);
			sql.params().set("jobName", jobName);
			sql.params().set("jobId", jobId);
			TJobEntity existsJob = DbSqlUtil.fetchEntity(dbDao, TJobEntity.class, sql);

			if (!Util.isEmpty(existsJob)) {
				//如果职位名称已存在
				throw new IllegalArgumentException("该公司此职位已存在,无法修改,职位名称:" + jobName);
			}

			dbDao.update(TJobEntity.class, Chain.make("name", jobName), Cnd.where("id", "=", newJob.getId()));
		}

		//2，截取功能模块id，根据功能id和公司id查询出公司功能id，用公司功能id和职位id往公司功能职位表添加数据
		if (!Util.isEmpty(functionIds)) {
			Iterable<String> funcIdIter = Splitter.on(",").omitEmptyStrings().split(functionIds);
			String funcIds = Joiner.on(",").join(funcIdIter);

			List<TComFunPosMapEntity> before = dbDao.query(TComFunPosMapEntity.class, Cnd.where("jobId", "=", jobId),
					null);
			List<TCompanyFunctionMapEntity> comFucs = dbDao.query(TCompanyFunctionMapEntity.class,
					Cnd.where("comId", "=", companyId).and("funId", "IN", funcIds), null);
			//欲更新为
			List<TComFunPosMapEntity> after = Lists.newArrayList();
			for (TCompanyFunctionMapEntity cf : comFucs) {
				TComFunPosMapEntity newComFun = new TComFunPosMapEntity();
				newComFun.setJobId(jobId);
				newComFun.setCompanyFunId(Long.valueOf(cf.getId()));
				after.add(newComFun);
			}
			dbDao.updateRelations(before, after);
		}
	}

	//校验部门名称唯一性
	public Object checkDeptNameExist(final String deptName, final Long deptId, final HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		int count = 0;
		if (Util.isEmpty(deptId)) {
			//add
			count = nutDao.count(TDepartmentEntity.class,
					Cnd.where("deptName", "=", deptName).and("comId", "=", companyId));
		} else {
			//update
			count = nutDao.count(TDepartmentEntity.class, Cnd.where("deptName", "=", deptName).and("id", "!=", deptId)
					.and("comId", "=", companyId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//校验职位名称唯一性
	public Object checkJobNameExist(final String jobName, final Long jobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(jobId)) {
			//add
			count = nutDao.count(TJobEntity.class, Cnd.where("name", "=", jobName));
		} else {
			//update
			count = nutDao.count(TJobEntity.class, Cnd.where("name", "=", jobName).and("id", "!=", jobId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//回显部门职位和职位功能
	public Object loadJobJosn(final Long deptId, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();

		//校验
		int deptCnt = nutDao.count(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		if (deptCnt <= 0) {
			return JsonResult.error("部门不存在!");
		}

		List<TJobEntity> jobList = dbDao.query(TJobEntity.class, Cnd.where("deptId", "=", deptId), null);
		List<TFunctionEntity> allModule = getCompanyFunctions(session);

		List<JobZnode> JobZnodes = Lists.newArrayList();
		for (TJobEntity job : jobList) {
			List<TFunctionEntity> functions = getJobFunctionInfos(job.getId(), allModule);

			JobZnode jn = new JobZnode();
			jn.setJobName(job.getName());
			jn.setJobId(job.getId());

			List<ZTreeNode> znodes = Lists.transform(functions, new Function<TFunctionEntity, ZTreeNode>() {
				//将TFunctionEntity转换为ZTreeNode
				@Override
				public ZTreeNode apply(TFunctionEntity f) {
					ZTreeNode n = new ZTreeNode();
					n.setId(f.getId());
					n.setPId(f.getParentId());
					n.setOpen(true);
					n.setName(f.getName());
					n.setChecked(f.getChecked());
					return n;
				}
			});//end of tansform
			String jsonNodes = Json.toJson(znodes);
			jn.setZnodes(jsonNodes);
			JobZnodes.add(jn);
		}

		TDepartmentEntity dept = dbDao.fetch(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		map.put("dept", dept);
		map.put("list", JobZnodes);
		map.put("zNodes", allModule);
		return map;
	}

	/**
	 * @param jobId
	 * @param session
	 * 删除职位,若此职位下面没有用户则可以删除;
	 * 若是有用户使用则不可能删除，需提示有哪个用户在使用
	 */
	@Aop("txDb")
	public Object deleteJob(final Long jobId, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		//查询出职位id,并查出该职位是否有用户正在使用
		Sql sql = Sqls.create(sqlManager.get("authoritymanage_delete_job"));
		sql.params().set("jobId", jobId);
		sql.params().set("jobStatus", UserJobStatusEnum.ON.intKey());
		sql.params().set("companyId", companyId);
		List<TUserEntity> listUser = DbSqlUtil.query(dbDao, TUserEntity.class, sql);
		//校验,若是该职位无用户使用，则可删除，反之亦然
		if (Util.isEmpty(listUser)) {
			//删除职位
			nutDao.clear(TCompanyJobEntity.class, Cnd.where("posid", "=", jobId));
			nutDao.clear(TComFunPosMapEntity.class, Cnd.where("jobId", "=", jobId));
			nutDao.delete(TJobEntity.class, jobId);
			return JsonResult.success("删除成功");
		} else {
			//返回此职位下的用户信息，提示不能删除
			return MobileResult.error("删除失败", listUser);
		}

	}
}