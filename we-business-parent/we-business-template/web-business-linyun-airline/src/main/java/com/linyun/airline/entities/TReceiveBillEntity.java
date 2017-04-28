package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_receive_bill")
public class TReceiveBillEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("收款id")
	private Integer receiveid;
	
	@Column
    @Comment("水单url")
	private String receiptUrl;
	

}