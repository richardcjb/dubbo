package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_user_area_map")
public class TUserAreaMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("用户id")
	private Integer userId;
	
	@Column
    @Comment("区域id")
	private Integer areaId;
	

}