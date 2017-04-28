/**
 * TPayOrderEntity.java
 * com.linyun.airline.admin.receivePayment.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.entities;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@Data
@Table("t_pay_order")
public class TPayOrderEntity {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("付款id")
	private Integer payid;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

	@Column
	@Comment("付款状态")
	private Integer paystauts;

	@Column
	@Comment("付款时间")
	private Date payDate;
}
