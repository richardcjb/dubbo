package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_receive")
public class TOrderReceiveEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("收款id")
	private Integer receiveid;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

	@Column
	@Comment("收款状态")
	private Integer receivestatus;
	@Column
	@Comment("收款时间")
	private Date receiveDate;

}