package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("s_log")
public class SLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private long id;

	@Column
	@Comment("操作员id")
	private Integer operatorId;

	@Column
	@Comment("功能id")
	private Integer functionId;

	@Column
	@Comment("操作员名称")
	private String operatorName;

	@Column
	@Comment("访问路径")
	private String path;

	@Column
	@Comment("功能名称")
	private String functionName;

	@Column
	@Comment("操作时间")
	private DateTime operatorTime;

	@Column
	@Comment("IP地址")
	private String ip;

}