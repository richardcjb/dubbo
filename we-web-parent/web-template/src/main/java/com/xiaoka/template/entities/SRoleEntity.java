package com.xiaoka.template.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.uxuexi.core.common.util.DateTimeUtil;

@Data
@Table("s_role")
public class SRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("角色名称")
	private String name = "";

	@Column
	@Comment("备注")
	private String remark = "";

	@Column
	@Comment("创建时间")
	private Date createTime = DateTimeUtil.nowDate();

}