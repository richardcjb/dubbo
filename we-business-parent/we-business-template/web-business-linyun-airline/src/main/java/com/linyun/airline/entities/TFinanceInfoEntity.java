package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_finance_info")
public class TFinanceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("公司id")
	private Integer comId;

	@Column
	@Comment("客户团号")
	private String cusgroupnum;

	@Column
	@Comment("类型")
	private Integer teamtype;

	@Column
	@Comment("开票日期")
	private Date billingdate;

	@Column
	@Comment("付款币种")
	private Integer paycurrency;

	@Column
	@Comment("付款方式")
	private Integer paymethod;

	@Column
	@Comment("销售人员")
	private String salesperson;

	@Column
	@Comment("开票人")
	private String issuer;

	@Column
	@Comment("人头数")
	private Integer personcount;

	@Column
	@Comment("结算状态")
	private Integer billingstatus;

	@Column
	@Comment("进澳时间")
	private Date enterausdate;

	@Column
	@Comment("出澳时间")
	private Date outausdate;

	@Column
	@Comment("应收")
	private Double receivable;

	@Column
	@Comment("减免")
	private Double relief;

	@Column
	@Comment("成本合计")
	private Double costtotal;

	@Column
	@Comment("实收合计")
	private Double incometotal;

	@Column
	@Comment("应返合计")
	private Double returntotal;

	@Column
	@Comment("利润合计")
	private Double profittotal;

	@Column
	@Comment("内陆跨海")
	private String neilu;

	@Column
	@Comment("开票人id")
	private Integer issuerid;

	@Column
	@Comment("进澳航空公司")
	private String enteraircom;

	@Column
	@Comment("出澳航空公司")
	private String outaircom;

	@Column
	@Comment("进澳出发时间")
	private String enterstarttime;

	@Column
	@Comment("进澳抵达时间")
	private String enterarrivetime;

	@Column
	@Comment("出澳出发时间")
	private String outstarttime;

	@Column
	@Comment("出澳抵达时间")
	private String outarrivetime;

}
