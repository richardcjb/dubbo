package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;
import com.xiaoka.template.entities.SLogEntity;

@Data
public class SLogForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**操作员id*/
	private Integer operatorId = 0;

	/**功能id*/
	private Integer functionId = 0;

	/**操作员名称*/
	private String operatorName = "";

	/**访问路径*/
	private String path = "";

	/**功能名称*/
	private String functionName = "";

	/**操作时间*/
	private Date operatorTime = DateTimeUtil.nowDate();

	/**IP地址*/
	private String ip;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(SLogEntity.class);
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