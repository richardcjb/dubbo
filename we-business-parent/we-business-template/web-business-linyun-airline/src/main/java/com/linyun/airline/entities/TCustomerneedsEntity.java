package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_customerneeds")
public class TCustomerneedsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航空公司名称")
	private String airline;

	@Column
	@Comment("旅行社")
	private String travel;

	@Column
	@Comment("人数")
	private Integer totalcount;

	@Column
	@Comment("天数")
	private Integer totalday;

	@Column
	@Comment("联运要求")
	private String uniontransport;

	@Column
	@Comment("去程日期")
	private Date leavedate;

	@Column
	@Comment("出发城市")
	private String leavecity;

	@Column
	@Comment("出发航班")
	private String leaveflight;

	@Column
	@Comment("回程日期")
	private Date backdate;

	@Column
	@Comment("返回城市")
	private String backcity;

	@Column
	@Comment("回程航班")
	private String backflight;

	@Column
	@Comment("是否关闭")
	private int isclose;

	@Column
	@Comment("操作人")
	private String opid;

	@Column
	@Comment("操作时间")
	private Date optime;

	@Column
	@Comment("最后修改时间")
	private Date lastupdatetime;

	@Column
	@Comment("所属公司")
	private long companyid;

	@Column
	@Comment("备注")
	private String remark;

}