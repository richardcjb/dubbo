/**
 * TMitigateInfoEntity.java
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
 * @Date	 2017年3月9日 	 
 */
@Data
@Table("t_mitigate_info")
public class TMitigateInfoEntity {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("减免对象")
	private String customname;

	@Column
	@Comment("减免客户id")
	private Integer customeid;

	@Column
	@Comment("金额")
	private Double account;

	@Column
	@Comment("金额大写")
	private String accountupper;

	@Column
	@Comment("币种")
	private String currency;

	@Column
	@Comment("申请人")
	private Integer applyid;

	@Column
	@Comment("审批人")
	private String approvelid;

	@Column
	@Comment("审批结果")
	private Integer applyResult;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("操作时间")
	private Date optime;

	@Column
	@Comment("订单类型")
	private Integer ordertype;

	@Column
	@Comment("用途")
	private String application;
}
