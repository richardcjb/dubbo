package com.linyun.airline.admin.companydict.comdicttype.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_company_dicttype")
public class ComDictTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("公司id")
	private long comId;

	@Column
	@Comment("字典类别编码")
	private String comTypeCode;

	@Column
	@Comment("字典类别名称")
	private String comTypeName;

	@Column
	@Comment("状态,1-启用，2--删除")
	private int status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("备注")
	private String remark;
}