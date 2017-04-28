/**
 * ShouInvoiceParamForm.java
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

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月9日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShouInvoiceParamForm extends DataTablesParamForm {

	private Integer userid;

	private Integer status;//开票状态
	private Integer billuserid;//收票人
	private Date shouInvoiceBeginDate;//收票日期
	private Date shouInvoiceEndDate;//收票日期
	private String PNR;//pnr
	private String paymentunit;//收款单位

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Long companyid;

	private Integer adminId;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//cnd.and("opid", "=", userid);
		cnd.and("invoicetype", "=", PayReceiveTypeEnum.PAY.intKey());
		if (!Util.isEmpty(companyid)) {
			cnd.and("tii.comId", "=", companyid);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tii.opid", "=", userid);
		}
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tii.invoicedate", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tii.invoicedate", "<=", enddates);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tpi.PNR", "like", "%" + searchInfo + "%").or("tii.paymentunit", "like", "%" + searchInfo + "%")
					.or("tuo.ordersnum", "like", "%" + searchInfo + "%")
					.or("tu.userName", "like", "%" + searchInfo + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("tii.status", "=", status);
		}
		cnd.orderBy("tii.optime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_shou_invoice_list_order");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}
}
