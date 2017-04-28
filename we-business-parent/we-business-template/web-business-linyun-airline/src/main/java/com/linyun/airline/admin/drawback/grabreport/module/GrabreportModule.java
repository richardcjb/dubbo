package com.linyun.airline.admin.drawback.grabreport.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportSqlForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportUpdateForm;
import com.linyun.airline.admin.drawback.grabreport.service.GrabreportViewService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/drawback/grabreport")
public class GrabreportModule {

	@Inject
	private GrabreportViewService grabreportViewService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */

	@At
	@Ok("jsp")
	public Object list(@Param("..") final TGrabReportSqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TGrabReportSqlForm sqlForm) {
		return grabreportViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object filePreview(@Param("id") long pid) {
		return grabreportViewService.addFilePreview(pid);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TGrabReportAddForm addForm) {
		return grabreportViewService.saveFilePreview(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return grabreportViewService.updateFilePreview(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabReportUpdateForm updateForm) {
		return grabreportViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		grabreportViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		grabreportViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}