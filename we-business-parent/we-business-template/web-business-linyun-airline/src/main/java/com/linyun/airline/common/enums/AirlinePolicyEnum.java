/**
 * AirlinePolicyEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月11日 	 
 */
public enum AirlinePolicyEnum implements IEnum {
	ENABLE(1, "存在"), DISABLE(0, "删除");
	private int key;
	private String value;

	private AirlinePolicyEnum(final int key, final String value) {
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
