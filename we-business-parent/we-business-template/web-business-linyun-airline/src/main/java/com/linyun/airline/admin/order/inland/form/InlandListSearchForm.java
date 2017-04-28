/**
 * InlandListSearchForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月13日 	 
 */
@Data
public class InlandListSearchForm extends DataTablesParamForm {

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer ordersstatus;

	private Integer ticketing;

	private Integer userid;

	private Integer companyId;

	private Long adminId;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
		cnd.and("orderstype", "=", OrderTypeEnum.FIT.intKey());
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			cnd.and("ordersstatus", "=", ordersstatus);
		}
		if (!Util.isEmpty(ticketing)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("receivestatus", "=", "").or("receivestatus", "is", null);
			cnd.and(sqlex);
		}

		/*if (!Util.isEmpty(userid)) {
			cnd.and("tuo.loginUserId", "=", userid);
		}*/
		if (!Util.isEmpty(companyId)) {
			cnd.and("tuo.companyId", "=", companyId);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && userid.longValue() != adminId) {
			cnd.and("tuo.loginUserId", "=", userid);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("tuo.ordersnum", "like", "%" + searchInfo + "%")
					.or("tci.shortName", "like", "%" + searchInfo + "%")
					.or("tci.linkMan", "like", "%" + searchInfo + "%");
			cnd.and(sqlex);
		}
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			String startdatestr = format.format(startdates);
			cnd.and("SUBSTR(tuo.ordersnum,1,8)", ">=", startdatestr);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			String enddatestr = format.format(enddates);
			cnd.and("SUBSTR(tuo.ordersnum,1,8)", "<=", enddatestr);
		}
		cnd.orderBy("tuo.ordersnum", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_inland_listdata");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
