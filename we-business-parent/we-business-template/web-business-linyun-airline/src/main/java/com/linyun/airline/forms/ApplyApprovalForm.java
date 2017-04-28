/**
 * ApplyApprovalForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

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
 * @Date	 2017年3月15日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplyApprovalForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	@Override
	public Sql sql(SqlManager sqlManager) {

		// TODO Auto-generated method stub
		return null;

	}
}
