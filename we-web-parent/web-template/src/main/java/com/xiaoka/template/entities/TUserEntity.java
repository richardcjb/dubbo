package com.xiaoka.template.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_user")
public class TUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("用户姓名")
	private String userName;
	
	@Column
    @Comment("用户名/手机号码")
	private String telephone;
	
	@Column
    @Comment("密码")
	private String password;
	
	@Column
    @Comment("座机号码")
	private String landline;
	
	@Column
    @Comment("联系QQ")
	private String qq;
	
	@Column
    @Comment("电子邮箱")
	private String email;
	
	@Column
    @Comment("用户类型")
	private Integer userType;
	
	@Column
    @Comment("状态")
	private Integer status;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	
	@Column
    @Comment("用户是否禁用")
	private Integer disableStatus;
	
	@Column
    @Comment("备注")
	private String remark;
	

}