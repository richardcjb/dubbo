package com.linyun.airline.admin.area.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TAreaAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AreaViewService extends BaseService<TAreaEntity> {

	@Inject
	private UserViewService userViewService;

	//添加区域保存
	/*public Object addAreaName(TAreaAddForm addForm, final HttpSession session) {
		//通过session获取当前登录用户的id
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();//得到用户的id
		//通过session获取当前登录公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		//先添加区域
		TAreaEntity areaEntity = new TAreaEntity();
		areaEntity.setComId(comId);
		areaEntity.setAreaName(addForm.getAreaName());
		areaEntity.setCreateTime(new Date());
		areaEntity.setRemark(addForm.getRemark());
		areaEntity = dbDao.insert(areaEntity);
		//获取到区域id
		Long areaId = areaEntity.getId();
		//往用户区域表中填数据
		TUserAreaMapEntity areaEntities = new TUserAreaMapEntity();
		areaEntities.setAreaId(areaId);
		areaEntities.setUserId(userId);
		dbDao.insert(areaEntities);
		return JsonResult.success("添加成功!");
	}*/

	//区域名称保存
	public Object addAreaName(TAreaAddForm addForm) {
		TAreaEntity areaEntity = this.add(addForm);
		return dbDao.insert(areaEntity);
	}

	//校验区域名称唯一性
	public Object checkAreaNameExist(final String areaName, final Long areaId, final HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		//查询该公司拥有的所有功能
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		int count = 0;
		if (Util.isEmpty(areaId)) {
			//add
			count = nutDao.count(TAreaEntity.class, Cnd.where("areaName", "=", areaName).and("comId", "=", comId));
		} else {
			//update
			count = nutDao.count(TAreaEntity.class,
					Cnd.where("areaName", "=", areaName).and("comId", "=", comId).and("id", "!=", areaId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//区域
	public Object areaSelect2(String dictAreaName, final String selectedAreaIds, final HttpSession session) {
		List<Record> dictNameList = userViewService.getAreaNameList(dictAreaName, selectedAreaIds, session);
		List<Select2Option> result = transform2SelectOptions(dictNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> dictNameList) {
		return Lists.transform(dictNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("areaId"));
				op.setText(record.getString("areaName"));
				return op;
			}
		});
	}
}