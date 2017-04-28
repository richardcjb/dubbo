/**
 * FileTypeEnum.java
 * com.linyun.airline.admin.drawback.grabfile.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 文件类型枚举
 * @author   崔建斌
 * @Date	 2017年2月21日 	 
 */
public enum FileTypeEnum implements IEnum {

	FOLDER(1, "文件夹"), FILE(2, "文件");
	private int key;
	private String value;

	private FileTypeEnum(int key, String value) {
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
