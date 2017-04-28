/**
 * IInfoService.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.service;

import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
public interface IInfoService {

	/**
	 * 修改字典信息
	 * @param form 
	 * @return
	 */
	public boolean update(InfoModForm form);

	/**
	 * 查询字典信息
	 * list - 功能列表
	 * Dirtype - 字典类型实体
	 * @param id  角色id
	 */
	Map<String, Object> findDirinfo(long id);

}
