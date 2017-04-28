/**
 * InlandInvoiceModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.order.inland.form.KaiInvoiceParamForm;
import com.linyun.airline.admin.order.inland.form.ShouInvoiceParamForm;
import com.linyun.airline.admin.order.inland.service.InlandInvoiceService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月3日 	 
 */
@IocBean
@At("admin/inland")
public class InlandInvoiceModule {

	@Inject
	private InlandInvoiceService inlandInvoiceService;

	/**
	 * 保存付款发票信息
	 */
	@At
	@POST
	public Object saveInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveInvoiceInfo(request);
	}

	@At
	@POST
	public Object saveReceiveInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveReceiveInvoiceInfo(request);
	}

	/**
	 * 打开减免页面
	 */
	@At
	@Ok("jsp")
	public Object mitigate(HttpServletRequest request) {
		return inlandInvoiceService.mitigate(request);
	}

	/**
	 * 保存减免信息
	 */
	@At
	@POST
	public Object saveMitigateData(HttpServletRequest request) {
		return inlandInvoiceService.saveMitigateData(request);
	}

	/**
	 * 开发票列表
	 */
	@At
	@POST
	public Object listKaiInvoiceData(@Param("..") KaiInvoiceParamForm paramForm, HttpServletRequest request) {
		return inlandInvoiceService.listKaiInvoiceData(paramForm, request);
	}

	/**
	 * 收发票列表
	 */
	@At
	@POST
	public Object listShouInvoiceData(@Param("..") ShouInvoiceParamForm paramForm, HttpServletRequest request) {
		return inlandInvoiceService.listShouInvoiceData(paramForm, request);
	}

	/**
	 * 打开开发票页面
	 */
	@At
	@Ok("jsp")
	public Object kaiInvoice(HttpServletRequest request) {
		return inlandInvoiceService.kaiInvoice(request);
	}

	/**
	 * 打开收发票页面
	 */
	@At
	@Ok("jsp")
	public Object shouInvoice(HttpServletRequest request) {
		return inlandInvoiceService.shouInvoice(request);
	}

	/**
	 * 保存开发票数据
	 */
	@At
	@POST
	public Object saveKaiInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveKaiInvoiceInfo(request);
	}

	/**
	 * 保存收发票数据
	 */
	@At
	@POST
	public Object saveShouInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveShouInvoiceInfo(request);
	}

	/**
	 * 加载日志
	 */
	@At
	@POST
	public Object loadOrderLog(HttpServletRequest request) {
		return inlandInvoiceService.loadOrderLog(request);
	}

	/**
	 * 检验是否是收款是否是同一个公司
	 */
	@At
	@POST
	public Object checkIsCommonCompany(HttpServletRequest request) {
		return inlandInvoiceService.checkIsCommonCompany(request);
	}

	/**
	 * 检验是否是付款是否是同一个公司
	 */
	@At
	@POST
	public Object checkPayIsCommonCompany(HttpServletRequest request) {
		return inlandInvoiceService.checkPayIsCommonCompany(request);
	}

	/**
	 * 上传发票
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadInvoice(@Param("image") File file, HttpServletRequest request) {
		return inlandInvoiceService.uploadInvoice(file, request);
	}

	/**
	 * 加载减免信息
	 */
	@At
	@POST
	public Object loadJianMianAccount(HttpServletRequest request) {
		return inlandInvoiceService.loadJianMianAccount(request);
	}
}
