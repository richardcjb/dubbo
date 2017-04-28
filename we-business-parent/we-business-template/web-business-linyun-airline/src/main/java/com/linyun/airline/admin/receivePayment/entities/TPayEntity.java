package com.linyun.airline.admin.receivePayment.entities;

import java.io.Serializable;
import java.util.Date;

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
@Table("t_pay")
public class TPayEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("银行卡id")
	private Integer bankId;

	@Column
	@Comment("付款地址")
	private Integer payAddress;

	@Column
	@Comment("用途")
	private Integer purpose;

	@Column
	@Comment("资金种类")
	private Integer fundType;

	@Column
	@Comment("付款时间")
	private Date payDate;

	@Column
	@Comment("确认付款时间")
	private Date confirmDate;

	@Column
	@Comment("付款手续费")
	private Double payFees;

	@Column
	@Comment("付款金额")
	private Double payMoney;

	@Column
	@Comment("付款金额大写")
	private String payChineseMoney;

	@Column
	@Comment("付款币种")
	private Integer payCurrency;

	@Column
	@Comment("是否发票")
	private Integer isInvioce;

	@Column
	@Comment("申请人")
	private Integer proposer;

	@Column
	@Comment("审批人")
	private String approver;

	@Column
	@Comment("审批结果")
	private Integer approveResult;

	@Column
	@Comment("审批时间") 
	private Date approveTime;

	@Column
	@Comment("公司id")
	private Integer companyId;

	@Column
	@Comment("付款订单合计")
	private Double totalMoney;

	@Column
	@Comment("订单类型（国际、内陆跨海）")
	private Integer ordertype;

	@Column
	@Comment("付款状态")
	private Integer status;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

}