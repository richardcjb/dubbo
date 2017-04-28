package com.linyun.airline.admin.search.module;

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

import com.linyun.airline.admin.search.form.SearchTicketSqlForm;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/search")
public class SearchModule {

	private static final Log log = Logs.get();

	@Inject
	private SearchViewService searchViewService;

	/**
	 * 跳转到查询页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object searchTicket() {
		return null;
	}

	/**
	 * 获取客户姓名下拉
	 */
	@At
	@POST
	public Object getLinkNameSelect(@Param("linkname") String linkname, HttpSession session) {
		return searchViewService.getLinkNameSelect(linkname, session);
	}

	/**
	 * 获取電話下拉
	 */
	@At
	@POST
	public Object getPhoneNumSelect(@Param("phonenum") String phonenum, HttpSession session) {
		return searchViewService.getPhoneNumSelect(phonenum, session);
	}

	/**
	 * 根据Id查询客户信息
	 */
	@At
	@POST
	public Object getCustomerById(@Param("id") Long id) {
		return searchViewService.getCustomerById(id);
	}

	//根据客户Id查询出发城市
	@At
	@POST
	public Object getCitys(@Param("id") final long id) throws Exception {
		return searchViewService.getCitys(id);
	}

	String typeCodestr = "GJNL";

	/**
	 * 初始化 线路类型
	 */
	@At
	@POST
	public String initCityTypeCode(@Param("typeCode") String typeCode) {
		if ("GJNL".equals(typeCode)) {
			typeCodestr = "GJNL";
		}
		if ("GJ".equals(typeCode)) {
			typeCodestr = "GJ";
		}
		return typeCodestr;
	}

	/**
	 * 获取出发、抵达城市下拉
	 */
	@At
	@POST
	public Object getCitySelect(@Param("cityname") String cityname, @Param("ids") final String ids) {
		String typeCode = "CFCS";
		return searchViewService.getCitySelect(cityname, typeCode, ids);
	}

	/**
	 * 获取航空公司下拉
	 */
	@At
	@POST
	public Object getAirLineSelect(@Param("airlinename") String airlinename, @Param("ids") final String ids) {
		return searchViewService.getAirLineSelect(airlinename, ids);
	}

	/**
	 * 根据代码 获取航空公司名称
	 */
	@At
	@POST
	public String getCAirNameByCode(@Param("airCompCode") String airCompCode) {
		String typeCode = "HKGS";
		return searchViewService.getCAirNameByCode(airCompCode, typeCode);
	}

	/**
	 * 获取航班号下拉
	 */
	@At
	@POST
	public Object getCAirNumSelect(@Param("airlinename") String airlinename, @Param("exname") String exname) {
		return searchViewService.getCAirNumSelect(airlinename, exname);
	}

	/**
	 * 查询跨海内陆机票
	 */
	@At
	@POST
	public Object searchSingleTickets(@Param("..") InstaFlightsSearchForm searchForm) {
		return searchViewService.searchSingleTickets(searchForm);
	}

	/**
	 * 查询国际机票
	 */
	@At
	@POST
	public Object searchTeamTickets(@Param("..") SearchTicketSqlForm sqlForm, HttpSession session) {
		return searchViewService.listPage4Datatables(sqlForm, session);
	}

	/**
	 * 获取客户需求 城市下拉
	 */
	@At
	@POST
	public Object getCustomerCitySelect(@Param("cityname") String cityname, @Param("exname") String exname) {
		return searchViewService.getCustomerCitySelect(cityname, exname);
	}

	/**
	 * 解析sbare
	 */
	@At
	@POST
	public Object parsingPNR(@Param("sabrePNR") String sabrePNR) {
		return searchViewService.parsingPNR(sabrePNR);
	}

	/**
	 * 解析etem
	 */
	@At
	@POST
	public Object parsingEtem(@Param("etemStr") String etemStr) {
		return searchViewService.parsingEtem(etemStr);
	}

	/**
	 * 保存查询结果
	 */
	@At
	@POST
	public Object saveCustomerNeeds(@Param("data") String data, HttpSession session) {
		try {
			Object map = searchViewService.saveCustomerNeeds(data, session);
			return MobileResult.success("修改成功", map);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("修改失败");
		}
	}

}