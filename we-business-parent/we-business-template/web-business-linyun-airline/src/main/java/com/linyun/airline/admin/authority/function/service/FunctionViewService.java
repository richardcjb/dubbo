package com.linyun.airline.admin.authority.function.service;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "functionViewService")
public class FunctionViewService extends BaseService<TFunctionEntity> {

	/**
	 * 根据请求路径查询功能
	 * @param requestPath
	 * @return
	 */
	public TFunctionEntity findFuctionByRequestPath(String requestPath) {
		return dbDao.fetch(TFunctionEntity.class, Cnd.where("url", "LIKE", "%" + requestPath + "%"));
	}
}