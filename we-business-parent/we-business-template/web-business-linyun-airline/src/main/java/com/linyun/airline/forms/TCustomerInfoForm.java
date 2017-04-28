package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TCustomerInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TCustomerInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private long id;

	/**上游公司id*/
	private long upComId;

	/**代理商id*/
	private long agentId;

	/**名称*/
	private String name;

	/**简称*/
	private String shortName;

	/**联系人*/
	private String linkMan;

	/**电话*/
	private String telephone;

	/**传真*/
	private String fax;

	/**网址*/
	private String siteUrl;

	/**地址*/
	private String address;

	/**国境内陆*/
	private String inlandLine;

	/**国际*/
	private String interLine;

	/**负责人id*/
	private long responsibleId;

	/**负责人姓名*/
	private String agentName;

	/**添加时间*/
	private DateTime createTime;

	/**出发城市*/
	private String departureCity;

	/**附件管理*/
	private String appendix;
	/**附件名称*/
	private String appendixName;

	/**旅行社类型*/
	private long travelType;

	/**付款方式（现金、支票、银行汇款、第三方、其他）*/
	private long payWay;

	/**是否提供发票（0：否   1：是）*/
	private long invoice;

	/**销售价*/
	private Double sellPrice;

	/**货币单位*/
	private String moneyUnit;

	/**结算形式（月结、周结、单结、其他）*/
	private long payType;

	/**合作时间*/
	private DateTime cooperateTime;

	/**合作到期时间*/
	private DateTime cooperateDueTime;

	/**签约时间*/
	private DateTime contractTime;

	/**签约到期时间*/
	private DateTime contractDueTime;

	/**是否签约（未签约、已签约、禁止合作）*/
	private long contract;

	/**是否禁用*/
	private long forbid;

	/**信用额度*/
	private Double creditLine;

	/**票价折扣*/
	private Double discountFare;

	/**已欠款*/
	private Double arrears;

	/**预存款*/
	private Double preDeposit;

	/**手续费*/
	private Double fees;

	/**汇率*/
	private Double exchangeRates;

	/**退税*/
	private Double taxRefund;

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
