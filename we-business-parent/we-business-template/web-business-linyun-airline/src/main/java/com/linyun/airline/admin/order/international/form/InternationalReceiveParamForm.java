/**
 * InternationalParamForm.java
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
 * @Date	 2017年3月15日 	 
 */
@Data
public class InternationalReceiveParamForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer ordersstatus;

	private String ticketing;

	private String ticketingpay;

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer userid;

	private Integer adminId;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			cnd.and("tprr.orderstatusid", "=", ordersstatus);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tuo.loginUserId", "=", userid);
		}
		cnd.and("tprr.recordtype", "=", PayReceiveTypeEnum.RECEIVE.intKey());
		if (!Util.isEmpty(companyid)) {
			cnd.and("tpi.companyid", "=", companyid);
		}
		if (!Util.isEmpty(ordersstatus)) {
			if (!Util.isEmpty(ticketing)) {
				SqlExpressionGroup sqlex = new SqlExpressionGroup();
				sqlex.and("tr.orderstatus", "=", "").or("tr.orderstatus", "is", null);
				cnd.and(sqlex);
			}
			/*if (!Util.isEmpty(ticketingpay)) {
				SqlExpressionGroup sqlex = new SqlExpressionGroup();
				sqlex.and("tuo.paystatus", "=", "").or("tuo.paystatus", "is", null);
				cnd.and(sqlex);
			}*/
		}
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("tuo.ordersnum", "like", "%" + searchInfo + "%").or("tpi.travelname", "like",
					"%" + searchInfo + "%");
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
		cnd.orderBy("tor.receiveDate", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_international_receive_list_sql");
		Sql sql = Sqls.create(sqlString);
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			sql.setParam("ordersstatus", ordersstatus);
		}
		sql.setCondition(cnd());
		return sql;
	}

}
