/**
 * ReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayPnrEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.form.inland.InlandPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.InlandRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inland.TSaveInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.form.inland.TUpdateInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.util.FormatDateUtil;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.admin.turnover.service.TurnOverViewService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.ApprovalResultEnum;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TTurnOverAddForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class ReceivePayService extends BaseService<TPayEntity> {

	private static final String YHCODE = "YH";
	private static final String BZCODE = "BZ";
	private static final int ENABLE = BankCardStatusEnum.ENABLE.intKey();
	private static final String YTCODE = ComDictTypeEnum.DICTTYPE_XMYT.key();
	private static final String ZJZLCODE = ComDictTypeEnum.DICTTYPE_ZJZL.key();
	private static final int RECEIVESTATUS = AccountReceiveEnum.RECEIVEDONEY.intKey();
	private static final int APPROVALPAYED = AccountPayEnum.APPROVALPAYED.intKey();
	private static final int APPROVALPAYING = AccountPayEnum.APPROVALPAYING.intKey();
	private static final int APPROVALENABLE = ApprovalResultEnum.ENABLE.intKey();
	private static final int ONJOB = UserJobStatusEnum.ON.intKey();

	//款项已收
	private static final int RECEDMSGTYPE = MessageWealthStatusEnum.RECEIVED.intKey();
	//款项已付
	private static final int PAYEDMSGTYPE = MessageWealthStatusEnum.PAYED.intKey();

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private SearchViewService searchViewService;

	@Inject
	private TurnOverViewService turnOverViewService;

	/**
	 * 
	 * 会计收款列表
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listRecData(InlandRecListSearchSqlForm form, HttpSession session, HttpServletRequest request) {

		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		//检索条件
		String name = form.getName();
		Date leaveBeginDate = form.getLeaveBeginDate();
		Date leaveEndDate = form.getLeaveEndDate();

		String sqlStr = sqlManager.get("get_receive_list_by_condition");
		Sql sql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("oc.leavetdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("oc.leavetdate", "<=", leaveEndDate);
		}
		List<Record> orders = dbDao.query(sql, cnd, null);

		//当前公司下的用户
		form.setCompanyid(companyId);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) listdata.get("data");
		for (Record record : list) {
			//收款id
			String id = record.get("id").toString();
			List<Record> rList = new ArrayList<Record>();
			for (Record r : orders) {
				String rid = r.getString("id");
				if (Util.eq(id, rid)) {
					rList.add(r);
				}
			}
			if (!Util.isEmpty(rList)) {
				record.put("orders", rList);
			}
		}
		List<Record> ordersBC = new ArrayList<Record>();
		for (Record r : list) {
			String oStr = r.getString("orders");
			if (!Util.isEmpty(oStr)) {
				ordersBC.add(r);
			}
		}

		listdata.remove("data");
		listdata.put("data", ordersBC);
		listdata.put("recordsFiltered", ordersBC.size());
		return listdata;
	}

	/**
	 * (保存  确认收款)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInlandRec(String recId, HttpSession session) {
		//获取当前登录用户
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity loginCompany = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		int orderRecEd = AccountReceiveEnum.RECEIVEDONEY.intKey();
		int updateNum = dbDao.update(TReceiveEntity.class, Chain.make("status", orderRecEd),
				Cnd.where("id", "in", recId));

		//添加流水 TODO
		TTurnOverAddForm addForm = new TTurnOverAddForm();
		TReceiveEntity receiveEntity = dbDao.fetch(TReceiveEntity.class, Cnd.where("id", "in", recId));
		int bankcardId = receiveEntity.getBankcardnameid();
		Double sum = receiveEntity.getSum();
		String bankcardnum = receiveEntity.getBankcardnum();
		String comName = loginCompany.getComName();
		addForm.setBankCardId(bankcardId);
		addForm.setTradeDate(new Date());
		addForm.setMoney(sum);
		addForm.setCardNum(bankcardnum);
		addForm.setPurpose("收入");
		addForm.setCompanyName(comName);
		turnOverViewService.addTurnOver(addForm, session);

		//收款成功添加消息提醒
		if (updateNum > 0) {
			//TODO  ******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_order_rec_rids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("r.id", "in", recId);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);
			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = record.getString("pnrnum");
				String uids = record.getString("userid");
				List<Long> receiveUserIds = Lists.newArrayList();
				receiveUserIds.add(Long.parseLong(uids));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
				map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
				searchViewService.addRemindMsg(map, ordernum, pnr, uid, RECEDMSGTYPE, receiveUserIds, session);
			}
		}

		return updateNum;
	}

	/**
	 * 付款中列表
	 */
	public Map<String, Object> listPayData(final InlandPayListSearchSqlForm sqlParamForm, HttpSession session) {
		//当前公司下的用户
		String userIds = userInComp(session);
		sqlParamForm.setLoginUserId(userIds);
		sqlParamForm.setOrderPnrStatus(APPROVALPAYING);
		checkNull(sqlParamForm, "sqlParamForm不能为空");
		Sql sql = sqlParamForm.sql(sqlManager);

		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> map = MapUtil.map();
		map.put("data", list);
		map.put("draw", sqlParamForm.getDraw());
		map.put("recordsTotal", pager.getPageSize());
		map.put("recordsFiltered", pager.getRecordCount());
		return map;
	}

	/**
	 * 
	 * TODO(会计已付款查询)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlParamForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listPayEdData(InlandPayEdListSearchSqlForm form, HttpSession session) {
		//当前公司下的用户
		String userIds = userInComp(session);
		form.setLoginUserId(userIds);
		form.setOrderPnrStatus(APPROVALPAYED);

		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");
		String pids = "";
		for (Record record : data) {
			pids += record.getString("pid") + ",";
		}
		if (pids.length() > 1) {
			pids = pids.substring(0, pids.length() - 1);
		}
		Sql sql = Sqls.create(sqlManager.get("receivePay_payed_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("p.id", "in", pids);
		cnd.and("pi.orderPnrStatus", "=", APPROVALPAYED);
		List<Record> orders = dbDao.query(sql, cnd, null);

		for (Record record : data) {
			String pid = record.getString("pid");
			String payId = "";
			List<Record> newOrders = new ArrayList<Record>();
			double totalmoney = 0.0;
			for (Record r : orders) {
				String id = r.getString("id");
				if (Util.eq(pid, id)) {
					payId += id + ",";
					String priceStr = r.getString("saleprice");
					if (!Util.isEmpty(priceStr)) {
						double price = Double.valueOf(priceStr);
						totalmoney += price;
					}
					newOrders.add(r);
				}

			}
			if (payId.length() > 1) {
				payId = payId.substring(0, payId.length() - 1);
			}
			record.put("orders", newOrders);
			record.put("totalmoney", totalmoney);
			record.put("payid", payId);
		}

		//已付款结果集排序
		Collections.sort(data, new Comparator() {
			public int compare(Object a, Object b) {
				Long one = Long.valueOf(((Record) a).getString("pid"));
				Long two = Long.valueOf(((Record) b).getString("pid"));
				return (int) (two - one);
			}
		});

		listdata.remove("data");
		listdata.put("data", data);
		return listdata;
	}

	/**
	 * 
	 * 到编辑已付款页面
	 * <p>
	 *
	 * @param request
	 * @return 
	 */
	public Object editConfirmPay(HttpServletRequest request, HttpSession session) {

		//当前公司所有用户id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		Map<String, Object> result = new HashMap<String, Object>();
		String payId = request.getParameter("payid");
		String sqlString = sqlManager.get("receivePay_payed_edit");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("p.id", "in", payId);
		cnd.and("pi.orderPnrStatus", "=", APPROVALPAYED);
		List<Record> payList = dbDao.query(sql, cnd, null);

		//总金额
		double totalMoney = 0;
		//申请人
		String proposer = "";
		//审批人
		String approver = "";
		//审批结果
		String approveresult = "";
		//操作人
		String operator = "";
		for (Record record : payList) {
			if (!Util.isEmpty(record.get("salesprice"))) {
				Double costpricesum = (Double) record.get("salesprice");
				totalMoney += Double.valueOf(costpricesum);
			}
			proposer = record.getString("proposerman");
			approver = record.getString("approver"); //审批人
			String opr = record.getString("operator"); //操作人
			if (!Util.eq(operator, opr)) {
				operator = opr;
			}
			approveresult = record.getString("approveresult");
			String payDateStr = record.getString("payDate");
			if (!Util.isEmpty(payDateStr)) {
				String payDateFormat = DateTimeUtil.format(DateTimeUtil.string2Date(payDateStr, null));
				record.put("payDate", payDateFormat);
			}
			String billDateStr = record.getString("billingdate");
			if (!Util.isEmpty(billDateStr)) {
				String formatBillDate = FormatDateUtil.dateToOrderDate(DateTimeUtil.string2Date(billDateStr, null));
				record.set("billingdate", formatBillDate);
			}
		}
		result.put("proposer", proposer);
		result.put("approver", approver);
		if (Util.eq(APPROVALENABLE, approveresult)) {
			result.put("approveresult", "同意");
		} else {
			result.put("approveresult", "拒绝");
		}
		result.put("payList", payList);
		//总金额
		result.put("totalMoney", totalMoney);

		//付款的水单信息
		TPayReceiptEntity receiptEntity = dbDao.fetch(TPayReceiptEntity.class, Cnd.where("payId", "in", payId));
		if (!Util.isEmpty(receiptEntity)) {
			result.put("receiptUrl", receiptEntity.getReceiptUrl());
		}

		//银行名称
		List<TBankCardEntity> bankList = new ArrayList<TBankCardEntity>();
		try {
			Cnd cnd2 = Cnd.NEW();
			cnd2.and("companyid", "=", companyId);
			cnd2.and("bankName", "!=", "");
			cnd2.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
			cnd2.groupBy("bankName");
			bankList = dbDao.query(TBankCardEntity.class, cnd2, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("bankList", bankList);

		//币种
		List<DictInfoEntity> BZList = new ArrayList<DictInfoEntity>();
		try {
			BZList = externalInfoService.findDictInfoByName("", BZCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("bzList", BZList);
		//付款用途
		List<ComDictInfoEntity> fkytList = new ArrayList<ComDictInfoEntity>();
		try {
			fkytList = findCodeByName("", YTCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("fkytList", fkytList);
		//资金种类
		List<ComDictInfoEntity> zjzlList = new ArrayList<ComDictInfoEntity>();
		try {
			zjzlList = findCodeByName("", ZJZLCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("zjzlList", zjzlList);

		result.put("payId", payId);

		//付款的银行卡信息
		String bankSqlStr = sqlManager.get("receivePay_payed_bank");
		Sql bankSql = Sqls.create(bankSqlStr);
		bankSql.setParam("PayId", payId);
		Record bank = dbDao.fetch(bankSql);
		result.put("companybank", bank);
		result.put("payId", payId);
		return result;
	}

	//查询用途 公司字典
	public List<ComDictInfoEntity> findCodeByName(String name, String typeCode, long companyId) throws Exception {
		Cnd cnd = Cnd.NEW();
		cnd.and("comDictName", "like", Strings.trim(name) + "%").and("status", "=", DataStatusEnum.ENABLE.intKey())
				.and("comTypeCode", "=", typeCode).and("comId", "=", companyId).groupBy("comDictName");
		List<ComDictInfoEntity> query = dbDao.query(ComDictInfoEntity.class, cnd, null);
		return query;
	}

	/**
	 * (确认收款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmRec(String id, HttpSession session) {
		//当前登录用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		//收款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(id));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class, Cnd.where("receiveid", "=", id), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("receivePay_toRec_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("uo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		Double sum = 0.0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				Double incometotal = (Double) record.get("currentpay");
				sum += incometotal;
			}
			String billDateStr = record.getString("billingdate");
			if (!Util.isEmpty(billDateStr)) {
				String formatBillDate = FormatDateUtil.dateToOrderDate(DateTimeUtil.string2Date(billDateStr, null));
				record.set("billingdate", formatBillDate);
			}

		}
		map.put("sum", sum);

		//订单信息
		map.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> receipts = dbDao
				.query(TReceiveBillEntity.class, Cnd.where("receiveid", "=", id), null);
		//银行卡下拉
		map.put("yhkSelect", yhkSelect);

		int bankcardid = fetch.getBankcardid();
		DictInfoEntity bank = dbDao.fetch(DictInfoEntity.class, bankcardid);
		String bankName = bank.getDictName();

		//订单信息id
		map.put("ids", ids);
		map.put("id", id);
		map.put("bankName", bankName);
		map.put("receive", fetch);
		if (receipts.size() > 0) {
			map.put("receipturl", receipts.get(0));
		}
		return map;

	}

	/**
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmPay(String inlandPnrIds, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//当前公司所有用户id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		Sql sql = Sqls.create(sqlManager.get("receivePay_pay_Ids"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("pi.id", "in", inlandPnrIds);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String payIds = "";
		if (!Util.isEmpty(orders)) {
			String shortname = orders.get(0).getString("shortname");
			for (Record record : orders) {
				String everyShortName = record.getString("shortname");
				String id = record.getString("id");
				String uid = record.getString("uid");
				if (!Util.eq(shortname, everyShortName)) {
					map.put("sameName", false);
				} else {
					map.put("sameName", true);
				}
				if (!Util.isEmpty(id)) {
					payIds += record.getString("id") + ",";
				}
				String billDateStr = record.getString("billdate");
				if (!Util.isEmpty(billDateStr)) {
					String formatBillDate = FormatDateUtil.dateToOrderDate(DateTimeUtil.string2Date(billDateStr, null));
					record.set("billdate", formatBillDate);
				}
			}
		}
		map.put("orders", orders);

		//计算合计金额
		double totalMoney = 0;
		//申请人
		String proposer = "";
		//审批人
		String approver = "";
		//审批结果
		String approveresult = "";
		//用途
		String purpose = "";
		//币种
		String payCurrency = "";
		//操作人
		String operator = "";
		String operatorList = "";
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("salePrice"))) {
				Double incometotal = (Double) record.get("salePrice");
				totalMoney += incometotal;
			}
			proposer = record.getString("proposer");
			approver = record.getString("approver"); //审批人
			String opr = record.getString("operator"); //操作人
			if (!Util.eq(operator, opr)) {
				operator = opr;
				operatorList += opr + ",";
			}
			approveresult = record.getString("approveresult");
			purpose = record.getString("purpose");
			payCurrency = record.getString("paycurrency");
		}
		map.put("totalMoney", totalMoney);
		map.put("proposer", proposer);
		map.put("approver", approver);
		map.put("purpose", purpose);
		map.put("payCurreny", payCurrency);
		map.put("operators", operatorList);
		if (Util.eq(APPROVALENABLE, approveresult)) {
			map.put("approveresult", "同意");
		} else {
			map.put("approveresult", "拒绝");
		}
		//银行名称
		List<TBankCardEntity> bankList = new ArrayList<TBankCardEntity>();
		try {
			Cnd cnd2 = Cnd.NEW();
			cnd2.and("companyid", "=", companyId);
			cnd2.and("bankName", "!=", "");
			cnd2.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
			cnd2.groupBy("bankName");
			bankList = dbDao.query(TBankCardEntity.class, cnd2, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bankList", bankList);

		//币种
		List<DictInfoEntity> BZList = new ArrayList<DictInfoEntity>();
		try {
			BZList = externalInfoService.findDictInfoByName("", BZCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bzList", BZList);

		//付款用途
		List<ComDictInfoEntity> fkytList = new ArrayList<ComDictInfoEntity>();
		try {
			fkytList = findCodeByName("", YTCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fkytList", fkytList);
		//资金种类
		List<ComDictInfoEntity> zjzlList = new ArrayList<ComDictInfoEntity>();
		try {
			zjzlList = findCodeByName("", ZJZLCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("zjzlList", zjzlList);
		map.put("ids", inlandPnrIds);

		return map;
	}

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInlandPay(TSaveInlandPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		//当前登陆用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();
		String bankCompStr = form.getBankComp();
		String bankcardId = bankCompStr.split(",")[0];
		String bankComp = bankCompStr.split(",")[1];
		String cardName = form.getCardName();
		String cardNum = form.getCardNum();
		Integer payAddress = form.getPayAddress();
		Integer purpose = form.getPurpose();
		Integer fundType = form.getFundType();
		Date payDate = form.getPayDate();
		Double payFees = form.getPayFees();
		Double payMoney = form.getPayMoney();
		String payChineseMoney = form.getPayChineseMoney();
		Integer currency = form.getPayCurrency();
		Integer isInvioce = form.getIsInvioce();
		String receiptUrl = form.getReceiptUrl();
		String pnrIds = form.getPayIds();
		Double totalMoney = form.getTotalMoney();
		String payNames = form.getPayNames();
		//操作人
		String operators = form.getOperators();
		if (Util.eq("false", payNames)) {
			//收款单位不一致，不能付款
			return false;
		}

		//验证银行卡是否有余额
		boolean cardMoney = turnOverViewService.checkBankCardNumEnoughOther(Integer.valueOf(bankcardId), "支出",
				totalMoney);
		if (Util.eq("false", cardMoney)) {
			//余额不足
			return "余额不足";
		}

		//付款水单 集合
		TPayReceiptEntity payReceiptEntity = new TPayReceiptEntity();
		//银行卡
		TCompanyBankCardEntity companyBankCard = new TCompanyBankCardEntity();
		companyBankCard.setCompanyId(companyId);
		if (!Util.eq("--请选择--", cardName)) {
			companyBankCard.setCardName(cardName);
		}
		if (!Util.eq("--请选择--", bankComp)) {
			companyBankCard.setBankComp(bankComp);
		}
		if (!Util.eq("--请选择--", cardNum)) {
			companyBankCard.setCardNum(cardNum);
		}
		//添加银行卡
		TCompanyBankCardEntity companyBankCardEntity = dbDao.insert(companyBankCard);
		Integer bankId = companyBankCardEntity.getId();
		//订单
		TUpOrderEntity upOrder = new TUpOrderEntity();
		//付款集合
		List<TPayEntity> updateList = new ArrayList<TPayEntity>();
		TPayPnrEntity payPnrEntity = dbDao.fetch(TPayPnrEntity.class, Cnd.where("pnrId", "in", pnrIds));
		TPayEntity payEntity = dbDao.fetch(TPayEntity.class, Cnd.where("id", "=", payPnrEntity.getPayId()));
		if (!Util.eq(null, bankId)) {
			payEntity.setBankId(bankId);
		}
		if (!Util.eq(null, payAddress)) {
			payEntity.setPayAddress(payAddress);
		}
		if (!Util.eq(null, purpose)) {
			payEntity.setPurpose(purpose);
		}
		if (!Util.eq(null, fundType)) {
			payEntity.setFundType(fundType);
		}
		if (!Util.eq(null, payDate)) {
			payEntity.setPayDate(payDate);
		}
		if (!Util.eq(null, payFees)) {
			payEntity.setPayFees(payFees);
		}
		if (!Util.eq(null, payMoney)) {
			payEntity.setPayMoney(payMoney);
		}
		if (!Util.eq(null, totalMoney)) {
			payEntity.setTotalMoney(totalMoney);
		}
		if (!Util.eq(null, currency)) {
			if (!Util.eq("--请选择--", currency)) {
				payEntity.setPayCurrency(currency);
			}
		}
		if (!Util.eq(null, isInvioce)) {
			payEntity.setIsInvioce(isInvioce);
		}
		if (!Util.eq(null, payChineseMoney)) {
			payEntity.setPayChineseMoney(payChineseMoney);
		}
		payEntity.setConfirmDate(DateTimeUtil.nowDate());
		updateList.add(payEntity);
		//更新付款订单信息
		if (!Util.isEmpty(updateList)) {
			dbDao.update(updateList);
		} else {
			dbDao.insert(updateList);
		}

		//添加水单
		if (!Util.isEmpty(receiptUrl)) {
			payReceiptEntity.setReceiptUrl(receiptUrl);
			payReceiptEntity.setPayId(payEntity.getId());
			dbDao.insert(payReceiptEntity);
		}

		//更新Pnr表中對應的状态
		List<TPnrInfoEntity> pnrInfoList = dbDao.query(TPnrInfoEntity.class, Cnd.where("id", "in", pnrIds), null);
		int updatenum = 0;
		if (!Util.eq(null, pnrIds)) {
			updatenum = dbDao.update(TPnrInfoEntity.class, Chain.make("orderPnrStatus", APPROVALPAYED),
					Cnd.where("id", "in", pnrIds));
		}

		//添加流水
		TTurnOverAddForm addForm = new TTurnOverAddForm();
		String comName = company.getComName();
		addForm.setBankCardId(Integer.valueOf(bankcardId));
		addForm.setTradeDate(new Date());
		addForm.setMoney(totalMoney);
		addForm.setCardNum(cardNum);
		addForm.setPurpose("支出");
		addForm.setCompanyName(comName);
		if (!Util.isEmpty(currency)) {
			DictInfoEntity dictInfoEntity = dbDao.fetch(DictInfoEntity.class, Long.valueOf(currency));
			String currencyStr = dictInfoEntity.getDictCode();
			addForm.setCurrency(currencyStr);
		}
		turnOverViewService.addTurnOver(addForm, session);

		//付款成功 操作台添加消息
		if (updatenum > 0) {
			//TODO  ******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_order_pnr_pids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("pi.id", "in", pnrIds);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);
			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = record.getString("PNR");
				String uids = record.getString("userid");
				List<Long> receiveUserIds = Lists.newArrayList();
				receiveUserIds.add(Long.valueOf(uids));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
				map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
				searchViewService.addRemindMsg(map, ordernum, pnr, uid, PAYEDMSGTYPE, receiveUserIds, session);
			}
		}

		return null;
	}

	/**
	 * 
	 * 编辑 付款
	 * <p>
	 *
	 * @param form
	 * @param session
	 * @return 
	 */
	public Object updateInlandPay(TUpdateInlandPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		//当前登陆用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();

		//付款订单id
		String payIds = form.getPayIds();
		//订单信息
		Integer payAddress = form.getPayAddress();
		Integer purpose = form.getPurpose();
		Integer fundType = form.getFundType();
		Date payDate = form.getPayDate();
		Double payFees = form.getPayFees();
		Double payMoney = form.getPayMoney();
		String payChineseMoney = form.getPayChineseMoney();
		Integer currency = form.getPayCurrency();
		Integer isInvioce = form.getIsInvioce();
		//银行卡信息
		String bankComp = form.getBankComp();
		String cardName = form.getCardName();
		String cardNum = form.getCardNum();
		//水单地址
		String receiptUrl = form.getReceiptUrl();

		TPayEntity payEntity = dbDao.fetch(TPayEntity.class, Cnd.where("id", "in", payIds));
		if (!Util.isEmpty(payAddress)) {
			payEntity.setPayAddress(payAddress);
		}
		if (!Util.isEmpty(purpose)) {
			payEntity.setPurpose(purpose);
		}
		if (!Util.isEmpty(fundType)) {
			payEntity.setFundType(fundType);
		}
		if (!Util.isEmpty(payDate)) {
			payEntity.setPayDate(payDate);
		}
		if (!Util.isEmpty(payFees)) {
			payEntity.setPayFees(payFees);
		}
		if (!Util.isEmpty(payMoney)) {
			payEntity.setPayMoney(payMoney);
		}
		if (!Util.isEmpty(currency)) {
			payEntity.setPayCurrency(currency);
		}
		if (!Util.isEmpty(isInvioce)) {
			payEntity.setIsInvioce(isInvioce);
		}
		if (!Util.isEmpty(payChineseMoney)) {
			payEntity.setPayChineseMoney(payChineseMoney);
		}
		dbDao.update(payEntity);

		//更新银行卡信息
		Integer bankId = payEntity.getBankId();
		TCompanyBankCardEntity bankEntity = dbDao.fetch(TCompanyBankCardEntity.class, Cnd.where("id", "=", bankId));
		if (!Util.isEmpty(bankEntity)) {
			if (!Util.isEmpty(bankComp)) {
				bankEntity.setBankComp(bankComp);
			}
			if (!Util.isEmpty(cardName)) {
				bankEntity.setCardName(cardName);
			}
			if (!Util.isEmpty(cardNum)) {
				bankEntity.setCardNum(cardNum);
			}
			dbDao.update(bankEntity);
		} else {
			TCompanyBankCardEntity comBankCard = new TCompanyBankCardEntity();
			if (!Util.isEmpty(bankComp)) {
				comBankCard.setBankComp(bankComp);
			}
			if (!Util.isEmpty(cardName)) {
				comBankCard.setCardName(cardName);
			}
			if (!Util.isEmpty(cardNum)) {
				comBankCard.setCardNum(cardNum);
			}
			TCompanyBankCardEntity insert = dbDao.insert(comBankCard);
			Integer id = insert.getId();
			dbDao.update(TPayEntity.class, Chain.make("bankId", id), Cnd.where("id", "in", payIds));
		}

		//更新水单信息
		TPayReceiptEntity payReceipt = dbDao.fetch(TPayReceiptEntity.class, Cnd.where("payId", "in", payIds));
		if (!Util.isEmpty(receiptUrl)) {
			if (!Util.isEmpty(payReceipt)) {
				payReceipt.setReceiptUrl(receiptUrl);
				dbDao.update(payReceipt);
			} else {
				TPayReceiptEntity payReceiptEntity = new TPayReceiptEntity();
				payReceiptEntity.setPayId(payEntity.getId());
				payReceiptEntity.setReceiptUrl(receiptUrl);
				dbDao.insert(payReceiptEntity);
			}
		}
		return null;
	}

	//水单上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(File file, HttpSession session) {
		try {
			String ext = Files.getSuffix(file);
			FileInputStream fileInputStream = new FileInputStream(file);
			String url = qiniuUploadService.uploadImage(fileInputStream, ext, null);
			//水单存储地址
			System.out.println(url);
			return url;
			//业务
		} catch (Exception e) {
			return MobileResult.error("操作失败", null);
		}
	}

	//根据银行id查询银行卡名称
	public Object getCardNames(long bankId, HttpSession session) {
		List<String> cardNames = new ArrayList<String>();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		TBankCardEntity bankEntity = dbDao.fetch(TBankCardEntity.class, Cnd.where("id", "=", bankId));
		if (!Util.isEmpty(bankEntity)) {
			String bankName = bankEntity.getBankName();
			List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, Cnd.where("status", "=", ENABLE)
					.and("bankName", "=", bankName).and("companyId", "=", companyId).groupBy("cardName"), null);
			for (TBankCardEntity bankCardEntity : bankCardList) {
				cardNames.add(bankCardEntity.getCardName());
			}
		}
		return cardNames;
	}

	//根据银行卡名称查询银行卡  卡号
	public Object getCardNums(String cardName, HttpSession session) {
		List<String> cardNums = new ArrayList<String>();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class,
				Cnd.where("status", "=", ENABLE).and("cardName", "=", cardName).and("companyId", "=", companyId), null);
		for (TBankCardEntity bankCardEntity : bankCardList) {
			cardNums.add(bankCardEntity.getCardNum());
		}

		return cardNums;
	}

	//当前公司所有的用户 id
	public String userInComp(HttpSession session) {
		//当前登陆用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserIdd = loginUser.getId();
		//当前公司所有用户id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		String str = sqlManager.get("receivePay_loginComp_user_ids");
		Sql userSql = Sqls.create(str);
		Cnd userCnd = Cnd.NEW();
		userCnd.and("uj.`status`", "=", ONJOB);
		userCnd.and("cj.comId", "=", companyId);
		List<Record> userList = dbDao.query(userSql, userCnd, null);
		String userIds = "";
		for (Record user : userList) {
			userIds += user.getString("userid") + ",";
		}
		if (userIds.length() > 1) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		return userIds;
	}

}
