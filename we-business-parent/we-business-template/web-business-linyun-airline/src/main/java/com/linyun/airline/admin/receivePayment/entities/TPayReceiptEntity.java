/**
 * TPayPnrEntity.java
 * com.linyun.airline.admin.receivePayment.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.entities;

import java.io.Serializable;

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
 * @author   彭辉
 * @Date	 2017年2月27日 	 
 */
@Data
@Table("t_pay_receipt")
public class TPayReceiptEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("付款id")
	private Integer payId;

	@Column
	@Comment("水单url")
	private String receiptUrl;
}
