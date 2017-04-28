/**
 * GrabType.java
 * com.linyun.airline.admin.drawback.grabfile.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年2月23日 	 
 */
public enum GrabTypeEnum implements IEnum {

	ALREADYGRAB(1, "已抓"), NOTGRAB(2, "未抓");
	private int key;
	private String value;

	private GrabTypeEnum(int key, String value) {
		this.key = key;
		this.value = value;
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
