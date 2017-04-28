package com.linyun.airline.admin.operationsArea.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.admin.operationsArea.form.TMessageForm;
import com.linyun.airline.admin.operationsArea.form.TMessageUpdateForm;
import com.linyun.airline.admin.operationsArea.service.OperationsAreaViewService;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/operationsArea")
public class OperationsAreaModule {

	private static final Log log = Logs.get();

	@Inject
	private OperationsAreaViewService operationsAreaViewService;

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

	/**
	 * 到添加自定义事件页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object customEvent(@Param("selDate") final String selDate) {
		return selDate;
	}

	/**
	 * 到修改自定义事件页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateCustomEvent(@Param("msgId") final long id) throws Exception {
		return operationsAreaViewService.toUpdateCustomEvent(id);
	}

	/**
	 * 执行修改自定义事件
	 */
	@At
	@POST
	public Object updateCustom(@Param("..") TMessageUpdateForm messageUpdateForm) {
		operationsAreaViewService.updateCustom(messageUpdateForm);
		return JsonResult.success("修改成功");
	}

	/**
	 * 执行修改自定义事件
	 */
	@At
	@POST
	public Object deleteCustom(@Param("..") TMessageUpdateForm messageUpdateForm) {
		operationsAreaViewService.deleteCustom(messageUpdateForm);
		return JsonResult.success("删除成功");
	}

	/**
	 * 跳转到桌面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object desktop(HttpSession session) {
		return operationsAreaViewService.getCheckBox(session);
	}

	/**
	 * 桌面自定义事件 列表展示
	 */
	@At
	@GET
	public Object getCustomEvents(HttpSession session, @Param("start") final String start,
			@Param("end") final String end) {
		return operationsAreaViewService.getCustomEvent(session, start, end);
	}

	/**
	 * 任务栏事件   询单、订单、任务 TODO
	 */
	@At
	@POST
	public Object getOrderMsgs(@Param("data") final String orderType, HttpSession session) {
		return operationsAreaViewService.getOrderMsgs(orderType, session);
	}

	/**
	 * 任务栏事件    我的提醒显示
	 */
	@At
	@POST
	public Object getTaskEvents(@Param("data") final String str, HttpSession session) {
		return operationsAreaViewService.getTaskEvents(session);
	}

	/**
	 * 任务栏事件   账期
	 */
	@At
	@POST
	public Object getPayTypeTerm(@Param("data") final String str, HttpSession session) {
		return operationsAreaViewService.getPayTypeTerm(session);
	}

	/**
	 * 自定义界面设置
	 */
	@At
	@POST
	public Object setCheckBox(HttpSession session, @Param("checkboxname") final String checkboxname) {
		return operationsAreaViewService.setCheckBox(session, checkboxname);
	}

	/**
	 * 自定义界面获取
	 */
	@At
	@POST
	public Object getCheckBox(HttpSession session) {
		return operationsAreaViewService.getCheckBox(session);
	}

	/**
	 * 小日历事件的获取
	 */
	@At
	@POST
	public Object getMinCalList(HttpSession session, @Param("timeStr") final String timeStr) {
		return operationsAreaViewService.getMinCalList(session, timeStr);
	}

	/**
	 * 单个小日历事件获取
	 */
	@At
	@POST
	public Object getMinCal(HttpSession session, @Param("gtime") final String timeStr) {
		return operationsAreaViewService.getMinCal(session, timeStr);
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
	public Object add(@Param("..") TMessageAddForm addForm, HttpSession session) {
		return operationsAreaViewService.addCustomEvent(addForm, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return operationsAreaViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TMessageUpdateForm updateForm) {
		return operationsAreaViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		operationsAreaViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		operationsAreaViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TMessageForm sqlParamForm, @Param("..") final Pager pager) {
		return operationsAreaViewService.listPage(sqlParamForm, pager);
	}

	/**
	 * 
	 * 更新消息表 上次读取时间和消息为已读
	 * <p>
	 *
	 * @param userMsgId  用户消息表id
	 * @return 
	 */
	@At
	@GET
	public Object updateMsgStatus(@Param("userMsgId") final int userMsgId) {
		return operationsAreaViewService.updateMsgStatus(userMsgId);
	}
}