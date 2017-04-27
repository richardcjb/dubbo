/**
 * ISqlForm.java
 * com.uxuexi.web.form
 * Copyright (c) 2014, 北京聚智未来科技有限公司版权所有.
*/

package com.uxuexi.core.web.form;

import org.nutz.dao.SqlManager;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.db.dao.IDbDao;

/**
 * 表单里用于创建sql对象的接口
 * <p>
 * nutz可以不用查询条数的count-sql，因此Deprecated此接口
 *
 * @author   庄君祥，朱晓川
 * @Date	 2013-10-10 	 
 */
public interface ISqlForm {
	/**
	 * 创建分页sql
	 *
	 * @return sql对象
	*/
	public Sql createPagerSql(final IDbDao dbDao, final SqlManager sqlManager);

	/**
	 * 创建查询总数sql
	 *
	 * @return sql对象
	*/
	public Sql createCountSql(final IDbDao dbDao, final SqlManager sqlManager);
}
