/**
 * AuthorityBean.java
 * com.linyun.airline.admin.authority.authoritymanage.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.authoritymanage.entity;

import lombok.Data;

/**
 * 权限管理bean
 * @author   崔建斌
 * @Date	 2016年12月7日 	 
 */
@Data
public class AuthorityBean implements Comparable<AuthorityBean> {
	//部门id
	private long deptId;
	//部门名称
	private String deptName;
	//职位id
	private long jobId;
	//职位名称
	private String name;
	//功能id
	private long funId;
	//模块名称(功能名称)
	private String moduleName;

	public void setStatus(String deptName, String name, String moduleName) {
		this.deptName = deptName;
		this.name = name;
		this.moduleName = moduleName;
	}

	/**使用次数*/
	private int count;

	@Override
	public int compareTo(AuthorityBean o) {
		return new Long(this.deptId).compareTo(new Long(o.deptId));
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
		AuthorityBean other = (AuthorityBean) obj;
		if (deptId != other.deptId)
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
		result = prime * result + (int) (deptId ^ (deptId >>> 32));
		return result;
	}
}
