package com.linyun.airline.admin.dictionary.departurecity.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_departure_city")
public class TDepartureCityEntity implements Serializable, Comparable<TDepartureCityEntity> {
	private static final long serialVersionUID = 1L;
	@Column
	@Comment("主键")
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("字典类别编码")
	private String typeCode;

	@Column
	@Comment("三字代码")
	private String dictCode;

	@Column
	@Comment("国家")
	private String countryName;

	@Column
	@Comment("州")
	private String stateName;

	@Column
	@Comment("英文名称")
	private String englishName;

	@Column
	@Comment("中文名称")
	private String chineseName;

	@Column
	@Comment("拼音")
	private String pinYin;

	@Column
	@Comment("描述")
	private String description;

	@Column
	@Comment("状态")
	private Integer status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("国际状态")
	private Integer internatStatus;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**使用次数*/
	private int count;

	@Override
	public int compareTo(TDepartureCityEntity o) {
		if (this.count > o.count) {
			return 1;
		} else if (this.count < o.count) {
			return -1;
		} else {
			/**
			 * 当compareTo返回0，TreeSet会认为“两个元素相等”
			 */
			if (this.id != o.id) {
				return new Long(this.id).compareTo(new Long(o.id));
			}
			return 0;
		}
	}

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
		TDepartureCityEntity other = (TDepartureCityEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
