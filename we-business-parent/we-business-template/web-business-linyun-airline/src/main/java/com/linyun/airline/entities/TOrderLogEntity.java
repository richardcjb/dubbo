/**
 * TOrderLogEntity.java
 * com.linyun.airline.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.entities;

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
 * @Date	 2017年3月10日 	 
 */
@Data
@Table("t_order_log")
public class TOrderLogEntity {

	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键id")
	private long id;

	@Column
	@Comment("内容")
	private String content;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("操作时间")
	private Date optime;

	@Column
	@Comment("订单号")
	private String ordernum;

	@Column
	@Comment("订单id")
	private Integer orderid;
}
