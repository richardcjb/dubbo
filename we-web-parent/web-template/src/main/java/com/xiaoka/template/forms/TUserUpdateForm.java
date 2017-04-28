package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**用户姓名*/
	private String userName;
		
	/**用户名/手机号码*/
	private String telephone;
		
	/**密码*/
	private String password;
		
	/**座机号码*/
	private String landline;
		
	/**联系QQ*/
	private String qq;
		
	/**电子邮箱*/
	private String email;
		
	/**用户类型*/
	private Integer userType;
		
	/**状态*/
	private Integer status;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
	/**用户是否禁用*/
	private Integer disableStatus;
		
	/**备注*/
	private String remark;
		
}