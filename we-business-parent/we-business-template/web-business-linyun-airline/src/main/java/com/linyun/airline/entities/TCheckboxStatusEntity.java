package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_checkbox_status")
public class TCheckboxStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;

	@Column
	@Comment("用户id")
	private Long userId;

	@Column
	@Comment("任务栏状态")
	private Long taskShow;

	@Column
	@Comment("大日历状态状态")
	private Long maxCShow;

	@Column
	@Comment("小日历状态状态")
	private Long minCShow;

}