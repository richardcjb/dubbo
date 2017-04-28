/**
 * TUserModForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Range;

import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.linyun.airline.entities.TUserJobEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TUserModForm extends ModForm {

	//部门名称
	private long deptId;

	//职位名称
	private long jobId;

	/*select2 id字符串*/
	private String selectedAreaIds;

	/**用户名*/
	private String userName;

	/**密码*/
	private String password;

	/**用户姓名*/
	private String fullName;

	/**新密码*/
	private String newPass;

	/**重复新密码*/
	private String repeatPass;

	/**用户名/手机号码*/
	private String telephone;

	/**座机号码*/
	private String landline;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**用户类型
	 * 0-前台用户，1-后台用户
	 * */
	@Range(min = 0, max = 1)
	private long userType;

	/**状态
	 * 0-未激活，1-激活，2-冻结
	 * */
	@Range(min = 0, max = 2)
	private long status;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**用户是否禁用*/
	private long disableStatus;

	/**备注*/
	private String remark;

	/**用户的职位*/
	private List<TUserJobEntity> map = new ArrayList<TUserJobEntity>();

	public void setPassword(String password) {
		if (!Util.isEmpty(password))
			this.password = MD5.sign(password, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
	}
}
