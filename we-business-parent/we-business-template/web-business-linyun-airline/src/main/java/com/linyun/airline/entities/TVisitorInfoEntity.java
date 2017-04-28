package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_visitor_info")
public class TVisitorInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer ordernum;

	@Column
	@Comment("姓名")
	private String visitorname;

	@Column
	@Comment("电话")
	private String phonenum;

	@Column
	@Comment("性别")
	private String gender;

	@Column
	@Comment("游客类型")
	private String visitortype;

	@Column
	@Comment("证件类型")
	private String cardtype;

	@Column
	@Comment("证件号")
	private String cardnum;

	@Column
	@Comment("有效期至")
	private String validuntil;

	@Column
	@Comment("出生日期")
	private String birthday;

	@Column
	@Comment("序号")
	private String num;

	@Column
	@Comment("pnrid")
	private Integer pnrid;

}