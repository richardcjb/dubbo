/**
 * EditPlanModule.java
 * com.linyun.airline.admin.customneeds.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.module;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.customneeds.form.EditPlanSqlForm;
import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.forms.TPlanInfoUpdateForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月20日 	 
 */
@IocBean
@At("/admin/customneeds")
public class EditPlanModule {
	private static final Log log = Logs.get();

	@Inject
	private EditPlanService editPlanService;

	/**
	 * 查询列表数据
	 */
	@At
	@POST
	public Object listEditPlanData(@Param("..") EditPlanSqlForm sqlForm, HttpSession session) {
		return editPlanService.listEditPlanData(sqlForm, session);
	}

	/**
	 * 导出编辑计划Excel
	 */
	@At
	public Object exportEditPlanExcel(@Param("..") EditPlanSqlForm sqlForm, HttpSession session,
			HttpServletResponse response) {
		return editPlanService.exportEditPlanExcel(sqlForm, session, response);
	}

	/**
	 * 跳转到编辑计划页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object editplanpage(@Param("id") long id, HttpSession session) {
		return editPlanService.editplanpage(id, session);
	}

	/**
	 * 编辑计划
	 */
	@At
	@POST
	public Object updateEditPlan(@Param("..") TPlanInfoUpdateForm updateForm) {
		return editPlanService.updateEditPlan(updateForm);
	}

	/**
	 * 关闭计划
	 */
	@At
	public Object closeEditPlan(@Param("id") long id) {
		return editPlanService.closeEditPlan(id);
	}

	/**
	 * 批量关闭计划
	 */
	@At
	public Object betchClosePlan(@Param("ids") String ids) {
		return editPlanService.editPlanService(ids);
	}

	/**
	 * 启用计划
	 */
	@At
	public Object enableEditPlan(@Param("id") long id) {
		return editPlanService.enableEditPlan(id);
	}

	/**
	 * 生成订单
	 */
	@At
	public Object generateOrderNum(HttpSession session, @Param("planids") String planIds) {
		return editPlanService.insertOrderNum(session, planIds);
	}
}
