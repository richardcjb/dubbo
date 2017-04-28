package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TAirlineInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TAirlineInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;

	/**航空公司代码*/
	private String aircom;

	/**出发城市*/
	private String leavecity;

	/**到达城市*/
	private String arrvicity;

	/**去程日期*/
	private String leavedate;

	/**回程日期*/
	private String backdate;

	/**原价(单价)*/
	private Double formprice;

	/**售价(单价)*/
	private Double price;

	/**结算货币代码*/
	private String currencyCode;

	/**数量*/
	private Integer passengercount;

	/**乘客类型*/
	private Integer passengertype;

	/**机票类型*/
	private Integer tickettype;

	/**旅程类型*/
	private Integer traveltype;

	/**需求id*/
	private Integer needid;

	/**计划id*/
	private Integer planid;

	/**出发时间*/
	private String leavetime;

	/**抵达时间*/
	private String arrivetime;

	/**航班号*/
	private String ailinenum;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAirlineInfoEntity.class);
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