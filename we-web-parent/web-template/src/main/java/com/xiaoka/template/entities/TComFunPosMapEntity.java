package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_com_fun_pos_map")
public class TComFunPosMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("职位id")
	private Integer jobId;
	
	@Column
    @Comment("公司功能id")
	private Integer funId;
	

}