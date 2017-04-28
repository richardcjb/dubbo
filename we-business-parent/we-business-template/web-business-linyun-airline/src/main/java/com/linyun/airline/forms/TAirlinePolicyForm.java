/**
 * TAirlinePolicyAddForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.util.Calendar;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.AirlinePolicyEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月11日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TAirlinePolicyForm extends DataTablesParamForm {
	/**id*/
	private Long id;
	/**文件名字*/
	private String fileName;
	/**文件路径*/
	private String url;
	/**文件大小*/
	private String fileSize;
	/**状态*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**公司id*/
	private Long companyId;
	//航空公司id
	private Long airlineCompanyId;
	//区域id
	private Long areaId;
	//区域名称
	private String areaName;
	//类型
	private String type;
	//开始时间
	private Date beginTime;
	//结束时间
	private Date endTime;

	@Override
	public Sql sql(SqlManager sqlManager) {

		/*String sqlString = EntityUtil.entityCndSql(TAirlinePolicyEntity.class);*/
		String sqlString = sqlManager.get("airlinepolicy_datalist");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		cnd.and("status", "=", AirlinePolicyEnum.ENABLE.intKey());
		if (!Util.isEmpty(airlineCompanyId)) {

			cnd.and("airlineCompanyId", "=", airlineCompanyId);
		}
		if (!Util.isEmpty(areaName)) {

			cnd.and("areaName", "=", areaName);
		}
		if (!Util.isEmpty(type)) {

			cnd.and("type", "=", type);
		}
		if (!Util.isEmpty(beginTime)) {

			cnd.and("updateTime", ">=", beginTime);

		}

		if (!Util.isEmpty(endTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date ccTime = calendar.getTime();

			cnd.and("updateTime", "<=", ccTime);

		}
		if (!Util.isEmpty(companyId)) {
			cnd.and("companyId", "=", companyId);
		}
		cnd.orderBy("updateTime", "desc");
		return cnd;
	}
}
