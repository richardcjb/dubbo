/**
 * OrderStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 订单 提醒模式
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年2月27日 	 
 */
public enum OrderRemindEnum implements IEnum {
	UNREPEAT(6, "不重复"), FIFTEENM(0, "每15分钟"), THIRTYM(1, "每30分钟"), HOUR(2, "每小时"), DAY(3, "每天"), WEEK(4, "每周"), MOUTH(
			5, "每月");

	private int key;
	private String value;

	private OrderRemindEnum(final int key, final String value) {
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
