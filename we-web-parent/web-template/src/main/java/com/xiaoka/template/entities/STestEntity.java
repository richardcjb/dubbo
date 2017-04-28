package com.xiaoka.template.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("s_test")
@Comment
public class STestEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("测试用户名称")
	private String sname;

	@Column
	@Comment("年龄")
	private Integer sage;

	@Column
	@Comment("性别")
	private String sex;

	@Column
	@Comment("积分")
	private Float score;

	@Column
	@Comment("创建时间")
	private Timestamp createTime;

}