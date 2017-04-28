package com.linyun.airline.admin.companydict.comdictinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComLoginNumUpdateForm extends ModForm {
	//网站地址
	private String webURl;
	//登录账号
	private String loginNumName;
	//航空公司
	private String airlineName;
	//字典信息状态,1-启用，2--删除
	private Integer status;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
}
