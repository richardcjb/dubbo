/**
 * ApplyApprovalEntity.java
 * com.linyun.airline.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.entities;

import java.io.Serializable;
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
 * @Date	 2017年3月15日 	 
 */
@Data
@Table("t_inter_message")
public class TInterMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单ID")
	private Integer orderid;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

	@Column
	@Comment("提醒类型")
	private Integer remindtype;

	@Column
	@Comment("提醒时间")
	private Date reminddate;

}
