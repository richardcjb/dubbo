package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_invoice_info")
public class TInvoiceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("发票项目")
	private Integer invoiceitem;

	@Column
	@Comment("发票日期")
	private Date invoicedate;

	@Column
	@Comment("开票人")
	private Integer billuserid;

	@Column
	@Comment("部门")
	private Integer deptid;

	@Column
	@Comment("付款单位")
	private String paymentunit;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("差额")
	private Double difference;

	@Column
	@Comment("余额")
	private Double balance;

	@Column
	@Comment("发票类型（收款、付款）")
	private Integer invoicetype;

	@Column
	@Comment("收款id")
	private Integer receiveid;

	@Column
	@Comment("pnrid")
	private Integer pnrid;

	@Column
	@Comment("opid")
	private Integer opid;

	@Column
	@Comment("optime")
	private Date optime;

	@Column
	@Comment("状态")
	private Integer status;

	@Column
	@Comment("公司id")
	private Integer comId;

	@Column
	@Comment("付款id")
	private Integer payid;

	@Column
	@Comment("状态类别（内陆跨海、国际）")
	private Integer ordertype;

	@Column
	@Comment("订单付款ID")
	private Integer orderpayid;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

}