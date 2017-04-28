/**
 * InterReceiptSqlForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

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
 * @Date	 2017年3月18日 	 
 */
@Data
public class InterReceiptSqlForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer userid;

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer receivestatus;

	private Integer adminId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_international_receive_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tr.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("tii.status", "is", null);
		if (!Util.isEmpty(companyid)) {
			cnd.and("tr.companyid", "=", companyid);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tr.userid", "=", userid);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("tr.customename", "like", "%" + searchInfo + "%").or("tu.userName", "like",
					"%" + searchInfo + "%");
			cnd.and(sqlex);
		}
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tr.receivedate", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tr.receivedate", "<=", enddates);
		}
		if (!Util.isEmpty(receivestatus)) {
			cnd.and("tr.status", "=", receivestatus);
		}
		cnd.orderBy("tr.status", "asc");
		cnd.orderBy("tr.receivedate", "desc");
		return cnd;
	}

}
