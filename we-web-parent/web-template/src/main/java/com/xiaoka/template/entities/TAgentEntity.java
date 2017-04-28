package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_agent")
public class TAgentEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("上游公司id")
	private Integer comId;
	
	@Column
    @Comment("代理商名称")
	private String name;
	

}