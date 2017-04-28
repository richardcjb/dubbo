/**
 * InternationalModule.java
 * com.linyun.airline.admin.order.international.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.module;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.order.international.form.InternationalParamForm;
import com.linyun.airline.admin.order.international.form.InternationalPayParamForm;
import com.linyun.airline.admin.order.international.form.InternationalReceiveParamForm;
import com.linyun.airline.admin.order.international.service.InternationalService;
import com.linyun.airline.entities.TPayReceiveRecordEntity;
import com.uxuexi.core.common.util.EnumUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月15日 	 
 */
@IocBean
@At("/admin/international")
public class InternationalModule {

	@Inject
	private InternationalService internationalService;

	/**
	 * 跳转到国际列表页
	 */
	@At
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		return null;
	}

	/**
	 * 国际列表数据
	 */
	@At
	@POST
	public Object internationalListData(@Param("..") InternationalParamForm paramForm, HttpServletRequest request) {
		return internationalService.internationalListData(paramForm, request);
	}

	/**
	 * 国际订单收款列表数据
	 */
	@At
	@POST
	public Object internationalReceiveListData(@Param("..") InternationalReceiveParamForm paramForm,
			HttpServletRequest request) {
		return internationalService.internationalReceiveListData(paramForm, request);
	}

	/**
	 * 国际订单付款列表数据
	 */
	@At
	@POST
	public Object internationalPayListData(@Param("..") InternationalPayParamForm paramForm, HttpServletRequest request) {
		return internationalService.internationalPayListData(paramForm, request);
	}

	/**
	 * 跳转到添加国际订单页面
	 */
	@At
	@Ok("jsp")
	public Object addInterOrder(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		return result;
	}

	/**
	 * 保存添加的订单
	 */
	@At
	@POST
	public Object saveAddOrder(HttpServletRequest request) {
		return internationalService.saveAddOrder(request);
	}

	/**
	 * 跳转到详情页
	 */
	@At
	@Ok("jsp")
	public Object internationalDetail(HttpServletRequest request) {
		return internationalService.internationalDetail(request);
	}

	/**
	 * 保存国际订单详情数据
	 */
	@At
	@POST
	public Object saveInternationalDetail(HttpServletRequest request) {
		return internationalService.saveInternationalDetail(request);
	}

	/**
	 * 跳转到添加航段页面
	 */
	@At
	@Ok("jsp")
	public Object addAirinfo(HttpServletRequest request) {
		return internationalService.addAirinfo(request);
	}

	/**
	 * 保存航段信息
	 */
	@At
	@POST
	public Object saveAirinfo(HttpServletRequest request) {
		return internationalService.saveAirinfo(request);
	}

	/**
	 * 跳转到编辑航段信息页面
	 */
	@At
	@Ok("jsp")
	public Object editAirinfo(HttpServletRequest request) {
		return internationalService.editAirinfo(request);
	}

	/**
	 * 保存编辑航段信息
	 */
	@At
	@POST
	public Object saveEditAirinfo(HttpServletRequest request) {
		return internationalService.saveEditAirinfo(request);
	}

	/**
	 * 上传游客信息
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadVisitorInfo(@Param("excelfile") File file, @Param("pnrid") Integer pnrid,
			HttpServletRequest request) {
		return internationalService.uploadVisitorInfo(file, pnrid, request);
	}

	/**
	 * 游客信息
	 */
	@At
	@Ok("jsp")
	public Object visitorInfo(HttpServletRequest request) {
		return internationalService.visitorInfo(request);
	}

	/**
	 * 跳转到添加预收款款记录页面
	 */
	@At
	@Ok("jsp")
	public Object addReceiveRecord(HttpServletRequest request) {
		return internationalService.addRecord(request);
	}

	/**
	 * 跳转到添加预付款款记录页面
	 */
	@At
	@Ok("jsp")
	public Object addPayRecord(HttpServletRequest request) {
		return internationalService.addRecord(request);
	}

	/**
	 * 保存记录
	 */
	@At
	@POST
	public Object saveRecord(HttpServletRequest request, @Param("..") TPayReceiveRecordEntity payreceive) {
		return internationalService.saveRecord(request, payreceive);
	}

	/**
	 * 跳转到编辑预收款记录页面
	 */
	@At
	@Ok("jsp")
	public Object editReceiveRecord(HttpServletRequest request) {
		return internationalService.editRecord(request);
	}

	/**
	 * 跳转到编辑预付款记录页面
	 */
	@At
	@Ok("jsp")
	public Object editPayRecord(HttpServletRequest request) {
		return internationalService.editRecord(request);
	}

	/**
	 * 保存编辑记录
	 */
	@At
	@POST
	public Object saveEditRecord(HttpServletRequest request, @Param("..") TPayReceiveRecordEntity payreceive) {
		return internationalService.saveEditRecord(request, payreceive);
	}

	/**
	 * 加载详情页面航程信息
	 */
	@At
	@POST
	public Object loadAirlineInfo(HttpServletRequest request) {
		return internationalService.loadAirlineInfo(request);
	}

	/**
	 * 加载详情页面收付款记录信息
	 */
	@At
	@POST
	public Object loadPayReceiveRecord(HttpServletRequest request) {
		return internationalService.loadPayReceiveRecord(request);
	}

	/**
	 * 打开日志
	 */
	@At
	@Ok("jsp")
	public Object orderLog(HttpServletRequest request) {
		return null;
	}

	/**
	 * 打开提醒
	 */
	@At
	@Ok("jsp")
	public Object orderRemind(HttpServletRequest request) {
		return internationalService.orderRemind(request);
	}

	/**
	 * 编辑游客信息页面
	 */
	@At
	@Ok("jsp")
	public Object editVisitorInfo(HttpServletRequest request) {
		return internationalService.editVisitorInfo(request);
	}

	/**
	 * 保存编辑游客信息页面
	 */
	@At
	@POST
	public Object saveEditVisitorInfo(HttpServletRequest request) {
		return internationalService.saveEditVisitorInfo(request);
	}

	/**
	 * 打开退票页面
	 */
	@At
	@Ok("jsp")
	public Object backTicket(HttpServletRequest request) {
		return internationalService.backTicket(request);
	}

	/**
	 * 打开收款页面
	 */
	@At
	@Ok("jsp")
	public Object openReceipt(HttpServletRequest request) {
		return internationalService.openReceipt(request);
	}

	/**
	 * 保存收款信息
	 */
	@At
	@POST
	public Object saveReceipt(HttpServletRequest request) {
		return internationalService.saveReceipt(request);
	}

	/**
	 * 打开付款页面
	 */
	@At
	@Ok("jsp")
	public Object openPayment(HttpServletRequest request) {
		return internationalService.openPayment(request);
	}

	/**
	 * 保存付款信息
	 */
	@At
	@POST
	public Object savePayment(HttpServletRequest request) {
		return internationalService.savePayment(request);
	}

	/**
	 * 添加国际订单
	 */
	@At
	@POST
	public Object saveInterOrderInfo(HttpServletRequest request) {
		return internationalService.saveInterOrderInfo(request);
	}

	/**
	 * 保存消息提醒
	 */
	@At
	@POST
	public Object saveOrderRemindInfo(HttpServletRequest request) {
		return internationalService.saveOrderRemindInfo(request);
	}
}
