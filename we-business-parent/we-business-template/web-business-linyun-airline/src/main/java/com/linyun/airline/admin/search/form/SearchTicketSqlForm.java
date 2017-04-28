/**
 * SearchTicketSqlForm.java
 * com.linyun.airline.admin.search.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.search.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.entities.TPlanInfoEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(查询团购票)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年1月6日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchTicketSqlForm extends DataTablesParamForm {

	/**
	 * 起飞机场/出发城市
	 */
	private String origin;

	/**
	 * 降落机场/到达城市
	 */
	private String destination;

	/**
	 * 出发日期
	 */
	private String departuredate;

	/**
	 * 返程日期
	 */
	private String returndate;

	/**
	 * 航空公司
	 */
	private String includedcarriers;

	/**
	 * 乘客数
	 */
	private Integer passengercount;

	private int issave;

	private long companyId;

	/**
	 * 舱位等级
	 */
	private String airLevel;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("issave", "=", "1");
		//查询无旅行社名称的
		SqlExpressionGroup exp = new SqlExpressionGroup();
		exp.and("travelname", "is", null).or("travelname", "=", "");
		cnd.and(exp);

		cnd.and("companyid", "=", companyId);
		/*if (!Util.isEmpty(origin)) {
			cnd.and("leavescity", "like", "%" + origin + "%");
		}
		if (!Util.isEmpty(destination)) {
			cnd.and("backscity", "like", "%" + destination + "%");
		}*/
		if (!Util.isEmpty(departuredate)) {
			cnd.and("leavesdate", ">=", departuredate);
		}
		if (!Util.isEmpty(returndate)) {
			cnd.and("leavesdate", "<=", returndate);
		}
		if (!Util.isEmpty(includedcarriers)) {
			cnd.and("airlinename", "like", "%" + includedcarriers + "%");
		}
		cnd.orderBy("leavesdate", "asc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
