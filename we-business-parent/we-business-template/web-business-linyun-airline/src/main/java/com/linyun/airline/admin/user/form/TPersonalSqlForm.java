/**
 * TPersonalSqlForm.java
 * com.linyun.airline.admin.user.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月24日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TPersonalSqlForm extends DataTablesParamForm {

	/**主键*/
	private long id;

	/**部门id*/
	private long deptId;

	/**职位id*/
	private long jobId;

	/**区域id*/
	private long areaId;

	/**用户名*/
	private String userName;

	/**用户姓名*/
	private String fullName;

	/**部门名称*/
	private String deptName;

	/**密码*/
	private String password;

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
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**用户是否禁用*/
	private long disableStatus;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("employee_personalInfo_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//自定义条件
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(id)) {
			cnd.and("u.id", "=", id);
		}
		return cnd;
	}
}
