/**
 * InlandPayListSearchForm.java
 * com.linyun.airline.admin.receivePayment.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.form.inter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * (会计 跨海内陆 确认付款列表)
 *
 * @author   彭辉
 * @Date	 2017年2月26日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterConfirmPaySqlForm extends DataTablesParamForm {

	/**当前登陆用户id*/
	private long pnrId;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//添加自定义查询条件（可选）
		SqlExpressionGroup group = new SqlExpressionGroup();
		//PNR id
		if (!Util.isEmpty(pnrId)) {
			cnd.and("pi.id", "=", pnrId);
		}

		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_pay_Ids");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
