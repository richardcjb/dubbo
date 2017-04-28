/**
 * InfoModule.java
 * com.xiaoka.template.admin.dictionary.dirinfo.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCityAddForm;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCitySqlForm;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCityUpdateForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoAddForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoSqlForm;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.admin.dictionary.dirinfo.service.impl.InfoServiceImpl;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.form.AlterStatusForm;
import com.linyun.airline.entities.DictInfoEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
@At("/admin/dictionary/dirinfo")
public class InfoModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private SqlManager sqlManager;

	@Inject
	private IInfoService iInfoService;

	@Inject
	private InfoServiceImpl infoService;

	/**
	 * 分页查询
	 * <P>
	 * 
	 * @param sqlForm  查询表单
	 * @param pager    分页对象
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final InfoSqlForm sqlForm, @Param("..") final Pager pager) {
		Map<String, Object> map = MapUtil.map();
		List<Record> deplist = infoService.getTypeNameSelect(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * @param sqlForm
	 * @param pager
	 * 出发城市列表页
	 */
	@At
	@Ok("jsp")
	public Object departureCityList(@Param("..") final TDepartureCitySqlForm sqlForm, @Param("..") final Pager pager) {
		Map<String, Object> map = MapUtil.map();
		List<Record> deplist = infoService.getTypeNameSelect(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final InfoSqlForm sqlForm) {
		return infoService.listData(sqlForm);
	}

	/**
	 * @param sqlForm
	 * 出发城市dataTable数据
	 */
	@At
	public Object departureCityData(@Param("..") final TDepartureCitySqlForm sqlForm) {
		return infoService.departureCityData(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("id") final long id) {
		Map<String, Object> map = iInfoService.findDirinfo(id);
		return map;
	}

	/**
	 * TODO 添加
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object add(@Param("..") final InfoAddForm addForm) {
		addForm.setCreateTime(new Date());
		FormUtil.add(dbDao, addForm, DictInfoEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'添加出发城市'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addDepartureCity(@Param("id") final long id) {
		Map<String, Object> map = infoService.findCityDirinfo(id);
		return map;
	}

	/**
	 * TODO 添加出发城市
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object addDepartureCity(@Param("..") final TDepartureCityAddForm addForm) {
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		FormUtil.add(dbDao, addForm, TDepartureCityEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> map = iInfoService.findDirinfo(id);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final InfoModForm modForm) {
		modForm.setCreateTime(new Date());
		iInfoService.update(modForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 跳转到'出发城市修改操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateDepartureCity(@Param("id") final long id) {
		Map<String, Object> map = infoService.findCityDirinfo(id);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 执行'出发城市修改操作'
	 */
	@At
	@POST
	public Object updateDepartureCity(@Param("..") final TDepartureCityUpdateForm modForm) {
		modForm.setCreateTime(new Date());
		modForm.setUpdateTime(new Date());
		infoService.updateDepartureCity(modForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, DictInfoEntity.class, id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, DictInfoEntity.class, ids);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 更新删除状态
	 * TODO(这里用一句话描述这个方法的作用)
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@POST
	public Object updateDeleteStatus(@Param("..") AlterStatusForm form) {
		try {
			dbDao.update(DictInfoEntity.class, Chain.make("status", form.getStatus()),
					Cnd.where("id", "=", form.getId()));
			dbDao.update(TDepartureCityEntity.class, Chain.make("status", form.getStatus()),
					Cnd.where("id", "=", form.getId()));
			return JsonResult.success("操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("操作失败!");
		}
	}

	/**
	 * 校验字典代码
	 */
	@At
	@POST
	public Object checkTypeCodeExist(@Param("dictCode") final String Code, @Param("id") final long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictInfoEntity> listCode = dbDao.query(DictInfoEntity.class, Cnd.where("dictCode", "=", Code), null);
		List<DictInfoEntity> listCode2 = dbDao.query(DictInfoEntity.class,
				Cnd.where("dictCode", "=", Code).and("id", "=", id), null);
		List<TDepartureCityEntity> listCityCode = dbDao.query(TDepartureCityEntity.class,
				Cnd.where("dictCode", "=", Code), null);
		List<TDepartureCityEntity> listCityCode2 = dbDao.query(TDepartureCityEntity.class,
				Cnd.where("dictCode", "=", Code).and("id", "=", id), null);
		if (!Util.isEmpty(listCode) || !Util.isEmpty(listCityCode)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listCode2) || !Util.isEmpty(listCityCode2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验字典信息名称
	 */
	@At
	@POST
	public Object checkDictNameExist(@Param("dictName") final String Name, @Param("id") final long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictInfoEntity> listName = dbDao.query(DictInfoEntity.class, Cnd.where("dictName", "=", Name), null);
		List<DictInfoEntity> listName2 = dbDao.query(DictInfoEntity.class,
				Cnd.where("dictName", "=", Name).and("id", "=", id), null);
		if (!Util.isEmpty(listName)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listName2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}
}
