/**
 * TypeServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirtype.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdicttype.service;

import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdicttype.entity.ComDictTypeEntity;
import com.linyun.airline.admin.companydict.comdicttype.form.ComTypeUpateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌s
 * @Date	 2016年11月3日 	 
 */
@IocBean
public class ComTypeDictService extends BaseService<ComDictTypeEntity> {

	/**
	 * 打开编辑页面数据回显
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> updateComDictTypeDate(long id) {
		Map<String, Object> obj = Maps.newHashMap();
		obj.put("dirtype", dbDao.fetch(ComDictTypeEntity.class, id));
		return obj;
	}

	public boolean update(ComTypeUpateForm updateForm) {
		// 修改字典类型实体
		FormUtil.modify(dbDao, updateForm, ComDictTypeEntity.class);
		return true;

	}
}
