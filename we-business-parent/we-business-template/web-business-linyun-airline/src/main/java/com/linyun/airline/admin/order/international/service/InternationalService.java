/**
 * InternationalService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PassengerTypeEnum;
import com.linyun.airline.admin.order.inland.enums.PayMethodEnum;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.inland.service.InlandListService;
import com.linyun.airline.admin.order.inland.util.FormatDateUtil;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.order.international.form.InternationalParamForm;
import com.linyun.airline.admin.order.international.form.InternationalPayParamForm;
import com.linyun.airline.admin.order.international.form.InternationalReceiveParamForm;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.ExcelReader;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFinanceInfoEntity;
import com.linyun.airline.entities.TInterMessageEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TPayReceiveRecordEntity;
import com.linyun.airline.entities.TPlanInfoEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TVisitorInfoEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月15日 	 
 */
@IocBean
public class InternationalService extends BaseService<TUpOrderEntity> {

	//航班号字典代码
	private static final String AIRLINECODE = "HBH";
	//城市字典代码
	private static final String CITYCODE = "CFCS";
	//内陆跨海
	private static final String NLKHCODE = "NLKH";
	//币种
	private static final String BIZHONGCODE = "BZ";
	//银行卡类型
	private static final String YHCODE = "YH";
	//付款用途
	private static final String FKYTCODE = "FKYT";
	//航空公司字典代码
	private static final String AIRCOMCODE = "HKGS";
	@Inject
	private EditPlanService editPlanService;
	@Inject
	private externalInfoService externalInfoService;
	@Inject
	private InterReceivePayService interReceivePayService;
	@Inject
	private InlandListService inlandListService;
	@Inject
	private SearchViewService searchViewService;

	/**
	 * 查询国际列表
	 * <p>
	 * TODO查询国际列表
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalListData(InternationalParamForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		paramForm.setCompanyid(new Long(company.getId()).intValue());
		paramForm.setAdminId(company.getAdminId().intValue());
		paramForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listPageData = this.listPage4Datatables(paramForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		for (Record record : list) {
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("planid", "=", record.get("id")).orderBy("leavedate", "asc"), null);
			record.put("airinfo", query);
			record.put("orderstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		listPageData.remove("data");
		listPageData.put("data", list);
		return listPageData;
	}

	/**
	 * 查询国际订单收款列表
	 * <p>
	 * TODO查询国际订单收款列表
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalReceiveListData(InternationalReceiveParamForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		paramForm.setCompanyid(new Long(company.getId()).intValue());
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setAdminId(company.getAdminId().intValue());
		Map<String, Object> listPageData = this.listPage4Datatables(paramForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		for (Record record : list) {
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("planid", "=", record.get("id")).orderBy("leavedate", "asc"), null);
			record.put("airinfo", query);
			record.put("orderstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		listPageData.remove("data");
		listPageData.put("data", list);
		return listPageData;
	}

	/**
	 * 查询国际订单付款列表
	 * <p>
	 * TODO查询国际订单付款列表
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalPayListData(InternationalPayParamForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setAdminId(company.getAdminId().intValue());
		paramForm.setCompanyid(new Long(company.getId()).intValue());
		Map<String, Object> listPageData = this.listPage4Datatables(paramForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		for (Record record : list) {
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("planid", "=", record.get("id")).orderBy("leavedate", "asc"), null);
			record.put("airinfo", query);
			record.put("orderstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		listPageData.remove("data");
		listPageData.put("data", list);
		return listPageData;
	}

	/**
	 * 跳转到添加国际订单页面
	 * <p>
	 * TODO跳转到添加国际订单页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveAddOrder(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		//生成订单号
		if (generateOrder) {
			orderinfo.setOrdersnum(editPlanService.generateOrderNum());
		}
		orderinfo.setOrderstype(OrderTypeEnum.TEAM.intKey());
		TUpOrderEntity insertOrder = dbDao.insert(orderinfo);
		List<Map<String, Object>> customdata = (List<Map<String, Object>>) fromJson.get("customdata");
		for (Map<String, Object> map : customdata) {
			//客户需求出发城市
			String leavecity = (String) map.get("leavecity");
			//客户需求抵达城市
			String arrivecity = (String) map.get("arrivecity");
			//日期
			String leavedate = (String) map.get("leavedate");
			Integer peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			Integer tickettype = Integer.valueOf((String) map.get("tickettype"));
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			if (!Util.isEmpty(leavedate)) {
				customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
			}
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			//与订单相关
			customneedEntity.setOrdernum(insertOrder.getId());
			TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			List<TAirlineInfoEntity> airlist = new ArrayList<TAirlineInfoEntity>();
			for (Map<String, Object> airmap : airinfo) {
				//航空公司
				String aircom = (String) airmap.get("aircom");
				//航班号
				String ailinenum = (String) airmap.get("ailinenum");
				//出发时间
				String leavetime = (String) airmap.get("leavetime");
				//抵达时间
				String arrivetime = (String) airmap.get("arrivetime");
				//成本价
				Double formprice = Double.valueOf((String) airmap.get("formprice"));
				//销售价
				Double price = Double.valueOf((String) airmap.get("price"));
				TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
				airlineEntity.setAircom(aircom);
				airlineEntity.setAilinenum(ailinenum);
				airlineEntity.setLeavetime(leavetime);
				airlineEntity.setArrivetime(arrivetime);
				airlineEntity.setFormprice(formprice);
				airlineEntity.setPrice(price);
				airlineEntity.setNeedid(insertCus.getId());
				airlist.add(airlineEntity);
				//添加航班信息
			}
			dbDao.insert(airlist);
		}
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 跳转到国际订单详情页
	 * <p>
	 * TODO跳转到国际订单详情页
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalDetail(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		result.put("user", user);
		String orderid = request.getParameter("orderid");
		//订单数据
		TUpOrderEntity orderinfo = this.fetch(Long.valueOf(orderid));
		result.put("orderinfo", orderinfo);
		TCustomerInfoEntity custominfo = new TCustomerInfoEntity();
		if (!Util.isEmpty(orderinfo.getUserid())) {
			custominfo = dbDao.fetch(TCustomerInfoEntity.class, orderinfo.getUserid().longValue());
		}
		result.put("custominfo", custominfo);
		Double historymony = searchViewService.getMoney(orderinfo.getUserid().longValue());
		result.put("historymony", historymony);
		//异步加载
		TFinanceInfoEntity finance = dbDao.fetch(TFinanceInfoEntity.class, Cnd.where("orderid", "=", orderid));
		result.put("finance", finance);
		result.put("internationalstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		result.put("passengertypeenum", EnumUtil.enum2(PassengerTypeEnum.class));
		//内陆跨海下拉
		List<DictInfoEntity> nlkhcode = new ArrayList<DictInfoEntity>();
		try {
			nlkhcode = externalInfoService.findDictInfoByName("", NLKHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("nlkhcode", nlkhcode);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		result.put("paymethodenum", EnumUtil.enum2(PayMethodEnum.class));
		//支付方式
		List<TBankCardEntity> paymethod = dbDao.query(TBankCardEntity.class,
				Cnd.where("companyId", "=", company.getId()).and("status", "=", BankCardStatusEnum.ENABLE.intKey()),
				null);
		TBankCardEntity bankCardEntity = new TBankCardEntity();
		bankCardEntity.setId(PayMethodEnum.THIRDPART.intKey());
		bankCardEntity.setBankName(PayMethodEnum.THIRDPART.value());
		bankCardEntity.setCardName(PayMethodEnum.THIRDPART.value());
		paymethod.add(0, bankCardEntity);
		result.put("paymethod", paymethod);
		result.put("receivestatus", PayReceiveTypeEnum.RECEIVE.intKey());
		result.put("paystatus", PayReceiveTypeEnum.PAY.intKey());
		result.put("aircomselect", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
		return result;
	}

	/**
	 * 保存国际订单详情数据
	 * <p>
	 * TODO保存国际订单详情数据
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveInternationalDetail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Long orderedid = Long.valueOf((String) fromJson.get("orderedid"));
		TUpOrderEntity orderinfo = this.fetch(orderedid);
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		orderinfo.setOrdersstatus(orderType);
		if (!Util.isEmpty(fromJson.get("peoplecount"))) {
			orderinfo.setPeoplecount(Integer.valueOf((String) fromJson.get("peoplecount")));
		}
		orderinfo.setAirlinecom((String) fromJson.get("airlinecom"));
		if (!Util.isEmpty(fromJson.get("costsingleprice"))) {
			orderinfo.setCostsingleprice(Double.valueOf((String) fromJson.get("costsingleprice")));
		}
		dbDao.update(orderinfo);
		String financeData = request.getParameter("financeData");
		saveFinanceData(financeData, orderType, user);
		return null;
	}

	/**
	 * 保存财务信息
	 */
	@SuppressWarnings("unchecked")
	private Object saveFinanceData(String financeData, Integer orderType, TUserEntity user) {
		Map<String, String> financeMap = JsonUtil.fromJson(financeData, Map.class);
		//财务信息id
		String id = financeMap.get("id");
		//订单id
		Integer orderid = null;
		if (!Util.isEmpty(financeMap.get("orderid"))) {
			orderid = Integer.valueOf(financeMap.get("orderid"));
		}
		//客户团号
		String cusgroupnum = financeMap.get("cusgroupnum");
		//类型
		Integer teamtype = null;
		if (!Util.isEmpty(financeMap.get("teamtype"))) {
			teamtype = Integer.valueOf(financeMap.get("teamtype"));
		}
		//内陆跨海
		String neilu = financeMap.get("neilu");
		//人数
		Integer personcount = null;
		if (!Util.isEmpty(financeMap.get("personcount"))) {
			personcount = Integer.valueOf(financeMap.get("personcount"));
		}
		Integer paycurrency = null;
		if (!Util.isEmpty(financeMap.get("paycurrency"))) {
			paycurrency = Integer.valueOf(financeMap.get("paycurrency"));
		}
		Integer paymethod = null;
		if (!Util.isEmpty(financeMap.get("paymethod"))) {
			paymethod = Integer.valueOf(financeMap.get("paymethod"));
		}
		//结算状态
		Integer billingstatus = null;
		if (!Util.isEmpty(financeMap.get("billingstatus"))) {
			billingstatus = Integer.valueOf(financeMap.get("billingstatus"));
		}
		//如澳时间
		Date enterausdate = null;
		if (!Util.isEmpty(financeMap.get("enterausdate"))) {
			enterausdate = DateUtil.string2Date(financeMap.get("enterausdate"), DateUtil.FORMAT_YYYY_MM_DD);
		}
		//出澳时间
		Date outausdate = null;
		if (!Util.isEmpty(financeMap.get("outausdate"))) {
			outausdate = DateUtil.string2Date(financeMap.get("outausdate"), DateUtil.FORMAT_YYYY_MM_DD);
		}
		//应收
		Double receivable = null;
		if (!Util.isEmpty(financeMap.get("receivable"))) {
			receivable = Double.valueOf(financeMap.get("receivable"));
		}
		//实收合计
		Double incometotal = null;
		if (!Util.isEmpty(financeMap.get("incometotal"))) {
			incometotal = Double.valueOf(financeMap.get("incometotal"));
		}
		//成本合计
		Double costtotal = null;
		if (!Util.isEmpty(financeMap.get("costtotal"))) {
			costtotal = Double.valueOf(financeMap.get("costtotal"));
		}
		//应返合计
		Double returntotal = null;
		if (!Util.isEmpty(financeMap.get("returntotal"))) {
			returntotal = Double.valueOf(financeMap.get("returntotal"));
		}
		//利润合计
		Double profittotal = null;
		if (!Util.isEmpty(financeMap.get("profittotal"))) {
			profittotal = Double.valueOf(financeMap.get("profittotal"));
		}
		//减免
		Double relief = null;
		if (!Util.isEmpty(financeMap.get("relief"))) {
			relief = Double.valueOf(financeMap.get("relief"));
		}
		TFinanceInfoEntity financeInfo = new TFinanceInfoEntity();
		//开票日期
		Date billingdate = null;
		if (!Util.isEmpty(financeMap.get("billingdate"))) {
			billingdate = DateUtil.string2Date(financeMap.get("billingdate"), DateUtil.FORMAT_YYYY_MM_DD);
		}
		if (orderType.equals(InternationalStatusEnum.TICKETING.intKey())) {
			billingdate = new Date();
			financeInfo.setIssuer(user.getFullName());
			financeInfo.setIssuerid(new Long(user.getId()).intValue());
		}
		//销售人员
		String salesperson = financeMap.get("salesperson");
		String enteraircom = financeMap.get("enteraircom");
		String enterstarttime = financeMap.get("enterstarttime");
		String enterarrivetime = financeMap.get("enterarrivetime");
		String outaircom = financeMap.get("outaircom");
		String outstarttime = financeMap.get("outstarttime");
		String outarrivetime = financeMap.get("outarrivetime");
		//开票人
		String issuer = financeMap.get("issuer");
		financeInfo.setOrderid(orderid);
		financeInfo.setCusgroupnum(cusgroupnum);
		financeInfo.setTeamtype(teamtype);
		financeInfo.setNeilu(neilu);
		financeInfo.setPersoncount(personcount);
		financeInfo.setBillingstatus(billingstatus);
		financeInfo.setEnterausdate(enterausdate);
		financeInfo.setOutausdate(outausdate);
		financeInfo.setReceivable(receivable);
		financeInfo.setIncometotal(incometotal);
		financeInfo.setCosttotal(costtotal);
		financeInfo.setReturntotal(returntotal);
		financeInfo.setProfittotal(profittotal);
		financeInfo.setRelief(relief);
		financeInfo.setBillingdate(billingdate);
		financeInfo.setSalesperson(salesperson);
		financeInfo.setPaycurrency(paycurrency);
		financeInfo.setPaymethod(paymethod);
		financeInfo.setEnteraircom(enteraircom);
		financeInfo.setEnterstarttime(enterstarttime);
		financeInfo.setEnterarrivetime(enterarrivetime);
		financeInfo.setOutaircom(outaircom);
		financeInfo.setOutstarttime(outstarttime);
		financeInfo.setOutarrivetime(outarrivetime);

		if (Util.isEmpty(id)) {
			dbDao.insert(financeInfo);
		} else {
			financeInfo.setId(Integer.valueOf(id));
			dbDao.update(financeInfo);
		}
		return null;
	}

	/**
	 * 跳转到添加航段页面
	 * <p>
	 * TODO跳转到添加航段页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addAirinfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		result.put("orderid", orderid);
		return result;
	}

	/**
	 * 保存航段信息
	 * <p>
	 * TODO保存航段信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveAirinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//判断是否是主航段
		Integer orderid = Integer.valueOf((String) fromJson.get("orderid"));
		List<TPnrInfoEntity> query = dbDao.query(TPnrInfoEntity.class, Cnd.where("orderid", "=", orderid), null);
		TPlanInfoEntity planinfo = dbDao.fetch(TPlanInfoEntity.class, Cnd.where("ordernumber", "=", orderid));
		String pnr = (String) fromJson.get("pnr");
		TPnrInfoEntity pnrinfo = new TPnrInfoEntity();
		pnrinfo.setOrderid(orderid);
		pnrinfo.setPNR(pnr);
		//是否是主航段
		if (query.size() > 0) {
			pnrinfo.setMainsection(0);
		} else {
			pnrinfo.setMainsection(1);
		}
		TPnrInfoEntity insertpnr = dbDao.insert(pnrinfo);
		List<Map<String, String>> airinfos = (List<Map<String, String>>) fromJson.get("airinfos");
		List<TAirlineInfoEntity> ailines = new ArrayList<TAirlineInfoEntity>();
		for (Map<String, String> map : airinfos) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(map.get("leavecity"));
			airline.setArrvicity(map.get("arrivecity"));
			if (!Util.isEmpty(map.get("leavedate"))) {
				airline.setLeavedate(DateUtil.string2Date(map.get("leavedate"), DateUtil.FORMAT_YYYY_MM_DD));
			}
			airline.setLeavetime(map.get("leavetime"));
			airline.setArrivetime(map.get("arrivetime"));
			airline.setAilinenum(map.get("ailinenum"));
			airline.setPnrid(insertpnr.getId());
			airline.setPlanid(planinfo.getId());
			ailines.add(airline);
		}
		return dbDao.insert(ailines);

	}

	/**
	 * 跳转到编辑航段信息页面
	 * <p>
	 * TODO跳转到编辑航段信息页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editAirinfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String pnrid = request.getParameter("pnrid");
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(pnrid));
		List<TAirlineInfoEntity> airlineinfo = dbDao.query(TAirlineInfoEntity.class, Cnd.where("pnrid", "=", pnrid),
				null);
		result.put("pnrinfo", pnrinfo);
		result.put("airinfo", airlineinfo);
		//准备城市下拉数据
		//List<DictInfoEntity> city = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", CITYCODE), null);
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//航班号下拉
		result.put("airline", externalInfoService.findDictInfoByText("", AIRLINECODE));
		return result;
	}

	/**
	 * 保存编辑航段信息
	 * <p>
	 * TODO保存编辑航段信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveEditAirinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//判断是否是主航段
		Integer pnrid = Integer.valueOf((String) fromJson.get("pnrid"));
		String pnr = (String) fromJson.get("pnr");
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, pnrid.intValue());
		pnrinfo.setPNR(pnr);
		dbDao.update(pnrinfo);
		TPlanInfoEntity planinfo = dbDao.fetch(TPlanInfoEntity.class,
				Cnd.where("ordernumber", "=", pnrinfo.getOrderid()));
		List<Map<String, String>> airinfos = (List<Map<String, String>>) fromJson.get("airinfos");
		//查询之前的
		List<TAirlineInfoEntity> before = dbDao.query(TAirlineInfoEntity.class, Cnd.where("pnrid", "=", pnrid), null);
		//组装之后的
		List<TAirlineInfoEntity> ailines = new ArrayList<TAirlineInfoEntity>();
		for (Map<String, String> map : airinfos) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(map.get("leavecity"));
			airline.setArrvicity(map.get("arrivecity"));
			if (!Util.isEmpty(map.get("leavedate"))) {
				airline.setLeavedate(DateUtil.string2Date(map.get("leavedate"), DateUtil.FORMAT_YYYY_MM_DD));
			}
			airline.setLeavetime(map.get("leavetime"));
			airline.setArrivetime(map.get("arrivetime"));
			airline.setAilinenum(map.get("ailinenum"));
			airline.setPnrid(pnrid);
			airline.setPlanid(planinfo.getId());
			ailines.add(airline);
		}
		dbDao.updateRelations(before, ailines);
		return null;
	}

	/**
	 * 上传游客信息
	 * <p>
	 * TODO上传游客信息
	 * @param file 
	 * @param pnrid 
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object uploadVisitorInfo(File file, Integer pnrid, HttpServletRequest request) {
		//String dingdanid = request.getParameter("dingdanid");
		//String pnrid = request.getParameter("pnrid");
		try {
			InputStream is = new FileInputStream(file);
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);
			List<TVisitorInfoEntity> visitors = new ArrayList<TVisitorInfoEntity>();
			for (int i = 1; i <= map.size(); i++) {
				String[] row = map.get(i);
				TVisitorInfoEntity visitor = new TVisitorInfoEntity();
				//订单id
				visitor.setPnrid(Integer.valueOf(pnrid));
				//游客姓名
				visitor.setVisitorname(row[0]);
				//游客电话
				visitor.setPhonenum(row[1]);
				visitor.setGender(row[2]);
				visitor.setVisitortype(row[3]);
				visitor.setCardtype(row[4]);
				visitor.setCardnum(row[5]);
				visitors.add(visitor);
			}
			//导入数据库
			dbDao.insert(visitors);
			//删除备份文件
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 游客信息
	 * <p>
	 * TODO游客信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visitorInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String pnrid = request.getParameter("pnrid");
		List<TVisitorInfoEntity> visitors = dbDao.query(TVisitorInfoEntity.class, Cnd.where("pnrid", "=", pnrid), null);
		result.put("visitors", visitors);
		return result;
	}

	/**
	 * 跳转到添加记录页面
	 * <p>
	 * TODO跳转到添加记录页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		String payreceivestatus = request.getParameter("payreceivestatus");
		String ordersstatus = request.getParameter("ordersstatus");
		String peoplecount = request.getParameter("peoplecount");
		String costsingleprice = request.getParameter("costsingleprice");
		//客户信息
		String customerId = request.getParameter("customerId");
		TCustomerInfoEntity custominfo = new TCustomerInfoEntity();
		if (!Util.isEmpty(customerId)) {
			custominfo = dbDao.fetch(TCustomerInfoEntity.class, Long.valueOf(customerId));
		}
		result.put("custominfo", custominfo);
		result.put("orderid", orderid);
		result.put("recordtype", payreceivestatus);
		result.put("ordersstatus", ordersstatus);
		result.put("costsingleprice", costsingleprice);
		//准备实际人数数据
		String sqlString = sqlManager.get("get_international_last_payreceive_record");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("t.orderid", "=", orderid);
		cnd.and("t.recordtype", "=", payreceivestatus);
		List<Record> payreceiverecord = dbDao.query(sql, cnd, null);
		if (payreceiverecord.size() > 0) {
			if (!Util.isEmpty(payreceiverecord.get(0).get("actualnumber"))) {
				peoplecount = String.valueOf(payreceiverecord.get(0).get("actualnumber"));
			}
		}
		//准备罚金
		Integer fineprice = 0;
		String sqlString1 = sqlManager.get("get_international_record_sumprice");
		Sql sql1 = Sqls.create(sqlString1);
		Cnd cnd1 = Cnd.NEW();
		cnd1.and("orderid", "=", orderid);
		cnd1.and("recordtype", "=", payreceivestatus);
		cnd1.groupBy("orderid", "recordtype");
		List<Record> payreceiverecordsum = dbDao.query(sql1, cnd1, null);
		if (payreceiverecordsum.size() > 0 && !Util.isEmpty(payreceiverecordsum.get(0).get("yishou"))) {
			fineprice = payreceiverecordsum.get(0).getInt("yishou");
		}
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		result.put("autualypeople", peoplecount);
		result.put("fineprice", fineprice);
		return result;
	}

	/**
	 * 保存记录
	 * <p>
	 * TODO保存记录
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveRecord(HttpServletRequest request, TPayReceiveRecordEntity payreceive) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		payreceive.setOpid(new Long(user.getId()).intValue());
		payreceive.setOptime(new Date());
		if (!Util.isEmpty(payreceive.getOrderstatus())) {
			payreceive.setOrderstatusid(Integer.valueOf(payreceive.getOrderstatus()));
			for (InternationalStatusEnum payenum : InternationalStatusEnum.values()) {
				if (Integer.valueOf(payreceive.getOrderstatus()).equals(payenum.intKey())) {
					payreceive.setOrderstatus(payenum.value());
					break;
				}
			}
		}
		TPayReceiveRecordEntity payrecord = dbDao.insert(payreceive);
		Integer orderid = payrecord.getOrderid();
		TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, orderid.longValue());
		TCustomerInfoEntity custome = dbDao.fetch(TCustomerInfoEntity.class, order.getUserid().longValue());
		//票价折扣
		double discountFare = 1;
		if (!Util.isEmpty(custome.getDiscountFare())) {
			discountFare = custome.getDiscountFare();
		}
		//手续费
		double fees = 0;
		if (!Util.isEmpty(custome.getFees())) {
			fees = custome.getFees();
		}
		//收款信息
		TPayReceiveRecordEntity receiveEntity = new TPayReceiveRecordEntity();
		//成本单价
		double costprice = payrecord.getCostprice() * discountFare / 100 + fees;
		receiveEntity.setCostprice(costprice);
		Integer recordtype = PayReceiveTypeEnum.RECEIVE.intKey();
		//记录类型
		receiveEntity.setRecordtype(recordtype);
		//预付款比例
		receiveEntity.setPrepayratio(payrecord.getPrepayratio());
		double prepayratio = 0;
		if (!Util.isEmpty(payrecord.getPrepayratio())) {
			prepayratio = payrecord.getPrepayratio();
		}
		//实际人数
		receiveEntity.setActualnumber(payrecord.getActualnumber());
		Integer actualnumber = 0;
		if (!Util.isEmpty(payrecord.getActualnumber())) {
			actualnumber = payrecord.getActualnumber();
		}
		//免罚金可减人数
		receiveEntity.setFreenumber(payrecord.getFreenumber());
		Integer freenumber = 0;
		if (!Util.isEmpty(payrecord.getFreenumber())) {
			freenumber = payrecord.getFreenumber();
		}
		receiveEntity.setOpid(new Long(user.getId()).intValue());
		receiveEntity.setOptime(new Date());
		//币种
		receiveEntity.setCurrency(payrecord.getCurrency());
		//订单ID
		receiveEntity.setOrderid(payrecord.getOrderid());
		//订单状态
		receiveEntity.setOrderstatus(payrecord.getOrderstatus());
		//订单状态ID
		receiveEntity.setOrderstatusid(payrecord.getOrderstatusid());
		//实际减少人数
		receiveEntity.setActualyreduce(payrecord.getActualyreduce());
		Integer actualyreduce = 0;
		if (!Util.isEmpty(payrecord.getActualyreduce())) {
			actualyreduce = payrecord.getActualyreduce();
		}
		//原有人数
		Integer peoplecount = order.getPeoplecount();
		String sqlString = sqlManager.get("get_international_last_payreceive_record");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("t.orderid", "=", orderid);
		cnd.and("t.recordtype", "=", recordtype);
		List<Record> payreceiverecord = dbDao.query(sql, cnd, null);
		if (payreceiverecord.size() > 0) {
			if (!Util.isEmpty(payreceiverecord.get(0).get("actualnumber"))) {
				peoplecount = payreceiverecord.get(0).getInt("actualnumber");
			}
		}
		//已收钱
		Integer fineprice = 0;
		String sqlString1 = sqlManager.get("get_international_record_sumprice");
		Sql sql1 = Sqls.create(sqlString1);
		Cnd cnd1 = Cnd.NEW();
		cnd1.and("orderid", "=", orderid);
		cnd1.and("recordtype", "=", recordtype);
		cnd1.groupBy("orderid", "recordtype");
		List<Record> payreceiverecordsum = dbDao.query(sql1, cnd1, null);
		if (payreceiverecordsum.size() > 0 && !Util.isEmpty(payreceiverecordsum.get(0).get("yishou"))) {
			fineprice = payreceiverecordsum.get(0).getInt("yishou");
		}
		double currentfine = 0;
		//罚金(已收钱数/之前的人数 *（实际减少人数-免罚金可减人数）)
		if (actualyreduce > freenumber) {
			currentfine = fineprice / peoplecount * (actualyreduce - freenumber);
		}
		receiveEntity.setCurrentfine(currentfine);
		//本期应付(销售单价*实际人数*预付款比例-已付)
		double currentdue = actualnumber * costprice * (prepayratio / 100) - fineprice;
		receiveEntity.setCurrentdue(currentdue);
		//税金
		double ataxprice = 0;
		if (!Util.isEmpty(payrecord.getAtaxprice())) {
			ataxprice = payrecord.getAtaxprice();
		}
		receiveEntity.setAtaxprice(ataxprice);
		receiveEntity.setCurrentpay(currentdue + ataxprice);
		return dbDao.insert(receiveEntity);
	}

	/**
	 * 跳转到编辑记录页面
	 * <p>
	 * TODO跳转到编辑记录页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String recordid = request.getParameter("recordid");
		TPayReceiveRecordEntity recordinfo = dbDao.fetch(TPayReceiveRecordEntity.class, Long.valueOf(recordid));
		result.put("recordinfo", recordinfo);
		Double prepayratio = recordinfo.getPrepayratio();
		String prepayratioresult = "";
		if (!Util.isEmpty(prepayratio)) {
			String prepayratiostr = String.valueOf(prepayratio);
			prepayratioresult = FormatDateUtil.subZeroAndDot(prepayratiostr);
		}
		result.put("prepayratio", prepayratioresult);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		return result;
	}

	/**
	 *加载详情页面航程信息
	 * <p>
	 * TODO加载详情页面航程信息
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadAirlineInfo(HttpServletRequest request) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String orderid = request.getParameter("orderid");
		List<TPnrInfoEntity> pnrinfo = dbDao.query(TPnrInfoEntity.class, Cnd.where("orderid", "=", orderid), null);
		for (TPnrInfoEntity tPnrInfoEntity : pnrinfo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pnrinfo", tPnrInfoEntity);
			List<TAirlineInfoEntity> airinfo = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("pnrid", "=", tPnrInfoEntity.getId()), null);
			for (TAirlineInfoEntity tAirlineInfoEntity : airinfo) {
				tAirlineInfoEntity.setCurrencyCode(FormatDateUtil.dateToOrderDate(tAirlineInfoEntity.getLeavedate()));
			}
			map.put("airinfo", airinfo);
			result.add(map);
		}
		return result;
	}

	/**
	 * 加载详情页面收付款记录信息
	 * <p>
	 * TODO加载详情页面收付款记录信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadPayReceiveRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		List<TPayReceiveRecordEntity> record = dbDao.query(TPayReceiveRecordEntity.class,
				Cnd.where("orderid", "=", orderid), null);
		result.put("receivestatus", PayReceiveTypeEnum.RECEIVE.intKey());
		result.put("paystatus", PayReceiveTypeEnum.PAY.intKey());
		result.put("record", record);
		return result;
	}

	/**
	 * 保存编辑记录
	 * <p>
	 * TODO保存编辑记录
	 *
	 * @param request
	 * @param payreceive
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditRecord(HttpServletRequest request, TPayReceiveRecordEntity payreceive) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		payreceive.setOpid(new Long(user.getId()).intValue());
		//payreceive.setOptime(new Date());
		return dbDao.update(payreceive);
	}

	/**
	 *编辑游客信息页面
	 * <p>
	 * TODO编辑游客信息页面
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editVisitorInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String visitorid = request.getParameter("visitorid");
		TVisitorInfoEntity visitorinfo = dbDao.fetch(TVisitorInfoEntity.class, Long.valueOf(visitorid));
		result.put("visitorinfo", visitorinfo);
		return result;
	}

	/**
	 * 保存编辑游客信息
	 * <p>
	 * TODO保存编辑游客信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditVisitorInfo(HttpServletRequest request) {
		String id = request.getParameter("id");
		TVisitorInfoEntity visitorinfo = dbDao.fetch(TVisitorInfoEntity.class, Long.valueOf(id));
		visitorinfo.setVisitorname(request.getParameter("visitorname"));
		visitorinfo.setCardtype(request.getParameter("cardtype"));
		visitorinfo.setGender(request.getParameter("gender"));
		visitorinfo.setCardnum(request.getParameter("cardnum"));
		visitorinfo.setVisitortype(request.getParameter("visitortype"));
		return dbDao.update(visitorinfo);
	}

	/**
	 * 打开退票页面
	 * <p>
	 * TODO打开退票页面
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object backTicket(HttpServletRequest request) {

		return null;
	}

	/**
	 * 打开收款页面
	 * <p>
	 * TODO 打开收款页面
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object openReceipt(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		String orderstatus = request.getParameter("orderstatus");
		String sqlString = sqlManager.get("get_international_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		//cnd.and("tuo.ordersstatus", "=", InternationalStatusEnum.TICKETING.intKey());
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("tuo.id", "in", ids);
		sql.setParam("orderstatus", orderstatus);
		sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				Double incometotal = (Double) record.get("currentpay");
				sumincome += incometotal;
			}
		}
		result.put("orders", orders);
		Sql create = Sqls.create(sqlManager.get("get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
		result.put("yhkSelect", yhkSelect);
		result.put("ids", ids);
		result.put("sumincome", sumincome);
		//订单状态
		result.put("orderstatus", orderstatus);
		return result;
	}

	/**
	 *保存收款信息
	 * <p>
	 * TODO保存收款信息
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveReceipt(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String ids = request.getParameter("ids");
		String bankcardid = request.getParameter("bankcardid");
		String bankcardname = request.getParameter("bankcardname");
		String bankcardnameid = request.getParameter("bankcardnameid");
		String bankcardnum = request.getParameter("bankcardnum");
		String billurl = request.getParameter("billurl");
		String sumincome = request.getParameter("sumincome");
		String orderstatus = request.getParameter("orderstatus");
		TReceiveEntity receiveEntity = new TReceiveEntity();
		receiveEntity.setBankcardid(Integer.valueOf(bankcardid));
		receiveEntity.setBankcardname(bankcardname);
		if (!Util.isEmpty(bankcardnameid)) {
			receiveEntity.setBankcardnameid(Integer.valueOf(bankcardnameid));
		}
		receiveEntity.setBankcardnum(bankcardnum);
		receiveEntity.setReceivedate(new Date());
		receiveEntity.setUserid(new Long(user.getId()).intValue());
		receiveEntity.setStatus(AccountReceiveEnum.RECEIVINGMONEY.intKey());
		receiveEntity.setOrderstype(OrderTypeEnum.TEAM.intKey());
		receiveEntity.setCompanyid(new Long(company.getId()).intValue());
		String customeName = "";
		if (!Util.isEmpty(ids)) {
			String orderid = ids.split(",")[0];
			TUpOrderEntity fetch = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(orderid));
			if (!Util.isEmpty(fetch.getUserid())) {
				TCustomerInfoEntity customeinfo = dbDao.fetch(TCustomerInfoEntity.class, fetch.getUserid().longValue());
				customeName = customeinfo.getShortName();
			}
		}
		//客户名称还未填写
		receiveEntity.setCustomename(customeName);
		if (!Util.isEmpty(sumincome)) {
			receiveEntity.setSum(Double.valueOf(sumincome));
		}
		if (!Util.isEmpty(orderstatus)) {
			receiveEntity.setOrderstatus(Integer.valueOf(orderstatus));
		}
		//保存收款信息
		TReceiveEntity insert = dbDao.insert(receiveEntity);
		Iterable<String> split = Splitter.on(",").split(ids);
		//组装订单收款表list
		List<TOrderReceiveEntity> orderreceives = new ArrayList<TOrderReceiveEntity>();
		//更新订单收款状态List
		List<TUpOrderEntity> orders = new ArrayList<TUpOrderEntity>();
		for (String str : split) {
			TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(str));
			TOrderReceiveEntity orderreceive = new TOrderReceiveEntity();
			orderreceive.setReceiveid(insert.getId());
			orderreceive.setOrderid(Integer.valueOf(str));
			orderreceive.setOrderstatus(Integer.valueOf(orderstatus));
			orderreceive.setReceiveDate(new Date());
			orderreceive.setReceivestatus(AccountReceiveEnum.RECEIVINGMONEY.intKey());
			orderreceives.add(orderreceive);
			//订单信息
			order.setReceivestatus(AccountReceiveEnum.RECEIVINGMONEY.intKey());
			orders.add(order);
			String pnrstr = "";
			TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class,
					Cnd.where("orderid", "=", str).and("mainsection", "=", 1));
			if (!Util.isEmpty(pnrinfo) && Util.isEmpty(pnrinfo.getPNR())) {
				pnrstr = pnrinfo.getPNR();
			}
			//消息提醒
			List<TFunctionEntity> function = dbDao.query(TFunctionEntity.class, Cnd.where("name", "=", "收付款"), null);
			long functionid = 0;
			if (function.size() > 0) {
				functionid = function.get(0).getId();
			}
			List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "国际订单");
			interReceivePayService.addInterRemindMsg(order.getId(), order.getOrdersnum(), pnrstr,
					String.valueOf(order.getOrdersstatus()), MessageWealthStatusEnum.RECSUBMITED.intKey(),
					PayReceiveTypeEnum.RECEIVE.intKey(), receiveusers, session);
		}
		//更新订单状态
		dbDao.update(orders);
		//更新订单收款表
		dbDao.insert(orderreceives);
		TReceiveBillEntity receiveBill = new TReceiveBillEntity();
		receiveBill.setReceiptUrl(billurl);
		receiveBill.setReceiveid(insert.getId());
		//更新水单表
		dbDao.insert(receiveBill);
		return null;
	}

	/**
	 * 打开付款页面
	 * <p>
	 * TODO打开付款页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object openPayment(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		String orderstatus = request.getParameter("orderstatus");
		String sqlString = sqlManager.get("get_international_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		//cnd.and("tuo.ordersstatus", "=", InternationalStatusEnum.TICKETING.intKey());
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", orderstatus);
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		/*cnd.and("tprr.orderstatusid", "=", orderstatus);
		cnd.and("tprr.recordtype", "=", PayReceiveTypeEnum.PAY.intKey());*/
		List<Record> orders = dbDao.query(sql, cnd, null);
		result.put("orders", orders);
		try {
			result.put("bzSelect", externalInfoService.findDictInfoByName("", BIZHONGCODE));
			//result.put("ytSelect", externalInfoService.findDictInfoByName("", FKYTCODE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		result.put("ytSelect", ytselect);
		result.put("user", user);
		result.put("ids", ids);
		result.put("orderstatus", orderstatus);
		return result;
	}

	/**
	 * 保存付款信息
	 * <p>
	 * TODO保存付款信息
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object savePayment(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String ids = request.getParameter("ids");
		String purpose = request.getParameter("purpose");
		String payCurrency = request.getParameter("payCurrency");
		String approver = request.getParameter("approver");
		String orderstatus = request.getParameter("orderstatus");
		TPayEntity payEntity = new TPayEntity();
		if (!Util.isEmpty(purpose)) {
			payEntity.setPurpose(Integer.valueOf(purpose));
		}
		if (!Util.isEmpty(payCurrency)) {
			payEntity.setPayCurrency(Integer.valueOf(payCurrency));
		}
		payEntity.setProposer(new Long(user.getId()).intValue());
		payEntity.setApprover(approver);
		payEntity.setCompanyId(new Long(company.getId()).intValue());
		payEntity.setOrdertype(OrderTypeEnum.TEAM.intKey());
		payEntity.setStatus(AccountPayEnum.APPROVAL.intKey());
		if (!Util.isEmpty(orderstatus)) {
			payEntity.setOrderstatus(Integer.valueOf(orderstatus));
		}
		TPayEntity insert = dbDao.insert(payEntity);
		Iterable<String> split = Splitter.on(",").split(ids);
		List<TPayOrderEntity> payorders = new ArrayList<TPayOrderEntity>();
		//更新PNR付款状态List
		List<TUpOrderEntity> orders = new ArrayList<TUpOrderEntity>();
		for (String str : split) {
			TUpOrderEntity orderifo = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(str));
			TPayOrderEntity payorder = new TPayOrderEntity();
			payorder.setPayid(insert.getId());
			payorder.setOrderid(Integer.valueOf(str));
			payorder.setPayDate(new Date());
			payorder.setOrderstatus(Integer.valueOf(orderstatus));
			payorder.setPaystauts(AccountPayEnum.APPROVAL.intKey());
			payorders.add(payorder);

			//更新订单状态
			orderifo.setPaystatus(AccountPayEnum.APPROVAL.intKey());
			orders.add(orderifo);
			String pnrstr = "";
			TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class,
					Cnd.where("orderid", "=", str).and("mainsection", "=", 1));
			if (!Util.isEmpty(pnrinfo) && Util.isEmpty(pnrinfo.getPNR())) {
				pnrstr = pnrinfo.getPNR();
			}
			//消息提醒
			List<TFunctionEntity> function = dbDao.query(TFunctionEntity.class, Cnd.where("name", "=", "收付款"), null);
			long functionid = 0;
			if (function.size() > 0) {
				functionid = function.get(0).getId();
			}
			List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "国际订单");
			interReceivePayService.addInterRemindMsg(orderifo.getId(), orderifo.getOrdersnum(), pnrstr,
					String.valueOf(orderifo.getOrdersstatus()), MessageWealthStatusEnum.PSAPPROVALING.intKey(),
					PayReceiveTypeEnum.PAY.intKey(), receiveusers, session);
		}
		//更新pnr状态
		dbDao.update(orders);
		dbDao.insert(payorders);
		return null;
	}

	public Object saveInterOrderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		TCustomerInfoEntity customerInfo = new TCustomerInfoEntity();
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//订单信息
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setOrdersnum(editPlanService.generateOrderNum());
		orderinfo.setCompanyId(new Long(company.getId()).intValue());
		if (!Util.isEmpty(fromJson.get("costsingleprice"))) {
			orderinfo.setCostsingleprice(Double.valueOf((String) fromJson.get("costsingleprice")));
		}
		orderinfo.setInterOrderStatus(Integer.valueOf((String) fromJson.get("interOrderStatus")));
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		orderinfo.setOrdersstatus(Integer.valueOf((String) fromJson.get("interOrderStatus")));
		orderinfo.setOrderstime(new Date());
		orderinfo.setOrderstype(OrderTypeEnum.TEAM.intKey());
		orderinfo.setAirlinecom((String) fromJson.get("airlinecom"));
		if (!Util.isEmpty(fromJson.get("peoplecount"))) {
			Integer peoplecount = Integer.valueOf((String) fromJson.get("peoplecount"));
			orderinfo.setPeoplecount(peoplecount);
		}
		if (!Util.isEmpty(fromJson.get("customerId"))) {
			Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
			orderinfo.setUserid(customerId);
			customerInfo = dbDao.fetch(TCustomerInfoEntity.class, customerId.longValue());
		}
		TUpOrderEntity insertOrder = dbDao.insert(orderinfo);
		//添加计划制作数据
		TPlanInfoEntity planinfo = new TPlanInfoEntity();
		planinfo.setCompanyid(new Long(company.getId()).intValue());
		planinfo.setCreatetime(new Date());
		planinfo.setIsclose(0);
		planinfo.setIssave(1);
		planinfo.setOpid(user.getId());
		planinfo.setOrdernumber(String.valueOf(insertOrder.getId()));
		if (!Util.isEmpty(fromJson.get("peoplecount"))) {
			Integer peoplecount = Integer.valueOf((String) fromJson.get("peoplecount"));
			planinfo.setPeoplecount(peoplecount);
		}
		planinfo.setTravelname(customerInfo.getShortName());
		TPlanInfoEntity insertPlanInfo = dbDao.insert(planinfo);
		//判断是否是主航段
		List<TPnrInfoEntity> query = dbDao.query(TPnrInfoEntity.class, Cnd.where("orderid", "=", insertOrder.getId()),
				null);
		String pnr = (String) fromJson.get("pnr");
		TPnrInfoEntity pnrinfo = new TPnrInfoEntity();
		pnrinfo.setOrderid(insertOrder.getId());
		pnrinfo.setPNR(pnr);
		//是否是主航段
		if (query.size() > 0) {
			pnrinfo.setMainsection(0);
		} else {
			pnrinfo.setMainsection(1);
		}
		TPnrInfoEntity insertpnr = dbDao.insert(pnrinfo);
		List<Map<String, String>> airinfos = (List<Map<String, String>>) fromJson.get("airinfos");
		List<TAirlineInfoEntity> ailines = new ArrayList<TAirlineInfoEntity>();
		for (Map<String, String> map : airinfos) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(map.get("leavecity"));
			airline.setArrvicity(map.get("arrivecity"));
			if (!Util.isEmpty(map.get("leavedate"))) {
				airline.setLeavedate(DateUtil.string2Date(map.get("leavedate"), DateUtil.FORMAT_YYYY_MM_DD));
			}
			airline.setLeavetime(map.get("leavetime"));
			airline.setArrivetime(map.get("arrivetime"));
			airline.setAilinenum(map.get("ailinenum"));
			airline.setPnrid(insertpnr.getId());
			airline.setPlanid(insertPlanInfo.getId());
			ailines.add(airline);
		}
		return dbDao.insert(ailines);
	}

	/**
	 * 消息提醒页面
	 * TODO(这里描述这个方法详情– 可选)
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object orderRemind(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		result.put("orderid", orderid);
		result.put("orderRemindEnum", EnumUtil.enum2(OrderRemindEnum.class));
		result.put(
				"booking",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.BOOKING.intKey())));
		result.put(
				"onebook",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.ONEBOOK.intKey())));
		result.put(
				"twobook",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.TWOBOOK.intKey())));
		result.put(
				"threebook",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.THREEBOOK.intKey())));
		result.put(
				"fullamount",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.FULLAMOUNT.intKey())));
		result.put(
				"tailmoney",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.TAILMONEY.intKey())));
		result.put(
				"ticketing",
				dbDao.fetch(
						TInterMessageEntity.class,
						Cnd.where("orderid", "=", orderid).and("orderstatus", "=",
								InternationalStatusEnum.TICKETING.intKey())));
		return result;
	}

	/**
	 * 保存消息提醒
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveOrderRemindInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> dataJson = JsonUtil.fromJson(data, Map.class);
		String orderid = (String) dataJson.get("orderid");
		TUpOrderEntity orderinfo = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(orderid));
		List<Map<String, String>> remindinfos = (List<Map<String, String>>) dataJson.get("remindinfos");
		List<TInterMessageEntity> before = dbDao.query(TInterMessageEntity.class, Cnd.where("orderid", "=", orderid),
				null);
		List<TInterMessageEntity> after = new ArrayList<TInterMessageEntity>();
		for (Map<String, String> map : remindinfos) {
			String orderstatus = map.get("orderstatus");
			Integer typeEnum = Integer.valueOf(map.get("remindstatus"));
			Integer remindType = Integer.valueOf(map.get("messageType"));
			String remindDate = map.get("remindData");
			TInterMessageEntity intermessage = new TInterMessageEntity();
			intermessage.setOrderid(orderinfo.getId());
			intermessage.setOrderstatus(Integer.valueOf(orderstatus));
			intermessage.setRemindtype(remindType);
			if (!Util.isEmpty(remindDate)) {
				intermessage.setReminddate(DateUtil.string2Date(remindDate, DateUtil.FORMAT_FULL_PATTERN));
				List<Long> receiveusers = new ArrayList<Long>();
				receiveusers.add(user.getId());
				interReceivePayService.addInterRepeatRemindMsg(orderinfo.getId(), orderinfo.getOrdersnum(), "",
						orderstatus, typeEnum, PayReceiveTypeEnum.REPEAT.intKey(), remindType, remindDate,
						receiveusers, session);
				after.add(intermessage);
			}
		}
		dbDao.updateRelations(before, after);
		return null;
	}
}
