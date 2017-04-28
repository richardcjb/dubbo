package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Range;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.TBankCardEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TBankCardForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**银行卡名称*/
	private String cardName;

	/**卡号*/
	private String cardNum;

	/**银行卡类型*/
	private String bankCardType;

	/**银行名称*/
	private String bankName;

	/**币种*/
	private String currency;
	/**状态*/
	@Range(min = 0, max = 1)
	private int status;
	/**更新时间*/
	private Date updateTime;

	/**备注*/
	private String remark;
	/**账户余额*/
	private double balance;
	/*公司id*/
	private Integer companyId;
	/*公司名称*/
	private String companyName;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TBankCardEntity.class);//单表
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

	public void setStatus(int status) {
		this.status = status;
	}
}