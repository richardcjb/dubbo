package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TUserMsgEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TCheckboxStatusForm implements SQLParamForm, Serializable {
	private static final Long serialVersionUID = 1L;
	/**id*/
	private Integer id;

	/**用户Id*/
	private Integer userId;

	/**任务栏是否隐藏*/
	private Integer taskShow;

	/**大日历是否隐藏*/
	private Integer maxCShow;

	/**小日历是否隐藏*/
	private Integer minCShow;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TUserMsgEntity.class);
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