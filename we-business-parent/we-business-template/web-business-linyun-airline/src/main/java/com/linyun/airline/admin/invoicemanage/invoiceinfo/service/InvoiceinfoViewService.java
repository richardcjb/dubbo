package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

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
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TKaiInvoiceInfoSqlForm;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TShouInvoiceInfoSqlForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.util.FormatDateUtil;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayPnrEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InvoiceinfoViewService extends BaseService<TInvoiceInfoEntity> {

	private static final String BIZHONGCODE = "BZ";
	//银行卡类型
	private static final String YHCODE = "YH";
	//付款用途
	private static final String FPXMCODE = "FPXM";

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private SearchViewService searchViewService;

	/**
	 * 开票人的查询
	 * @param session
	 */
	public Object getIssuerBycompany(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("invoicemanage_invoice_getfullname_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("ii.comId", "=", companyId);
		cnd.groupBy("u.fullName");
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listIssuer", query);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public Object listKaiInvoiceData(TKaiInvoiceInfoSqlForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();//当前登录用户id
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//检索条件
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setCompanyid(company.getId());
		Long comId = company.getId();//得到公司的id
		Map<String, Object> DatatablesData = this.listPage4Datatables(paramForm);
		List<Record> listdata = (List<Record>) DatatablesData.get("data");
		for (Record record : listdata) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			String sqlString = sqlManager.get("invoicemanage_kaiinvoice_search_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("ii.comId", "=", comId);
			cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
			cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
			cnd.and("ii.id", "=", record.getInt("id"));
			cnd.groupBy("tuo.ordersnum");
			//订单信息
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			/*record.put("username", dbDao.fetch(TUserEntity.class, Long.valueOf(record.getInt("billuserid")))
					.getFullName());*/
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
			record.put("invoiceinfoenum", EnumUtil.enum2(InvoiceInfoEnum.class));
			record.put("ytselect", ytselect);
			String invoicedate = FormatDateUtil.dateToOrderDate((Date) record.get("invoicedate"));
			record.put("invoicedate", invoicedate);
			record.put("username", username);
		}
		List<Record> listdataNew = new ArrayList<Record>();
		for (Record record : listdata) {
			String orders = record.getString("orders");
			if (orders.contains("id")) {
				listdataNew.add(record);
			}
		}
		DatatablesData.remove("data");
		DatatablesData.put("data", listdataNew);
		return DatatablesData;

	}

	/**
	 * 打开开发票页面
	 * @param request
	 */
	public Object kaiInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = Maps.newHashMap();
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
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//订单信息
		result.put("orders", orders);
		double sumjine = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				sumjine += (Double) record.get("incometotal");
			}
			String billingdate = FormatDateUtil.dateToOrderDate((Date) record.get("billingdate"));
			record.put("billingdate", billingdate);
		}
		result.put("sumjine", sumjine);
		double invoicebalance = sumjine;
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", invoicebalance);
		Sql create = Sqls.create(sqlManager.get("get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
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
		//开发票消息提醒
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(invoiceinfo.getReceiveid()));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String ordersnum = "";
		Integer orderId = null;
		List<Long> receiveUids = Lists.newArrayList();
		for (Record record : orders) {
			ordersnum = record.getString("ordersnum");
			orderId = record.getInt("id");
			Integer uId = record.getInt("loginUserId");
			if (!Util.isEmpty(uId)) {
				receiveUids.add(Long.valueOf(uId + ""));
			}
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
		map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
		searchViewService.addRemindMsg(map, ordersnum, "", orderId, MessageWealthStatusEnum.INVIOCE.intKey(),
				receiveUids, session);
		return null;
	}

	/**
	 *收发票列表
	 * @param paramForm
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public Object listShouInvoiceData(TShouInvoiceInfoSqlForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();//获得当前登录用户
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//获取到当前登录公司id
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setComId(comId);
		Map<String, Object> datatableData = this.listPage4Datatables(paramForm);
		List<Record> listdata = (List<Record>) datatableData.get("data");
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		for (Record record : listdata) {
			record.put("ytselect", ytselect);
			record.put("invoiceinfoenum", EnumUtil.enum2(InvoiceInfoEnum.class));
			String invoicedate = FormatDateUtil.dateToOrderDate((Date) record.get("invoicedate"));
			record.put("invoicedate", invoicedate);
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
			record.put("username", username);
		}
		return datatableData;

	}

	/**
	 * 打开收发票页面
	 * @param request
	 */
	public Object shouInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		Map<String, Object> result = new HashMap<String, Object>();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		String sqlString = sqlManager.get("invoicemanage_get_kaiinvoice_info_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tpi.id", "=", invoiceinfo.getPnrid());
		List<Record> pnrinfo = dbDao.query(sql, cnd, null);
		result.put("pnrinfo", pnrinfo);
		double sumjine = 0;
		for (Record record : pnrinfo) {
			String billingdate = FormatDateUtil.dateToOrderDate((Date) record.get("billingdate"));
			record.put("billingdate", billingdate);
			if (!Util.isEmpty(record.get("costpricesum"))) {
				Double salespricesum = (Double) record.get("costpricesum");
				sumjine += Double.valueOf(salespricesum);
			}
		}
		result.put("sumjine", formatDouble(sumjine));
		double invoicebalance = sumjine;
		for (TInvoiceDetailEntity detail : invoiceDetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", formatDouble(invoicebalance));
		List<TPayPnrEntity> query = dbDao.query(TPayPnrEntity.class, Cnd.where("pnrId", "=", invoiceinfo.getPnrid()),
				null);
		TPayEntity payinfo = new TPayEntity();
		String billurl = "";
		if (query.size() > 0) {
			payinfo = dbDao.fetch(TPayEntity.class, query.get(0).getPayId().longValue());
			if (!Util.isEmpty(payinfo)) {
				List<TPayReceiptEntity> query2 = dbDao.query(TPayReceiptEntity.class,
						Cnd.where("payId", "=", payinfo.getId()), null);
				if (query2.size() > 0) {
					billurl = query2.get(0).getReceiptUrl();
				}
			}
		}
		Sql create = Sqls.create(sqlManager.get("invoicemanage_get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
		//付款银行卡信息
		Record companybank = new Record();
		String pagesqlStr = sqlManager.get("invoicemanage_get_kai_invoice_page_data");
		Sql pagesql = Sqls.create(pagesqlStr);
		Cnd pagecnd = Cnd.NEW();
		pagecnd.and("tpp.pnrId", "=", invoiceinfo.getPnrid());
		List<Record> banks = dbDao.query(pagesql, pagecnd, null);
		if (banks.size() > 0) {
			companybank = banks.get(0);
		}
		result.put("ytselect", ytselect);
		result.put("companybank", companybank);
		result.put("id", id);
		result.put("billurl", billurl);
		result.put("yhkSelect", yhkSelect);
		result.put("payinfo", payinfo);
		result.put("invoiceDetail", invoiceDetail);
		result.put("invoiceinfo", invoiceinfo);
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
		//收发票消息提醒
		String sqlString = sqlManager.get("invoicemanage_get_kaiinvoice_info_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tpi.id", "=", invoiceinfo.getPnrid());
		List<Record> pnrinfo = dbDao.query(sql, cnd, null);
		String ordersnum = "";
		String pnr = "";
		Integer orderId = null;
		List<Long> receiveUids = Lists.newArrayList();
		for (Record record : pnrinfo) {
			ordersnum = record.getString("ordersnum");
			orderId = record.getInt("orderids");
			pnr = record.getString("pnr");
			Integer uId = record.getInt("userid");
			if (!Util.isEmpty(uId)) {
				receiveUids.add(Long.valueOf(uId + ""));
			}
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
		map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
		searchViewService.addRemindMsg(map, ordersnum, pnr, orderId, MessageWealthStatusEnum.RECINVIOCE.intKey(),
				receiveUids, session);
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