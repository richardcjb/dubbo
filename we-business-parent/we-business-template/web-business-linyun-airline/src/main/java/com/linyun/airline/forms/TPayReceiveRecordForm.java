package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TPayReceiveRecordEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TPayReceiveRecordForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**记录类型*/
	private Integer recordtype;

	/**成本单价*/
	private Double costprice;

	/**预付款比例*/
	private Double prepayratio;

	/**实际人数*/
	private Integer actualnumber;

	/**免罚金可减人数*/
	private Integer freenumber;

	/**本期罚金*/
	private Double currentfine;

	/**本期应付*/
	private Double currentdue;

	/**税金单价*/
	private Double ataxprice;

	/**本期实付*/
	private Double currentpay;

	/**币种*/
	private Integer currency;

	/**操作人*/
	private Integer opid;

	/**操作时间*/
	private Date optime;

	/**订单id*/
	private Integer orderid;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TPayReceiveRecordEntity.class);
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