package com.linyun.airline.admin.invoicemanage.invoicedetail.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInvoiceDetailSqlForm extends DataTablesParamForm {
	/**主键id*/
	private Integer id;

	/**发票信息id*/
	private Integer invoiceinfoid;

	/**发票号*/
	private String invoicenum;

	/**发票金额*/
	private Double invoicebalance;

	/**发票图片url*/
	private String invoiceurl;

	/**操作人*/
	private Integer opid;

	/**操作时间*/
	private Date optime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TInvoiceDetailEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）

		return cnd;
	}
}