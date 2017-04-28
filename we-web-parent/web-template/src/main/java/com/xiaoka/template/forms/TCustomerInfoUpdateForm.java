package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
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
		
}