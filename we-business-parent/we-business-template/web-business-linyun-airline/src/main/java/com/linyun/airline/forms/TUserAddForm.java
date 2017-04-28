package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.AddForm;

/**
* @ClassName: TUserAddForm
* @author 崔建斌 
* @date 2016年11月18日 下午2:05:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TUserAddForm extends AddForm {
	/**主键*/
	private long id;

	/*select2 id字符串*/
	private String selectedAreaIds;

	/**用户名*/
	private String userName;

	/**用户姓名*/
	private String fullName;

	/**密码*/
	private String password;

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
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**用户是否禁用(0-启用，1-禁用，默认为0)*/
	private long disableStatus;

	/**备注*/
	private String remark;

	public void setPassword(String password) {
		if (!Util.isEmpty(password))
			this.password = MD5.sign(password, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
	}

}