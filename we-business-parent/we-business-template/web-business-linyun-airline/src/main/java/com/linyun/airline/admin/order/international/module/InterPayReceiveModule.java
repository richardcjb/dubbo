/**
 * InterPayReceiveModule.java
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

import com.linyun.airline.admin.order.international.form.InterPaymentSqlForm;
import com.linyun.airline.admin.order.international.form.InterReceiptSqlForm;
import com.linyun.airline.admin.order.international.service.InterPayReceiveService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@IocBean
@At("/admin/international/payreceive")
public class InterPayReceiveModule {

	@Inject
	private InterPayReceiveService interPayReceiveService;

	/**
	 * 收付款收款列表
	 */
	@At
	@POST
	public Object listShouFuKuanData(@Param("..") InterReceiptSqlForm sqlForm, HttpServletRequest request) {
		return interPayReceiveService.listShouFuKuanData(sqlForm, request);
	}

	/**
	 * 国际收付款付款列表数据
	 */
	@At
	@POST
	public Object listFuKuanData(@Param("..") InterPaymentSqlForm sqlForm, HttpServletRequest request) {
		return interPayReceiveService.listFuKuanData(sqlForm, request);
	}

	/**
	 *打开开发票页面 
	 */
	@At
	@Ok("jsp")
	public Object openInvoice(HttpServletRequest request) {
		return interPayReceiveService.openInvoice(request);
	}

	/**
	 * 保存开发票信息
	 */
	@At
	@POST
	public Object saveOpenInvoiceInfo(HttpServletRequest request) {
		return interPayReceiveService.saveOpenInvoiceInfo(request);
	}

	/**
	 * 打开收发票页面
	 */
	@At
	@Ok("jsp")
	public Object receiveInvoice(HttpServletRequest request) {
		return interPayReceiveService.receiveInvoice(request);
	}

	/**
	 * 保存收发票信息
	 */
	@At
	@POST
	public Object saveInvoiceInfo(HttpServletRequest request) {
		return interPayReceiveService.saveInvoiceInfo(request);
	}

}
