package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户名*/
	private String userName;

	/**密码*/
	private String password;

	/**用户姓名*/
	private String fullName;

	/**用户名/手机号码*/
	private String telephone;

	/**座机号码*/
	private String landline;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**用户类型*/
	private long userType;

	/**状态*/
	private long status;

	/**创建时间*/
	private DateTime createTime;

	/**更新时间*/
	private DateTime updateTime;

	/**用户是否禁用*/
	private long disableStatus;

	/**备注*/
	private String remark;

}