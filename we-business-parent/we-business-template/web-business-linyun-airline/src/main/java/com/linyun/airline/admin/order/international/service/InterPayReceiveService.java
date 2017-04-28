/**
 * InterPayReceiveService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

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

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.inland.service.InlandListService;
import com.linyun.airline.admin.order.inland.util.FormatDateUtil;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.order.international.form.InterPaymentSqlForm;
import com.linyun.airline.admin.order.international.form.InterReceiptSqlForm;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TFinanceInfoEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
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
 * @Date	 2017年3月18日 	 
 */
@IocBean
public class InterPayReceiveService extends BaseService<TReceiveEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";
	@Inject
	private externalInfoService externalInfoService;
	@Inject
	private InterReceivePayService interReceivePayService;
	@Inject
	private InlandListService inlandListService;

	/**
	 * 国际收款列表
	 * <p>
	 * TODO国际收款列表
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listShouFuKuanData(InterReceiptSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		sqlForm.setAdminId(company.getAdminId().intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			String sqlString = sqlManager.get("get_international_receive_list_order");
			Sql sql = Sqls.create(sqlString);
			sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
			Cnd cnd = Cnd.NEW();
			cnd.and("tr.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			for (Record order : orders) {
				order.put("leavesdate", FormatDateUtil.dateToOrderDate((Date) order.get("leavesdate")));

			}
			record.put("orders", orders);
			String username = "";
			if (orders.size() > 0) {
				TFinanceInfoEntity financeinfo = dbDao.fetch(TFinanceInfoEntity.class,
						Integer.valueOf(orders.get(0).getInt("orderid")).longValue());
				if (!Util.isEmpty(financeinfo) && !Util.isEmpty(financeinfo.getIssuer())) {
					username = financeinfo.getIssuer();
				}
			}
			record.put("fullname", username);
			record.put("receiveenum", EnumUtil.enum2(AccountReceiveEnum.class));
			record.put("internationalstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		return listData;
	}

	/**
	 * 国际付款列表数据
	 * <p>
	 * TODO国际付款列表数据
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listFuKuanData(InterPaymentSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setAdminId(company.getAdminId().intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			String sqlString = sqlManager.get("get_international_pay_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.limit();
			cnd.and("tp.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			String username = "";
			if (orders.size() > 0) {
				TFinanceInfoEntity financeinfo = dbDao.fetch(TFinanceInfoEntity.class,
						Integer.valueOf(orders.get(0).getInt("orderid")).longValue());
				if (!Util.isEmpty(financeinfo) && !Util.isEmpty(financeinfo.getIssuer())) {
					username = financeinfo.getIssuer();
				}
			}
			record.put("username", username);
			record.put("orders", orders);
			record.put("receiveenum", EnumUtil.enum2(AccountPayEnum.class));
			record.put("internationalstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		return listData;
	}

	/**
	 * 打开开发票页面
	 * <p>
	 * TODO打开开发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object openInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = new HashMap<String, Object>();
		//付款id
		String id = request.getParameter("id");
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(id));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class, Cnd.where("receiveid", "=", id), null);
		Integer orderstatus = null;
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
			orderstatus = tOrderReceiveEntity.getOrderstatus();
		}
		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String sqlString = sqlManager.get("get_international_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", orderstatus);
		sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
		List<Record> orders = dbDao.query(sql, cnd, null);
		String customename = "";
		if (orders.size() > 0) {
			customename = (String) orders.get(0).get("customename");
		}
		result.put("customename", customename);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				Double incometotal = (Double) record.get("currentpay");
				sumincome += incometotal;
			}
		}
		result.put("sumincome", sumincome);
		//订单信息
		result.put("orders", orders);
		Sql create = Sqls.create(sqlManager.get("get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
		//水单信息
		List<TReceiveBillEntity> query2 = dbDao.query(TReceiveBillEntity.class, Cnd.where("receiveid", "=", id), null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//用途下拉
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		result.put("ytselect", ytselect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;
	}

	/**
	 * 保存开发票信息
	 * <p>
	 * TODO保存开发票信息
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveOpenInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		TInvoiceInfoEntity invoiceinfo = new TInvoiceInfoEntity();
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		//invoiceinfo.setBilluserid(new Long(user.getId()).intValue());
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(Double.valueOf((String) fromJson.get("difference")));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setInvoicetype(InvoiceInfoEnum.INVOIC_ING.intKey());
		if (!Util.isEmpty(fromJson.get("receiveid"))) {
			Integer receiveid = Integer.valueOf((String) fromJson.get("receiveid"));
			invoiceinfo.setReceiveid(receiveid);
			TReceiveEntity receiveinfo = dbDao.fetch(TReceiveEntity.class, receiveid.longValue());
			invoiceinfo.setOpid(receiveinfo.getUserid());
			//消息提醒
			Sql sql = Sqls.create(sqlManager.get("select_receive_order_info"));
			Cnd cnd = Cnd.NEW();
			cnd.and("tor.receiveid", "=", receiveid);
			List<Record> query = dbDao.query(sql, cnd, null);
			for (Record record : query) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
				map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
				List<TFunctionEntity> function = dbDao.query(TFunctionEntity.class, Cnd.where("name", "=", "发票管理"),
						null);
				long functionid = 0;
				if (function.size() > 0) {
					functionid = function.get(0).getId();
				}
				List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "国际发票");
				interReceivePayService.addInterRemindMsg(record.getInt("id"), record.getString("ordersnum"), "",
						record.getString("ordersstatus"), MessageWealthStatusEnum.INVIOCING.intKey(),
						PayReceiveTypeEnum.RECEIVE.intKey(), receiveusers, session);
			}
		}
		if (!Util.isEmpty(fromJson.get("orderstatus"))) {
			invoiceinfo.setOrderstatus(Integer.valueOf((String) fromJson.get("orderstatus")));
		}
		//invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setOrdertype(OrderTypeEnum.TEAM.intKey());
		invoiceinfo.setComId(new Long(company.getId()).intValue());
		invoiceinfo.setStatus(InvoiceInfoEnum.INVOIC_ING.intKey());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			invoiceDetailEntity.setInvoicebalance(Double.valueOf(map.get("invoicebalance")));
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setImagename(map.get("filename"));
			invoiceDetailEntity.setInvoiceinfoid(insert.getId());
			invoicedetails.add(invoiceDetailEntity);
		}
		//保存发票明细
		dbDao.insert(invoicedetails);
		return null;
	}

	/**
	 * 打开收发票页面
	 * <p>
	 * TODO打开收发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object receiveInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		TPayOrderEntity payorders = dbDao.fetch(TPayOrderEntity.class, Long.valueOf(id));
		String ids = "";
		/*for (TPayOrderEntity tPayOrderEntity : payorders) {
			ids += tPayOrderEntity.getOrderid() + ",";
		}*/
		ids += payorders.getOrderid();
		//ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_international_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.id", "in", ids);
		sql.setParam("orderstatus", payorders.getOrderstatus());
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		List<Record> orders = dbDao.query(sql, cnd, null);
		String customename = "";
		if (orders.size() > 0) {
			customename = (String) orders.get(0).get("customename");
		}
		result.put("customename", customename);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				Double incometotal = (Double) record.get("currentpay");
				sumincome += incometotal;
			}
		}
		//订单信息
		result.put("orders", orders);
		//付款信息
		TPayEntity payinfo = dbDao.fetch(TPayEntity.class, payorders.getPayid().longValue());
		TCompanyBankCardEntity companybank = new TCompanyBankCardEntity();
		if (!Util.isEmpty(payinfo.getBankId())) {
			companybank = dbDao.fetch(TCompanyBankCardEntity.class, payinfo.getBankId().longValue());
		}
		List<TPayReceiptEntity> payReceipt = new ArrayList<TPayReceiptEntity>();
		payReceipt = dbDao.query(TPayReceiptEntity.class, Cnd.where("payId", "=", payorders.getPayid()), null);
		TPayReceiptEntity billurl = new TPayReceiptEntity();
		if (payReceipt.size() > 0) {
			billurl = payReceipt.get(0);
		}
		Sql create = Sqls.create(sqlManager.get("get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
		//用途下拉
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		result.put("ytselect", ytselect);
		result.put("companybank", companybank);
		result.put("id", id);
		result.put("billurl", billurl);
		result.put("yhkSelect", yhkSelect);
		result.put("payinfo", payinfo);
		result.put("payorders", payorders);
		//总金额
		result.put("sumjine", sumincome);
		return result;
	}

	/**
	 * 保存收发票信息
	 * <p>
	 * TODO保存收发票信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		TInvoiceInfoEntity invoiceinfo = new TInvoiceInfoEntity();
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		//invoiceinfo.setBilluserid(new Long(user.getId()).intValue());
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(Double.valueOf((String) fromJson.get("difference")));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setInvoicetype(InvoiceInfoEnum.RECEIPT_INVOIC_ING.intKey());
		if (!Util.isEmpty(fromJson.get("orderpayid"))) {
			Integer orderpayid = Integer.valueOf((String) fromJson.get("orderpayid"));
			invoiceinfo.setOrderpayid(orderpayid);
			//消息提醒
			TPayOrderEntity payorder = dbDao.fetch(TPayOrderEntity.class, orderpayid.longValue());
			TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, payorder.getOrderid().longValue());
			//罚款信息
			TPayEntity payinfo = dbDao.fetch(TPayEntity.class, payorder.getPayid().longValue());
			invoiceinfo.setOpid(payinfo.getProposer());
			List<TFunctionEntity> function = dbDao.query(TFunctionEntity.class, Cnd.where("name", "=", "发票管理"), null);
			long functionid = 0;
			if (function.size() > 0) {
				functionid = function.get(0).getId();
			}
			List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "国际发票");
			interReceivePayService.addInterRemindMsg(order.getId(), order.getOrdersnum(), "",
					String.valueOf(order.getOrdersstatus()), MessageWealthStatusEnum.RECINVIOCING.intKey(),
					PayReceiveTypeEnum.PAY.intKey(), receiveusers, session);
		}
		if (!Util.isEmpty(fromJson.get("orderstatus"))) {
			invoiceinfo.setOrderstatus(Integer.valueOf((String) fromJson.get("orderstatus")));
		}
		invoiceinfo.setComId(new Long(company.getId()).intValue());
		//invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setOrdertype(OrderTypeEnum.TEAM.intKey());
		invoiceinfo.setStatus(InvoiceInfoEnum.RECEIPT_INVOIC_ING.intKey());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			invoiceDetailEntity.setInvoicebalance(Double.valueOf(map.get("invoicebalance")));
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setImagename(map.get("filename"));
			invoiceDetailEntity.setInvoiceinfoid(insert.getId());
			invoicedetails.add(invoiceDetailEntity);
		}
		//保存发票明细
		dbDao.insert(invoicedetails);
		return null;
	}

}
