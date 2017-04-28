package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class SUserUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户名*/
	private String userName = "";

	/**密码*/
	private String password = "";

	/**用户邮箱*/
	private String email = "";

	/**手机号*/
	private String tel = "";

	/**参见字典-用户类型*/
	private String userType = "";

	/**用户状态，0-初始化，1-启用，2-停用*/
	private Integer status = 0;

	/**创建时间*/
	private Date createTime = DateTimeUtil.nowDate();

}