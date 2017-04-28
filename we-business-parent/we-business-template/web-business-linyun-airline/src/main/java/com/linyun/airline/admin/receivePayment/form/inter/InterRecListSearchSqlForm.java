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
public class InterRecListSearchSqlForm extends DataTablesParamForm {

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**收款状态*/
	private String receiveStatus;

	/**记录状态*/
	private int recordtype;

	/**出发日期 -- 开始出发日期*/
	private Date leaveBeginDate;

	/**出发日期 -- 截止出发日期*/
	private Date leaveEndDate;

	/**当前公司用户id*/
	private long companyId;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(receiveStatus)) {
			cnd.and("orec.receivestatus", "=", receiveStatus); //收款状态 收款中、已收款
		}
		if (!Util.isEmpty(orderStatus)) {
			cnd.and("orec.orderstatus", "=", orderStatus); //订单状态 一订、二订。。。
		}
		cnd.and("r.companyid", "=", companyId);

		cnd.and("r.orderstype", "=", OrderTypeEnum.TEAM.intKey()); //团队（国际）
		cnd.and("prr.recordtype", "=", recordtype);
		//表示已审批  TODO
		/*cnd.and("orec.receivestatus", "=", 2);*/
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_inter_rec_invioce_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		sql.setParam("prrOStatus", orderStatus);
		return sql;
	}

}
