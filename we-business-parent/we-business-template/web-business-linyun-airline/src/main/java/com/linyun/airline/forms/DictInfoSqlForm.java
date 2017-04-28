/**
 * DictInfoSqlForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

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
 * @Date	 2016年11月24日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictInfoSqlForm extends DataTablesParamForm {

	//字典信息
	private String dictName;
	//按状态查询
	private int status = DataStatusEnum.ENABLE.intKey();

	/**创建时间*/
	private Date createTime;

	//字典代码
	private String dictCode;

	//字典类别编码
	private String typeCode;

	@Override
	public Sql sql(SqlManager paramSqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = paramSqlManager.get("dict_info_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(dictName) || !Util.isEmpty(typeCode)) {
			cnd.and("i.dictName", "LIKE", "%" + dictName + "%").or("i.typeCode", "LIKE", "%" + typeCode + "%");
		}
		if (!Util.isEmpty(status)) {
			cnd.and("i.status", "=", 1);
		}
		return cnd;
	}
}
