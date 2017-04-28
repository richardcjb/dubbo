/**
 * InternationalInvoiceModule.java
 * com.linyun.airline.admin.order.international.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.internationalinvoice.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.invoicemanage.internationalinvoice.form.InternationalKaiSqlForm;
import com.linyun.airline.admin.invoicemanage.internationalinvoice.form.InternationalShouSqlForm;
import com.linyun.airline.admin.invoicemanage.internationalinvoice.service.InternationalInvoiceInfoService;

/**
 * @author   崔建斌
 * @Date	 2017年3月30日 	 
 */
@IocBean
@At("/admin/invoicemanage/internationalinvoice")
public class InternationalInvoiceInfoModule {

	@Inject
	private InternationalInvoiceInfoService internationalInvoiceInfoService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final InternationalKaiSqlForm sqlForm, @Param("..") final Pager pager,
			final HttpSession session) {
		return internationalInvoiceInfoService.getIssuerBycompany(session);
	}

	/**
	 * 开发票列表页面
	 */
	@At
	@POST
	public Object listKaiInvoiceData(@Param("..") InternationalKaiSqlForm sqlForm, HttpServletRequest request) {
		return internationalInvoiceInfoService.listKaiInvoiceData(sqlForm, request);
	}

	/**
	 * 收发票列表页面
	 */
	@At
	@POST
	public Object listShouInvoiceData(@Param("..") InternationalShouSqlForm sqlForm, HttpServletRequest request) {
		return internationalInvoiceInfoService.listShouInvoiceData(sqlForm, request);
	}

	/**
	 * 打开开发票编辑页面
	 */
	@At
	@Ok("jsp")
	public Object kaiInterOpenInvoice(HttpServletRequest request) {
		return internationalInvoiceInfoService.kaiInterOpenInvoice(request);
	}

	/**
	 * 打开收发票编辑页面
	 */
	@At
	@Ok("jsp")
	public Object shouInterOpenInvoice(HttpServletRequest request) {
		return internationalInvoiceInfoService.shouInterOpenInvoice(request);
	}

	/**
	 * 保存开发票数据
	 */
	@At
	@POST
	public Object saveKaiInvoiceInfo(HttpServletRequest request) {
		return internationalInvoiceInfoService.saveKaiInvoiceInfo(request);
	}

	/**
	 * 保存收发票数据
	 */
	@At
	@POST
	public Object saveShouInvoiceInfo(HttpServletRequest request) {
		return internationalInvoiceInfoService.saveShouInvoiceInfo(request);
	}
}
