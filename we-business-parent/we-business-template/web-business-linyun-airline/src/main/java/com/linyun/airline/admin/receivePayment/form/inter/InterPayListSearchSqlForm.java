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

import com.linyun.airline.common.enums.OrderTypeEnum;
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
public class InterPayListSearchSqlForm extends DataTablesParamForm {

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**付款状态*/
	private String payStatus;

	/**记录状态*/
	private int recordtype;

	/**出发日期 -- 开始出发日期*/
	private Date leaveBeginDate;

	/**出发日期 -- 截止出发日期*/
	private Date leaveEndDate;

	/**公司id*/
	private long companyId;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(payStatus)) {
			cnd.and("po.paystauts", "=", payStatus); //收款状态 收款中、已收款
		}
		if (!Util.isEmpty(orderStatus)) {
			cnd.and("prr.orderstatusid", "=", orderStatus); //订单状态 一订、二订。。。
		}
		cnd.and("p.ordertype", "=", OrderTypeEnum.TEAM.intKey()); //团队（国际）
		cnd.and("p.companyId", "=", companyId);
		cnd.and("prr.recordtype", "=", recordtype);
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_inter_pay_invioce_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
