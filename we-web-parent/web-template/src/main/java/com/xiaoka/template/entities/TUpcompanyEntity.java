package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_upcompany")
public class TUpcompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	
	@Column
    @Comment("公司名称")
	private String name;
	
	@Column
    @Comment("备注")
	private String remark;
	

}