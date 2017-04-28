/**
 * ReceivePayModule.java
 * com.linyun.airline.admin.receivePayment.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.receivePayment.form.inland.InlandPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.InlandRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.TSaveInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.form.inland.TUpdateInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.service.ReceivePayService;

@IocBean
@At("/admin/receivePay/inland")
public class ReceivePayModule {

	private static final Log log = Logs.get();

	@Inject
	private ReceivePayService receivePayService;

	private Long payCurrency;

	/**
	 * 跳转到 收付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object receivePayment() {
		return null;
	}

	/**
	 * 跳转到 确认收款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmReceive(@Param("inlandRecId") String inlandRecId, HttpSession session) {
		return receivePayService.toConfirmRec(inlandRecId, session);
	}

	/**
	 * 跳转到 确认付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmPay(@Param("inlandPayIds") String inlandPayIds, HttpSession session) {
		return receivePayService.toConfirmPay(inlandPayIds, session);
	}

	/**
	 * 根据银行id查询 银行卡
	 */
	@At
	public Object getCardNames(@Param("bankId") final Long bankId, HttpSession session) {
		//根据前端传过来的部门id查询出职位
		return receivePayService.getCardNames(bankId, session);
	}

	/**
	 * 根据银行名称 银行卡卡号
	 */
	@At
	public Object getCardNums(@Param("cardName") final String cardName, HttpSession session) {
		//根据前端传过来的部门id查询出职位
		return receivePayService.getCardNums(cardName, session);
	}

	/**
	 * 
	 *會計付款中   分页
	 */
	@At
	public Object inlandPayList(@Param("..") final InlandPayListSearchSqlForm form, HttpSession session) {
		return receivePayService.listPayData(form, session);
	}

	/**
	 * 
	 *會計   已付款分页
	 */
	@At
	public Object inlandPayEdList(@Param("..") final InlandPayEdListSearchSqlForm form, HttpSession session) {
		return receivePayService.listPayEdData(form, session);
	}

	/**
	 * 
	 * 确认付款
	 */
	@At
	public Object saveInlandPay(@Param("..") final TSaveInlandPayAddFrom form, HttpSession session) {
		return receivePayService.saveInlandPay(form, session);
	}

	/**
	 * 到编辑已付款
	 */
	@At
	@Ok("jsp")
	public Object editConfirmPay(HttpServletRequest request, HttpSession session) {
		return receivePayService.editConfirmPay(request, session);
	}

	/**
	 * 
	 * 编辑付款
	 */
	@At
	public Object updateInlandPay(@Param("..") final TUpdateInlandPayAddFrom form, HttpSession session) {
		return receivePayService.updateInlandPay(form, session);
	}

	/**
	 * 
	 *會計收款分页
	 */
	@At
	public Object inlandRecList(@Param("..") final InlandRecListSearchSqlForm form, HttpSession session,
			HttpServletRequest request) {
		return receivePayService.listRecData(form, session, request);
	}

	/**
	 * 
	 * 确认收款
	 */
	@At
	public Object saveInlandRec(@Param("id") final String recId, HttpSession session) {
		return receivePayService.saveInlandRec(recId, session);
	}

	//水单上传 返回值文件存储地址
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("fileId") File file, HttpSession session) {
		return receivePayService.upload(file, session);
	}

}
