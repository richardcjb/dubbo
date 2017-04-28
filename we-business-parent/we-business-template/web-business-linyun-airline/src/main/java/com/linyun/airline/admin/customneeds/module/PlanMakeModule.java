package com.linyun.airline.admin.customneeds.module;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.customneeds.form.PlanMakeSqlForm;
import com.linyun.airline.admin.customneeds.service.ExportXinHangService;
import com.linyun.airline.admin.customneeds.service.PlanMakeService;
import com.linyun.airline.forms.TPlanInfoAddForm;

@IocBean
@At("/admin/customneeds")
public class PlanMakeModule {

	private static final Log log = Logs.get();

	@Inject
	private PlanMakeService planMakeService;
	@Inject
	private ExportXinHangService exportXinHangService;

	/**
	 * 获取航空公司下拉
	 */
	@At
	@POST
	public Object getAirComSelect(@Param("aircom") String aircom) {
		return planMakeService.getAirComSelect(aircom);
	}

	/**
	 * 获取旅行社下拉
	 */
	@At
	@POST
	public Object getTravelNameSelect(@Param("travelname") String TravelName, final HttpSession session) {
		return planMakeService.getTravelNameSelect(TravelName, session);
	}

	/**
	 * 获取航班下拉
	 */
	@At
	@POST
	public Object getAirLineSelect(@Param("airlinename") String airlinename, @Param("exname") String exname) {
		return planMakeService.getAirLineSelect(airlinename, exname);
	}

	/**
	 * 获取城市下拉
	 */
	@At
	@POST
	public Object getCitySelect(@Param("cityname") String cityname, @Param("exname") String exname) {
		return planMakeService.getCitySelect(cityname, exname);
	}

	/**
	 * 获取联运城市下拉
	 */
	@At
	@POST
	public Object getUnionCitySelect(@Param("cityname") String cityname) {
		return planMakeService.getUnionCitySelect(cityname);
	}

	/**
	 * 制作计划
	 */
	@At
	@POST
	public Object airlineMakePlan(@Param("..") TPlanInfoAddForm addForm, HttpSession session) {
		return planMakeService.airlineMakePlan(addForm, session);
	}

	/**
	 * 计划制作列表数据
	 */
	@At
	public Object listPlanMakeData(@Param("..") PlanMakeSqlForm sqlForm, HttpSession session) {
		return planMakeService.listPlanMakeData(sqlForm, session);
	}

	/**
	 * 保存计划
	 */
	@At
	public Object savePlanData(HttpSession session) {
		return planMakeService.savePlanData(session);
	}

	/**
	 * 导出东航模板
	 */
	@At
	public Object exportDongHangTemplate(HttpServletResponse response, HttpSession session) {
		return planMakeService.exportDongHangTemplate(response, session);
	}

	/**
	 * 导出南航模板
	 */
	@At
	public Object exportNanHangTemplate(HttpServletResponse response, HttpSession session) {
		return planMakeService.exportNanHangTemplate(response, session);
	}

	/**
	 * 导出凌云模板
	 */
	@At
	public Object exportGuoTaiTemplate(HttpServletResponse response, HttpSession session) {
		return planMakeService.exportGuoTaiTemplate(response, session);
	}

	@At
	public Object exportLingYunTemplate(HttpServletResponse response, HttpSession session) {
		return planMakeService.exportLingYunTemplate(response, session);
	}

	/**
	 * 判断是否存在未保存的计划
	 */
	@At
	public Object isHavePlanData(HttpSession session) {
		return planMakeService.isHavePlanData(session);
	}

	/**
	 * 删除计划制作临时信息
	 */
	@At
	public Object deleteMakePlanData(HttpSession session) {
		return planMakeService.deleteMakePlanData(session);
	}

	/**
	 * 导出新航模板
	 */
	@At
	public Object exportXinHangTemplate(HttpSession session, HttpServletResponse response) {
		return exportXinHangService.exportXinHangTemplate(session, response);
	}
}