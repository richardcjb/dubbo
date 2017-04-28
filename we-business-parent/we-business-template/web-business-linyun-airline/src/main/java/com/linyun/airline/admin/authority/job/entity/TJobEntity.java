package com.linyun.airline.admin.authority.job.entity;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_job")
public class TJobEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("部门id")
	private long deptId;

	@Column
	@Comment("职位名称")
	private String name;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("备注")
	private String remark;

	/**标记该职位是否属于某个登录用户*/
	private boolean checked = false;

}