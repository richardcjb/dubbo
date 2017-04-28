package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_receive")
public class TReceiveEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("银行卡id")
	private Integer bankcardid;

	@Column
	@Comment("银行名称")
	private String bankcardname;

	@Column
	@Comment("银行卡号")
	private String bankcardnum;

	@Column
	@Comment("合计")
	private Double sum;

	@Column
	@Comment("收款时间")
	private Date receivedate;

	@Column
	@Comment("客户名称")
	private String customename;

	@Column
	@Comment("操作人")
	private Integer userid;

	@Column
	@Comment("收款状态")
	private Integer status;

	@Column
	@Comment("订单类型")
	private Integer orderstype;

	@Column
	@Comment("公司id")
	private Integer companyid;

	@Column
	@Comment("订单状态")
	private Integer orderstatus;

	@Column
	@Comment("银行卡名称ID")
	private Integer bankcardnameid;
}