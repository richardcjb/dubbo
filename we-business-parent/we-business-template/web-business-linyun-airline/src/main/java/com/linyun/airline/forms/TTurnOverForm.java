package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.TTurnOverEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTurnOverForm extends DataTablesParamForm {
	/**主键id*/
	private Integer id;

	/**银行卡id*/
	private Integer bankCardId;

	/**交易日期*/
	private Date tradeDate;

	/**平均汇率*/
	private String averageRate;

	/**金额*/
	private double money;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**卡号*/
	private String cardNum;

	/**用途*/
	private String purpose;

	/**币种*/
	private String currency;

	/**备注*/
	private String remark;
	/**是否启用*/
	private String status;

	//银行卡名称
	private String bankCardName;
	//项目
	private String projectName;
	//支出或者收入的操作
	private String operation;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TTurnOverEntity.class);//单表
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