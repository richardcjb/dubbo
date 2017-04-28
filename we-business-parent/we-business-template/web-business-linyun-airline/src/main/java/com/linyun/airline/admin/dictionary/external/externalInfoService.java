/**
 * IInfoService.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.external;

import java.util.List;

import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.entities.DictInfoEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */

public interface externalInfoService {

	public List<DictInfoEntity> findDictInfoByName(String name, String typeCode) throws Exception;

	public List<DictInfoEntity> findDictInfoByText(String name, String typeCode);

	public List<DictInfoEntity> findDictInfoByTypes(String name, String typeCode1, String typeCode2) throws Exception;

	public List<TDepartureCityEntity> findCityByCode(String name, String typeCode);

}
