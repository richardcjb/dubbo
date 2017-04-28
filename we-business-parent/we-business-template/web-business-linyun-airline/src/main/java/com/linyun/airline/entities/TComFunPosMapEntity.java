package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_com_fun_pos_map")
public class TComFunPosMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("职位id")
	private long jobId;

	@Column
	@Comment("公司功能id")
	private long companyFunId;

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
		TComFunPosMapEntity other = (TComFunPosMapEntity) obj;
		if (companyFunId != other.companyFunId)
			return false;
		if (jobId != other.jobId)
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
		result = prime * result + (int) (companyFunId ^ (companyFunId >>> 32));
		result = prime * result + (int) (jobId ^ (jobId >>> 32));
		return result;
	}

}