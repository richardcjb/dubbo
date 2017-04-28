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

import com.linyun.airline.admin.receivePayment.form.inter.InterPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.TSaveInterPayAddFrom;
import com.linyun.airline.admin.receivePayment.form.inter.TUpdateInterPayAddFrom;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;

@IocBean
@At("/admin/receivePay/inter")
public class InterReceivePayModule {

	private static final Log log = Logs.get();

	@Inject
	private InterReceivePayService interReceivePayService;

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
	public Object confirmReceive(@Param("interRecId") String interRecId, HttpSession session) {
		return interReceivePayService.toConfirmRec(interRecId, session);
	}

	/**
	 * 跳转到 确认付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmPay(@Param("orderIds") String orderIds, HttpSession session) {
		return interReceivePayService.toConfirmPay(orderIds, session);
	}

	/**
	 * 到编辑已付款
	 */
	@At
	@Ok("jsp")
	public Object editConfirmPay(HttpServletRequest request, HttpSession session) {
		return interReceivePayService.editConfirmPay(request, session);
	}

	/**
	 * 
	 * 编辑付款
	 */
	@At
	public Object updateInterPay(@Param("..") final TUpdateInterPayAddFrom form, HttpSession session) {
		return interReceivePayService.updateInterPay(form, session);
	}

	/**
	 * 根据银行id查询 银行卡
	 */
	@At
	public Object getCardNames(@Param("bankId") final Long bankId, HttpSession session) {
		//根据前端传过来的部门id查询出职位
		return interReceivePayService.getCardNames(bankId, session);
	}

	/**
	 * 根据银行名称 银行卡卡号
	 */
	@At
	public Object getCardNums(@Param("cardName") final String cardName, HttpSession session) {
		//根据前端传过来的部门id查询出职位
		return interReceivePayService.getCardNums(cardName, session);
	}

	/**
	 * 
	 *會計付款中   分页
	 */
	@At
	public Object interPayList(@Param("..") final InterPayListSearchSqlForm form, HttpSession session) {
		return interReceivePayService.listPayData(form, session);
	}

	/**
	 * 
	 *會計   已付款分页
	 */
	@At
	public Object interPayEdList(@Param("..") final InterPayEdListSearchSqlForm form, HttpSession session) {
		return interReceivePayService.listPayEdData(form, session);
	}

	/**
	 * 
	 * 确认付款
	 */
	@At
	public Object saveInterPay(@Param("..") final TSaveInterPayAddFrom form, HttpSession session) {
		return interReceivePayService.saveInterPay(form, session);
	}

	/**
	 * 
	 *會計收款分页
	 */
	@At
	public Object interRecList(@Param("..") final InterRecListSearchSqlForm form, HttpSession session,
			HttpServletRequest request) {
		return interReceivePayService.listRecData(form, session, request);
	}

	/**
	 * 
	 * 确认收款
	 */
	@At
	public Object saveInterRec(@Param("recIds") final String recId, @Param("orderIds") final String orderIds,
			@Param("orderStatus") final String orderStatus, HttpSession session) {
		return interReceivePayService.saveInterRec(recId, orderIds, orderStatus, session);
	}

	//水单上传 返回值文件存储地址
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("fileId") File file, HttpSession session) {
		return interReceivePayService.upload(file, session);
	}

}
