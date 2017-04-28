package com.linyun.airline.admin.authority.companyfunction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.authority.companyfunction.form.CompanyFuctionForm;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TComFunPosMapEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyFunctionMapEntity;
import com.linyun.airline.entities.TCompanyJobEntity;
import com.linyun.airline.entities.TUserJobEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "companyAuthorityService")
public class CompanyAuthorityService extends BaseService<TCompanyEntity> {

	/**
	 * 根据请求路径查询功能
	 * @param requestPath
	 * @return
	 */
	public TFunctionEntity findFuctionByRequestPath(String requestPath) {
		return dbDao.fetch(TFunctionEntity.class, Cnd.where("url", "LIKE", "%" + requestPath + "%"));
	}

	public Map<String, Object> findCompany(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//全部功能
		List<TFunctionEntity> allFunc = dbDao.query(TFunctionEntity.class, null, null);
		//根据公司查询关系
		List<TCompanyFunctionMapEntity> relation = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", id), null);
		//如果公司有功能
		if (!Util.isEmpty(relation)) {
			//公司功能集合
			List<TFunctionEntity> companyFuncs = Lists.transform(relation,
					new Function<TCompanyFunctionMapEntity, TFunctionEntity>() {
						@Override
						public TFunctionEntity apply(TCompanyFunctionMapEntity arg) {
							TFunctionEntity func = new TFunctionEntity();
							func.setId(arg.getFunId());
							return func;
						}
					});
			for (TFunctionEntity f : allFunc) {
				if (companyFuncs.contains(f)) {
					f.setChecked("true");
				}
			}
		}
		obj.put("list", allFunc);
		obj.put("company", dbDao.fetch(TCompanyEntity.class, id));
		return obj;
	}

	/**
	 * 
	 * 获取上游公司和代理商的数量
	 * <p>
	 * TODO
	 *
	 * @param sqlManagerm
	 * @return 返回上游公司、代理商、总数 封装到map中
	 */
	public Map<String, Object> getUpCompanyAndAgentCount(SqlManager sqlManagerm) {
		Map<String, Object> map = MapUtil.map();
		long upconpany = getCompanyCount(CompanyTypeEnum.UPCOMPANY.intKey());
		long agent = getCompanyCount(CompanyTypeEnum.AGENT.intKey());
		map.put("upconpany", upconpany);
		map.put("agent", agent);
		map.put("totalcompany", upconpany + agent);
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
	private long getCompanyCount(int comType) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		sql.setCondition(cnd);
		return Daos.queryCount(nutDao, sql.toString());
	}

	public Object updateComFunctions(CompanyFuctionForm updateForm) {
		updateCompanyFunctionMap(updateForm);
		return JsonResult.success("更新成功");
	}

	/**修改公司功能关系*/
	private void updateCompanyFunctionMap(CompanyFuctionForm form) {
		String functionIds = form.getFunctionIds();
		//根据公司查询关系
		Long comId = form.getCompanyId();//得到公司id
		//根据公司id查询出管理员账号id
		TCompanyEntity singleCom = dbDao.fetch(TCompanyEntity.class, Cnd.where("id", "=", comId));
		Long adminId = singleCom.getAdminId();//得到管理员账号id
		//根据管理员账号id查询用户就职表
		TUserJobEntity singleUserJob = dbDao.fetch(TUserJobEntity.class, Cnd.where("userid", "=", adminId));
		Long companyJobId = singleUserJob.getCompanyJobId();//得到公司职位id
		//根据公司职位id查询公司职位表
		TCompanyJobEntity singleComJob = dbDao.fetch(TCompanyJobEntity.class, Cnd.where("id", "=", companyJobId));
		Long posId = singleComJob.getPosid();//得到职位id

		List<TCompanyFunctionMapEntity> before = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", comId), null);
		//欲更新为
		List<TCompanyFunctionMapEntity> after = new ArrayList<TCompanyFunctionMapEntity>();
		if (!Util.isEmpty(functionIds)) {
			Iterable<String> funcIds = Splitter.on(",").omitEmptyStrings().split(functionIds);
			for (String fid : funcIds) {
				long funcId = Long.valueOf(fid);
				if (funcId <= 0) {
					continue;
				}
				TCompanyFunctionMapEntity each = new TCompanyFunctionMapEntity();
				each.setComId(comId);
				each.setFunId(funcId);
				after.add(each);
			}
		}
		dbDao.updateRelations(before, after);
		List<TCompanyFunctionMapEntity> allComFun = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", comId), null);
		//截取功能模块id,根据功能id和公司id查询出公司功能id
		if (allComFun.size() > 0) {
			List<TComFunPosMapEntity> beforeComFun = dbDao.query(TComFunPosMapEntity.class,
					Cnd.where("jobId", "=", posId), null);
			//欲更新为
			List<TComFunPosMapEntity> afterComFun = Lists.newArrayList();
			for (TCompanyFunctionMapEntity tComFunMap : allComFun) {
				TComFunPosMapEntity funpos = new TComFunPosMapEntity();
				funpos.setJobId(posId);
				funpos.setCompanyFunId(tComFunMap.getId());
				afterComFun.add(funpos);
			}
			dbDao.updateRelations(beforeComFun, afterComFun);
		}

	}
}