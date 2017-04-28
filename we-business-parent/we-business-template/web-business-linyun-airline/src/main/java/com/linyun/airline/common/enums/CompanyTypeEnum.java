package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 公司类型
 * <p>
 * 上游公司？or 代理商
 * @author   刘旭利
 * @Date	 2016年11月23日
 */
public enum CompanyTypeEnum implements IEnum {
	UPCOMPANY(1, "上游公司"), AGENT(2, "代理商");
	private int key;
	private String value;

	private CompanyTypeEnum(final int key, final String value) {
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
