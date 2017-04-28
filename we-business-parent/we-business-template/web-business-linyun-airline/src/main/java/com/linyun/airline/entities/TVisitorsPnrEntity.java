package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_visitors_pnr")
public class TVisitorsPnrEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("PNR信息id")
	private Integer pNRid;

	@Column
	@Comment("游客名单id")
	private Integer visitorslistid;

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
		TVisitorsPnrEntity other = (TVisitorsPnrEntity) obj;
		if (pNRid == null) {
			if (other.pNRid != null)
				return false;
		} else if (!pNRid.equals(other.pNRid))
			return false;
		if (visitorslistid == null) {
			if (other.visitorslistid != null)
				return false;
		} else if (!visitorslistid.equals(other.visitorslistid))
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
		result = prime * result + ((pNRid == null) ? 0 : pNRid.hashCode());
		result = prime * result + ((visitorslistid == null) ? 0 : visitorslistid.hashCode());
		return result;
	}

}