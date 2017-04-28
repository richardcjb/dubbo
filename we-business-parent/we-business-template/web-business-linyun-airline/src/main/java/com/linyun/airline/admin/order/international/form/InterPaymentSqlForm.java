/**
 * InterPaymentSqlForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.common.enums.AccountPayEnum;
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
 * @Date	 2017年3月18日 	 
 */
@Data
public class InterPaymentSqlForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer userid;

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer paystatus;

	private Integer adminId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_international_pay_list");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tp.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("tii.status", "is", null);
		if (!Util.isEmpty(companyid)) {
			cnd.and("tp.companyid", "=", companyid);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tuo.loginUserId", "=", userid);
		}
		cnd.and("tpo.paystauts", "!=", AccountPayEnum.REFUSE.intKey());
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
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
			cnd.and("tpo.payDate", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			String enddatestr = format.format(enddates);
			cnd.and("tpo.payDate", "<=", enddates);
		}
		if (!Util.isEmpty(paystatus)) {
			cnd.and("tpo.paystauts", "=", paystatus);
		}
		cnd.orderBy("tpo.paystauts", "asc");
		cnd.orderBy("tpo.payDate", "desc");
		return cnd;
	}

}
