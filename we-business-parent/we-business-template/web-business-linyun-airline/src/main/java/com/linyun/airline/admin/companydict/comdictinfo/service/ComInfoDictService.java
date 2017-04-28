/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
<<<<<<< HEAD
*/

package com.linyun.airline.admin.companydict.comdictinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComLoginNumEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComThirdPayMentEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComAddTypeEnum;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoSqlForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoUpdateForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComLoginNumUpdateForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
public class ComInfoDictService extends BaseService<ComDictInfoEntity> {

	@Inject
	private ComInfoDictService comInfoDictService;

	/**
	 * 公司字典类别名称查询
	 * @param session
	 */
	/*public Object getComDictTypeName(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("company_select_dicttypename"));
		Cnd cnd = Cnd.NEW();
		cnd.and("cty.comId", "=", companyId);
		cnd.and("cty.status", "=", DataStatusEnum.ENABLE.intKey());
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listTypeName", query);
		return obj;
	}*/

	/**
	 * 根据枚举类型得到公司字典类别名称
	 */
	public Object getDictTypeName() {
		Map<String, Object> obj = Maps.newHashMap();
		obj.put("dicttypelist", EnumUtil.enum2(ComAddTypeEnum.class));
		return obj;
	}

	public boolean update(ComInfoUpdateForm form) {
		// 修改字典信息实体
		FormUtil.modify(dbDao, form, ComDictInfoEntity.class);
		return true;
	}

	//编辑时数据回显
	public Map<String, Object> findDirinfo(long id, final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("company_dict_updateinfo"));
		Cnd cnd = Cnd.NEW();
		cnd.and("cin.id", "=", id);
		cnd.and("cin.comId", "=", comId);
		List<Record> single = dbDao.query(sql, cnd, null);
		obj.put("single", single);
		return obj;
	}

	/**
	 * 获取字典类别名称下拉框
	 * @param sqlManager
	 * @param typeCode
	 * @param departmentForm
	 */
	@SuppressWarnings("all")
	public List<Record> getTypeNameSelect(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(ComDictInfoEntity.class);
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
	 * 字典信息列表，得到字典类别名称
	 * @param sqlForm
	 */
	@SuppressWarnings("unchecked")
	public Object comInfoListData(ComInfoSqlForm sqlForm) {
		Map<String, Object> datatablesdata = this.listPage4Datatables(sqlForm);
		List<Record> listdata = (List<Record>) datatablesdata.get("data");
		for (Record record : listdata) {
			record.put("comdicttypeenum", EnumUtil.enum2(ComDictTypeEnum.class));
		}
		datatablesdata.remove("data");
		datatablesdata.put("data", listdata);
		return datatablesdata;

	}

	/**
	 * 登录账号table数据
	 * @param sqlForm
	 */
	/*@SuppressWarnings("unchecked")
	public Object loginNumData(@Param("..") final ComLoginNumSqlForm sqlForm) {
		Map<String, Object> map = comInfoDictService.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) map.get("data");
		List<ComLoginNumEntity> lst = Lists.newArrayList();
		if (!Util.isEmpty(list)) {
			for (Record r : list) {
				ComLoginNumEntity en = new ComLoginNumEntity();
				en.setAirlineName(r.getString("airlineName"));
				en.setComDdictCode(r.getString("comDdictCode"));
				en.setComId(r.getInt("comId"));
				en.setComTypeCode(r.getString("comTypeCode"));
				en.setCreateTime(r.getTimestamp("createTime"));
				en.setId(r.getInt("id"));
				en.setLoginNumName(r.getString("loginNumName"));
				en.setRemark(r.getString("remark"));
				en.setStatusName(r.getString("statename"));
				en.setUpdateTime(r.getTimestamp("updateTime"));
				en.setWebURl(r.getString("webURl"));
				lst.add(en);
			}
		}
		map.put("data", lst);
		return map;
	}*/
	//航空公司
	public Object airLine(String airlineName, final String airlineIds, final HttpSession session) {
		List<Record> dictNameList = this.getAreaNameList(airlineName, airlineIds, session);
		List<Select2Option> result = transform2SelectOptions(dictNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> dictNameList) {
		return Lists.transform(dictNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("airLineId"));
				op.setText(record.getString("dictCode") + "-" + record.getString("dictName"));
				return op;
			}
		});
	}

	/**
	 * 获取航空公司名称下拉框
	 * @param dictName
	 */
	public List<Record> getAreaNameList(String areaName, final String airlineIds, final HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		String sqlString = sqlManager.get("company_dict_airlineName_select2");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("dt.typeCode", "=", "HKGS");
		cnd.and("(dt.dictName", "like", Strings.trim(areaName) + "%");
		cnd.or("dt.dictCode", "like", "%" + Strings.trim(areaName) + "%");
		if (!Util.isEmpty(airlineIds)) {
			cnd.and("dt.id", "NOT IN", airlineIds);
		}
		/*if (!Util.isEmpty(comId)) {
			cnd.and("dt.comId", "=", comId);
		}*/
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	//回显登录账号数据
	public Map<String, Object> updateLoginNum(Long id) {
		Map<String, Object> obj = Maps.newHashMap();
		//查询航空公司
		Sql sql = Sqls.create(sqlManager.get("company_dict_airlineName_update"));
		Cnd cnd = Cnd.NEW();
		cnd.and("lob.id", "=", id);
		cnd.groupBy("lob.airlineName ");
		sql.setCondition(cnd);
		List<ComLoginNumEntity> areaList = DbSqlUtil.query(dbDao, ComLoginNumEntity.class, sql);
		//区域id 拼串
		String areaIds = "";
		for (ComLoginNumEntity tAreaEntity : areaList) {
			areaIds += tAreaEntity.getId() + ",";
		}
		if (areaIds.length() > 0) {
			areaIds = areaIds.substring(0, areaIds.length() - 1);
		}
		obj.put("areaIds", areaIds);
		obj.put("airlineInfo", Lists.transform(areaList, new Function<ComLoginNumEntity, Select2Option>() {
			@Override
			public Select2Option apply(ComLoginNumEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());//字典id
				op.setText(record.getAirlineName());//信息名称
				return op;
			}
		}));
		obj.put("loginNumData", dbDao.fetch(ComLoginNumEntity.class, id));
		return obj;
	}

	//编辑保存登录账号数据
	public boolean updateLoginNum(ComLoginNumUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		ComLoginNumEntity fetch = dbDao.fetch(ComLoginNumEntity.class, updateForm.getId());
		Integer status = fetch.getStatus();
		if (!fetch.getAirlineName().equals(updateForm.getAirlineName())) {
			Long airlineNameId = Long.parseLong(updateForm.getAirlineName());
			DictInfoEntity single = dbDao.fetch(DictInfoEntity.class, airlineNameId);
			if (Util.isEmpty(single)) {
				/*if (status.equals(DataStatusEnum.ENABLE.intKey())) {
					updateForm.setStatus(DataStatusEnum.DELETE.intKey());
				} else if (status.equals(DataStatusEnum.DELETE.intKey())) {
					updateForm.setStatus(DataStatusEnum.ENABLE.intKey());
				}*/
				updateForm.setAirlineName(fetch.getAirlineName());
			} else if (!Util.isEmpty(single)) {
				/*if (status.equals(DataStatusEnum.ENABLE.intKey())) {
					updateForm.setStatus(DataStatusEnum.DELETE.intKey());
				} else if (status.equals(DataStatusEnum.DELETE.intKey())) {
					updateForm.setStatus(DataStatusEnum.ENABLE.intKey());
				}*/
				String dictName = single.getDictName();
				updateForm.setAirlineName(dictName);
			}
		}
		ComLoginNumEntity comlog = new ComLoginNumEntity();
		BeanUtil.copyProperties(updateForm, comlog);
		this.updateIgnoreNull(comlog);//更新表中的数据
		// 修改字典信息实体
		FormUtil.modify(dbDao, updateForm, ComLoginNumEntity.class);
		return true;
	}

	//回显登录账号数据
	public Map<String, Object> updateThirdPayMnet(Integer id, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> obj = Maps.newHashMap();
		Cnd cnd = Cnd.NEW();
		cnd.and("comId", "=", comId);
		cnd.and("id", "=", id);
		ComThirdPayMentEntity single = dbDao.fetch(ComThirdPayMentEntity.class, cnd);
		obj.put("single", single);
		return obj;
	}
}
