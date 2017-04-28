/**
 * InterValidateModule.java
 * com.linyun.airline.admin.order.international.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;

import com.linyun.airline.admin.order.international.service.InterValidateService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月31日 	 
 */
@IocBean
@At("/admin/intervalidate")
public class InterValidateModule {

	@Inject
	private InterValidateService interValidateService;

	/**
	 * 判断收款记录是否存在
	 */
	@At
	@POST
	public Object checkRecordIsExist(HttpServletRequest request) {
		return interValidateService.checkRecordIsExist(request);
	}

	/**
	 * 判断付款记录是否存在
	 */
	@At
	@POST
	public Object checkPayRecordIsExist(HttpServletRequest request) {
		return interValidateService.checkPayRecordIsExist(request);
	}
}
