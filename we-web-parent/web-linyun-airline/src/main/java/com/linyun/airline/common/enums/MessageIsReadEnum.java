package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息中的  是否可读
 * <p>
 * 
 * @author   彭辉
 * @Date	 2017年02月16日
 */
public enum MessageIsReadEnum implements IEnum {
	UNREAD(0, "未读取"), READ(1, "已读");

	private int key;
	private String value;

	private MessageIsReadEnum(final int key, final String value) {
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
