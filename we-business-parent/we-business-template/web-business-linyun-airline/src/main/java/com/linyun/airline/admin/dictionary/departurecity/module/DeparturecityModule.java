package com.linyun.airline.admin.dictionary.departurecity.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCityAddForm;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCitySqlForm;
import com.linyun.airline.admin.dictionary.departurecity.form.TDepartureCityUpdateForm;
import com.linyun.airline.admin.dictionary.departurecity.service.DeparturecityViewService;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/departurecity")
public class DeparturecityModule {

	@Inject
	private DeparturecityViewService departurecityViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TDepartureCitySqlForm sqlForm, @Param("..") final Pager pager) {
		return departurecityViewService.listPage(sqlForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TDepartureCityAddForm addForm) {
		return departurecityViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return departurecityViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TDepartureCityUpdateForm updateForm) {
		return departurecityViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		departurecityViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		departurecityViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}