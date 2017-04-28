package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_job")
public class TJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("部门id")
	private Integer deptId;
	
	@Column
    @Comment("职位名称")
	private String name;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("备注")
	private String remark;
	

}