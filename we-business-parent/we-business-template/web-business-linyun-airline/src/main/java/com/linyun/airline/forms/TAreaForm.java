package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TAreaEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAreaForm extends DataTablesParamForm {
	/**用户id*/
	private Long userId;

	/**公司id*/
	private Long comId;

	/**区域id*/
	private Long areaId;

	/**区域名称*/
	private String areaName;

	/**创建时间*/
	private Date createTime;

	/**备注*/
	private String remark;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//String sqlString = sqlManager.get("authoritymanage_area_list");//多表查询当前登录用户负责的区域区域
		String sqlString = EntityUtil.entityCndSql(TAreaEntity.class);//单表
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//自定义查询条件
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(comId)) {
			cnd.and("comId", "=", comId);
		}
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}