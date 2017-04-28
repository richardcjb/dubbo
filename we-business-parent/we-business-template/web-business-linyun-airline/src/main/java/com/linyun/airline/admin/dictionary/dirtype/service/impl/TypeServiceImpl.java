/**
 * TypeServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirtype.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirtype.form.TypeModForm;
import com.linyun.airline.admin.dictionary.dirtype.service.ITypeService;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌s
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "iTypeService")
public class TypeServiceImpl extends BaseService<DictTypeEntity> implements ITypeService {

	@Inject
	private IDbDao dbDao;

	@Override
	public boolean update(TypeModForm form) {
		// 修改字典类型实体
		FormUtil.modify(dbDao, form, DictTypeEntity.class);
		return true;

	}

	@Override
	public Map<String, Object> findDirtype(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirtype", dbDao.fetch(DictTypeEntity.class, id));
		return obj;
	}

	/*@Override
	public Map<String, Object> findDirtypeCode(Condition condition) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirtype", dbDao.fetch(DictTypeEntity.class, condition));
		return obj;
	}*/

}
