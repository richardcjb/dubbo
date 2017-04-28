package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**上游公司id*/
	private long upComId;

	/**客户id*/
	private long agentId;

	/**客户类型*/
	private long comType;

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

	/**负责人*/
	private long responsibleId;

	/**添加时间*/
	private Date createTime;

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
	/**其他付款方式*/
	private String paywayName;

	/**是否提供发票（0：否   1：是）*/
	private long invoice;

	/**销售价*/
	private Double sellPrice;

	/**货币单位*/
	private String moneyUnit;

	/**结算形式（月结、周结、单结、其他）*/
	private long payType;
	/**其他结算方式*/
	private String paytypeName;

	/**合作时间*/
	private Date cooperateTime;

	/**合作到期时间*/
	private Date cooperateDueTime;

	/**签约时间*/
	private Date contractTime;

	private String contractTimeString;

	/**签约到期时间*/
	private Date contractDueTime;

	private String contractDueTimeString;

	/**是否签约（未签约、已签约、禁止合作）*/
	private long contract;

	/**是否禁用*/
	private long forbid;

	/**业务范围*/
	private String business;

	/**出发城市Ids*/
	private String outcityname;

	/**国境内陆*/
	private String sLine1;

	/**国际线路*/
	private String internationLine;

	/**发票项目*/
	private String sInvName;

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
}
