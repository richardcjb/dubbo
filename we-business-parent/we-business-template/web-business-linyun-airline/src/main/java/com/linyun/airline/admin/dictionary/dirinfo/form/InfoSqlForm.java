/**
 * InfoSqlForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.form;

import java.util.Date;

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
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoSqlForm extends DataTablesParamForm {

	//字典类别编码
	private String typeCode;
	//字典代码
	private String dictCode;
	//字典信息
	private String dictName;
	//描述
	private String description;
	//状态
	private int status = 1;
	//创建时间
	private Date createTime;

	/**国家*/
	private String countryName;

	/**州*/
	private String stateName;

	/**英文名称*/
	private String englishName;

	/**中文名称*/
	private String chinesename;

	/**拼音*/
	private String pinYin;

	/**修改时间*/
	private Date updateTime;

	/**国际状态*/
	private Integer internatStatus;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("i.dictName", "LIKE", "%" + dictName + "%");
		if (!Util.isEmpty(dictName)) {
			cnd.and(group);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("i.status", "=", status);
		}
		if (!Util.isEmpty(typeCode)) {
			cnd.and("i.typeCode", "=", typeCode);
		}
		cnd.orderBy("i.createTime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("dict_info_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
