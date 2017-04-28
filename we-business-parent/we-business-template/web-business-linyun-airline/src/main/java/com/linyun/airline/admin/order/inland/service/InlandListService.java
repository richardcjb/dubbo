/**
 * InlandListService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.companydict.comdictinfo.entity.ComThirdPayMentEntity;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.OrderStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月6日 	 
 */
@IocBean
public class InlandListService extends BaseService<TUpOrderEntity> {

	private static final int ENABLE = BankCardStatusEnum.ENABLE.intKey();

	/**
	 * 格式化订单状态
	 * <p>
	 * TODO格式化订单状态
	 *
	 * @param status
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object formatOrderStatus(Integer status) {
		String result = "";
		for (OrderStatusEnum orderstatus : OrderStatusEnum.values()) {
			if (status == orderstatus.intKey()) {
				result = orderstatus.value();
				break;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Object saveCustomeneedInfo(HttpServletRequest request) {
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//客户信息id
		String customneedid = (String) fromJson.get("customneedid");
		//客户需求出发城市
		String leavecity = (String) fromJson.get("leavecity");
		//客户需求抵达城市
		String arrivecity = (String) fromJson.get("arrivecity");
		//日期
		String leavedate = (String) fromJson.get("leavedate");
		Integer peoplecount = null;
		if (!Util.isEmpty(fromJson.get("peoplecount"))) {
			peoplecount = Integer.valueOf((String) fromJson.get("peoplecount"));
		}
		Integer tickettype = null;
		if (!Util.isEmpty(fromJson.get("tickettype"))) {
			tickettype = Integer.valueOf((String) fromJson.get("tickettype"));
		}
		Double realtimexrate = null;
		if (!Util.isEmpty(fromJson.get("realtimexrate"))) {
			realtimexrate = formatDouble(Double.valueOf((String) fromJson.get("realtimexrate")));
		}
		Double avgexrate = null;
		if (!Util.isEmpty(fromJson.get("avgexrate"))) {
			avgexrate = formatDouble(Double.valueOf((String) fromJson.get("avgexrate")));
		}
		String paycurrency = (String) fromJson.get("paycurrency");
		Integer paymethod = null;
		if (!Util.isEmpty(fromJson.get("paymethod"))) {
			paymethod = Integer.valueOf((String) fromJson.get("paymethod"));
		}
		String remark = (String) fromJson.get("remark");
		TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
		customneedEntity.setLeavecity(leavecity);
		customneedEntity.setArrivecity(arrivecity);
		if (!Util.isEmpty(leavedate)) {
			customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
		}
		customneedEntity.setPeoplecount(peoplecount);
		customneedEntity.setTickettype(tickettype);
		customneedEntity.setRealtimexrate(realtimexrate);
		customneedEntity.setAvgexrate(avgexrate);
		customneedEntity.setPaycurrency(paycurrency);
		customneedEntity.setPaymethod(paymethod);
		customneedEntity.setRemark(remark);
		//与订单相关
		customneedEntity.setOrdernum(Integer.valueOf((String) fromJson.get("orderid")));
		TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
		customneedid = String.valueOf(insertCus.getId());
		//TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
		//航班信息
		List<Map<String, Object>> airinfo = (List<Map<String, Object>>) fromJson.get("airinfo");
		List<TAirlineInfoEntity> airlinelist = new ArrayList<TAirlineInfoEntity>();
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
			Double formprice = null;
			if (!Util.isEmpty(airmap.get("formprice"))) {
				formprice = formatDouble(Double.valueOf((String) airmap.get("formprice")));
			}
			//销售价
			Double price = null;
			if (!Util.isEmpty(airmap.get("price"))) {
				price = formatDouble(Double.valueOf((String) airmap.get("price")));
			}
			TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
			airlineEntity.setAircom(aircom);
			airlineEntity.setAilinenum(ailinenum);
			airlineEntity.setLeavetime(leavetime);
			airlineEntity.setArrivetime(arrivetime);
			airlineEntity.setFormprice(formprice);
			airlineEntity.setPrice(price);
			airlineEntity.setNeedid(Integer.valueOf(customneedid));
			airlinelist.add(airlineEntity);
		}
		//添加航班信息
		if (airlinelist.size() > 0) {
			dbDao.insert(airlinelist);
		}
		return insertCus;

	}

	/**
	 * 加载银行卡名称下拉
	 * <p>
	 * TODO加载银行卡名称下拉
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadBankCardNameSelect(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String bankcardid = request.getParameter("bankcardid");
		DictInfoEntity fetch = dbDao.fetch(DictInfoEntity.class, Long.valueOf(bankcardid));
		Cnd cnd = Cnd.NEW();
		cnd.and("bankName", "=", fetch.getDictName());
		cnd.and("status", "=", ENABLE);
		cnd.and("companyId", "=", company.getId());
		List<TBankCardEntity> banklist = dbDao.query(TBankCardEntity.class, cnd, null);
		return banklist;
	}

	//根据银行卡名称查询银行卡  卡号
	public Object getCardNums(String cardName, HttpSession session) {
		List<String> cardNums = new ArrayList<String>();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class,
				Cnd.where("status", "=", ENABLE).and("cardName", "=", cardName).and("companyId", "=", companyId), null);
		for (TBankCardEntity bankCardEntity : bankCardList) {
			cardNums.add(bankCardEntity.getCardNum());
		}

		return cardNums;
	}

	public Object loadBankCardNumSelect(HttpServletRequest request) {
		String bankcardname = request.getParameter("bankcardname");
		TBankCardEntity fetch = dbDao.fetch(TBankCardEntity.class, Long.valueOf(bankcardname));
		// TODO Auto-generated method stub
		return fetch;

	}

	/**
	 * 设置财务信息
	 * <p>
	 * TODO设置财务信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object setFinanceInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		String sqlstring = sqlManager.get("select_order_pnrs_info");
		Sql sql = Sqls.create(sqlstring);
		Cnd cnd = Cnd.NEW();
		cnd.and("toc.ordernum", "=", orderid);
		List<Record> query = dbDao.query(sql, cnd, null);
		double yingshousum = 0;
		double chengbensum = 0;
		int renshusum = 0;
		for (Record record : query) {
			if (!Util.isEmpty(record.get("costpricesum"))) {
				chengbensum += Double.valueOf((Double) record.get("costpricesum"));
			}
			if (!Util.isEmpty(record.get("salespricesum"))) {
				yingshousum += Double.valueOf((Double) record.get("salespricesum"));
			}
			if (!Util.isEmpty(record.get("peoplecount"))) {
				renshusum += (Integer) record.get("peoplecount");
			}
		}
		result.put("chengbensum", formatDouble(chengbensum));
		result.put("yingshousum", formatDouble(yingshousum));
		result.put("renshusum", renshusum);
		return result;
	}

	/**
	 * 保留两位小数
	 */
	@SuppressWarnings("unused")
	private Double formatDouble(Double doublenum) {
		Double result = null;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		if (!Util.isEmpty(doublenum)) {
			String format = decimalFormat.format(doublenum);
			result = Double.valueOf(format);
		}
		return result;
	}

	/**
	 * 加载客户下拉
	 * <p>
	 * TODO加载客户下拉
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadCustomeSelect(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String customerneedid = request.getParameter("customerneedid");
		Integer threepaymethod = null;
		if (!Util.isEmpty(customerneedid)) {
			TOrderCustomneedEntity customerneed = dbDao.fetch(TOrderCustomneedEntity.class,
					Long.valueOf(customerneedid));
			threepaymethod = customerneed.getThirdcustomid();
		}
		result.put("threepaymethod", threepaymethod);
		//		String sqlString = sqlManager.get("customer_list_info");
		List<ComThirdPayMentEntity> thirdPayMent = dbDao.query(ComThirdPayMentEntity.class,
				Cnd.where("comId", "=", company.getId()).and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
		result.put("thirdPayMent", thirdPayMent);
		//		Sql sql = Sqls.create(sqlString);
		//		Cnd cnd = Cnd.NEW();
		//		cnd.and("uc.comId", "=", company.getId());
		//		List<Record> query = dbDao.query(sql, cnd, null);
		return result;
	}

	/**
	 * 加载国际专用卡支付余额
	 * <p>
	 * TODO加载国际专用卡支付余额
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadBalance(HttpServletRequest request) {
		TBankCardEntity result = new TBankCardEntity();
		HttpSession session = request.getSession();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String paymethod = request.getParameter("paymethod");
		result = dbDao.fetch(TBankCardEntity.class, Long.valueOf(paymethod));
		return result;
	}

	public List<Long> getUserIdsByFun(Long companyid, Long parentid, String functionname) {
		List<Long> result = new ArrayList<Long>();
		String sqlString = sqlManager.get("select_function_user_ids");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("parentid", parentid);
		sql.setParam("companyid", companyid);
		sql.setParam("functionname", "%" + functionname + "%");
		List<Record> query = dbDao.query(sql, null, null);
		for (Record record : query) {
			if (!Util.isEmpty(record.getInt("userid"))) {
				result.add(new Integer(record.getInt("userid")).longValue());
			}
		}
		return result;
	}
}
