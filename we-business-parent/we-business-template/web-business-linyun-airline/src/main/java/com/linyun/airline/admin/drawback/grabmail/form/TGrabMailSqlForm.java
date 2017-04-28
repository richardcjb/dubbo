package com.linyun.airline.admin.drawback.grabmail.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.drawback.grabmail.entity.TGrabMailEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabMailSqlForm extends DataTablesParamForm {
	/**主键*/
	private long id;

	/**主题*/
	private String theme;

	/**发件人*/
	private String sender;

	/**收件人*/
	private String addressee;

	/**发送时间*/
	private String sendTime;

	/**抓取时间*/
	private Date grabTime;

	/**抓取状态(1-已抓;2-未抓;)*/
	private long grabStatus;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TGrabMailEntity.class);
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