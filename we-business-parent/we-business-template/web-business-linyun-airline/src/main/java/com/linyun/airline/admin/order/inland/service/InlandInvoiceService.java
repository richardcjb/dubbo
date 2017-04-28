/**
 * InlandInvoiceService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.io.File;
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

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.inland.form.KaiInvoiceParamForm;
import com.linyun.airline.admin.order.inland.form.ShouInvoiceParamForm;
import com.linyun.airline.admin.order.inland.util.FormatDateUtil;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayPnrEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.enums.ReductionStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TMitigateInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TOrderLogEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
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
 * @Date	 2017年3月3日 	 
 */
@IocBean
public class InlandInvoiceService extends BaseService<TInvoiceInfoEntity> {

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
	@Inject
	private InlandListService inlandListService;

	/**
	 * 保存付款发票信息
	 * <p>
	 * 保存付款发票信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
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
		//if (!Util.isEmpty(fromJson.get("billuserid"))) {
		//invoiceinfo.setBilluserid(new Long(user.getId()).intValue());
		//}
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(formatDouble(Double.valueOf((String) fromJson.get("difference"))));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(formatDouble(Double.valueOf((String) fromJson.get("balance"))));
		}
		invoiceinfo.setInvoicetype(PayReceiveTypeEnum.PAY.intKey());
		if (!Util.isEmpty(fromJson.get("pnrid"))) {
			Integer pnrid = Integer.valueOf((String) fromJson.get("pnrid"));
			invoiceinfo.setPnrid(pnrid);
			TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, pnrid.longValue());
			invoiceinfo.setOpid(pnrinfo.getUserid());
			TOrderCustomneedEntity customneed = dbDao.fetch(TOrderCustomneedEntity.class, pnrinfo.getNeedid()
					.longValue());
			TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, customneed.getOrdernum().longValue());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
			map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
			List<TFunctionEntity> function = dbDao.query(TFunctionEntity.class, Cnd.where("name", "=", "发票管理"), null);
			long functionid = 0;
			if (function.size() > 0) {
				functionid = function.get(0).getId();
			}
			List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "内陆发票");
			searchViewService.addRemindMsg(map, order.getOrdersnum(), pnrinfo.getPNR(), order.getId(),
					MessageWealthStatusEnum.RECINVIOCING.intKey(), receiveusers, session);
		}
		//invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setComId(new Long(company.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setOrdertype(OrderTypeEnum.FIT.intKey());
		invoiceinfo.setInvoicedate(new Date());
		invoiceinfo.setStatus(InvoiceInfoEnum.RECEIPT_INVOIC_ING.intKey());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoiceDetailEntity.setInvoicebalance(formatDouble(Double.valueOf(map.get("invoicebalance"))));
			}
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setImagename(map.get("filename"));
			invoiceDetailEntity.setInvoiceinfoid(insert.getId());
			invoicedetails.add(invoiceDetailEntity);
		}
		//保存发票明细
		dbDao.insert(invoicedetails);
		return null;

	}

	@SuppressWarnings("unchecked")
	public Object saveReceiveInvoiceInfo(HttpServletRequest request) {

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
		/*if (!Util.isEmpty(fromJson.get("billuserid"))) {
		}*/
		//invoiceinfo.setBilluserid(new Long(user.getId()).intValue());
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(formatDouble(Double.valueOf((String) fromJson.get("difference"))));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(formatDouble(Double.valueOf((String) fromJson.get("balance"))));
		}
		invoiceinfo.setInvoicetype(PayReceiveTypeEnum.RECEIVE.intKey());
		if (!Util.isEmpty(fromJson.get("pnrid"))) {
			Integer receiveid = Integer.valueOf((String) fromJson.get("pnrid"));
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
				List<Long> receiveusers = inlandListService.getUserIdsByFun(company.getId(), functionid, "内陆发票");
				searchViewService.addRemindMsg(map, record.getString("ordersnum"), "", record.getInt("id"),
						MessageWealthStatusEnum.INVIOCING.intKey(), receiveusers, session);
			}
		}
		//invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setComId(new Long(company.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setOrdertype(OrderTypeEnum.FIT.intKey());
		invoiceinfo.setStatus(InvoiceInfoEnum.INVOIC_ING.intKey());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoiceDetailEntity.setInvoicebalance(formatDouble(Double.valueOf(map.get("invoicebalance"))));
			}
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
	 * 打开减免页面
	 * <p>
	 * TODO打开减免页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object mitigate(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		result.put("user", user);
		result.put("orderid", request.getParameter("id"));
		String customeid = request.getParameter("customeid");
		TCustomerInfoEntity fetch = dbDao.fetch(TCustomerInfoEntity.class, Long.valueOf(customeid));
		result.put("customeinfo", fetch);
		TMitigateInfoEntity mitigate = dbDao.fetch(
				TMitigateInfoEntity.class,
				Cnd.where("orderid", "=", request.getParameter("id")).and("applyResult", "!=",
						ReductionStatusEnum.REFUSE.intKey()));
		result.put("mitigate", mitigate);
		TUserEntity applyuser = new TUserEntity();
		if (!Util.isEmpty(mitigate) && !Util.isEmpty(mitigate.getApplyid())) {
			applyuser = dbDao.fetch(TUserEntity.class, mitigate.getApplyid().longValue());
		}
		//申请人
		result.put("applyuser", applyuser);
		String applyresult = "";
		if (!Util.isEmpty(mitigate) && !Util.isEmpty(mitigate.getApplyResult())) {
			for (ReductionStatusEnum statusenum : ReductionStatusEnum.values()) {
				if (mitigate.getApplyResult().equals(statusenum.intKey())) {
					applyresult = statusenum.value();
				}
			}
		}
		result.put("applyresult", applyresult);
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

	public Object saveMitigateData(HttpServletRequest request) {
		String orderid = request.getParameter("orderid");
		TUpOrderEntity fetch = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(orderid));
		String customeid = request.getParameter("customeid");
		String customname = request.getParameter("customname");
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String account = request.getParameter("account");
		String accountupper = request.getParameter("accountupper");
		String currency = request.getParameter("currency");
		String approvelid = request.getParameter("approvelid");
		String application = request.getParameter("application");
		TMitigateInfoEntity mitigateInfoEntity = new TMitigateInfoEntity();
		mitigateInfoEntity.setOrderid(Integer.valueOf(orderid));
		mitigateInfoEntity.setCustomeid(Integer.valueOf(customeid));
		mitigateInfoEntity.setCustomname(customname);
		mitigateInfoEntity.setApplyid(new Long(user.getId()).intValue());
		mitigateInfoEntity.setApplyResult(ReductionStatusEnum.APPROVALING.intKey());
		mitigateInfoEntity.setOrdertype(fetch.getOrderstype());
		if (!Util.isEmpty(account)) {
			mitigateInfoEntity.setAccount(formatDouble(Double.valueOf(account)));
		}
		mitigateInfoEntity.setAccountupper(accountupper);
		mitigateInfoEntity.setCurrency(currency);
		mitigateInfoEntity.setApprovelid(approvelid);
		mitigateInfoEntity.setOptime(new Date());
		mitigateInfoEntity.setApplication(application);
		//mitigateInfoEntity.setOrdertype(OrderTypeEnum.FIT.intKey());
		return dbDao.insert(mitigateInfoEntity);

	}

	/**
	 * 开发票页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param paramForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listKaiInvoiceData(KaiInvoiceParamForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//检索条件
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId()),
				null);
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setCompanyid(company.getId());
		paramForm.setAdminId(company.getAdminId().intValue());
		Long comId = company.getId();//得到公司的id
		Map<String, Object> DatatablesData = this.listPage4Datatables(paramForm);
		List<Record> listdata = (List<Record>) DatatablesData.get("data");
		for (Record record : listdata) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			String sqlString = sqlManager.get("get_kai_invoice_search_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("ii.comId", "=", comId);
			cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
			cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
			cnd.and("ii.id", "=", record.getInt("id"));
			//订单信息
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			String username = "";
			if (!Util.isEmpty(record.get("billuserid"))) {
				username = dbDao.fetch(TUserEntity.class, Long.valueOf(record.getInt("billuserid"))).getFullName();
			}
			record.put("username", username);
			record.put("invoiceinfoenum", EnumUtil.enum2(InvoiceInfoEnum.class));
			record.put("ytselect", ytselect);
			String invoicedate = FormatDateUtil.dateToOrderDate((Date) record.get("invoicedate"));
			record.put("invoicedate", invoicedate);
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
	 *收发票列表
	 * <p>
	 * TODO收发票列表
	 *
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listShouInvoiceData(ShouInvoiceParamForm paramForm, HttpServletRequest request) {

		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		paramForm.setUserid(new Long(user.getId()).intValue());
		paramForm.setCompanyid(company.getId());
		paramForm.setAdminId(company.getAdminId().intValue());
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
		}
		return datatableData;

	}

	/**
	 * 打开开发票页面
	 * <p>
	 * TODO打开开发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object kaiInvoice(HttpServletRequest request) {
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
	 * 打开收发票页面
	 * <p>
	 * TODO打开收发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
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
		String sqlString = sqlManager.get("get_fukuan_info_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tpi.id", "=", invoiceinfo.getPnrid());
		List<Record> pnrinfo = dbDao.query(sql, cnd, null);
		result.put("pnrinfo", pnrinfo);
		double sumjine = 0;
		for (Record record : pnrinfo) {
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
		Sql create = Sqls.create(sqlManager.get("get_bank_info_select"));
		create.setParam("companyId", company.getId());
		create.setParam("typeCode", YHCODE);
		List<Record> yhkSelect = dbDao.query(create, null, null);
		//付款银行卡信息
		Record companybank = new Record();
		String pagesqlStr = sqlManager.get("get_fukuan_invoice_page_data");
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
	 * 保存开发票数据
	 * <p>
	 * TODO保存开发票数据
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveKaiInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
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
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
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
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object saveShouInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
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
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
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
		return null;
	}

	/**
	 * 加载日志
	 * <p>
	 * TODO加载日志
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadOrderLog(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		List<TOrderLogEntity> orderlogs = dbDao.query(TOrderLogEntity.class, Cnd.where("orderid", "=", orderid), null);
		result.put("logs", orderlogs);
		return result;

	}

	/**
	 * 检验收款方是否是同一家公司
	 * <p>
	 * TODO检验收款方是否是同一家公司
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkIsCommonCompany(HttpServletRequest request) {
		boolean result = true;
		String ids = request.getParameter("ids");
		List<TUpOrderEntity> orders = dbDao.query(TUpOrderEntity.class, Cnd.where("id", "in", ids), null);
		for (int i = 0; i < orders.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (!orders.get(j).getUserid().equals(orders.get(i).getUserid())) {
					result = false;
				}
			}
		}
		return result;

	}

	/**
	 * 检验付款方是否是同一家公司
	 * <p>
	 * TODO检验付款方是否是同一家公司
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkPayIsCommonCompany(HttpServletRequest request) {
		boolean result = true;
		String ids = request.getParameter("ids");
		String sqlString = sqlManager.get("get_check_pnr_order_sql");
		List<Record> query = dbDao.query(Sqls.create(sqlString), Cnd.where("tpi.id", "in", ids), null);
		for (int i = 0; i < query.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (!query.get(j).get("userid").equals(query.get(i).get("userid"))) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * 上传发票
	 * <p>
	 * TODO上传发票
	 *
	 * @param file
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object uploadInvoice(File file, HttpServletRequest request) {
		Map<String, Object> result = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		result.put("data", CommonConstants.IMAGES_SERVER_ADDR + result.get("data"));
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
	 * 加载减免信息
	 * <p>
	 * TODO加载减免信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadJianMianAccount(HttpServletRequest request) {
		TMitigateInfoEntity result = new TMitigateInfoEntity();
		String orderid = request.getParameter("orderid");
		List<TMitigateInfoEntity> mitigates = dbDao.query(TMitigateInfoEntity.class,
				Cnd.where("orderid", "=", orderid), null);
		if (mitigates.size() > 0) {
			for (TMitigateInfoEntity tMitigateInfoEntity : mitigates) {
				if (tMitigateInfoEntity.getApplyResult().equals(ReductionStatusEnum.AGREE.intKey())) {
					result = tMitigateInfoEntity;
				}
			}
		}

		return result;
	}
}
