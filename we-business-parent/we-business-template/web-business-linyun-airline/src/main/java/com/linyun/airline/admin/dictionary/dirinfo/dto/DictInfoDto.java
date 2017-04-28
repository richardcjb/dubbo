/**
 * DictInfoDto.java
 * com.linyun.airline.admin.dictionary.dirinfo.dto
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.dto;

import java.util.Date;

import lombok.Data;

import com.linyun.airline.common.enums.DataStatusEnum;
import com.uxuexi.core.common.util.EnumUtil;

/**
 * 字典信息列表页面数据传输对象
 * @author   崔建斌
 * @Date	 2016年12月6日 	 
 */
@Data
public class DictInfoDto implements Comparable<DictInfoDto> {

	private Integer id;

	private String dictname;

	private String typename;

	private Integer status;

	private String statusname;

	/**字典类别编码*/
	private String typecode;

	/**三字代码*/
	private String dictcode;

	/**国家*/
	private String countryname;

	/**州*/
	private String statename;

	/**英文名称*/
	private String englishname;

	/**中文名称*/
	private String chinesename;

	/**拼音*/
	private String pinyin;

	/**描述*/
	private String description;

	/**创建时间*/
	private Date createtime;

	/**修改时间*/
	private Date updatetime;

	/**国际状态*/
	private Integer internatstatus;

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
		DictInfoDto other = (DictInfoDto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		Long intId = Long.valueOf(this.id);
		return intId.hashCode();
	}

	/**使用次数*/
	private int count;

	@Override
	public int compareTo(DictInfoDto o) {
		if (this.count > o.count) {
			return 1;
		} else if (this.count < o.count) {
			return -1;
		} else {
			return 0;
		}
	}

	public void setStatus(int status) {
		this.status = status;
		this.statusname = EnumUtil.getValue(DataStatusEnum.class, status);
	}

}
