package com.linyun.airline.admin.drawback.mailroportmap.moudle;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.drawback.mailroportmap.form.TGrabMailReportMapAddForm;
import com.linyun.airline.admin.drawback.mailroportmap.form.TGrabMailReportMapSqlForm;
import com.linyun.airline.admin.drawback.mailroportmap.form.TGrabMailReportMapUpdateForm;
import com.linyun.airline.admin.drawback.mailroportmap.service.MailroportmapViewService;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/mailroportmap")
public class MailroportmapModule {

	@Inject
	private MailroportmapViewService mailroportmapViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TGrabMailReportMapSqlForm sqlParamForm, @Param("..") final Pager pager) {
		return mailroportmapViewService.listPage(sqlParamForm, pager);
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
	public Object add(@Param("..") TGrabMailReportMapAddForm addForm) {
		return mailroportmapViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return mailroportmapViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabMailReportMapUpdateForm updateForm) {
		return mailroportmapViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		mailroportmapViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		mailroportmapViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}