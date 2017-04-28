/**
 * InlandModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.order.inland.form.FuKuanParamForm;
import com.linyun.airline.admin.order.inland.form.InlandListSearchForm;
import com.linyun.airline.admin.order.inland.form.PayApplyListForm;
import com.linyun.airline.admin.order.inland.form.ShouKuanParamFrom;
import com.linyun.airline.admin.order.inland.service.InlandService;
import com.linyun.airline.common.enums.OrderStatusEnum;
import com.uxuexi.core.common.util.EnumUtil;

/**
 * TODO内陆跨海module
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月13日 	 
 */
@IocBean
@At("admin/inland")
public class InlandModule {

	@Inject
	private InlandService inlandService;

	/**
	 * 跳转到内陆跨海列表
	 */
	@At
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		return null;
	}

	/**
	 * 查询内陆跨海列表数据
	 */
	@At
	public Object listData(@Param("..") InlandListSearchForm form, HttpServletRequest request) {
		return inlandService.listData(form, request);
	}

	/**
	 * 跳转到内陆跨海添加页面
	 */
	@At
	@Ok("jsp")
	public Object addOrder(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderstatusenum", EnumUtil.enum2(OrderStatusEnum.class));
		return result;
	}

	/**
	 * 添加内陆跨海订单
	 */
	@At
	@POST
	public Object addOrderInfo(@Param("data") String data, HttpServletRequest request) {
		return inlandService.addOrderInfo(data, request);
	}

	/**
	 * 跳转到查询订单详情页面
	 */
	@At
	@Ok("jsp")
	public Object queryDetail(@Param("id") Integer id) {
		return inlandService.queryDetail(id);
	}

	/**
	 * 保存查询订单详情
	 */
	@At
	@POST
	public Object saveOrderInfo(@Param("data") String data, HttpServletRequest request) {
		return inlandService.saveOrderInfo(data, request);
	}

	/**
	 * 跳转到查询订单详情页面
	 */
	@At
	@Ok("jsp")
	public Object bookingDetail(@Param("id") Integer id, HttpServletRequest request) {
		return inlandService.bookingDetail(id, request);
	}

	/**
	 *保存预订订单数据
	 */
	@At
	@POST
	public Object saveBookingOrderInfo(@Param("data") String data, HttpServletRequest request) {
		return inlandService.saveBookingOrderInfo(data, request);
	}

	/**
	 * 下载游客模板
	 */
	@At
	public Object downloadVisitorTemplate(HttpServletRequest request, HttpServletResponse response) {
		return inlandService.downloadVisitorTemplate(request, response);
	}

	/**
	 * 上传游客
	 */
	@At
	@POST
	@Ok("jsp")
	@AdaptBy(type = UploadAdaptor.class)
	public Object importVisitor(@Param("excelFile") TempFile file, HttpServletRequest request) {
		return inlandService.importVisitor(file, request);
	}

	/**
	 * 跳转到添加PNR页面
	 */
	@At
	@Ok("jsp")
	public Object addPnr(HttpServletRequest request) {
		return inlandService.addPnr(request);
	}

	/**
	 * 添加PNR页面
	 */
	@At
	@POST
	public Object addPnrInfo(HttpServletRequest request) {
		return inlandService.addPnrInfo(request);
	}

	/**
	 * 异步加载pnr数据
	 */
	@At
	@POST
	public Object loadPNRdata(HttpServletRequest request) {
		return inlandService.loadPNRdata(request);
	}

	/**
	 * 打开PNR页面
	 */
	@At
	@Ok("jsp")
	public Object pnrDetailPage(HttpServletRequest request) {
		return inlandService.pnrDetailPage(request);
	}

	/**
	 * 显示游客的详细信息
	 */
	@At
	@POST
	public Object showVisitorInfo(Long id) {
		return inlandService.showVisitorInfo(id);
	}

	/**
	 * 跳转到编辑PNR信息页面
	 */
	@At
	@Ok("jsp")
	public Object editPnr(HttpServletRequest request) {
		return inlandService.editPnr(request);
	}

	/**
	 * 保存编辑PNR页面
	 */
	@At
	@POST
	public Object editPnrInfo(HttpServletRequest request) {
		return inlandService.editPnrInfo(request);
	}

	/**
	 * 跳转到出票收款页面
	 */
	@At
	@Ok("jsp")
	public Object seaInvoice(HttpServletRequest request) {
		return inlandService.seaInvoice(request);
	}

	/**
	 * 跳转到出票付款页面
	 */
	@At
	@Ok("jsp")
	public Object seaPayApply(HttpServletRequest request) {
		return inlandService.seaPayApply(request);
	}

	/**
	 * 内陆跨海出票收款列表数据
	 */
	@At
	public Object listPayData(@Param("..") PayApplyListForm sqlform, HttpServletRequest request) {
		return inlandService.listPayData(sqlform, request);
	}

	/**
	 * 保存收款信息
	 */
	@At
	@POST
	public Object saveSeaInvoice(HttpServletRequest request) {
		return inlandService.saveSeaInvoice(request);
	}

	/**
	 * 保存付款信息
	 */
	@At
	@POST
	public Object saveSeaPayApply(HttpServletRequest request) {
		return inlandService.saveSeaPayApply(request);
	}

	/**
	 * 收付款（收款）列表数据
	 */
	@At
	@POST
	public Object listShouFuKuanData(@Param("..") ShouKuanParamFrom sqlParamForm, HttpServletRequest request) {
		return inlandService.listShouFuKuanData(sqlParamForm, request);
	}

	/**
	 * 打开开发票页面
	 */
	@At
	@Ok("jsp")
	public Object openInvoice(HttpServletRequest request) {
		return inlandService.openInvoice(request);
	}

	/**
	 * 收付款（付款）页面数据
	 */
	@At
	@POST
	public Object listFuKuanData(@Param("..") FuKuanParamForm sqlParamForm, HttpServletRequest request) {
		return inlandService.listFuKuanData(sqlParamForm, request);
	}

	/**
	 * 保存开发票数据
	 */

	/**
	 * 收发票
	 */
	@At
	@Ok("jsp")
	public Object receiveInvoice(HttpServletRequest request) {
		return inlandService.receiveInvoice(request);
	}

	@At
	@POST
	public Object getOrderInfoById(@Param("id") Long id) {
		return inlandService.getOrderInfoById(id);
	}
}
