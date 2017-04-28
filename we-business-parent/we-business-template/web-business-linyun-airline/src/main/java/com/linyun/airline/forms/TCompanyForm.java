package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TCompanyForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private long id;

	/**管理员账号id*/
	private long adminId;

	/**公司名称*/
	private String comName;

	/**公司类型*/
	private String comType;

	/**备注*/
	private String remark;

	/**联系人*/
	private String connect;

	/**联系人手机号*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**座机*/
	private String phonenumber;

	/**地址*/
	private String address;

	/**营业执照*/
	private String license;

	/**操作人*/
	private long opid;

	/**创建时间*/
	private DateTime createtime;

	/**最后修改时间*/
	private DateTime lastupdatetime;

	/**删除标识*/
	private String deletestatus;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		String sqlString = sqlManager.get("company_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(comName)) {
			cnd.and("t.comName", "LIKE", "%" + comName + "%").or("t.connect", "LIKE", "%" + comName + "%")
					.or("t.mobile", "LIKE", "%" + comName + "%");

		}
		return cnd;
	}
}