/**
 * TCompanyUserForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.Company.form;

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
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年11月21日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyUserSqlForm extends DataTablesParamForm {

	private Long id;

	private String userName;

	private String depid;

	private long adminid;

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//TODO 添加自定义查询条件（可选）
		cnd.and("tu.status", "=", 1);
		cnd.and("tu.id", "!=", adminid);
		if (!Util.isEmpty(id)) {
			cnd.and("tcj.comId", "=", id);
		}
		if (!Util.isEmpty(userName)) {
			cnd.and("tu.userName", "like", "%" + userName + "%").or("tu.telephone", "like", "%" + userName + "%");
		}
		if (!Util.isEmpty(depid)) {
			cnd.and("td.id", "=", depid);
		}
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {

		String sqlString = sqlManager.get("company_user_info_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

}
