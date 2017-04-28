/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
<<<<<<< HEAD
*/

package com.linyun.airline.admin.dictionary.dirinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCitySqlForm;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCityUpdateForm;
import com.linyun.airline.admin.dictionary.dirinfo.dto.DictInfoDto;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoSqlForm;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "iInfoService")
public class InfoServiceImpl extends BaseService<DictInfoEntity> implements IInfoService {

	@Inject
	private InfoServiceImpl infoService;

	@Override
	public boolean update(InfoModForm form) {
		// 修改字典信息实体
		FormUtil.modify(dbDao, form, DictInfoEntity.class);
		return true;
	}

	//修改出发城市实体
	public boolean updateDepartureCity(TDepartureCityUpdateForm form) {
		// 修改字典信息实体
		FormUtil.modify(dbDao, form, TDepartureCityEntity.class);
		return true;
	}

	@Override
	public Map<String, Object> findDirinfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirtype",
				dbDao.query(DictTypeEntity.class,
						Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "!=", "CFCS"), null));
		//obj.put("deparinfo", dbDao.fetch(TDepartureCityEntity.class, id));
		obj.put("dirinfo", dbDao.fetch(DictInfoEntity.class, id));
		return obj;
	}

	//添加时出发城市下拉框
	public Map<String, Object> findCityDirinfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		DictTypeEntity singleDictType = dbDao.fetch(DictTypeEntity.class, null);
		String typeCode = singleDictType.getTypeCode();//得到typeCode
		if ("CFCS".equals(typeCode)) {
			obj.put("dirtype", dbDao.query(DictTypeEntity.class,
					Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "=", "CFCS"), null));
		}
		obj.put("deparinfo", dbDao.fetch(TDepartureCityEntity.class, id));
		return obj;
	}

	/**
	 * 
	 * 获取字典类别名称下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 * @param sqlManager
	 * @param typeCode
	 * @param departmentForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("all")
	public List<Record> getTypeNameSelect(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(DictTypeEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("status", "=", DataStatusEnum.ENABLE.intKey());//状态为已启用的数据
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 获取字典信息名称下拉框
	 * @param typeCode
	 * @param dictName
	 */

	public List<Record> getDictNameList(String typeCode, String dictName) {
		String sqlString = sqlManager.get("dict_info_area");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("i.typeCode", "=", "QY");
		cnd.and("i.status", "=", 1);
		cnd.and("i.dictName", "like", dictName + "%");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 除去出发城市其他内容table数据加载
	 * @param sqlForm
	 */
	@SuppressWarnings("unchecked")
	public Object listData(final InfoSqlForm sqlForm) {
		Map<String, Object> map = infoService.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) map.get("data");
		List<DictInfoDto> lst = Lists.newArrayList();
		if (!Util.isEmpty(list)) {
			for (Record r : list) {
				DictInfoDto en = new DictInfoDto();
				en.setStatus(r.getInt("status"));
				en.setId(r.getInt("id"));
				en.setDictcode(r.getString("dictCode"));
				en.setTypecode(r.getString("typeCode"));
				en.setDictname(r.getString("dictName"));
				en.setDescription(r.getString("description"));
				en.setTypename(r.getString("typeName"));
				en.setCreatetime(r.getTimestamp("createTime"));
				lst.add(en);
			}
		}
		map.put("data", lst);
		return map;
	}

	/**
	 * @param sqlForm
	 * 出发城市table数据
	 */
	@SuppressWarnings("unchecked")
	public Object departureCityData(@Param("..") final TDepartureCitySqlForm sqlForm) {
		Map<String, Object> map = infoService.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) map.get("data");
		List<DictInfoDto> lst = Lists.newArrayList();
		if (!Util.isEmpty(list)) {
			for (Record r : list) {
				DictInfoDto en = new DictInfoDto();
				en.setChinesename(r.getString("chineseName"));
				en.setCountryname(r.getString("countryName"));
				en.setDescription(r.getString("description"));
				en.setDictcode(r.getString("dictCode"));
				en.setDictname(r.getString("dictName"));
				en.setEnglishname(r.getString("englishName"));
				en.setId(r.getInt("id"));
				en.setInternatstatus(r.getInt("internatStatus"));
				en.setPinyin(r.getString("pinYin"));
				en.setStatename(r.getString("statename"));
				en.setStatus(r.getInt("status"));
				en.setTypecode(r.getString("typeCode"));
				en.setTypename(r.getString("typeName"));
				en.setCreatetime(r.getTimestamp("createTime"));
				en.setUpdatetime(r.getTimestamp("updateTime"));
				lst.add(en);
			}
		}
		map.put("data", lst);
		return map;
	}
}
