/**
 * InterValidateService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.entities.TPayReceiveRecordEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月31日 	 
 */
@IocBean
public class InterValidateService extends BaseService<TUpOrderEntity> {

	/**
	 * 判断收款记录是否存在
	 * <p>
	 * TODO判断收款记录是否存在
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkRecordIsExist(HttpServletRequest request) {
		boolean result = true;
		String orderid = request.getParameter("orderid");
		String ordersstatus = request.getParameter("ordersstatus");
		Integer receivetype = PayReceiveTypeEnum.RECEIVE.intKey();
		Cnd cnd = Cnd.NEW();
		cnd.and("orderid", "=", orderid);
		cnd.and("orderstatusid", "=", ordersstatus);
		cnd.and("recordtype", "=", receivetype);
		List<TPayReceiveRecordEntity> receiverecord = dbDao.query(TPayReceiveRecordEntity.class, cnd, null);
		if (receiverecord.size() > 0) {
			result = false;
		}
		return result;
	}

	/**
	 * 判断付款记录是否存在
	 * <p>
	 * TODO判断付款记录是否存在
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkPayRecordIsExist(HttpServletRequest request) {
		boolean result = true;
		String orderid = request.getParameter("orderid");
		String ordersstatus = request.getParameter("ordersstatus");
		Integer receivetype = PayReceiveTypeEnum.PAY.intKey();
		Cnd cnd = Cnd.NEW();
		cnd.and("orderid", "=", orderid);
		cnd.and("orderstatusid", "=", ordersstatus);
		cnd.and("recordtype", "=", receivetype);
		List<TPayReceiveRecordEntity> receiverecord = dbDao.query(TPayReceiveRecordEntity.class, cnd, null);
		if (receiverecord.size() > 0) {
			result = false;
		}
		return result;
	}
}
