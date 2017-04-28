package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_pnr_info")
public class TPnrInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("PNR")
	private String pNR; 

	@Column
	@Comment("成本单价")
	private Double costprice;

	@Column
	@Comment("成本总价")
	private Double costpricesum;

	@Column
	@Comment("销售单价")
	private Double salesprice;

	@Column
	@Comment("销售总价")
	private Double salespricesum;

	@Column
	@Comment("人数")
	private Integer peoplecount;

	@Column
	@Comment("登陆账号")
	private String loginid;

	@Column
	@Comment("币种")
	private String currency;

	@Column
	@Comment("航班号信息id")
	private Integer airinfoid;

	@Column
	@Comment("客户需求id")
	private Integer needid;

	@Column
	@Comment("订单pnr状态")
	private Integer orderPnrStatus;

	@Column
	@Comment("操作人")
	private Integer userid;

	@Column
	@Comment("操作时间")
	private Date optime;

	@Column
	@Comment("是否主航段")
	private Integer mainsection;

	@Column
	@Comment("订单id（国际用）")
	private Integer orderid;

}