/**
 * InternationalInvoiceService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.internationalinvoice.service;

import java.text.DecimalFormat;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.invoicemanage.internationalinvoice.form.InternationalKaiSqlForm;
import com.linyun.airline.admin.invoicemanage.internationalinvoice.form.InternationalShouSqlForm;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.inland.util.FormatDateUtil;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * @author   崔建斌
 * @Date	 2017年3月30日 	 
 */
@IocBean
public class InternationalInvoiceInfoService extends BaseService<TInvoiceInfoEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private InterReceivePayService interReceivePayService;

	/**
	 * 开票人的查询
	 * @param session
	 */
	public Object getIssuerBycompany(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("international_invoice__search_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("ii.comId", "=", companyId);
		cnd.groupBy("u.fullName");
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listIssuer", query);
		return obj;
	}

	/**
	 * 国际开发票列表
	 * @param sqlForm
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public Object listKaiInvoiceData(InternationalKaiSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//检索条件
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			String sqlString = sqlManager.get("international_invoice_inter_shou_invoice_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("ii.id", "=", record.getInt("id"));
			sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
			cnd.groupBy("tuo.ordersnum");
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			String username = "";
			/*TUserEntity billuser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
			if (!Util.isEmpty(billuser)) {
				username = billuser.getFullName();
			}*/
			TUserEntity billuser = dbDao.fetch(TUserEntity.class, userId);
			Integer userType = billuser.getUserType();//获取当前登录用户的类型
			if (!Util.isEmpty(billuser) && !Util.isEmpty(userType) && userType == (UserTypeEnum.UPCOM.intKey())
					|| userType == (UserTypeEnum.AGENT.intKey())) {
				username = billuser.getFullName();
			} else {
				TUserEntity fulluser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
				if (!Util.isEmpty(fulluser)) {
					username = fulluser.getFullName();
				}
			}
			record.put("ytselect", ytselect);
			String invoicedate = FormatDateUtil.dateToOrderDate((Date) record.get("invoicedate"));
			record.put("invoicedate", invoicedate);
			record.put("username", username);
		}
		return listData;
	}

	/**
	 * 国际收发票列表数据
	 * @param sqlForm
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public Object listShouInvoiceData(InternationalShouSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		for (Record record : data) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			/*String sqlString = sqlManager.get("get_inter_shou_invoice_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("tii.id", "=", record.getInt("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);*/
			//record.put("orders", orders);
			String username = "";
			TUserEntity billuser = dbDao.fetch(TUserEntity.class, userId);
			Integer userType = billuser.getUserType();//获取当前登录用户的类型
			if (!Util.isEmpty(billuser) && !Util.isEmpty(userType) && userType == (UserTypeEnum.UPCOM.intKey())
					|| userType == (UserTypeEnum.AGENT.intKey())) {
				username = billuser.getFullName();
			} else {
				TUserEntity fulluser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
				if (!Util.isEmpty(fulluser)) {
					username = fulluser.getFullName();
				}
			}
			/*TUserEntity billuser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
			if (!Util.isEmpty(billuser)) {
				username = billuser.getFullName();
			}*/
			record.put("ytselect", ytselect);
			record.put("username", username);
			String invoicedate = FormatDateUtil.dateToOrderDate((Date) record.get("invoicedate"));
			record.put("invoicedate", invoicedate);
		}
		return listData;
	}

	/**
	 * 打开开发票页面
	 * @param request
	 */
	public Object kaiInterOpenInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = new HashMap<String, Object>();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoicedetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(invoiceinfo.getReceiveid()));

		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		result.put("ytselect", ytselect);
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		Integer orderstatus = null;
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
			orderstatus = tOrderReceiveEntity.getOrderstatus();
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("international_invoice_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", orderstatus);
		sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
		cnd.groupBy("tuo.ordersnum");
		List<Record> orders = dbDao.query(sql, cnd, null);
		for (Record record : orders) {
			String billingdate = FormatDateUtil.dateToOrderDate((Date) record.get("billingdate"));
			record.put("billingdate", billingdate);
		}
		//订单信息
		result.put("orders", orders);
		double sumjine = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				sumjine += (Double) record.get("currentpay");
			}
		}
		result.put("sumjine", sumjine);
		double invoicebalance = sumjine;
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", invoicebalance);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> query2 = dbDao.query(TReceiveBillEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		//发票信息
		result.put("invoiceinfo", invoiceinfo);
		//发票明细
		result.put("invoicedetail", invoicedetail);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;

	}

	/**
	 * 保存开发票数据
	 * @param request
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public Object saveKaiInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Integer userId = (int) user.getId();
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		invoiceinfo.setBilluserid(userId);
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		/*if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}*/
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		Double difference = null;
		if (!Util.isEmpty(fromJson.get("difference"))) {
			difference = formatDouble(Double.valueOf((String) fromJson.get("difference")));
		}
		invoiceinfo.setDifference(difference);
		Double balance = null;
		if (!Util.isEmpty(fromJson.get("balance"))) {
			balance = formatDouble(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setBalance(balance);
		//		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setStatus(InvoiceInfoEnum.INVOIC_ED.intKey());
		//保存发票信息
		dbDao.update(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		//之前的发票信息
		List<TInvoiceDetailEntity> before = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", id), null);
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity entity = new TInvoiceDetailEntity();
			entity.setInvoicenum(map.get("invoicenum"));
			Double invoicebalance = null;
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoicebalance = Double.valueOf(map.get("invoicebalance"));
			}
			entity.setInvoicebalance(formatDouble(invoicebalance));
			entity.setInvoiceurl(map.get("invoiceurl"));
			entity.setImagename(map.get("filename"));
			entity.setInvoiceinfoid(Integer.valueOf(id));
			invoicedetails.add(entity);
		}
		dbDao.updateRelations(before, invoicedetails);
		//国际开发票消息提醒
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(invoiceinfo.getReceiveid()));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		Integer orderstatus = null;
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
			orderstatus = tOrderReceiveEntity.getOrderstatus();
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("international_invoice_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", orderstatus);
		sql.setParam("recordtype", PayReceiveTypeEnum.RECEIVE.intKey());
		cnd.groupBy("tuo.ordersnum");
		List<Record> orders = dbDao.query(sql, cnd, null);
		String ordersnum = "";
		Integer orderId = null;
		List<Long> receiveUids = Lists.newArrayList();
		Integer ordertatus = invoiceinfo.getOrderstatus();
		for (Record record : orders) {
			orderId = record.getInt("id");
			ordersnum = record.getString("ordersnum");
			Integer uId = record.getInt("loginUserId");
			if (!Util.isEmpty(uId)) {
				receiveUids.add(Long.valueOf(uId + ""));
			}
		}
		interReceivePayService.addInterRemindMsg(orderId, ordersnum, "", ordertatus + "",
				MessageWealthStatusEnum.INVIOCE.intKey(), PayReceiveTypeEnum.RECEIVE.intKey(), receiveUids, session);
		return null;
	}

	/**
	 * 打开收发票页面
	 * @param request
	 */
	public Object shouInterOpenInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//项目用途
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		//付款订单表信息
		TPayOrderEntity payorder = dbDao.fetch(TPayOrderEntity.class, invoiceinfo.getOrderpayid().longValue());
		//付款信息
		TPayEntity fetch = dbDao.fetch(TPayEntity.class, Long.valueOf(payorder.getPayid()));
		List<TInvoiceDetailEntity> invoicedetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		List<TPayOrderEntity> query = dbDao.query(TPayOrderEntity.class, Cnd.where("payid", "=", fetch.getId()), null);
		String ids = "";
		/*for (TPayOrderEntity tPayOrderEntity : query) {
			ids += tPayOrderEntity.getOrderid() + ",";
		}*/
		//ids = ids.substring(0, ids.length() - 1);
		ids += payorder.getOrderid();
		String sqlString = sqlManager.get("international_invoice_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", payorder.getOrderstatus());
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		List<Record> orders = dbDao.query(sql, cnd, null);
		//订单信息
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TPayReceiptEntity> query2 = dbDao.query(TPayReceiptEntity.class, Cnd.where("payId", "=", fetch.getId()),
				null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		result.put("ytselect", ytselect);
		TCompanyBankCardEntity companybank = new TCompanyBankCardEntity();
		if (!Util.isEmpty(fetch.getBankId())) {
			companybank = dbDao.fetch(TCompanyBankCardEntity.class, fetch.getBankId().longValue());
		}
		//总金额
		double sumjine = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				sumjine += Double.valueOf((Double) record.get("currentpay"));
			}
		}
		result.put("sumjine", sumjine);
		double invoicebalance = sumjine;
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", invoicebalance);
		result.put("companybank", companybank);
		//发票信息
		result.put("invoiceinfo", invoiceinfo);
		//发票明细
		result.put("invoicedetail", invoicedetail);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));
		return result;
	}

	/**
	 * 保存收发票数据
	 * @param request
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public Object saveShouInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Integer userId = (int) user.getId();
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		invoiceinfo.setBilluserid(userId);
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		/*if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}*/
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		Double difference = null;
		if (!Util.isEmpty(fromJson.get("difference"))) {
			difference = formatDouble(Double.valueOf((String) fromJson.get("difference")));
		}
		invoiceinfo.setDifference(difference);
		Double balance = null;
		if (!Util.isEmpty(fromJson.get("balance"))) {
			balance = formatDouble(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setBalance(balance);
		//		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setStatus(InvoiceInfoEnum.Already_INVOICe.intKey());//已收发票
		//保存发票信息
		dbDao.update(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> before = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", id), null);
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			Double invoicebalance = null;
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoicebalance = formatDouble(Double.valueOf(map.get("invoicebalance")));
			}
			invoiceDetailEntity.setInvoicebalance(invoicebalance);
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setImagename(map.get("filename"));
			invoiceDetailEntity.setInvoiceinfoid(Integer.valueOf(id));
			invoicedetails.add(invoiceDetailEntity);
		}
		dbDao.updateRelations(before, invoicedetails);
		//收发票消息提醒 InvoiceInfoEnum.Already_INVOICe.intKey()
		TPayOrderEntity payorder = dbDao.fetch(TPayOrderEntity.class, invoiceinfo.getOrderpayid().longValue());
		String ids = "";
		ids += payorder.getOrderid();
		String sqlString = sqlManager.get("international_invoice_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		sql.setParam("orderstatus", payorder.getOrderstatus());
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		List<Record> orders = dbDao.query(sql, cnd, null);
		String ordersnum = "";
		Integer orderId = null;
		Integer ordertatus = invoiceinfo.getOrderstatus();
		List<Long> receiveUids = Lists.newArrayList();
		for (Record record : orders) {
			ordersnum = record.getString("ordersnum");
			orderId = record.getInt("id");
			Integer uId = record.getInt("loginUserId");
			if (!Util.isEmpty(uId)) {
				receiveUids.add(Long.valueOf(uId + ""));
			}
		}
		interReceivePayService.addInterRemindMsg(orderId, ordersnum, "", ordertatus + "",
				MessageWealthStatusEnum.RECINVIOCE.intKey(), PayReceiveTypeEnum.RECEIVE.intKey(), receiveUids, session);
		return null;
	}

	/**
	 * 保留两位小数
	 */
	private Double formatDouble(Double doublenum) {
		Double result = null;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		if (!Util.isEmpty(doublenum)) {
			String format = decimalFormat.format(doublenum);
			result = Double.valueOf(format);
		}
		return result;
	}
}
