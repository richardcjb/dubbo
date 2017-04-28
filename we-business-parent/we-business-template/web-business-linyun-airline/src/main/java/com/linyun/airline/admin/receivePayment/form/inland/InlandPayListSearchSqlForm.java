/**
 * InlandPayListSearchForm.java
 * com.linyun.airline.admin.receivePayment.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.form.inland;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

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
public class InlandPayListSearchSqlForm extends DataTablesParamForm {

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**出发日期 -- 开始出发日期*/
	private Date leaveBeginDate;

	/**出发日期 -- 截止出发日期*/
	private Date leaveEndDate;

	/**当前公司下的用户ids*/
	private String loginUserId;

	/**订单状态*/
	private long orderPnrStatus;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//添加自定义查询条件（可选）
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%").or("pi.PNR", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		if (!Util.isEmpty(orderStatus)) {
			cnd.and("pi.orderPnrStatus", "=", orderStatus);
		}

		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("oc.leavetdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("oc.leavetdate", "<=", leaveEndDate);
		}

		cnd.and("uo.orderstype", "=", OrderTypeEnum.FIT.intKey()); //散客
		cnd.and("pi.orderPnrStatus", "=", orderPnrStatus);
		cnd.and("pi.userid", "in", loginUserId);

		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_pay_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
