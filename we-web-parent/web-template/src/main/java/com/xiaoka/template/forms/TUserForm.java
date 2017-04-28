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
import com.xiaoka.template.entities.TUserEntity;

@Data
public class TUserForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**用户姓名*/
	private String userName;

	/**用户名/手机号码*/
	private String telephone;

	/**密码*/
	private String password;

	/**座机号码*/
	private String landline;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**用户类型*/
	private Integer userType;

	/**状态*/
	private Integer status;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**用户是否禁用*/
	private Integer disableStatus;

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