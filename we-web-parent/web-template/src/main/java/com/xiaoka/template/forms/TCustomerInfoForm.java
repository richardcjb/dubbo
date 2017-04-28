package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;
import com.xiaoka.template.entities.TCustomerInfoEntity;

@Data
public class TCustomerInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**上游公司id*/
	private Integer comId;

	/**代理商id*/
	private Integer agentId;

	/**名称*/
	private String name;

	/**名字*/
	private String linkMan;

	/**电话*/
	private String telephone;

	/**品牌*/
	private String brand;

	/**传真*/
	private String fax;

	/**网址*/
	private String siteUrl;

	/**邮编*/
	private String zipCode;

	/**地址*/
	private String address;

	/**负责人*/
	private String agent;

	/**添加时间*/
	private Date createTime;

	/**出发城市*/
	private String departureCity;

	/**线路区域*/
	private Integer travelLine;

	/**附件管理*/
	private String appendix;

	/**业务范围*/
	private String businessRange;

	/**旅行社类型*/
	private Integer travelType;

	/**付款方式*/
	private Integer payWay;

	/**是否提供发票（0：否   1：是）*/
	private Integer invoice;

	/**销售价*/
	private Double sellPrice;

	/**结算形式*/
	private Integer payType;

	/**合作时间*/
	private Date cooperateTime;

	/**合作到期时间*/
	private Date cooperateDueTime;

	/**签约时间*/
	private Date contractTime;

	/**签约到期时间*/
	private Date contractDueTime;

	/**是否签约*/
	private Integer contract;

	/**是否禁用*/
	private Integer forbid;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TCustomerInfoEntity.class);
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