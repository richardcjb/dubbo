package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TFinanceInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TFinanceInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/***/
	private Integer id;

	/**订单id*/
	private Integer orderid;

	/**客户团号*/
	private String cusgroupnum;

	/**类型*/
	private Integer teamtype;

	/**开票日期*/
	private String billingdate;

	/**付款币种*/
	private Integer paycurrency;

	/**付款方式*/
	private Integer paymethod;

	/**销售人员*/
	private String salesperson;

	/**开票人*/
	private String issuer;

	/**人头数*/
	private Integer personcount;

	/**结算状态*/
	private Integer billingstatus;

	/**进澳时间*/
	private String enterausdate;

	/**出澳时间*/
	private String outausdate;

	/**应收*/
	private Double receivable;

	/**减免*/
	private Double relief;

	/**成本合计*/
	private Double costtotal;

	/**实收合计*/
	private Double incometotal;

	/**应返合计*/
	private Double returntotal;

	/**利润合计*/
	private Double profittotal;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TFinanceInfoEntity.class);
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