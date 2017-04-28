/**
 * FuKuanParamForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.common.enums.AccountPayEnum;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月2日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FuKuanParamForm extends DataTablesParamForm {

	private Integer userid;

	private Long companyid;

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer status;

	private Integer adminId;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//cnd.and("tpi.userid", "=", userid);
		cnd.and("tpi.orderPnrStatus", "!=", AccountPayEnum.REFUSE.intKey());
		cnd.and("tii.status", "is", null);
		if (!Util.isEmpty(companyid)) {
			cnd.and("tuo.companyid", "=", companyid);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tuo.loginUserId", "=", userid);
		}
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tpi.optime", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tpi.optime", "<=", enddates);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tuo.ordersnum", "like", "%" + searchInfo + "%").or("tpi.PNR", "like", "%" + searchInfo + "%")
					.or("tci.name", "like", "%" + searchInfo + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("tpi.orderPnrStatus", "=", status);
		}
		//cnd.and("tpi.orderPnrStatus", "=", AccountPayEnum.APPROVALPAYED.intKey());
		cnd.orderBy("tpi.orderPnrStatus", "asc");
		cnd.orderBy("tpi.optime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_pay_fukuan_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
