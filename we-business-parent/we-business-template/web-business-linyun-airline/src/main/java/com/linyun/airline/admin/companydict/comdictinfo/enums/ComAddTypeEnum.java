/**
 * ComAddTypeEnum.java
 * com.linyun.airline.admin.companydict.comdictinfo.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年4月4日 	 
 */
public enum ComAddTypeEnum implements IEnum {
	DICTTYPE_LSXM("LSXM", "流水项目"), DICTTYPE_XMYT("XMYT", "项目用途"), DICTTYPE_ZJZL("ZJZL", "资金种类");
	private String key;
	private String value;

	private ComAddTypeEnum(final String key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}
}
