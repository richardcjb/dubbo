/**
 * OrderStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 审批结果
 * <p>
 * @author   孙斌
 * @Date	 2017年3月4日 	 
 */
public enum ApprovalResultEnum implements IEnum {

	ENABLE(1, "通过"), DISABLE(0, "拒绝");

	private int key;
	private String value;

	private ApprovalResultEnum(final int key, final String value) {
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
