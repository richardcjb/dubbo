/**
 * TAirlinePolicyFindForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.SqlManager;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TAirlinePolicyFindForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	//航空公司id
	private long airlineCompanyId;
	//区域id
	private long areaId;
	//类型
	private String type;

	private Date beginTime;

	@Override
	public Sql sql(SqlManager sqlManager) {

		// TODO Auto-generated method stub
		return null;

	}

}
