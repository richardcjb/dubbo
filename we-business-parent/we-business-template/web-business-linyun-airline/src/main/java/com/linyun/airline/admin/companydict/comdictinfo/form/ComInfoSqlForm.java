/**
 * InfoSqlForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.form;

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
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComInfoSqlForm extends DataTablesParamForm {

	//主键
	private long id;
	//公司id
	private Long comId;
	//字典类型id
	private long dictTypeId;
	//字典类别编码
	private String comTypeCode;
	//字典类别名称
	private String comTypeName;
	//字典代码
	private String comDdictCode;
	//字典信息
	private String comDictName;
	//字典信息状态,1-启用，2--删除
	private int status = 1;

	private String statusName;
	//全拼
	private String quanPin;
	//简拼
	private String jianpin;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(status)) {
			cnd.and("cmd.status", "=", status);
		}
		if (!Util.isEmpty(comTypeCode)) {
			cnd.and("cmd.comTypeCode", "=", comTypeCode);
		}
		if (!Util.isEmpty(comDictName)) {
			cnd.and("cmd.comDictName", "LIKE", "%" + comDictName + "%");
		}
		if (!Util.isEmpty(comId)) {
			cnd.and("cmd.comId", "=", comId);
		}
		cnd.orderBy("cmd.createtime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("company_dictinfo_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
