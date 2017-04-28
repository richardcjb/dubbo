/**
 * UserPublicService.java
 * com.linyun.airline.admin.user.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年11月23日 	 
 */
@IocBean
public class UserPublicService extends BaseService<TUserEntity> {
	private static final Log log = Logs.get();

}
