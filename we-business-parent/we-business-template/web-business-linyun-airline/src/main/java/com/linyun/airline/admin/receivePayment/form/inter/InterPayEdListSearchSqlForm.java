/**
 * InlandPayListSearchForm.java
 * com.linyun.airline.admin.receivePayment.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.form.inter;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.common.enums.AccountPayEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * (查询 检索付款列表)
 *
 * (这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年2月24日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterPayEdListSearchSqlForm extends DataTablesParamForm {

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**记录状态*/
	private int recordtype;

	/**出发日期 -- 开始出发日期*/
	private Date leaveBeginDate;

	/**出发日期 -- 截止出发日期*/
	private Date leaveEndDate;

	/**当前公司id公司id*/
	private long companyId;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//添加自定义查询条件（可选）
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}

		//TODO 出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("pi.leavesdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("pi.leavesdate", "<=", leaveEndDate);
		}
		cnd.and("po.paystauts", "=", AccountPayEnum.APPROVALPAYED.intKey()); //已收款
		cnd.and("prr.orderstatusid", "=", orderStatus);
		cnd.and("p.companyId", "=", companyId);
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_inter_payed_orders");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		sql.setParam("orderstatus", orderStatus);
		sql.setParam("recordtype", recordtype);
		return sql;
	}

}
