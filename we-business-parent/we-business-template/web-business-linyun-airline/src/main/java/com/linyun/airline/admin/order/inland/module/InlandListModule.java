/**
 * InlandListModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.order.inland.service.InlandListService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月6日 	 
 */
@IocBean
@At("admin/inland")
public class InlandListModule {

	@Inject
	private InlandListService inlandListService;

	/**
	 * 格式化订单状态
	 */
	@At
	@POST
	public Object formatOrderStatus(@Param("status") Integer status) {
		return inlandListService.formatOrderStatus(status);
	}

	/**
	 * 保存需求信息
	 */
	@At
	@POST
	public Object saveCustomeneedInfo(HttpServletRequest request) {
		return inlandListService.saveCustomeneedInfo(request);
	}

	/**
	 * 加载银行卡名称下拉
	 */
	@At
	@POST
	public Object loadBankCardNameSelect(HttpServletRequest request) {
		return inlandListService.loadBankCardNameSelect(request);
	}

	/**
	 * 加载银行卡号
	 */
	@At
	@POST
	public Object loadBankCardNumSelect(HttpServletRequest request) {
		return inlandListService.loadBankCardNumSelect(request);
	}

	/**
	 * 设置财务信息
	 */
	@At
	@POST
	public Object setFinanceInfo(HttpServletRequest request) {
		return inlandListService.setFinanceInfo(request);
	}

	/**
	 * 加载客户下拉
	 */
	@At
	@POST
	public Object loadCustomeSelect(HttpServletRequest request) {
		return inlandListService.loadCustomeSelect(request);
	}

	/**
	 * 加载国际专用卡支付余额
	 */
	@At
	@POST
	public Object loadBalance(HttpServletRequest request) {
		return inlandListService.loadBalance(request);
	}
}
