package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息中的  是否提醒
 * <p>
 * 
 * @author   彭辉
 * @Date	 2017年02月16日
 */
public enum MessageIsRemindEnum implements IEnum {
	NO(0, "不提醒"), YES(1, "提醒");

	private int key;
	private String value;

	private MessageIsRemindEnum(final int key, final String value) {
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
