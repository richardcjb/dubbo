package com.linyun.airline.admin.companydict.comdictinfo.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComLoginNumAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	//主键
	private Integer id;
	//公司id
	private Long comId;
	//字典类别编码
	private String comTypeCode;
	//字典代码
	private String comDdictCode;
	//网站地址
	private String webURl;
	//登录账号
	private String loginNumName;
	//航空公司
	private String airlineName;
	//字典信息状态,1-启用，2--删除
	private Integer status;
	private String statusName;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
}
