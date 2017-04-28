/**
 * SalaryEnum.java
 * com.linyun.airline.admin.user.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年3月27日 	 
 */
public enum SalaryEnum implements IEnum {
	ALREADY_WAGES(1, "已发"), NO_WAGES(2, "未发");
	private int key;
	private String value;

	private SalaryEnum(final int key, final String value) {
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

	public int intKey() {
		return key;
	}
}
