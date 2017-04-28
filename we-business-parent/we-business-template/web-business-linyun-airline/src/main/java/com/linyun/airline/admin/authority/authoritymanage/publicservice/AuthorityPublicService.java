/**
 * AuthorityPublicService.java
 * com.linyun.airline.admin.authority.authoritymanage.publicservice
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.authoritymanage.publicservice;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyFunctionMapEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 权限管理对外接口
 * @author   崔建斌
 * @Date	 2017年3月13日 	 
 */
@IocBean
public class AuthorityPublicService extends BaseService<TCompanyFunctionMapEntity> {

	//新增上游公司后给公司配置功能
	public boolean companyFunction(TCompanyAddForm addForm) {
		//通过session获取公司的id
		//TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long adminId = addForm.getAdminId();//得到公司管理员的id
		//根据公司id查询出是上游公司还是代理商
		TCompanyEntity fetchType = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", adminId));
		long companyId = fetchType.getId();//得到公司id
		String comType = fetchType.getComType();//得到公司类型
		int parseComType = Integer.parseInt(comType);//类型转换
		//上游公司功能ID
		int[] function = { 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62, 66, 68, 69, 75, 79,
				80, 81, 84, 85, 86, 87, 88, 89, 90, 91, 94, 95, 96, 97 };
		List<TCompanyFunctionMapEntity> functionList = Lists.newArrayList();
		if (parseComType == CompanyTypeEnum.UPCOMPANY.intKey()) {
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		}
		List<TCompanyFunctionMapEntity> insert = dbDao.insert(functionList);
		return !Util.isEmpty(insert);
	}

	//编辑上游公司后给公司配置功能
	public boolean companyUpdateFunction(TCompanyUpdateForm updateForm) {
		//通过session获取公司的id
		//TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long adminId = updateForm.getAdminId();//得到公司管理员的id
		//根据公司id查询出是上游公司还是代理商
		TCompanyEntity fetchType = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", adminId));
		long companyId = fetchType.getId();//得到公司id
		String comType = fetchType.getComType();//得到公司类型
		int parseComType = Integer.parseInt(comType);//类型转换
		//上游公司功能ID
		int[] function = { 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62, 66, 68, 69, 75, 79,
				80, 81, 84, 85, 86, 87, 88, 89, 90, 91, 94, 95, 96, 97 };
		List<TCompanyFunctionMapEntity> before = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", companyId), null);
		List<TCompanyFunctionMapEntity> functionList = Lists.newArrayList();
		if (parseComType == CompanyTypeEnum.UPCOMPANY.intKey()) {
			if (!Util.isEmpty(before)) {
				dbDao.delete(before);
			}
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		} else if (parseComType == CompanyTypeEnum.AGENT.intKey()) {
			if (!Util.isEmpty(before)) {
				dbDao.delete(before);
			}
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		}
		List<TCompanyFunctionMapEntity> insert = dbDao.insert(functionList);
		return !Util.isEmpty(insert);
	}

	//新增代理商后给公司配置功能
	public boolean DlsCompanyFunction(TCompanyAddForm addForm) {
		//通过session获取公司的id
		//TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long adminId = addForm.getAdminId();//得到公司管理员的id
		//根据公司id查询出是上游公司还是代理商
		TCompanyEntity fetchType = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", adminId));
		long companyId = fetchType.getId();//得到公司id
		String comType = fetchType.getComType();//得到公司类型
		int parseComType = Integer.parseInt(comType);//类型转换
		//上游公司功能ID
		int[] function = { 43, 44, 46, 51 };
		List<TCompanyFunctionMapEntity> functionList = Lists.newArrayList();
		if (parseComType == CompanyTypeEnum.AGENT.intKey()) {
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		}
		List<TCompanyFunctionMapEntity> insert = dbDao.insert(functionList);
		return !Util.isEmpty(insert);
	}

	//编辑代理商后给公司配置功能
	public boolean DlscompanyUpdateFunction(TCompanyUpdateForm updateForm) {
		//通过session获取公司的id
		//TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long adminId = updateForm.getAdminId();//得到公司管理员的id
		//根据公司id查询出是上游公司还是代理商
		TCompanyEntity fetchType = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", adminId));
		long companyId = fetchType.getId();//得到公司id
		String comType = fetchType.getComType();//得到公司类型
		int parseComType = Integer.parseInt(comType);//类型转换
		//上游公司功能ID
		int[] function = { 43, 44, 46, 51 };
		List<TCompanyFunctionMapEntity> before = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", companyId), null);
		List<TCompanyFunctionMapEntity> functionList = Lists.newArrayList();
		if (parseComType == CompanyTypeEnum.AGENT.intKey()) {
			if (!Util.isEmpty(before)) {
				dbDao.delete(before);
			}
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		} else if (parseComType == CompanyTypeEnum.UPCOMPANY.intKey()) {
			if (!Util.isEmpty(before)) {
				dbDao.delete(before);
			}
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		}
		List<TCompanyFunctionMapEntity> insert = dbDao.insert(functionList);
		return !Util.isEmpty(insert);
	}
}
