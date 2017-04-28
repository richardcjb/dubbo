package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息中的  消息状态
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageRemindEnum implements IEnum {
	MOUTH(1, "自然月1日"), WEEK(2, "自然每周一"), DAY(3, "每天"), HOUR(4, "每小时"), FIFTEENM(5, "每15分钟"), THIRTYM(7, "每30分钟"), TIMED(
			6, "定时"), UNREPEAT(8, "不重复");

	private int key;
	private String value;

	private MessageRemindEnum(final int key, final String value) {
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
