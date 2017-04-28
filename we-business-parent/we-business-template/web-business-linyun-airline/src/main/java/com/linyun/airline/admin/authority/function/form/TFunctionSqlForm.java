/**
 * TFunctionSqlForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.function.form;

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
 * @Date	 2016年11月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionSqlForm extends DataTablesParamForm {

	/**上级功能id*/
	private long parentId;

	/**功能名称*/
	private String name;

	/**访问地止*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private long level;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private long sort;

	/**头像*/
	private String portrait;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("function_manage_list");
		//String sqlString = EntityUtil.entityCndSql(TFunctionEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//自定义条件
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(name))
			cnd.and("t.name", "LIKE", "%" + name + "%");

		if (!Util.isEmpty(parentId) && parentId > 0) {
			cnd.and("t.parentId", "=", parentId);
		}
		cnd.desc("t.id");
		return cnd;
	}
}
