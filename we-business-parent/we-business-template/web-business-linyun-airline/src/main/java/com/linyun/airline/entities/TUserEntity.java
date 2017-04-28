package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.linyun.airline.common.enums.UserStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;

@Data
@Table("t_user")
public class TUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("用户名")
	private String userName;

	@Column
	@Comment("用户姓名")
	private String fullName;

	@Column
	@Comment("密码")
	private String password;

	@Column
	@Comment("用户名/手机号码")
	private String telephone;

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
	private int userType;

	@Column
	@Comment("状态")
	private int status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("用户是否禁用")
	private int disableStatus;

	@Column
	@Comment("备注")
	private String remark;

	/**用户类型名称*/
	private String userTypeName;

	/**用户状态名称*/
	private String userStatusName;

	public String getUserStatusName() {
		if (!Util.isEmpty(status)) {
			userStatusName = EnumUtil.getValue(UserStatusEnum.class, status);
		}
		return userStatusName;
	}

	public String getUserTypeName() {
		if (!Util.isEmpty(userType)) {
			userTypeName = EnumUtil.getValue(UserTypeEnum.class, userType);
		}
		return userTypeName;
	}

}