/**
 * TBankCardEntity.java
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
 * @author   孙斌
 * @Date	 2017年3月2日 	 
 */
@Data
@Table("t_bankcard")
public class TBankCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	private Integer id;
	@Column
	@Comment("银行卡名称")
	private String cardName;

	@Column
	@Comment("卡号")
	private String cardNum;

	@Column
	@Comment("银行卡类型")
	private String bankCardType;
	@Column
	@Comment("银行名称")
	private String bankName;
	@Column
	@Comment("银行id")
	private String bankNameId;
	@Column
	@Comment("币种")
	private String currency;
	@Column
	@Comment("创建时间")
	private Date createTime;
	@Column
	@Comment("修改时间")
	private Date updateTime;
	@Column
	@Comment("状态")
	private int status;
	@Column
	@Comment("备注")
	private String remark;
	@Column
	@Comment("公司id")
	private Integer companyId;
	@Column
	@Comment("公司账户余额")
	private double balance;
	@Column
	@Comment("公司账户初始金额")
	private double initialAmount;

}
