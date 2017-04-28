package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_area")
public class TAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private Long id;

	@Column
	@Comment("公司id")
	private Long comId;

	@Column
	@Comment("区域名称")
	private String areaName;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("备注")
	private String remark;

}