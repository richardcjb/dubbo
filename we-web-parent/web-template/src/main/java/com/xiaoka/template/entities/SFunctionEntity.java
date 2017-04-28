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
@Table("s_function")
public class SFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("上级功能id")
	private Integer parentId;

	@Column
	@Comment("功能名称")
	private String name = "";

	@Column
	@Comment("访问地址")
	private String url = "";

	@Column
	@Comment("功能等级，是指在功能树中所处的层级")
	private Integer level = 0;

	@Column
	@Comment("备注")
	private String remark = "";

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime = DateTimeUtil.nowDate();

	@Column
	@Comment("序号")
	private Integer sort = 0;

}