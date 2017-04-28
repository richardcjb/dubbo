package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TReceiveEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TReceiveForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**银行卡id*/
	private Integer bankcardid;

	/**银行名称*/
	private String bankcardname;

	/**银行卡号*/
	private String bankcardnum;

	/**合计*/
	private Double sum;

	/**收款时间*/
	private Date receivedate;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TReceiveEntity.class);
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