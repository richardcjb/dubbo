/**
 * ApplyApprovalService.java
 * com.linyun.airline.admin.applyapproval.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.applyapproval.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;

import com.linyun.airline.admin.applyapproval.entity.ApprovalList;
import com.linyun.airline.admin.applyapproval.entity.ApprovalListInter;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.ApprovalResultEnum;
import com.linyun.airline.common.enums.MessageRemindEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.OrderRemindEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.enums.ReductionStatusEnum;
import com.linyun.airline.entities.ApplyApprovalEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TMitigateInfoEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月15日 	 
 */

public class ApplyApprovalService extends BaseService<ApplyApprovalEntity> {
	@Inject
	private SearchViewService searchViewService;
	@Inject
	private InterReceivePayService interReceivePayService;

	public Map<String, Object> findNums(HttpSession session) {
		String sqlStringInter = sqlManager.get("applyapproval_list_international");
		Sql sqlInter = Sqls.create(sqlStringInter);
		sqlInter.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		Cnd cnd = Cnd.NEW();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		//国际
		cnd.and("companyId", "=", companyId);
		cnd.and("orderstype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("paystatus", "=", AccountPayEnum.APPROVAL.intKey());
		sqlInter.setCondition(cnd);

		List<Record> list = dbDao.query(sqlInter, cnd, null);
		int internationalNum = list.size();
		/********国际减免的处理开始**********/
		String reduceInteString = sqlManager.get("applyapproval_reduce_inland");
		Sql reduceInteSql = Sqls.create(reduceInteString);
		Cnd reduceInteCnd = Cnd.NEW();
		reduceInteCnd.and("uo.companyId", "=", companyId);
		reduceInteCnd.and("mi.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		reduceInteCnd.and("mi.applyResult", "=", ReductionStatusEnum.APPROVALING.intKey());

		List<Record> reduceInteList = dbDao.query(reduceInteSql, reduceInteCnd, null);
		int reduceInteNum = reduceInteList.size();
		/********国际减免的处理结束**********/
		/********内陆减免的处理开始**********/
		String reduceInlandString = sqlManager.get("applyapproval_reduce_inland");
		Sql reduceInlandSql = Sqls.create(reduceInlandString);
		Cnd reduceInlandCnd = Cnd.NEW();
		reduceInlandCnd.and("uo.companyId", "=", companyId);
		reduceInlandCnd.and("mi.ordertype", "=", OrderTypeEnum.FIT.intKey());
		reduceInlandCnd.and("mi.applyResult", "=", ReductionStatusEnum.APPROVALING.intKey());
		List<Record> reduceInlandList = dbDao.query(reduceInlandSql, reduceInlandCnd, null);
		int reduceInlandNum = reduceInlandList.size();
		/********内陆减免的处理结束**********/
		//内陆
		String sqlString = sqlManager.get("applyapproval_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd1 = Cnd.NEW();
		cnd1.and("companyId", "=", companyId);
		cnd1.and("orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd1.and("orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
		List<Record> list1 = dbDao.query(sql, cnd1, null);
		int inlandNum = list1.size();
		Map<String, Object> re = MapUtil.map();
		re.put("internationalNum", internationalNum + reduceInteNum);
		re.put("inlandNum", inlandNum + reduceInlandNum);

		return re;

	}

	public Object findData(HttpSession session, String operation, String date) {
		Integer orderType = null;
		if ("international".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.TEAM.intKey();
			String sqlString = sqlManager.get("applyapproval_list_international");
			Sql sql = Sqls.create(sqlString);
			sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());

			Cnd cnd = Cnd.NEW();
			//国际
			cnd.and("orderstype", "=", orderType);
			TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
			Long companyId = company.getId();
			//国际
			cnd.and("companyId", "=", companyId);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				cnd.and("date(orderstime)", "=", date);
				re.put("date", date);
			}
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.APPROVALPAYING.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.REFUSE.intKey());*/
			/********国际减免的处理开始**********/
			String reduceInteString = sqlManager.get("applyapproval_reduce_inland");
			Sql reduceInteSql = Sqls.create(reduceInteString);
			Cnd reduceInteCnd = Cnd.NEW();
			reduceInteCnd.and("uo.companyId", "=", companyId);
			reduceInteCnd.and("mi.ordertype", "=", OrderTypeEnum.TEAM.intKey());
			/*dbDao.query(clazz, reduceInteCnd, pager)*/
			reduceInteSql.setCondition(reduceInteCnd);
			//List<Record> reduceInteList = dbDao.query(reduceInteSql, reduceInteCnd, null);
			/********国际减免的处理结束**********/

			sql.setCondition(cnd);
			//List<Record> datalist = dbDao.query(sql, cnd, null);
			/******主数据*******/
			List<ApprovalListInter> query = DbSqlUtil.query(dbDao, ApprovalListInter.class, sql);
			/******主数据*******/
			/******副数据*******/
			List<ApprovalListInter> query1 = DbSqlUtil.query(dbDao, ApprovalListInter.class, reduceInteSql);
			for (ApprovalListInter approvalList : query1) {
				approvalList.setOrderstime(approvalList.getOptime());
				approvalList.setPaystatus(approvalList.getApplyResult());
				approvalList.setAmount(approvalList.getAccount());
				approvalList.setShortName(approvalList.getCustomname());
				approvalList.setIsReduce("YES");
			}
			query.addAll(query1);
			Collections.sort(query, new Comparator<ApprovalListInter>() {

				@Override
				public int compare(ApprovalListInter o1, ApprovalListInter o2) {

					if (!Util.isEmpty(o1.getOrderstime()) || !Util.isEmpty(o1.getOrderstime())) {
						if (!Util.isEmpty(o1.getOrderstime()) && !Util.isEmpty(o2.getOrderstime())) {

							if (o1.getOrderstime().getTime() < o2.getOrderstime().getTime()) {
								return 1;
							} else if (o1.getOrderstime() == o2.getOrderstime()) {

								return 0;
							} else if (o1.getOrderstime().getTime() > o2.getOrderstime().getTime()) {
								return -1;
							}
						}
					}
					return 0;

				}

			});
			/******副数据*******/
			re.put("query", query);
			//re.put("datalist", datalist);
			re.put("operation", operation);
			//re.put("reduceInteList", reduceInteList);
			return re;
		} else if ("inlandNum".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.FIT.intKey();
			String sqlString = sqlManager.get("applyapproval_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			//内陆
			cnd.and("orderstype", "=", orderType);
			TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
			Long companyId = company.getId();
			//内陆
			cnd.and("companyId", "=", companyId);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				cnd.and("date(optime)", "=", date);
				re.put("date", date);
			}
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.APPROVALPAYING.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.REFUSE.intKey());*/
			/********内陆减免的处理开始**********/
			String reduceInlandString = sqlManager.get("applyapproval_reduce_inland");
			Sql reduceInlandSql = Sqls.create(reduceInlandString);
			Cnd reduceInlandCnd = Cnd.NEW();
			reduceInlandCnd.and("uo.companyId", "=", companyId);
			reduceInlandCnd.and("mi.ordertype", "=", OrderTypeEnum.FIT.intKey());
			reduceInlandSql.setCondition(reduceInlandCnd);
			//List<Record> reduceInlandList = dbDao.query(reduceInlandSql, reduceInlandCnd, null);
			/********内陆减免的处理结束**********/
			sql.setCondition(cnd);
			//List<Record> datalist = dbDao.query(sql, cnd, null);
			/******主数据*******/
			List<ApprovalList> query = DbSqlUtil.query(dbDao, ApprovalList.class, sql);
			/******主数据*******/
			/******副数据*******/
			List<ApprovalList> query1 = DbSqlUtil.query(dbDao, ApprovalList.class, reduceInlandSql);
			for (ApprovalList approvalList : query1) {
				approvalList.setOrderPnrStatus(approvalList.getApplyResult());
				approvalList.setCostpricesum(approvalList.getAccount());
				approvalList.setShortName(approvalList.getCustomname());
				approvalList.setIsReduce("YES");
			}
			query.addAll(query1);
			Collections.sort(query, new Comparator<ApprovalList>() {

				@Override
				public int compare(ApprovalList o1, ApprovalList o2) {

					if (!Util.isEmpty(o1.getOptime()) || !Util.isEmpty(o1.getOptime())) {
						if (o1.getOptime().getTime() < o2.getOptime().getTime()) {
							return 1;
						} else if (o1.getOptime() == o2.getOptime()) {

							return 0;
						} else if (o1.getOptime().getTime() > o2.getOptime().getTime()) {
							return -1;
						}
					}
					return 0;

				}

			});
			/******副数据*******/
			//re.put("datalist", datalist);
			re.put("operation", operation);
			//re.put("reduceInlandList", reduceInlandList);
			re.put("query", query);
			return re;
		} else if ("others".equalsIgnoreCase(operation)) {

		}

		return null;
	}

	public Object findDetail(HttpSession session, String operation, String id, String date, String reduce) {
		if (Util.isEmpty(reduce)) {

			Integer orderType = null;
			if ("international".equalsIgnoreCase(operation)) {
				orderType = OrderTypeEnum.TEAM.intKey();
				String sqlString = sqlManager.get("applyapproval_list_international");
				Sql sql = Sqls.create(sqlString);
				sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
				Cnd cnd = Cnd.NEW();
				/*Long companyId = 23l;*/
				TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
				Long companyId = company.getId();
				//国际
				cnd.and("companyId", "=", companyId);
				/*cnd.and("orderstype", "=", orderType);*/
				/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());*/

				cnd.and("id", "=", id);
				sql.setCondition(cnd);
				List<Record> datalist = dbDao.query(sql, cnd, null);
				Map<String, Object> re = MapUtil.map();
				if (!Util.isEmpty(date)) {
					re.put("date", date);
				}
				re.put("detaillist", datalist.get(0));
				re.put("userId", datalist.get(0).get("userId"));
				re.put("operation", operation);
				re.put("reduce", "reduceno");
				return re;
			} else if ("inlandNum".equalsIgnoreCase(operation)) {
				orderType = OrderTypeEnum.FIT.intKey();
				String sqlString = sqlManager.get("applyapproval_list");
				Sql sql = Sqls.create(sqlString);
				Cnd cnd = Cnd.NEW();
				/*Long companyId = 23l;*/
				TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
				Long companyId = company.getId();
				//国际
				cnd.and("companyId", "=", companyId);
				/*cnd.and("orderstype", "=", orderType);*/
				/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());*/

				cnd.and("id", "=", id);
				sql.setCondition(cnd);
				List<Record> datalist = dbDao.query(sql, cnd, null);
				Map<String, Object> re = MapUtil.map();
				if (!Util.isEmpty(date)) {
					re.put("date", date);
				}
				re.put("detaillist", datalist.get(0));
				re.put("userId", datalist.get(0).get("userId"));

				re.put("operation", operation);
				re.put("reduce", "reduceno");
				return re;
			} else if ("others".equalsIgnoreCase(operation)) {

			}
		}
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		if ("YES".equalsIgnoreCase(reduce)) {

			/********内陆减免的处理开始**********/
			String reduceInlandString = sqlManager.get("applyapproval_reduce_inland");
			Sql reduceInlandSql = Sqls.create(reduceInlandString);
			Cnd reduceInlandCnd = Cnd.NEW();
			/*reduceInlandCnd.and("uo.companyId", "=", companyId);
			reduceInlandCnd.and("mi.ordertype", "=", OrderTypeEnum.TEAM.intKey());*/
			/******国际内陆同一，id唯一根据id查取具体的信息****/
			reduceInlandCnd.and("mi.id", "=", id);
			List<Record> reduceList = dbDao.query(reduceInlandSql, reduceInlandCnd, null);

			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				re.put("date", date);
			}
			if (reduceList.size() > 0) {

				re.put("reduceList", reduceList.get(0));
			}
			re.put("operation", operation);
			re.put("reduce", reduce);
			return re;
			/********内陆减免的处理结束**********/
		}
		return null;
	}

	public Map<String, String> doAgree(HttpSession session, Long usingId, Long id, Long status, String temp,
			Long orderId, String operation, Long reduceId, String reduce, Integer reduceStatus, Long resultId,
			Long userId) {
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		//查看会计id
		String sqlUserStr = sqlManager.get("receivePay_count_accounting_companyId");
		Sql sqlUser = Sqls.create(sqlUserStr);
		Cnd cndUser = Cnd.NEW();
		sqlUser.setParam("companyid", companyId);
		List<Record> listUser = dbDao.query(sqlUser, cndUser, null);
		if ("reduceno".equalsIgnoreCase(reduce)) {
			Integer approvalResult = null;
			Integer approvalStatus = null;
			Integer orderType = null;
			Integer upOrderid = null;
			String ordersnum = null;
			String pnr = null;
			int a = 0;
			if ("agree".equalsIgnoreCase(temp)) {
				approvalResult = ApprovalResultEnum.ENABLE.intKey();
				approvalStatus = AccountPayEnum.APPROVALPAYING.intKey();
				orderType = MessageWealthStatusEnum.APPROVAL.intKey();
				a = MessageWealthStatusEnum.APPROVAL.intKey();
			} else if ("refuse".equalsIgnoreCase(temp)) {
				approvalResult = ApprovalResultEnum.DISABLE.intKey();
				approvalStatus = AccountPayEnum.REFUSE.intKey();
				orderType = MessageWealthStatusEnum.UNAPPROVAL.intKey();
				a = MessageWealthStatusEnum.UNAPPROVAL.intKey();
			}
			if ("inlandNum".equalsIgnoreCase(operation)) {
				TPnrInfoEntity pnrInfo = dbDao.fetch(TPnrInfoEntity.class, id);
				String sqlString = sqlManager.get("applyapproval_message_reminder");
				Sql sql = Sqls.create(sqlString);
				Cnd cnd = Cnd.NEW();
				cnd.and("pi.id", "=", pnrInfo.getId());
				List<Record> list = dbDao.query(sql, cnd, null);
				//订单id

				upOrderid = Integer.valueOf(list.get(0).getString("id"));
				//pnr
				pnr = pnrInfo.getPNR();
				//订单号

				ordersnum = list.get(0).getString("ordersnum");
				pnrInfo.setOrderPnrStatus(approvalStatus);
				pnrInfo.setOptime(new Date());
				int res1 = this.updateIgnoreNull(pnrInfo);
				//验证一个订单的pnr是否全部审核通过
				boolean flag = orderTrueOrFalse(usingId);
				if (flag) {
					TPayEntity pay = dbDao.fetch(TPayEntity.class, usingId);
					pay.setApproveResult(ApprovalResultEnum.ENABLE.intKey());
					pay.setApproveTime(new Date());
					int res = this.updateIgnoreNull(pay);
				}
				if (res1 > 0) {
					Map<String, Object> remindMap = new HashMap<String, Object>();
					remindMap.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
					remindMap.put("remindDate", DateUtil.Date2String(new Date()));
					//remindMap.put("remindType", MessageRemindEnum.UNREPEAT.intKey());
					List<Long> receiveUids = new ArrayList<Long>();
					if (listUser.size() > 0) {
						for (Record long1 : listUser) {
							if (!Util.isEmpty(long1.get("userid"))) {
								int longid = (Integer) long1.get("userid");
								Long long2 = (long) longid;
								receiveUids.add(long2);
							}
						}
					}
					receiveUids.add(userId);
					searchViewService.addRemindMsg(remindMap, ordersnum, pnr, upOrderid, orderType, receiveUids,
							session);

					return JsonResult.success("审核通过");
				}
			} else if ("international".equalsIgnoreCase(operation)) {
				TUpOrderEntity upOrderInfo = dbDao.fetch(TUpOrderEntity.class, orderId);
				//订单id
				upOrderid = upOrderInfo.getId();
				//pnr
				TPnrInfoEntity PayPnrEntity = dbDao.fetch(TPnrInfoEntity.class,
						Cnd.where("orderid", "=", orderId).and("mainsection", "=", 1));
				pnr = PayPnrEntity.getPNR();
				//订单号
				ordersnum = upOrderInfo.getOrdersnum();
				upOrderInfo.setPaystatus(approvalStatus);
				int res2 = this.updateIgnoreNull(upOrderInfo);
				TPayOrderEntity payoOrderEntity = dbDao.fetch(TPayOrderEntity.class, Cnd.where("id", "=", resultId));
				payoOrderEntity.setPaystauts(approvalStatus);
				payoOrderEntity.setPayDate(new Date());
				int res3 = this.updateIgnoreNull(payoOrderEntity);
				boolean flag = orderInterTrueOrFalse(usingId);
				if (flag) {
					TPayEntity pay = dbDao.fetch(TPayEntity.class, usingId);
					pay.setApproveResult(ApprovalResultEnum.ENABLE.intKey());
					pay.setApproveTime(new Date());
					pay.setStatus(approvalStatus);
					int res = this.updateIgnoreNull(pay);
				}
				if (res3 > 0) {
					Map<String, Object> remindMap = new HashMap<String, Object>();
					remindMap.put("remindDate", DateUtil.Date2String(new Date()));
					remindMap.put("remindType", MessageRemindEnum.UNREPEAT.intKey());
					//searchViewService.addRemindMsg(remindMap, ordersnum, pnr, upOrderid, orderType, session);
					int payRecType = 2;
					List<Long> receiveUids = new ArrayList<Long>();
					if (listUser.size() > 0) {
						for (Record long1 : listUser) {
							if (!Util.isEmpty(long1.get("userid"))) {
								int longid = (Integer) long1.get("userid");
								Long long2 = (long) longid;
								receiveUids.add(long2);
							}
						}
					}
					receiveUids.add(userId);
					interReceivePayService.addInterRemindMsg(orderId.intValue(), ordersnum, pnr,
							payoOrderEntity.getOrderstatus() + "", a, payRecType, receiveUids, session);
					return JsonResult.success("审核通过");
				}
			}
		}
		if ("YES".equalsIgnoreCase(reduce)) {
			int doingOperation = 0;
			if ("agree".equalsIgnoreCase(temp)) {
				doingOperation = ReductionStatusEnum.AGREE.intKey();
			} else if ("refuse".equalsIgnoreCase(temp)) {
				doingOperation = ReductionStatusEnum.REFUSE.intKey();

			}
			TMitigateInfoEntity mitigateInfo = dbDao.fetch(TMitigateInfoEntity.class, reduceId);
			TUpOrderEntity upOrderEntity = dbDao.fetch(TUpOrderEntity.class, (long) mitigateInfo.getOrderid());
			Chain chain = Chain.make("optime", new Date());
			if (doingOperation > 0) {

				chain.add("applyResult", doingOperation);
			}
			Map<String, Object> remindMap = new HashMap<String, Object>();
			remindMap.put("remindType", OrderRemindEnum.UNREPEAT.intKey());
			remindMap.put("remindDate", DateUtil.Date2String(new Date()));
			remindMap.put("derateMoney", mitigateInfo.getAccount() + "");
			List<Long> receiveUids = new ArrayList<Long>();
			receiveUids.add((long) mitigateInfo.getApplyid());
			searchViewService.addRemindMsg(remindMap, upOrderEntity.getOrdersnum(), "", mitigateInfo.getOrderid(),
					MessageWealthStatusEnum.DERATEMONEY.intKey(), receiveUids, session);

			TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
			chain.add("approvelid", user.getId());
			int res3 = dbDao.update(TMitigateInfoEntity.class, chain, Cnd.where("id", "=", reduceId));
			if (res3 > 0) {
				return JsonResult.success("审核通过");
			}

		}

		//String remindType = (String) fromJson.get("remindType");
		/*int upOrderid = id; //订单id
		String ordersnum = orderinfo.getOrdersnum();//订单号
		 */
		return JsonResult.error("审核失败");

	}

	private boolean orderTrueOrFalse(Long orderId) {
		boolean flag = true;
		String sqlString = sqlManager.get("applyapproval_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		//国际
		cnd.and("orderId", "=", orderId);
		sql.setCondition(cnd);
		List<Record> datalist = dbDao.query(sql, cnd, null);
		for (Record record : datalist) {
			Integer status = (Integer) record.get("orderPnrStatus");
			if (status == AccountPayEnum.APPROVAL.intKey()) {
				flag = false;
				break;
			}
		}
		return flag;

	}

	//判断pay表的状态是否应该为通过
	private boolean orderInterTrueOrFalse(Long orderId) {
		boolean flag = true;
		String sqlString = sqlManager.get("applyapproval_list_international");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		Cnd cnd = Cnd.NEW();
		//国际
		cnd.and("usingId", "=", orderId);
		sql.setCondition(cnd);
		List<Record> datalist = dbDao.query(sql, cnd, null);
		for (Record record : datalist) {
			Integer status = (Integer) record.get("paystatus");
			if (status == AccountPayEnum.APPROVAL.intKey()) {
				flag = false;
				break;
			}
		}
		return flag;

	}

}
