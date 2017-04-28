package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_company_function_map")
public class TCompanyFunctionMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("功能id")
	private Integer funId;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	

}