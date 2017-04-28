package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TMessageEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TMessageForm implements SQLParamForm, Serializable {
	private static final Long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**标题*/
	private String msgTitle;

	/**内容*/
	private String msgContent;

	/**消息类型*/
	private Integer msgType;

	/**提醒类型*/
	private Long reminderMode;

	/**是否提醒*/
	private Long isRemind;

	/**生成日期*/
	private Date generateTime;
	private String generateTimeString;

	/**优先级*/
	private Integer priorityLevel;

	/**跳转订单id*/
	private Long upOrderId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TMessageEntity.class);
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