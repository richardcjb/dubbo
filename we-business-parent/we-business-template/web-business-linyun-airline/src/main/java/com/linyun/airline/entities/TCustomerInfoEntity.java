package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_customer_info")
public class TCustomerInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("上游公司id")
	private long upComId;

	@Column
	@Comment("客户id")
	private long agentId;

	@Column
	@Comment("客户类型")
	private long customerType;

	@Column
	@Comment("名称")
	private String name;

	@Column
	@Comment("简称")
	private String shortName;

	@Column
	@Comment("联系人")
	private String linkMan;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("传真")
	private String fax;

	@Column
	@Comment("网址")
	private String siteUrl;

	@Column
	@Comment("地址")
	private String address;

	@Column
	@Comment("国境内陆")
	private String inlandLine;

	@Column
	@Comment("国际")
	private String interLine;

	@Column
	@Comment("负责人")
	private long responsibleId;

	@Column
	@Comment("添加时间")
	private Date createTime;

	@Column
	@Comment("出发城市")
	private String departureCity;

	@Column
	@Comment("附件管理")
	private String appendix;

	@Column
	@Comment("附件名称")
	private String appendixName;

	@Column
	@Comment("旅行社类型")
	private int travelType;

	@Column
	@Comment("付款方式（现金、支票、银行汇款、第三方、其他）")
	private int payWay;

	@Column
	@Comment("其他付款方式")
	private String paywayName;

	@Column
	@Comment("是否提供发票（0：否   1：是）")
	private int invoice;

	@Column
	@Comment("销售价")
	private Double sellPrice;

	@Column
	@Comment("货币单位")
	private String moneyUnit;

	@Column
	@Comment("结算形式（月结、周结、单结、其他）")
	private int payType;

	@Column
	@Comment("其他结算方式")
	private String paytypeName;

	@Column
	@Comment("合作时间")
	private Date cooperateTime;

	@Column
	@Comment("合作到期时间")
	private Date cooperateDueTime;

	@Column
	@Comment("签约时间")
	private Date contractTime;
	private String contractTimeString;

	@Column
	@Comment("签约到期时间")
	private Date contractDueTime;
	private String contractDueTimeString;

	@Column
	@Comment("是否签约（未签约、已签约、禁止合作）")
	private int contract;

	@Column
	@Comment("是否禁用")
	private long forbid;

	@Column
	@Comment("业务范围")
	private String business;

	@Column
	@Comment("信用额度")
	private Double creditLine;

	@Column
	@Comment("票价折扣")
	private Double discountFare;

	@Column
	@Comment("已欠款")
	private Double arrears;

	@Column
	@Comment("预存款")
	private Double preDeposit;

	@Column
	@Comment("手续费")
	private Double fees;

	@Column
	@Comment("汇率")
	private Double exchangeRates;

	@Column
	@Comment("退税")
	private Double taxRefund;
}
