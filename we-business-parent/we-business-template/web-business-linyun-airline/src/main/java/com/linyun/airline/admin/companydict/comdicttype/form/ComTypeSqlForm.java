/**
 * TypeSqlForm.java
 * com.linyun.airline.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdicttype.form;

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
public class ComTypeSqlForm extends DataTablesParamForm {
	//主键
	private long id;

	//公司id
	private Long comId;

	//公司名称
	private String comName;

	//字典类别编码
	private String comTypeCode;

	//字典类别名称
	private String comTypeName;

	//状态,1-启用，2--删除
	private int status = DataStatusEnum.ENABLE.intKey();

	//创建时间
	private Date createTime;

	//更新时间
	private Date updateTime;

	//备注
	private String remark;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("company_dicttype_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(status)) {
			cnd.and("cd.status", "=", status);
		}
		if (!Util.isEmpty(comTypeName)) {
			cnd.and("cd.comTypeName", "LIKE", "%" + comTypeName + "%");
		}
		if (!Util.isEmpty(comId)) {
			cnd.and("cd.comId", "=", comId);
		}
		cnd.orderBy("cd.createtime", "desc");
		return cnd;
	}

}
