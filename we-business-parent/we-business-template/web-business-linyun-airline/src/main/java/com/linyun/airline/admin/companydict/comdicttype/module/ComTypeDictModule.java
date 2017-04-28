/**
 * TypeModule.java
 * com.xiaoka.template.admin.dictionary.dirtype.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdicttype.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.linyun.airline.admin.companydict.comdicttype.entity.ComDictTypeEntity;
import com.linyun.airline.admin.companydict.comdicttype.form.ComTypeAddForm;
import com.linyun.airline.admin.companydict.comdicttype.form.ComTypeSqlForm;
import com.linyun.airline.admin.companydict.comdicttype.form.ComTypeUpateForm;
import com.linyun.airline.admin.companydict.comdicttype.service.ComTypeDictService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.form.AlterStatusForm;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 公司字典类别
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
@At("/admin/companydict/comdicttype")
public class ComTypeDictModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private ComTypeDictService comTypeDictService;

	/**
	 * 分页查询
	 * @param sqlForm  查询表单
	 * @param pager      分页对象
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final ComTypeSqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object comListData(@Param("..") final ComTypeSqlForm sqlForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		sqlForm.setComId(comId);
		return comTypeDictService.listPage4Datatables(sqlForm);
	}

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
	public Object add(@Param("..") final ComTypeAddForm addForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		addForm.setComId(companyId);
		addForm.setCreateTime(new Date());
		FormUtil.add(dbDao, addForm, ComDictTypeEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 * @param id
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> map = comTypeDictService.updateComDictTypeDate(id);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 执行'修改操作'
	 * @param modForm
	 */
	@At
	@POST
	public Object update(@Param("..") final ComTypeUpateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		comTypeDictService.update(updateForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, ComDictTypeEntity.class, id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, ComDictTypeEntity.class, ids);
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
			dbDao.update(ComDictTypeEntity.class, Chain.make("status", form.getStatus()),
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
	public Object checkTypeCodeExist(@Param("comTypeCode") final String Code, @Param("id") final long id,
			final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComDictTypeEntity> listCode = dbDao.query(ComDictTypeEntity.class, Cnd.where("comTypeCode", "=", Code)
				.and("comId", "=", comId), null);
		List<ComDictTypeEntity> listCode2 = dbDao.query(ComDictTypeEntity.class, Cnd.where("comTypeCode", "=", Code)
				.and("comId", "=", comId).and("id", "=", id), null);
		if (!Util.isEmpty(listCode)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listCode2)) {
				map.put("valid", true);
			}
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
	public Object checkTypeNameExist(@Param("comTypeName") final String Name, @Param("id") final long id,
			final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComDictTypeEntity> listName = dbDao.query(ComDictTypeEntity.class, Cnd.where("comTypeName", "=", Name)
				.and("comId", "=", comId), null);
		List<ComDictTypeEntity> listName2 = dbDao.query(ComDictTypeEntity.class, Cnd.where("comTypeName", "=", Name)
				.and("comId", "=", comId).and("id", "=", id), null);
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
