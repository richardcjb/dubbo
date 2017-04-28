package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TUserForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private long id;

	/**用户名*/
	private String userName;

	/**密码*/
	private String password;

	/**用户姓名*/
	private String fullName;

	/**用户名/手机号码*/
	private String telephone;

	/**座机号码*/
	private String landline;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**用户类型*/
	private long userType;

	/**状态*/
	private long status;

	/**创建时间*/
	private DateTime createTime;

	/**更新时间*/
	private DateTime updateTime;

	/**用户是否禁用*/
	private long disableStatus;

	/**备注*/
	private String remark;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TUserEntity.class);
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