package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_company")
public class TCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("管理员账号id")
	private Integer adminId;
	
	@Column
    @Comment("公司名称")
	private String comName;
	
	@Column
    @Comment("公司类型")
	private String comType;
	
	@Column
    @Comment("备注")
	private String remark;
	

}