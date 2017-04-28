package com.linyun.airline.entities;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户职位关系实体
 * @author   崔建斌
 * @Date	 2016-11-17	 
 */
@Data
@Table("t_user_job")
public class TUserJobEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private int id;

	@Column
	@Comment("用户id")
	private long userid;

	@Column
	@Comment("公司职位id")
	private long companyJobId;

	@Column
	@Comment("状态")
	private int status;

	@Column
	@Comment("入职时间")
	private Date hireDate;

	@Column
	@Comment("离职时间")
	private Date leaveDate;

	@Column
	@Comment("备注")
	private String remark;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TUserJobEntity other = (TUserJobEntity) obj;
		if (companyJobId != other.companyJobId)
			return false;
		if (userid != other.userid)
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyJobId ^ (companyJobId >>> 32));
		result = prime * result + (int) (userid ^ (userid >>> 32));
		return result;
	}

}