/**
 * TypeSqlForm.java
 * com.linyun.airline.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.DataStatusEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月2日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypeSqlForm extends DataTablesParamForm {
	//类型名称
	private String typeName;
	//状态
	private int status = DataStatusEnum.ENABLE.intKey();
	//字典类别编码
	private String typeCode;
	//创建时间
	private Date createTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("dict_type_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(typeName) || !Util.isEmpty(typeCode)) {
			cnd.and("t.typeName", "LIKE", "%" + typeName + "%").or("t.typeCode", "LIKE", "%" + typeCode + "%");
		}
		cnd.and("t.status", "=", 1);
		cnd.orderBy("t.createtime", "desc");
		return cnd;
	}

}
