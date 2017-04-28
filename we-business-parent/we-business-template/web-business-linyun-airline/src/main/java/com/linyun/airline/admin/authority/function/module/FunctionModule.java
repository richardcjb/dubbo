package com.linyun.airline.admin.authority.function.module;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.authority.function.form.TFunctionAddForm;
import com.linyun.airline.admin.authority.function.form.TFunctionModForm;
import com.linyun.airline.admin.authority.function.form.TFunctionSqlForm;
import com.linyun.airline.admin.authority.function.service.FunctionViewService;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 功能管理	控制类
* @ClassName: FunctionModule
* @author 崔建斌 
* @date 2016年11月18日 上午11:35:11
 */
@IocBean
@At("/admin/authority/function")
public class FunctionModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	/**
	 * 注入容器中管理sql的对象，用于从sql文件中根据key取得sql
	 */
	@Inject
	private SqlManager sqlManager;

	@Inject
	private FunctionViewService functionViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("functions", dbDao.query(TFunctionEntity.class, null, null));
		return obj;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TFunctionSqlForm sqlForm) {
		return functionViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return dbDao.query(TFunctionEntity.class, null, null);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TFunctionAddForm addForm) {
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		FormUtil.add(dbDao, addForm, TFunctionEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//上级功能选择的时候要排除自己
		obj.put("list", dbDao.query(TFunctionEntity.class, Cnd.where("id", "!=", id), null));
		obj.put("function", dbDao.fetch(TFunctionEntity.class, id));
		return obj;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TFunctionModForm modForm) {
		modForm.setUpdateTime(new Date());
		modForm.setCreateTime(new Date());
		TFunctionEntity single = dbDao.fetch(TFunctionEntity.class, modForm.getId());
		String portrait = single.getPortrait();
		modForm.setPortrait(portrait);
		FormUtil.modify(dbDao, modForm, TFunctionEntity.class);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TFunctionEntity.class, id);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TFunctionEntity.class, ids);
		return JsonResult.success("删除成功");
	}
}