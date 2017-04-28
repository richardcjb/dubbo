package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.DictInfoEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class DictInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private long id;

	/**字典类别编码*/
	private String typeCode;

	/**字典代码*/
	private String dictCode;

	/**字典信息*/
	private String dictName;

	/**描述*/
	private String description;

	/**字典信息状态,1-启用，2--删除*/
	private long status;

	/**全拼*/
	private String quanPin;

	/**简拼*/
	private String jianpin;

	/**创建时间*/
	private Date createTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(DictInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(dictName)) {
			cnd.and("i.dictName", "LIKE", "%" + dictName + "%");
		}
		return cnd;
	}
}
