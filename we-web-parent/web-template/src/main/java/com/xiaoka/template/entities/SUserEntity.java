package com.xiaoka.template.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.uxuexi.core.common.util.DateTimeUtil;

@Data
@Table("s_user")
public class SUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("用户名")
	private String userName = "";

	@Column
	@Comment("密码")
	private String password = "";

	@Column
	@Comment("用户邮箱")
	private String email = "";

	@Column
	@Comment("手机号")
	private String tel = "";

	@Column
	@Comment("参见字典-用户类型")
	private String userType = "";

	@Column
	@Comment("用户状态，0-初始化，1-启用，2-停用")
	private Integer status = 0;

	@Column
	@Comment("创建时间")
	private Date createTime = DateTimeUtil.nowDate();

}