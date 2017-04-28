package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_customer_info")
public class TCustomerInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("上游公司id")
	private Integer comId;
	
	@Column
    @Comment("代理商id")
	private Integer agentId;
	
	@Column
    @Comment("名称")
	private String name;
	
	@Column
    @Comment("名字")
	private String linkMan;
	
	@Column
    @Comment("电话")
	private String telephone;
	
	@Column
    @Comment("品牌")
	private String brand;
	
	@Column
    @Comment("传真")
	private String fax;
	
	@Column
    @Comment("网址")
	private String siteUrl;
	
	@Column
    @Comment("邮编")
	private String zipCode;
	
	@Column
    @Comment("地址")
	private String address;
	
	@Column
    @Comment("负责人")
	private String agent;
	
	@Column
    @Comment("添加时间")
	private Date createTime;
	
	@Column
    @Comment("出发城市")
	private String departureCity;
	
	@Column
    @Comment("线路区域")
	private Integer travelLine;
	
	@Column
    @Comment("附件管理")
	private String appendix;
	
	@Column
    @Comment("业务范围")
	private String businessRange;
	
	@Column
    @Comment("旅行社类型")
	private Integer travelType;
	
	@Column
    @Comment("付款方式")
	private Integer payWay;
	
	@Column
    @Comment("是否提供发票（0：否   1：是）")
	private Integer invoice;
	
	@Column
    @Comment("销售价")
	private Double sellPrice;
	
	@Column
    @Comment("结算形式")
	private Integer payType;
	
	@Column
    @Comment("合作时间")
	private Date cooperateTime;
	
	@Column
    @Comment("合作到期时间")
	private Date cooperateDueTime;
	
	@Column
    @Comment("签约时间")
	private Date contractTime;
	
	@Column
    @Comment("签约到期时间")
	private Date contractDueTime;
	
	@Column
    @Comment("是否签约")
	private Integer contract;
	
	@Column
    @Comment("是否禁用")
	private Integer forbid;
	

}