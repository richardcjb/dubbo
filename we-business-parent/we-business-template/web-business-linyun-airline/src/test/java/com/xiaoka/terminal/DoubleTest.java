/**
 * DoubleTest.java
 * com.xiaoka.terminal
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.terminal;

import java.text.DecimalFormat;

/**
 * 测试double数据类型显示时保留小数点后两位，并且四舍五入
 * @author   崔建斌
 * @Date	 2017年3月9日 	 
 */
public class DoubleTest {

	public static void main(String[] args) {
		//double数据四舍五入保留小数点后两位
		DecimalFormat df = new DecimalFormat("#.00");
		String doubleData = df.format(64.64636);
		System.out.println(doubleData);
	}

}
