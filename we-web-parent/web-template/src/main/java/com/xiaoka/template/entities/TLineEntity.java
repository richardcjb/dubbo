package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_line")
public class TLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("线路类型id")
	private Integer typeId;
	
	@Column
    @Comment("线路名称")
	private String name;
	
	@Column
    @Comment("描述")
	private String description;
	

}