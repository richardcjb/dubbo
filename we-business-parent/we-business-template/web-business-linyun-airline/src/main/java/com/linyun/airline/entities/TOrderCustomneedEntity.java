package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_customneed")
public class TOrderCustomneedEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航空公司代码")
	private String aircom;

	@Column
	@Comment("出发城市")
	private String leavecity;

	@Column
	@Comment("抵达城市")
	private String arrivecity;

	@Column
	@Comment("去程日期")
	private Date leavetdate;

	@Column
	@Comment("回程日期")
	private Date backdate;

	@Column
	@Comment("原价(单价)")
	private Double originalprice;

	@Column
	@Comment("售价(单价)")
	private Double price;

	@Column
	@Comment("结算货币代码")
	private String currencyCode;

	@Column
	@Comment("数量")
	private Integer peoplecount;

	@Column
	@Comment("乘客类型")
	private Integer passengertype;

	@Column
	@Comment("机票类型")
	private Integer tickettype;

	@Column
	@Comment("旅程类型")
	private Integer rengetype;

	@Column
	@Comment("订单id")
	private Integer ordernum;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("实时汇率")
	private Double realtimexrate;

	@Column
	@Comment("平均汇率")
	private Double avgexrate;

	@Column
	@Comment("付款币种")
	private String paycurrency;

	@Column
	@Comment("付款方式")
	private Integer paymethod;

	@Column
	@Comment("第三方支付ID")
	private Integer thirdcustomid;
}