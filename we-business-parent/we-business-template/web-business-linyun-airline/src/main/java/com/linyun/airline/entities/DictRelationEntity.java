package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("dict_relation")
public class DictRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("字典id")
	private long sourceId;

	@Column
	@Comment("关联字典id")
	private long targetId;

	@Column
	@Comment("删除标识:(0,表示未删除；1,表示已删除)")
	private long dr;

}