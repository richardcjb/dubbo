package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_company")
public class TCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;

	@Column
	@Comment("管理员账号id")
	private Long adminId;

	@Column
	@Comment("公司名称")
	private String comName;

	@Column
	@Comment("公司类型")
	private String comType;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("联系人")
	private String connect;

	@Column
	@Comment("联系人手机号")
	private String mobile;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("座机")
	private String phonenumber;

	@Column
	@Comment("地址")
	private String address;

	@Column
	@Comment("营业执照")
	private String license;

	@Column
	@Comment("操作人")
	private long opid;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("最后修改时间")
	private Date lastupdatetime;

	@Column
	@Comment("删除标识")
	private String deletestatus;

}