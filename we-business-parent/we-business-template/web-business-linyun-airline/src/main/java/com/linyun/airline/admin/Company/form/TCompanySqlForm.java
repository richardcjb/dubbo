/**
 * TCompanySqlForm.java
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
import org.nutz.dao.util.cri.SqlExpressionGroup;

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
public class TCompanySqlForm extends DataTablesParamForm {

	/**公司名称*/
	private String companyName;

	/**公司类型*/
	private String comType;

	private Long logincompanyid;

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(companyName)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("t.comName", "LIKE", "%" + companyName + "%").or("t.connect", "LIKE", "%" + companyName + "%")
					.or("t.mobile", "LIKE", "%" + companyName + "%");
			cnd.and(expg);
		}
		if (!Util.isEmpty(comType)) {
			cnd.and("t.comType", "=", comType);
		}
		cnd.and("t.deletestatus", "=", 0);
		if (!Util.isEmpty(logincompanyid)) {
			cnd.and("t.id", "!=", logincompanyid);
		}
		cnd.orderBy("t.createtime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("company_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
