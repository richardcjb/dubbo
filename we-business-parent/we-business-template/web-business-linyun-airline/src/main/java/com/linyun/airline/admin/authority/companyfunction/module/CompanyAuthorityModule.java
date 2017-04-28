package com.linyun.airline.admin.authority.companyfunction.module;

import org.nutz.dao.SqlManager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.Company.form.TCompanySqlForm;
import com.linyun.airline.admin.authority.companyfunction.form.CompanyFuctionForm;
import com.linyun.airline.admin.authority.companyfunction.service.CompanyAuthorityService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/authority/companyfunction")
public class CompanyAuthorityModule {

	@Inject
	private CompanyAuthorityService companyAuthorityService;
	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private SqlManager sqlManager;

	/**
	 * 公司权限配置页面
	 */
	@At
	@Ok("jsp")
	public Object companyList() {
		return companyAuthorityService.getUpCompanyAndAgentCount(sqlManager);
	}

	//跳转到公司全选设置界面
	@At
	@GET
	@Ok("jsp")
	public Object companyUpdate(@Param("id") final long id) {
		return companyAuthorityService.findCompany(id);
	}

	/**
	 * 执行'权限分配'
	 */
	@At
	@POST
	public Object update(@Param("..") CompanyFuctionForm updateForm) {
		try {
			companyAuthorityService.updateComFunctions(updateForm);
			return JsonResult.success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("修改失败");
		}
	}

	/**
	 * 分页查询公司信息
	 */
	@At
	public Object listData(@Param("..") final TCompanySqlForm paramForm) {
		return companyAuthorityService.listPage4Datatables(paramForm);
	}
}
