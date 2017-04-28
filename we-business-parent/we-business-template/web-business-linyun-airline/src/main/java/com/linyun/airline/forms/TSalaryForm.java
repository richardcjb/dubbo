package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.TSalaryEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TSalaryForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**成本合计*/
	private double costTotal;

	/**实收合计*/
	private double incomeTotal;

	/**提成*/
	private String commission;

	/**实发提成*/
	private double actualCommission;

	/**基本工资*/
	/**更新时间*/
	private Date updateTime;
	/**创建时间*/
	private Date createTime;

	/**备注*/
	private String remark;
	/**工资合计*/
	private double salaryTotal;

	/**开票人*/
	private String drawer;

	/**团数*/
	private Integer groupNumber;

	/**人头数*/
	private Integer headCount;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TSalaryEntity.class);//单表
		//String sqlString = sqlManager.get("authoritymanage_area_list");//多表查询当前登录用户负责的区域区域
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//自定义查询条件
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(id)) {
			cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		}
		if (!Util.isEmpty(updateTime)) {
			cnd.orderBy("updateTime", "DESC");
		}
		/**
		 * 按照更新日期查询
		 */
		cnd.orderBy("updateTime", "DESC");
		return cnd;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}