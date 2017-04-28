package com.linyun.airline.admin.companydict.comdictinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComLoginNumSqlForm extends DataTablesParamForm {
	//主键
	private Integer id;
	//公司id
	private Long comId;
	//字典类别编码
	private String comTypeCode;
	//字典代码
	private String comDdictCode;
	//网站地址
	private String webURl;
	//登录账号
	private String loginNumName;
	//航空公司
	private String airlineName;
	//字典信息
	private String dictName;
	//字典信息状态,1-启用，2--删除
	private Integer status;
	private String statusName;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql,
		 * 请使用sqlManager获取自定义的sql,并设置查询条件
		 */
		String sqlString = sqlManager.get("company_dict_loginNum_listDate");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("lob.airlineName", "LIKE", "%" + airlineName + "%");
		if (!Util.isEmpty(airlineName)) {
			cnd.and(group);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("lob.status", "=", status);
		}
		if (!Util.isEmpty(comTypeCode)) {
			cnd.and("lob.comTypeCode", "=", comTypeCode);
		}
		if (!Util.isEmpty(comId)) {
			cnd.and("lob.comId", "=", comId);
		}
		cnd.orderBy("lob.createTime", "desc");
		return cnd;
	}
}
