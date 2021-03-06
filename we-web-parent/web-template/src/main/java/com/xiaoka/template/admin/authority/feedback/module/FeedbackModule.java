package com.xiaoka.template.admin.authority.feedback.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.xiaoka.template.admin.authority.feedback.service.FeedbackViewService;
import com.xiaoka.template.forms.FeedbackAddForm;
import com.xiaoka.template.forms.FeedbackForm;
import com.xiaoka.template.forms.FeedbackUpdateForm;

@IocBean
@At("/admin/feedback")
@Filters({//@By(type = AuthFilter.class)
})
public class FeedbackModule {

	private static final Log log = Logs.get();

	@Inject
	private FeedbackViewService feedbackViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final FeedbackForm sqlParamForm, @Param("..") final Pager pager) {
		return feedbackViewService.listPage(sqlParamForm, pager);
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
	public Object add(@Param("..") FeedbackAddForm addForm) {
		return feedbackViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return feedbackViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") FeedbackUpdateForm updateForm) {
		return feedbackViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		feedbackViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		feedbackViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}