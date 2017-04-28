package com.linyun.airline.admin.receivePayment.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 *  付款实体
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   彭辉
 * @Date	 2017年2月24日
 */
@Data
@Table("t_company_bank_card")
public class TCompanyBankCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private long companyId;

	@Column
	@Comment("银行名称")
	private String bankComp;

	@Column
	@Comment("银行卡名称")
	private String cardName;

	@Column
	@Comment("银行卡号码")
	private String cardNum;

	@Column
	@Comment("银行卡类型")
	private Integer cardType;

}