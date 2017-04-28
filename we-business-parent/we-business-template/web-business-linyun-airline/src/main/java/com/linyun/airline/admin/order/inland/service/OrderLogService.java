/**
 * OrderLogService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.entities.TOrderLogEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月10日 	 
 */
@IocBean
public class OrderLogService extends BaseService<TOrderLogEntity> {

	public void addOrderLog(Integer userid, Integer orderid, String ordernum, String content) {
		TOrderLogEntity orderLog = new TOrderLogEntity();
		orderLog.setOpid(userid);
		orderLog.setOptime(new Date());
		orderLog.setOrderid(orderid);
		orderLog.setOrdernum(ordernum);
		orderLog.setContent(content);
		dbDao.insert(orderLog);
	}
}
