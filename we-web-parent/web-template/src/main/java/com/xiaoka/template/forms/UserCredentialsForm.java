package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;
import com.xiaoka.template.entities.UserCredentialsEntity;

@Data
public class UserCredentialsForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**用户id*/
	private Long userId;

	/**登录平台*/
	private Integer platform;

	/**第三方平台用户标识*/
	private String unionId;

	/**创建时间*/
	private Date createTime;

	/**上次登录时间*/
	private Date lastLoginTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(UserCredentialsEntity.class);
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