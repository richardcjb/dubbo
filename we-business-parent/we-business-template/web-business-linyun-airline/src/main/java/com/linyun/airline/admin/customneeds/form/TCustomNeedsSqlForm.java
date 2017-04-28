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

import com.linyun.airline.entities.TCustomerneedsEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
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
public class TCustomNeedsSqlForm extends DataTablesParamForm {

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
	private String leavedate;

	/**出发城市*/
	private String leavecity;

	/**出发航班*/
	private String leaveflight;

	/**回程日期*/
	private String backdate;

	/**返回城市*/
	private String backcity;

	/**回程航班*/
	private String backflight;

	/**是否关闭*/
	private int isclose;

	/**操作人*/
	private String opid;

	/**操作时间*/
	private Date optime;

	/**最后修改时间*/
	private Date lastupdatetime;

	/**所属公司*/
	private long companyid;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("isclose", "=", isclose);
		if (!Util.isEmpty(airline)) {
			cnd.and("airline", "like", "%" + airline + "%");
		}
		if (!Util.isEmpty(travel)) {
			cnd.and("travel", "like", "%" + travel + "%");
		}
		if (!Util.isEmpty(totalcount)) {
			cnd.and("totalcount", "=", totalcount);
		}
		if (!Util.isEmpty(totalday)) {
			cnd.and("totalday", "=", totalday);
		}
		if (!Util.isEmpty(leavedate)) {
			cnd.and("leavedate", "=", DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(leavecity)) {
			cnd.and("leavecity", "=", leavecity);
		}
		if (!Util.isEmpty(backdate)) {
			cnd.and("backdate", "=", DateUtil.string2Date(backdate, DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(backcity)) {
			cnd.and("backcity", "=", backcity);
		}
		if (!Util.isEmpty(isclose)) {
			cnd.and("isclose", "=", isclose);
		}
		if (!Util.isEmpty(companyid)) {
			cnd.and("companyid", "=", companyid);
		}
		cnd.orderBy("optime", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TCustomerneedsEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
