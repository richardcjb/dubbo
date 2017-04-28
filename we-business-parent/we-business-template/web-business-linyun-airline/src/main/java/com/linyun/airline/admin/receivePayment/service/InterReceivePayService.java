/**
 * ReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.nutz.dao.sql.Sql;
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
import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.service.RemindMessageService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.form.inter.InterPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.TSaveInterPayAddFrom;
import com.linyun.airline.admin.receivePayment.form.inter.TUpdateInterPayAddFrom;
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
import com.linyun.airline.common.enums.MessageLevelEnum;
import com.linyun.airline.common.enums.MessageRemindEnum;
import com.linyun.airline.common.enums.MessageSourceEnum;
import com.linyun.airline.common.enums.MessageStatusEnum;
import com.linyun.airline.common.enums.MessageTypeEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.SearchOrderStatusEnum;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TTurnOverAddForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InterReceivePayService extends BaseService<TPayEntity> {

	private static final String YHCODE = "YH";
	private static final String BZCODE = "BZ";
	private static final String MAINSECTION = "1";
	private static final int ENABLE = BankCardStatusEnum.ENABLE.intKey();
	private static final String YTCODE = ComDictTypeEnum.DICTTYPE_XMYT.key();
	private static final String ZJZLCODE = ComDictTypeEnum.DICTTYPE_ZJZL.key();
	private static final int RECEIVINGMONEY = AccountReceiveEnum.RECEIVINGMONEY.intKey(); //收款中
	private static final int RECEIVESTATUS = AccountReceiveEnum.RECEIVEDONEY.intKey(); //已收款
	private static final int APPROVALPAYED = AccountPayEnum.APPROVALPAYED.intKey();
	private static final int APPROVALPAYING = AccountPayEnum.APPROVALPAYING.intKey();
	private static final int APPROVALENABLE = ApprovalResultEnum.ENABLE.intKey();
	private static final int ONJOB = UserJobStatusEnum.ON.intKey();
	//款项已收
	private static final int RECEDMSGTYPE = MessageWealthStatusEnum.RECEIVED.intKey();
	//款项已付
	private static final int PAYEDMSGTYPE = MessageWealthStatusEnum.PAYED.intKey();
	//订单状态
	private static final int FIRBOOKING = SearchOrderStatusEnum.FIRBOOKING.intKey();
	//收付款枚举
	private static final int PAYTYPE = PayReceiveTypeEnum.PAY.intKey(); //付款记录
	private static final int RECEIVETYPE = PayReceiveTypeEnum.RECEIVE.intKey(); //收款记录
	//重复提醒枚举
	private static final int REPEATTYPE = PayReceiveTypeEnum.REPEAT.intKey(); //收款记录

	//消息提醒中的订单状态
	private static final int SEARCHMSG = SearchOrderStatusEnum.SEARCH.intKey();
	private static final int BOOKINGMSG = SearchOrderStatusEnum.BOOKING.intKey();
	private static final int TICKETINGMSG = SearchOrderStatusEnum.TICKETING.intKey();
	private static final int BILLINGMSG = SearchOrderStatusEnum.BILLING.intKey();
	private static final int CLOSEMSG = SearchOrderStatusEnum.CLOSE.intKey();
	private static final int FIRBOOKINGMSG = SearchOrderStatusEnum.FIRBOOKING.intKey();
	private static final int SECBOOKINGMSG = SearchOrderStatusEnum.SECBOOKING.intKey();
	private static final int THRBOOKINGMSG = SearchOrderStatusEnum.THRBOOKING.intKey();
	private static final int ALLBOOKINGMSG = SearchOrderStatusEnum.ALLBOOKING.intKey();
	private static final int LASTBOOKINGMSG = SearchOrderStatusEnum.LASTBOOKING.intKey();

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private SearchViewService searchViewService;

	@Inject
	private ReceivePayService receivePayService;

	@Inject
	private RemindMessageService remindMessageService;

	@Inject
	private TurnOverViewService turnOverViewService;

	/**
	 * 
	 * 会计收款列表
	 * <p>
	 *  (这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return 
	 */
	public Object listRecData(InterRecListSearchSqlForm form, HttpSession session, HttpServletRequest request) {

		//当前公司下的用户
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		//检索条件
		String name = form.getName();
		Date leaveBeginDate = form.getLeaveBeginDate();
		Date leaveEndDate = form.getLeaveEndDate();
		String orderStatus = form.getOrderStatus();
		if (Util.isEmpty(orderStatus)) {
			orderStatus = "3";
		} else {
			if (Util.eq("firBooking", orderStatus)) {
				//一订
				orderStatus = InternationalStatusEnum.ONEBOOK.intKey() + "";
			}
			if (Util.eq("secBooking", orderStatus)) {
				//二订
				orderStatus = InternationalStatusEnum.TWOBOOK.intKey() + "";
			}
			if (Util.eq("thrBooking", orderStatus)) {
				//三订
				orderStatus = InternationalStatusEnum.THREEBOOK.intKey() + "";
			}
			if (Util.eq("allBooking", orderStatus)) {
				//全款
				orderStatus = InternationalStatusEnum.FULLAMOUNT.intKey() + "";
			}
			if (Util.eq("lastBooking", orderStatus)) {
				//尾款
				orderStatus = InternationalStatusEnum.TAILMONEY.intKey() + "";
			}
			if (Util.eq("outTicket", orderStatus)) {
				//出票
				orderStatus = InternationalStatusEnum.TICKETING.intKey() + "";
			}
		}
		form.setOrderStatus(orderStatus);
		form.setCompanyId(companyId);
		form.setRecordtype(RECEIVETYPE);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) listdata.get("data");

		String sqlStr = sqlManager.get("receivePay_inter_rec_order_list");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("orderstatus", orderStatus);
		sql.setParam("recordtype", RECEIVETYPE);
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("ai.leavedate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("ai.leavedate", "<=", leaveEndDate);
		}
		List<Record> orders = dbDao.query(sql, cnd, null);

		for (Record record : list) {
			//计算合计金额
			Double sum = 0.0;
			//订单号
			String oNum = "";
			//收款id
			String id = record.get("id").toString();
			List<Record> rList = new ArrayList<Record>();
			for (Record r : orders) {
				String rid = r.getString("id");
				String orderNum = r.getString("ordersnum");
				if (Util.eq(id, rid)) {
					rList.add(r);
					if (!Util.isEmpty(r.get("currentpay"))) {
						if (!Util.eq(oNum, orderNum)) {
							Double currentpay = (Double) r.get("currentpay");
							sum += currentpay;
						}
						oNum = orderNum;
					}
				}
			}
			if (!Util.isEmpty(rList)) {
				record.put("orders", rList);
			}
			record.put("sum", sum);
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
	 * (确认收款页面)
	 *
	 * @param inlandPayIds
	 * @return 
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
		cnd.and("prr.recordtype", "=", RECEIVETYPE);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		Double sum = 0.0;
		String prrOrderStatus = "";
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				Double incometotal = (Double) record.get("currentpay");
				sum += incometotal;
			}
			prrOrderStatus = record.getString("prrorderstatus");
		}
		map.put("sum", sum);
		map.put("prrOrderStatus", prrOrderStatus);

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
	 * (保存  确认收款)
	 *
	 * @param inlandPayIds
	 * @return 
	 */
	public Object saveInterRec(String recId, String orderIds, String orderStatus, HttpSession session) {

		int updateNum = 0;
		int orderRecEd = AccountReceiveEnum.RECEIVEDONEY.intKey();
		//获取当前公司
		TCompanyEntity loginCompany = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<TOrderReceiveEntity> newRecOrderList = new ArrayList<TOrderReceiveEntity>();
		List<TOrderReceiveEntity> orderReceiveList = dbDao.query(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "in", recId), null);
		if (!Util.isEmpty(orderReceiveList)) {
			for (TOrderReceiveEntity recOrderEntity : orderReceiveList) {
				recOrderEntity.setReceivestatus(orderRecEd);
				recOrderEntity.setReceiveDate(DateUtil.nowDate());
				newRecOrderList.add(recOrderEntity);
			}
			updateNum = dbDao.update(newRecOrderList);
		}
		dbDao.update(TReceiveEntity.class, Chain.make("status", orderRecEd), Cnd.where("id", "in", recId));

		//添加流水
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
			// ******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_inter_order_rec_rids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("prr.orderid", "in", orderIds);
			cnd.and("prr.orderstatusid", "=", orderStatus);
			cnd.and("pi.mainsection", "=", MAINSECTION);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);

			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = record.getString("PNR");
				String uids = record.getString("userid");
				List<Long> receiveUserIds = Lists.newArrayList();
				receiveUserIds.add(Long.valueOf(uids));
				addInterRemindMsg(uid, ordernum, pnr, orderStatus, RECEDMSGTYPE, RECEIVETYPE, receiveUserIds, session);
			}
		}

		return updateNum;
	}

	public String addInterRemindMsg(int orderId, String ordernum, String pnr, String orderStatus, int typeEnum,
			int payRecType, List<Long> receiveUids, HttpSession session) {
		int msgOrderStatus = 0;
		String statusStr = "";
		switch (orderStatus) {
		case "1":
			msgOrderStatus = SEARCHMSG;
			statusStr = "查询";
			break;
		case "2":
			msgOrderStatus = BOOKINGMSG;
			statusStr = "预订";
			break;
		case "3":
			msgOrderStatus = FIRBOOKINGMSG;
			statusStr = "一订";
			break;
		case "4":
			msgOrderStatus = SECBOOKINGMSG;
			statusStr = "二订";
			break;
		case "5":
			msgOrderStatus = THRBOOKINGMSG;
			statusStr = "三订";
			break;
		case "6":
			msgOrderStatus = ALLBOOKINGMSG;
			statusStr = "全款";
			break;
		case "7":
			msgOrderStatus = LASTBOOKINGMSG;
			statusStr = "尾款";
			break;
		case "8":
			msgOrderStatus = TICKETINGMSG;
			statusStr = "出票";
			break;
		case "9":
			msgOrderStatus = CLOSEMSG;
			statusStr = "关闭";
			break;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
		map.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
		map.put("orderStatus", msgOrderStatus);
		map.put("orderStatusStr", statusStr);
		map.put("payRecType", payRecType);
		String addRemindMsg = addRemindMsg(map, ordernum, pnr, orderId, typeEnum, receiveUids, session);
		return addRemindMsg;
	}

	//重复提醒设置
	public String addInterRepeatRemindMsg(int orderId, String ordernum, String pnr, String orderStatus, int typeEnum,
			int payRecType, int remindType, String remindDate, List<Long> receiveUids, HttpSession session) {
		int msgOrderStatus = 0;
		String statusStr = "";
		switch (orderStatus) {
		case "1":
			msgOrderStatus = SEARCHMSG;
			statusStr = "查询";
			break;
		case "2":
			msgOrderStatus = BOOKINGMSG;
			statusStr = "预订";
			break;
		case "3":
			msgOrderStatus = FIRBOOKINGMSG;
			statusStr = "一订";
			break;
		case "4":
			msgOrderStatus = SECBOOKINGMSG;
			statusStr = "二订";
			break;
		case "5":
			msgOrderStatus = THRBOOKINGMSG;
			statusStr = "三订";
			break;
		case "6":
			msgOrderStatus = ALLBOOKINGMSG;
			statusStr = "全款";
			break;
		case "7":
			msgOrderStatus = LASTBOOKINGMSG;
			statusStr = "尾款";
			break;
		case "8":
			msgOrderStatus = TICKETINGMSG;
			statusStr = "出票";
			break;
		case "9":
			msgOrderStatus = CLOSEMSG;
			statusStr = "关闭";
			break;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("remindDate", remindDate);
		map.put("remindType", remindType);
		map.put("orderStatus", msgOrderStatus);
		map.put("orderStatusStr", statusStr);
		map.put("payRecType", payRecType);
		String addRemindMsg = addRemindMsg(map, ordernum, pnr, orderId, typeEnum, receiveUids, session);
		return addRemindMsg;
	}

	/**
	 * 付款中列表
	 */
	public Map<String, Object> listPayData(final InterPayListSearchSqlForm form, HttpSession session) {

		//检索条件
		String name = form.getName();
		Date leaveBeginDate = form.getLeaveBeginDate();
		Date leaveEndDate = form.getLeaveEndDate();

		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		String orderStatus = form.getOrderStatus();
		if (Util.isEmpty(orderStatus)) {
			orderStatus = "3";
		} else {
			if (Util.eq("firBooking", orderStatus)) {
				//一订
				orderStatus = InternationalStatusEnum.ONEBOOK.intKey() + "";
			}
			if (Util.eq("secBooking", orderStatus)) {
				//二订
				orderStatus = InternationalStatusEnum.TWOBOOK.intKey() + "";
			}
			if (Util.eq("thrBooking", orderStatus)) {
				//三订
				orderStatus = InternationalStatusEnum.THREEBOOK.intKey() + "";
			}
			if (Util.eq("allBooking", orderStatus)) {
				//全款
				orderStatus = InternationalStatusEnum.FULLAMOUNT.intKey() + "";
			}
			if (Util.eq("lastBooking", orderStatus)) {
				//尾款
				orderStatus = InternationalStatusEnum.TAILMONEY.intKey() + "";
			}
			if (Util.eq("outTicket", orderStatus)) {
				//出票
				orderStatus = InternationalStatusEnum.TICKETING.intKey() + "";
			}
		}
		form.setOrderStatus(orderStatus);
		form.setCompanyId(companyId);
		form.setRecordtype(PAYTYPE);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> payList = (List<Record>) listdata.get("data");

		//查询订单
		String sqlStr = sqlManager.get("receivePay_inter_pay_order_list");
		Sql conSql = Sqls.create(sqlStr);
		conSql.setParam("orderstatus", orderStatus);
		conSql.setParam("recordtype", PAYTYPE);
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%").or("pi.PNR", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("tpi.leavesdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("tpi.leavesdate", "<=", leaveEndDate);
		}
		/*cnd.and("tpi.orderPnrStatus", "=", APPROVALPAYED);*/
		List<Record> orders = dbDao.query(conSql, cnd, null);

		for (Record record : payList) {
			String payId = record.getString("id");
			List<Record> orderList = new ArrayList<Record>();
			for (Record r : orders) {
				String orderId = r.getString("id");
				String reduceNumStr = r.getString("actualyreduce");
				if (Util.isEmpty(reduceNumStr)) {
					reduceNumStr = "0";
				}
				String actualNumStr = r.getString("actualnumber");
				if (Util.isEmpty(actualNumStr)) {
					actualNumStr = "0";
				}
				int peopleCount = Integer.valueOf(actualNumStr) + Integer.valueOf(reduceNumStr);
				r.set("peoplecount", peopleCount);
				if (Util.eq(payId, orderId)) {
					orderList.add(r);
				}
			}
			if (!Util.isEmpty(orderList)) {
				record.put("orders", orderList);
			}
		}
		List<Record> ordersBC = new ArrayList<Record>();
		for (Record r : payList) {
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
	 * 
	 * (会计已付款查询)
	 * <p>
	 * 
	 *
	 * @param sqlParamForm
	 * @return 
	 */
	public Object listPayEdData(InterPayEdListSearchSqlForm form, HttpSession session) {
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		form.setCompanyId(companyId);
		String orderStatus = form.getOrderStatus();
		if (Util.isEmpty(orderStatus)) {
			orderStatus = "3";
		} else {
			if (Util.eq("firBooking", orderStatus)) {
				//一订
				orderStatus = InternationalStatusEnum.ONEBOOK.intKey() + "";
			}
			if (Util.eq("secBooking", orderStatus)) {
				//二订
				orderStatus = InternationalStatusEnum.TWOBOOK.intKey() + "";
			}
			if (Util.eq("thrBooking", orderStatus)) {
				//三订
				orderStatus = InternationalStatusEnum.THREEBOOK.intKey() + "";
			}
			if (Util.eq("allBooking", orderStatus)) {
				//全款
				orderStatus = InternationalStatusEnum.FULLAMOUNT.intKey() + "";
			}
			if (Util.eq("lastBooking", orderStatus)) {
				//尾款
				orderStatus = InternationalStatusEnum.TAILMONEY.intKey() + "";
			}
			if (Util.eq("outTicket", orderStatus)) {
				//出票
				orderStatus = InternationalStatusEnum.TICKETING.intKey() + "";
			}
		}
		form.setOrderStatus(orderStatus);
		form.setRecordtype(PAYTYPE);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");

		List<String> payIds = new ArrayList<String>();
		String id = "";
		for (Record record : data) {
			String pid = record.getString("pid");
			if (!Util.eq(id, pid)) {
				payIds.add(pid);
			}
			id = pid;
		}
		List<Record> newData = new ArrayList<Record>();
		for (String pid : payIds) {
			Record record = new Record();
			double totalmoney = 0; //总额
			String shortname = ""; //收款单位
			int payStatus = AccountPayEnum.APPROVALPAYED.intKey(); //收款状态
			String issuer = "";
			String prrIds = "";
			List<Record> orders = new ArrayList<Record>();
			for (Record r : data) {
				String pidStr = r.getString("pid");
				String prrid = r.getString("prrid");
				shortname = r.getString("shortname");
				issuer = r.getString("issuer");
				String currentpayStr = r.getString("currentpay");
				//同一个支付订单
				if (Util.eq(pid, pidStr)) {
					if (!Util.isEmpty(currentpayStr)) {
						totalmoney = Double.valueOf(currentpayStr);
					}
					prrIds += prrid + ",";
					orders.add(r);
				} else {
					if (!Util.isEmpty(currentpayStr)) {
						totalmoney = Double.valueOf(currentpayStr);
					}
				}
			}
			prrIds = prrIds.substring(0, prrIds.length() - 1);
			record.put("pid", pid);
			record.put("prrIds", prrIds);
			record.put("totalmoney", totalmoney);
			record.put("shortname", shortname);
			record.put("payStatus", payStatus);
			record.put("issuer", issuer);
			record.put("orders", orders);

			newData.add(record);
		}

		listdata.remove("data");
		listdata.put("data", newData);
		return listdata;
	}

	/**
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return 
	 */
	public Object toConfirmPay(String orderIds, HttpSession session) {
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		Map<String, Object> map = new HashMap<String, Object>();
		Sql sql = Sqls.create(sqlManager.get("receivePay_inter_pay_order_ids"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("prr.id", "in", orderIds);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String payIds = "";
		String pOrderIds = "";
		String pOrderStatus = "";
		if (!Util.isEmpty(orders)) {
			String shortname = orders.get(0).getString("shortname");
			for (Record record : orders) {
				String everyShortName = record.getString("shortname");
				String id = record.getString("id");
				String uid = record.getString("uid");
				String prrOrderStatus = record.getString("prrorderstatus");
				if (!Util.eq(shortname, everyShortName)) {
					map.put("sameName", false);
				} else {
					map.put("sameName", true);
				}
				if (!Util.isEmpty(id)) {
					payIds += record.getString("id") + ",";
				}
				if (!Util.isEmpty(uid)) {
					pOrderIds += record.getString("uid") + ",";
				}
				if (!Util.isEmpty(prrOrderStatus)) {
					pOrderStatus = record.getString("prrorderstatus");
				}
			}
		}

		//同一订单号，一行显示   TODO
		List<Record> NewList = new ArrayList<Record>();
		String oNumStr = "";
		for (Record record : orders) {
			String oNum = record.getString("ordersnum");
			if (!Util.eq(oNumStr, oNum)) {
				NewList.add(record);
			}
			oNumStr = oNum;
		}
		//新集合、
		for (Record record : NewList) {
			List<String> pnrList = new ArrayList<String>();
			String newONum = record.getString("ordersnum");
			for (Record r : orders) {
				String oNum = r.getString("ordersnum");
				if (Util.eq(newONum, oNum)) {
					String pnrStr = r.getString("pnrnum");
					if (!Util.isEmpty(pnrStr)) {
						pnrList.add(pnrStr);
					}
				}
			}
			record.set("pnrnum", pnrList);
		}

		map.put("orders", NewList);

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
		String oids = "";
		String oStr = "";
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("currentpay"))) {
				String ordernumStr = record.getString("ordersnum");
				if (!Util.eq(oStr, ordernumStr)) {
					Double incometotal = (Double) record.get("currentpay");
					totalMoney += incometotal;
				}
				oStr = ordernumStr;
			}
			oids = record.getString("id") + ",";
			proposer = record.getString("proposerMan");
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
			fkytList = receivePayService.findCodeByName("", YTCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fkytList", fkytList);
		//资金种类
		List<ComDictInfoEntity> zjzlList = new ArrayList<ComDictInfoEntity>();
		try {
			zjzlList = receivePayService.findCodeByName("", ZJZLCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oids = oids.substring(0, oids.length() - 1);
		map.put("zjzlList", zjzlList);
		map.put("ids", oids);
		if (pOrderIds.length() > 1) {
			pOrderIds = pOrderIds.substring(0, pOrderIds.length() - 1);
		}
		map.put("pOrderIds", pOrderIds);
		map.put("orderStatus", pOrderStatus);
		return map;
	}

	/**
	 * 
	 * 到编辑已付款页面
	 * <p>
	 * TODO
	 * @param request
	 * @return 
	 */
	public Object editConfirmPay(HttpServletRequest request, HttpSession session) {

		//当前公司所有用户id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		Map<String, Object> result = new HashMap<String, Object>();
		String payId = request.getParameter("payid");
		String prrIds = request.getParameter("prrIds");

		//TODO
		String sqlString = sqlManager.get("receivePay_inter_payed_edit");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("p.id", "in", payId);
		cnd.and("prr.id", "in", prrIds);
		cnd.and("po.paystauts", "=", APPROVALPAYED);
		List<Record> payList = dbDao.query(sql, cnd, null);
		//总金额
		double totalMoney = 0.00;
		//申请人
		String proposer = "";
		//审批人
		String approver = "";
		//审批结果
		String approveresult = "";
		//操作人
		String operator = "";
		String oStr = "";
		for (Record record : payList) {
			//计算订单总金额
			if (!Util.isEmpty(record.get("currentpay"))) {
				String ordernumStr = record.getString("ordersnum");
				if (!Util.eq(oStr, ordernumStr)) {
					Double costpricesum = (Double) record.get("currentpay");
					totalMoney += Double.valueOf(costpricesum);
				}
				oStr = ordernumStr;
			}

			proposer = record.getString("proposerMan");
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

		//同一订单号，一行显示   TODO
		List<Record> NewList = new ArrayList<Record>();
		String oNumStr = "";
		for (Record record : payList) {
			String oNum = record.getString("ordersnum");
			if (!Util.eq(oNumStr, oNum)) {
				NewList.add(record);
			}
			oNumStr = oNum;
		}
		//新集合、
		for (Record record : NewList) {
			List<String> pnrList = new ArrayList<String>();
			String newONum = record.getString("ordersnum");
			for (Record r : payList) {
				String oNum = r.getString("ordersnum");
				if (Util.eq(newONum, oNum)) {
					String pnrStr = r.getString("pnrnum");
					if (!Util.isEmpty(pnrStr)) {
						pnrList.add(pnrStr);
					}
				}
			}
			record.set("pnrnum", pnrList);
		}

		result.put("payList", NewList);
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

	/**
	 * 
	 * 编辑 付款
	 * <p>
	 *
	 * @param form
	 * @param session
	 * @return 
	 */
	public Object updateInterPay(TUpdateInterPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
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
		int updateNum = dbDao.update(payEntity);

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

		return "seccess";
	}

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return  (这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInterPay(TSaveInterPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
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
		String payIds = form.getPayIds();
		String orderIds = form.getOrderIds();
		String orderStatus = form.getOrderStatus();
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
		List<TPayReceiptEntity> payReceiptList = new ArrayList<TPayReceiptEntity>();
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
		TPayOrderEntity payOrder = dbDao.fetch(TPayOrderEntity.class, Cnd.where("payid", "in", payIds));
		int payids = payOrder.getPayid();
		List<TPayEntity> payEntityList = dbDao.query(TPayEntity.class, Cnd.where("id", "in", payIds), null);
		for (TPayEntity payEntity : payEntityList) {
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
				payEntity.setPayCurrency(currency);
			}
			if (!Util.eq(null, isInvioce)) {
				payEntity.setIsInvioce(isInvioce);
			}
			if (!Util.eq(null, payChineseMoney)) {
				payEntity.setPayChineseMoney(payChineseMoney);
			}
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
			}
			payReceiptList.add(payReceiptEntity);
		}
		//添加水单表
		if (!Util.isEmpty(payReceiptList)) {
			dbDao.insert(payReceiptList);
		}
		int updateNum = 0;
		//更新付款订单表状态
		List<TPayOrderEntity> newPayOrderList = new ArrayList<TPayOrderEntity>();
		List<TPayOrderEntity> payOrderList = dbDao.query(TPayOrderEntity.class, Cnd.where("payid", "in", payids), null);
		if (!Util.isEmpty(payOrderList)) {
			for (TPayOrderEntity payOrderEntity : payOrderList) {
				payOrderEntity.setPaystauts(APPROVALPAYED);
				payOrderEntity.setPayDate(DateUtil.nowDate());
				newPayOrderList.add(payOrderEntity);

			}
			updateNum = dbDao.update(newPayOrderList);
		}
		//更新付款表状态   TODO payIds
		/*dbDao.update(TPayEntity.class, Chain.make("status", APPROVALPAYED), Cnd.where("id", "in", payIds));*/

		//添加流水 TODO
		TTurnOverAddForm addForm = new TTurnOverAddForm();
		String comName = tCompanyEntity.getComName();
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
		if (updateNum > 0) {
			//******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_inter_order_pay_rids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("prr.orderid", "in", orderIds);
			cnd.and("prr.orderstatusid", "=", orderStatus);
			cnd.and("pi.mainsection", "=", MAINSECTION);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);

			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = record.getString("PNR");
				String uids = record.getString("userid");
				List<Long> receiveUserIds = Lists.newArrayList();
				receiveUserIds.add(Long.valueOf(uids));
				addInterRemindMsg(uid, ordernum, pnr, orderStatus, PAYEDMSGTYPE, PAYTYPE, receiveUserIds, session);
			}
		}

		return "success";
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
		DictInfoEntity bankEntity = dbDao.fetch(DictInfoEntity.class, Cnd.where("id", "=", bankId));
		if (!Util.isEmpty(bankEntity)) {
			String bankName = bankEntity.getDictName();
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

	//查询用途 公司字典
	public List<ComDictInfoEntity> findCodeByName(String name, String typeCode, long companyId) throws Exception {
		Cnd cnd = Cnd.NEW();
		cnd.and("comDictName", "like", Strings.trim(name) + "%").and("status", "=", DataStatusEnum.ENABLE.intKey())
				.and("comTypeCode", "=", typeCode).and("comId", "=", companyId).groupBy("comDictName");
		List<ComDictInfoEntity> query = dbDao.query(ComDictInfoEntity.class, cnd, null);
		return query;
	}

	/**
	 * 
	 * ******************************************添加消息提醒*************************************************
	 * <p>
	 *
	 * @param data Json数据
	 * @param generateOrderNum  订单号
	 * @param pnr  pnr号
	 * @param orderStatus  订单状态(使用消息提醒的枚举)
	 * @param session
	 * @return 
	 */
	public String addRemindMsg(Map<String, Object> fromJson, String generateOrderNum, String pnr, int upOrderId,
			int orderStatus, List<Long> receiveUids, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();
		//消息接收方ids
		List<Long> receiveUserIds = Lists.newArrayList();
		if (!Util.isEmpty(receiveUids)) {
			for (Long uid : receiveUids) {
				receiveUserIds.add(uid);
			}
		} else {
			receiveUserIds.add(userId);
		}
		//消息来源id
		long SourceUserId = userId;
		//消息来源方类型
		int sourceUserType = MessageSourceEnum.SYSTEMMSG.intKey();
		//消息接收方类型（个人、公司、系统）
		int receiveUserType = MessageSourceEnum.PERSONALMSG.intKey();
		//消息状态
		int msgStatus = MessageStatusEnum.UNREAD.intKey();

		//提醒日期 TODO
		String remindDateStr = (String) fromJson.get("remindDate");
		//客户信息id
		/*String customerInfoId = (String) fromJson.get("customerInfoId");*/
		String customerInfoId = null;
		//消息提醒日期
		Date remindDateTime = DateUtil.nowDate();
		if (!Util.isEmpty(remindDateStr)) {
			remindDateTime = DateUtil.string2Date(remindDateStr);
		}

		//订单状态
		String orderStatusStr = fromJson.get("orderStatusStr").toString();
		//收付款状态
		String payRecType = fromJson.get("payRecType").toString();

		//消息提醒方式
		String remindStr = fromJson.get("remindType").toString();
		if (Util.isEmpty(remindStr)) {
			remindStr = "6";
		}
		Integer remindType = Integer.valueOf(remindStr);
		switch (remindType) {
		case 0:
			//每15Min  消息表：5
			remindType = MessageRemindEnum.FIFTEENM.intKey();
			break;
		case 1:
			//每30Min  消息表：7
			remindType = MessageRemindEnum.THIRTYM.intKey();
			break;
		case 2:
			//每1H 消息表：4
			remindType = MessageRemindEnum.HOUR.intKey();
			break;
		case 3:
			//每一天 消息表：3
			remindType = MessageRemindEnum.DAY.intKey();
			break;
		case 4:
			//每一周 消息表：2
			remindType = MessageRemindEnum.WEEK.intKey();
			break;
		case 5:
			//每一月 消息表：1
			remindType = MessageRemindEnum.MOUTH.intKey();
			break;
		case 6:
			//不重复（只提醒一次） 消息表：8
			remindType = MessageRemindEnum.UNREPEAT.intKey();
			break;
		default:
			//自定义 消息表：6
			remindType = MessageRemindEnum.TIMED.intKey();
			break;
		}
		long reminderMode = remindType;

		//消息类型和订单状态 orderStatus有关
		int msgType = MessageTypeEnum.NOTICEMSG.intKey(); //消息类型---默认为"系统通知消息"
		int msgLevel = MessageLevelEnum.MSGLEVEL1.intKey(); //消息优先级---默认为"优先级一"
		String msgContent = ""; //消息内容
		switch (orderStatus) {
		case 1:
			//查询 4
			msgType = MessageTypeEnum.SEARCHMSG.intKey();
			//消息等级2
			msgLevel = MessageLevelEnum.MSGLEVEL2.intKey();
			//消息内容
			msgContent = "查询单号：" + generateOrderNum + " 记录编号：" + pnr;
			break;
		case 2:
			//预订 5
			msgType = MessageTypeEnum.BOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL2.intKey();
			msgContent = "预定单号：" + generateOrderNum + " 记录编号：" + pnr;
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 3:
			//开票 (消息内容TODO)  6   
			msgType = MessageTypeEnum.DRAWBILLMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL3.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "开发票中";
			break;
		case 4:
			//出票 (消息内容TODO) 7
			msgType = MessageTypeEnum.MAKEOUTBILLMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL3.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "发票已开";
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 5:
			//关闭 (消息内容TODO)  0
			msgType = MessageTypeEnum.CLOSEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL1.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "已关闭";
			break;
		case 6:
			//一订 8
			msgType = MessageTypeEnum.FIRBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			if (Util.eq(PAYTYPE, payRecType)) {
				//付款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "付款已提交";
			}
			if (Util.eq(RECEIVETYPE, payRecType)) {
				//收款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			}
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}

			break;
		case 7:
			//二订 9
			msgType = MessageTypeEnum.SECBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			if (Util.eq(PAYTYPE, payRecType)) {
				//付款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "付款已提交";
			}
			if (Util.eq(RECEIVETYPE, payRecType)) {
				//收款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			}
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 8:
			//三订 10
			msgType = MessageTypeEnum.THRBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			if (Util.eq(PAYTYPE, payRecType)) {
				//付款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "付款已提交";
			}
			if (Util.eq(RECEIVETYPE, payRecType)) {
				//收款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			}
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 9:
			//全款 11
			msgType = MessageTypeEnum.ALLBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			if (Util.eq(PAYTYPE, payRecType)) {
				//付款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "付款已提交";
			}
			if (Util.eq(RECEIVETYPE, payRecType)) {
				//收款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			}
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 10:
			//尾款 12
			msgType = MessageTypeEnum.LASTBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			if (Util.eq(PAYTYPE, payRecType)) {
				//付款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "付款已提交";
			}
			if (Util.eq(RECEIVETYPE, payRecType)) {
				//收款
				msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			}
			if (Util.eq(REPEATTYPE, payRecType)) {
				msgContent = "单号：" + generateOrderNum + " 当前状态：" + orderStatusStr + "状态";
			}
			break;
		case 11:
			//已收款 14
			msgType = MessageTypeEnum.RECEIVEDMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "款项已收";
			break;
		case 12:
			//已付款 15
			msgType = MessageTypeEnum.PAYEDMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "款项已付";
			break;
		case 13:
			//收款款已开发票 16
			msgType = MessageTypeEnum.INVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " " + orderStatusStr + "发票已开";
			break;
		case 14:
			//付款已收发票 17
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "发票已收";
			break;
		case 15:
			//付款 已审批18
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "审批已通过";
			break;
		case 16:
			//付款已收发票 19
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "审批已拒绝";
			break;
		case 17: //MessageWealthStatusEnum
			//付款 收款已提交 20
			msgType = MessageTypeEnum.RECSUBMITED.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收款已提交";
			break;
		case 18: //MessageWealthStatusEnum
			//付款 需付款已提交申请 21  MessageTypeEnum
			msgType = MessageTypeEnum.PSAPPROVALING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "需付款/已提交申请";
			break;
		case 19: //MessageWealthStatusEnum
			//付款 （会计）开发票中 22  MessageTypeEnum
			msgType = MessageTypeEnum.INVIOCING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " " + orderStatusStr + "开发票中";
			break;
		case 20: //MessageWealthStatusEnum
			//付款 （会计）收发票中 23  MessageTypeEnum
			msgType = MessageTypeEnum.RECINVIOCING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 记录编号：" + pnr + " " + orderStatusStr + "收发票中";
			break;
		}

		/*添加的消息 存放到map中*/
		Map<String, Object> mapMsg = Maps.newHashMap();
		mapMsg.put("msgContent", msgContent);
		mapMsg.put("msgType", msgType);
		mapMsg.put("msgLevel", msgLevel);
		mapMsg.put("msgStatus", msgStatus);
		mapMsg.put("reminderMode", reminderMode);
		mapMsg.put("SourceUserId", SourceUserId);
		mapMsg.put("sourceUserType", sourceUserType);
		mapMsg.put("receiveUserIds", receiveUserIds);
		mapMsg.put("receiveUserType", receiveUserType);
		mapMsg.put("customerInfoId", customerInfoId);
		mapMsg.put("remindMsgDate", remindDateTime);
		mapMsg.put("upOrderId", upOrderId);
		mapMsg.put("upOrderStatus", orderStatus);
		remindMessageService.addMessageEvent(mapMsg);
		return "消息添加成功";
	}

}
