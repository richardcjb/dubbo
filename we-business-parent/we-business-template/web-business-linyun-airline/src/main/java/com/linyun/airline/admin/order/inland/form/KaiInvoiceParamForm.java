/**
 * KaiInvoiceParamForm.java
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

import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
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
public class KaiInvoiceParamForm extends DataTablesParamForm {

	private Integer userid;
	private Integer status;//开票状态
	private Integer billuserid;//开票人
	private Date kaiInvoiceBeginDate;//开票日期
	private Date kaiInvoiceEndDate;//开票日期
	private String invoicenum;//发票号
	private String paymentunit;//付款单位

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Long companyid;

	private Integer adminId;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//cnd.and("opid", "=", userid);
		if (!Util.isEmpty(companyid)) {
			cnd.and("comId", "=", companyid);
		}
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("opid", "=", userid);
		}
		cnd.and("ordertype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("invoicedate", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("invoicedate", "<=", enddates);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("paymentunit", "like", "%" + searchInfo + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("status", "=", status);
		}
		cnd.orderBy("optime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
