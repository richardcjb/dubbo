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
 * @Date	 2017年3月4日 	 
 */
@Data
@Table("t_turnover")
public class TTurnOverEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	@Column
	@Comment("银行卡id")
	private Integer bankCardId;

	@Column
	@Comment("交易日期")
	private Date tradeDate;

	@Column
	@Comment("平均汇率")
	private String averageRate;
	@Column
	@Comment("银行卡名称")
	private String bankName;
	@Column
	@Comment("金额")
	private double money;
	@Column
	@Comment("创建时间")
	private Date createTime;
	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("卡号")
	private String cardNum;
	@Column
	@Comment("用途")
	private String purpose;
	@Column
	@Comment("币种")
	private String currency;
	@Column
	@Comment("备注")
	private String remark;
	@Column
	@Comment("状态")
	private Integer status;
	@Column
	@Comment("单位名称")
	private String companyName;
	@Column
	@Comment("项目名称")
	private String projectName;
	@Column
	@Comment("发票状态")
	private String invoiceStatus;
	@Column
	@Comment("卡里历史余额")
	private double historymoney;

}
