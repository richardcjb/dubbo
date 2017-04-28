/**
 * Naval.java
 * com.linyun.airline.admin.drawback.grabfile.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 文件夹层级结构枚举
 * @author   崔建斌
 * @Date	 2017年2月21日 	 
 */
public enum FileNavalEnum implements IEnum {
	FITD7JQTT(1, "D7(JQ,TT)/时间/客户团号/文件"), FITQF(2, "QF/客户团号/文件"), FITVA(3, "VA/时间/客户团号/PNR/文件"), TEAMJQTT(4,
			"JQ(TT)/文件"), TEAMVA(5, "VA/PNR/文件");
	private int key;
	private String value;

	private FileNavalEnum(int key, String value) {
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
