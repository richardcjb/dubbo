/**
 * TypeModule.java
 * com.xiaoka.template.admin.dictionary.dirtype.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.dictionary.dirtype.form.TypeAddForm;
import com.linyun.airline.admin.dictionary.dirtype.form.TypeModForm;
import com.linyun.airline.admin.dictionary.dirtype.form.TypeQueryForm;
import com.linyun.airline.admin.dictionary.dirtype.service.ITypeService;
import com.linyun.airline.admin.dictionary.dirtype.service.impl.TypeServiceImpl;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.form.AlterStatusForm;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.common.util.EnumUtil;
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
@At("/admin/dictionary/dirtype")
public class TypeModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private ITypeService iTypeService;

	@Inject
	TypeServiceImpl typeViewService;

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public void add() {
	}

	/**
	 * 
	 * TODO 添加
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object add(@Param("..") final TypeAddForm addForm) {
		addForm.setCreateTime(new Date());
		FormUtil.add(dbDao, addForm, DictTypeEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> map = iTypeService.findDirtype(id);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final TypeModForm modForm) {
		modForm.setCreateTime(new Date());
		iTypeService.update(modForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 分页查询
	 * <P>
	 * 
	 * @param queryForm  查询表单
	 * @param pager      分页对象
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TypeQueryForm queryForm, @Param("..") final Pager pager) {
		if (Util.isEmpty(queryForm.getTypeName())) {
			queryForm.setTypeName(null);
		}
		Map<String, Object> map = FormUtil.query(dbDao, DictTypeEntity.class, queryForm, pager);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, DictTypeEntity.class, id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, DictTypeEntity.class, ids);
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
			dbDao.update(DictTypeEntity.class, Chain.make("status", form.getStatus()),
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
	public Object checkTypeCodeExist(@Param("typeCode") final String Code) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictTypeEntity> listCode = dbDao.query(DictTypeEntity.class, Cnd.where("typeCode", "=", Code), null);
		if (!Util.isEmpty(listCode)) {
			map.put("valid", false);
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验字典类别名称
	 */
	@At
	@POST
	public Object checkTypeNameExist(@Param("typeName") final String Name) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictTypeEntity> listName = dbDao.query(DictTypeEntity.class, Cnd.where("typeName", "=", Name), null);
		if (!Util.isEmpty(listName)) {
			map.put("valid", false);
		} else {
			map.put("valid", true);
		}
		return map;
	}
}
