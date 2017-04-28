/**
 * RelationEntity.java
 * com.xiaoka.template.admin.dictionary.dirrelation.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirrelation.entity;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Table("dict_relation")
@Data
@Comment
public class RelationEntity {

	@Column
	@Id(auto = true)
	@Comment("主键")
	private int id;

	@Column
	@Comment("字典id")
	private int sourceId;

	@Column
	@Comment("关联字典id")
	private int targetId;
}
