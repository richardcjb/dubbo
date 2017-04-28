package com.linyun.airline.admin.customneeds.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.customneeds.form.TCustomNeedsSqlForm;
import com.linyun.airline.admin.customneeds.service.CustomneedsViewService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerneedsEntity;
import com.linyun.airline.forms.TCustomerneedsAddForm;
import com.linyun.airline.forms.TCustomerneedsForm;
import com.linyun.airline.forms.TCustomerneedsUpdateForm;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/customneeds")
public class CustomneedsModule {

	private static final Log log = Logs.get();

	private static final String HUANHANG = "&#13;&#10;";

	@Inject
	private CustomneedsViewService customneedsViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCustomerneedsForm sqlParamForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCustomerneedsAddForm addForm, HttpSession session) {
		return customneedsViewService.addCustomNeedsInfo(addForm, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		TCustomerneedsEntity fetch = customneedsViewService.fetch(id);
		fetch.setRemark(fetch.getRemark().replace("\n", HUANHANG));
		return fetch;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerneedsUpdateForm updateForm, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		TCustomerneedsEntity fetch = customneedsViewService.fetch(updateForm.getId());
		updateForm.setOptime(fetch.getOptime());
		updateForm.setCompanyid(company.getId());
		return customneedsViewService.updateCustomNeedsInfo(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		customneedsViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		customneedsViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 查询客户需求列表数据
	 */
	@At
	public Object listData(@Param("..") final TCustomNeedsSqlForm sqlParamForm, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlParamForm.setCompanyid(company.getId());
		return customneedsViewService.listPage4Datatables(sqlParamForm);
	}

	/**
	 * 关闭客户需求信息
	 */
	@At
	@POST
	public Object closeCustomNeeds(@Param("id") long id) {
		return customneedsViewService.closeCustomNeeds(id);
	}

	/**
	 * 启用客户需求
	 */
	@At
	@POST
	public Object enableCustomNeeds(@Param("id") long id) {
		return customneedsViewService.enableCustomNeeds(id);
	}

	/**
	 * 导入Excel
	 */
	@At
	@POST
	@Ok("jsp")
	@AdaptBy(type = UploadAdaptor.class)
	public Object inportExcelData(@Param("excelFile") TempFile file, HttpSession session) {
		return customneedsViewService.inportExcelData(file, session);
	}

	/**
	 * 下载模板
	 * @throws Exception 
	 */
	@At
	@GET
	@Ok("json")
	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return customneedsViewService.downloadTemplate(request, response);
	}

	/**
	 * 导出客户需求Excel
	 */
	@At
	@GET
	@Ok("json")
	public Object exportCustomNeedsExcel(HttpServletResponse response,
			@Param("..") final TCustomNeedsSqlForm sqlParamForm, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlParamForm.setCompanyid(company.getId());
		return customneedsViewService.exportCustomNeedsExcel(response, sqlParamForm);
	}
}