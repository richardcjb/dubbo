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
import com.xiaoka.template.entities.SUserEntity;

@Data
public class SUserForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**用户id*/
	private Integer id;

	/**用户名*/
	private String userName = "";

	/**密码*/
	private String password = "";

	/**用户邮箱*/
	private String email = "";

	/**手机号*/
	private String tel = "";

	/**参见字典-用户类型*/
	private String userType = "";

	/**用户状态，0-初始化，1-启用，2-停用*/
	private Integer status = 0;

	/**创建时间*/
	private Date createTime = DateTimeUtil.nowDate();

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(SUserEntity.class);
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