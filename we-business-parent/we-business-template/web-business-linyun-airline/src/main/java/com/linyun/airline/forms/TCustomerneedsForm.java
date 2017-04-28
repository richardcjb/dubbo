package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TCustomerneedsEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TCustomerneedsForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**航空公司名称*/
	private String airline;

	/**旅行社*/
	private String travel;

	/**人数*/
	private Integer totalcount;

	/**天数*/
	private Integer totalday;

	/**联运要求*/
	private String uniontransport;

	/**去程日期*/
	private Date leavedate;

	/**出发城市*/
	private String leavecity;

	/**出发航班*/
	private String leaveflight;

	/**回程日期*/
	private Date backdate;

	/**返回城市*/
	private String backcity;

	/**回程航班*/
	private String backflight;

	/**是否关闭*/
	private Integer isclose;

	/**操作人*/
	private String opid;

	/**操作时间*/
	private Date optime;

	/**最后修改时间*/
	private Date lastupdatetime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TCustomerneedsEntity.class);
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