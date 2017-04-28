/**
 * TCompanySqlForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TPlanInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年11月21日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanMakeSqlForm extends DataTablesParamForm {

	private Integer id;

	private String travelname;

	private String airlinename;

	private Date leavesdate;

	private String leaveairline;

	private String leavescity;

	private Date backsdate;

	private String backairline;

	private String backscity;

	private Integer peoplecount;

	private Integer dayscount;

	private String unioncity;

	private Integer teamtype;

	private String ordernumber;

	private int issave;

	private Integer opid;

	private Date createtime;

	private Date laseupdatetime;

	private long companyid;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("issave", "=", "0");
		cnd.and("companyid", "=", companyid);
		cnd.orderBy("leavesdate", "asc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		//String sqlString = sqlManager.get("get_plan_make_info_withtime");
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
