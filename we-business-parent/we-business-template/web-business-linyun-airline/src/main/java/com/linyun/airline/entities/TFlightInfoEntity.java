package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_flight_info")
public class TFlightInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("出发城市")
	private String leavecity;
	
	@Column
    @Comment("返回城市")
	private String backcity;
	
	@Column
    @Comment("航班号")
	private String airlinenum;
	
	@Column
    @Comment("起飞时间")
	private String leavetime;
	
	@Column
    @Comment("到达时间")
	private String backtime;
	
	@Column
    @Comment("班期")
	private String banqi;
	
	@Column
    @Comment("机型")
	private String airtype;
	
	@Column
    @Comment("航空公司")
	private Integer aircomid;
	

}