package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;
import com.xiaoka.template.admin.authority.functionmanage.entity.FunctionEntity;

@Data
public class TFunctionForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**上级功能id*/
	private Integer parentId;

	/**功能名称*/
	private String name;

	/**访问地止*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private Integer level;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private Integer sort;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(FunctionEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）

		return cnd;
	}
}