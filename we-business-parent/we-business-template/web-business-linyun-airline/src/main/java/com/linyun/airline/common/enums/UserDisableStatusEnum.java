/**
 * UserDisableStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户是否禁用(0-用户没有被禁用,1-用户被禁用)
 * @author   崔建斌
 * @Date	 2016年12月21日 	 
 */
public enum UserDisableStatusEnum implements IEnum {
	YES(0, "否"), NO(1, "是");
	private int key;
	private String value;

	private UserDisableStatusEnum(final int key, final String value) {
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
