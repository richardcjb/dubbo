/**
 * RelationServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirrelation.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirrelation.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirrelation.entity.RelationEntity;
import com.linyun.airline.admin.dictionary.dirrelation.form.RelationModForm;
import com.linyun.airline.admin.dictionary.dirrelation.service.IRelationService;
import com.uxuexi.core.db.dao.IDbDao;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "iRelationService")
public class RelationServiceImpl implements IRelationService {

	@Inject
	private IDbDao dbDao;

	@Override
	public boolean update(RelationModForm form) {
		// 修改字典类型实体
		//FormUtil.modify(dbDao, form, RelationEntity.class);
		return true;

	}

	@Override
	public Map<String, Object> findRelation(int id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirrelation", dbDao.fetch(RelationEntity.class, id));
		return obj;

	}

}
