package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_customer_line")
public class TCustomerLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("客户信息id")
	private Integer infoId;
	
	@Column
    @Comment("线路ID")
	private Integer lineId;
	

}