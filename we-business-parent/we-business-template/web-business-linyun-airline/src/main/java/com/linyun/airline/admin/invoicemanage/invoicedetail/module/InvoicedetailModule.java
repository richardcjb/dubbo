package com.linyun.airline.admin.invoicemanage.invoicedetail.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.invoicemanage.invoicedetail.form.TInvoiceDetailAddForm;
import com.linyun.airline.admin.invoicemanage.invoicedetail.form.TInvoiceDetailSqlForm;
import com.linyun.airline.admin.invoicemanage.invoicedetail.form.TInvoiceDetailUpdateForm;
import com.linyun.airline.admin.invoicemanage.invoicedetail.service.InvoicedetailViewService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/invoicemanage/invoicemanage")
public class InvoicedetailModule {

	@Inject
	private InvoicedetailViewService invoicemanageViewService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TInvoiceDetailSqlForm sqlForm, @Param("..") final Pager pager) {
		return invoicemanageViewService.listPage(sqlForm, null);
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
	public Object add(@Param("..") TInvoiceDetailAddForm addForm) {
		return invoicemanageViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return invoicemanageViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TInvoiceDetailUpdateForm updateForm) {
		return invoicemanageViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		invoicemanageViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		invoicemanageViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}