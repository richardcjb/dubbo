package com.linyun.airline.admin.dictionary.departurecity.form;

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

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartureCitySqlForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**字典类别编码*/
	private String typeCode;

	/**三字代码*/
	private String dictCode;

	/**国家*/
	private String countryName;

	/**州*/
	private String stateName;

	/**英文名称*/
	private String englishName;

	/**中文名称*/
	private String chineseName;

	/**拼音*/
	private String pinYin;

	/**描述*/
	private String description;

	/**状态*/
	private Integer status;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**国际状态*/
	private Integer internatStatus;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql,
		 * 请使用sqlManager获取自定义的sql,并设置查询条件
		 */
		String sqlString = sqlManager.get("dict_departurecity_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("dc.ChineseName", "LIKE", "%" + chineseName + "%");
		if (!Util.isEmpty(chineseName)) {
			cnd.and(group);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("dc.status", "=", status);
		}
		if (!Util.isEmpty(typeCode)) {
			cnd.and("dc.typeCode", "=", typeCode);
		}
		cnd.orderBy("dc.createTime", "desc");
		return cnd;
	}
}
