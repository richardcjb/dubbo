/**
 * InternationalInvoiceModule.java
 * com.linyun.airline.admin.order.international.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.order.international.form.InternationalKaiListForm;
import com.linyun.airline.admin.order.international.form.InternationalShouListForm;
import com.linyun.airline.admin.order.international.service.InternationalInvoiceService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月19日 	 
 */
@IocBean
@At("/admin/international/invoice")
public class InternationalInvoiceModule {

	@Inject
	private InternationalInvoiceService internationalInvoiceService;

	/**
	 * 开发票列表数据
	 */
	@At
	@POST
	public Object listKaiInvoiceData(@Param("..") InternationalKaiListForm sqlForm, HttpServletRequest request) {
		return internationalInvoiceService.listKaiInvoiceData(sqlForm, request);
	}

	/**
	 * 收发票页面
	 */
	@At
	@POST
	public Object listShouInvoiceData(@Param("..") InternationalShouListForm sqlForm, HttpServletRequest request) {
		return internationalInvoiceService.listShouInvoiceData(sqlForm, request);
	}

	/**
	 * 打开开发票编辑页面
	 */
	@At
	@Ok("jsp")
	public Object kaiInvoice(HttpServletRequest request) {
		return internationalInvoiceService.kaiInvoice(request);
	}

	/**
	 * 打开收发票编辑页面
	 */
	@At
	@Ok("jsp")
	public Object shouInvoice(HttpServletRequest request) {
		return internationalInvoiceService.shouInvoice(request);
	}
}
