package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**公司id*/
	private long id;

	/**管理员账号id*/
	private long adminId;

	/**公司名称*/
	private String comName;

	/**用户名*/
	private String telephone;
	/**公司类型*/
	private int comType;

	/**备注*/
	private String remark;

	/**联系人*/
	private String connect;

	/**联系人手机号*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**座机*/
	private String phonenumber;

	/**地址*/
	private String address;

	/**营业执照*/
	private String license;

	/**操作人*/
	private long opid;

	/**创建时间*/
	private Date createtime;

	/**最后修改时间*/
	private Date lastupdatetime;

	/**删除标识*/
	private int deletestatus;

}
