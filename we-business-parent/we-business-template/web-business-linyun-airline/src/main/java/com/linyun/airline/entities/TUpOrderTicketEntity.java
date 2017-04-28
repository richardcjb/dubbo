package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_up_order_ticket")
public class TUpOrderTicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单ID")
	private Integer orderid;

	@Column
	@Comment("机票ID")
	private long ticketid;

	@Column
	@Comment("售价（总价）")
	private Double price;

}