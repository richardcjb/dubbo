/**
 * ComLoginNumEntity.java
 * com.linyun.airline.admin.companydict.comdictinfo.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.entity;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 登录账号实体类
 * @author   崔建斌
 * @Date	 2017年3月29日 	 
 */
@Data
@Table("t_login_number")
public class ComLoginNumEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comId;

	@Column
	@Comment("字典类别编码")
	private String comTypeCode;

	@Column
	@Comment("字典代码")
	private String comDdictCode;

	@Column
	@Comment("网站地址")
	private String webURl;

	@Column
	@Comment("登录账号")
	private String loginNumName;

	@Column
	@Comment("航空公司")
	private String airlineName;

	@Column
	@Comment("字典信息状态,1-启用，2--删除")
	private Integer status;

	private String statusName;

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
