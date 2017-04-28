package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_customer_service")
public class TCustomerServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("客户信息id")
	private Integer infoId;
	
	@Column
    @Comment("字典代码")
	private String dictCode;
	
	@Column
    @Comment("字典名称")
	private String dictName;
	
	@Column
    @Comment("字典类型代码")
	private String dictTypeCode;
	

}