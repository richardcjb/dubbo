package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_plan_info")
public class TPlanInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("旅行社名称")
	private String travelname;

	@Column
	@Comment("航空公司名称")
	private String airlinename;

	@Column
	@Comment("去程日期")
	private Date leavesdate;

	@Column
	@Comment("去程航班")
	private String leaveairline;

	@Column
	@Comment("出发城市")
	private String leavescity;

	@Column
	@Comment("回程日期")
	private Date backsdate;

	@Column
	@Comment("回程航班")
	private String backairline;

	@Column
	@Comment("返回城市")
	private String backscity;

	@Column
	@Comment("人数")
	private Integer peoplecount;

	@Column
	@Comment("天数")
	private Integer dayscount;

	@Column
	@Comment("联运城市")
	private String unioncity;

	@Column
	@Comment("1、系列团，2、临时团；3关闭")
	private Integer teamtype;

	@Column
	@Comment("订单号")
	private String ordernumber;

	@Column
	@Comment("是否保存")
	private int issave;

	@Column
	@Comment("操作人")
	private long opid;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("最后修改时间")
	private Date laseupdatetime;

	@Column
	@Comment("所属公司")
	private long companyid;

	@Column
	@Comment("选择时间的类型（每周or自由）")
	private int timetype;

	@Column
	@Comment("起始时间")
	private Date starttime;

	@Column
	@Comment("结束时间")
	private Date endtime;

	@Column
	@Comment("是否关闭")
	private int isclose;

	@Column
	@Comment("FOC")
	private int foc;

	@Column
	@Comment("返程出发城市")
	private String backleavecity;

	@Column
	@Comment("返程抵达城市")
	private String backbackcity;

	@Column
	@Comment("售价")
	private Double price;

	@Column
	@Comment("结算货币代码")
	private String currencycode;

	@Column
	@Comment("乘客类型")
	private int passengertype;

	@Column
	@Comment("机票类型")
	private int tickettype;

	@Column
	@Comment("航程类型")
	private int rengetype;
}