/**
 * TestPnr.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年2月16日 	 
 */
public class TestPnr {
	public static void main(String[] args) throws Exception {
		String input = "128FEBAKLSYD/D￥VA<<\n" + " 28FEB  TUB   AKL/Z￥13   SYD/-2\n"
				+ "1VA/NZ 7419 Y4 B4 H4 K4 L4 E4 AKLSYD 100P 235P 320 0 DCA /E\n" + "            N4 V4 Q0 T0 M0 U0\n"
				+ "2VA/NZ 7405 J4 C4 D4 I4 Y4 B4*AKLSYD 405P 540P 763 0 DCA /E\n"
				+ "            H4 K4 L4 E4 N4 V4 Q4 T0\n"
				+ "3VA/NZ 7405 J4 C4 D4 I4 Y4 B4*AKLSYD 405P 540P 763 0 DCA /E\n"
				+ "            H4 K4 L4 E4 N4 V4 Q4 T0\n"
				+ "4VA/** 7405 J4 C4 D4 I4 Y4 B4*AKLSYD 405P 540P 76H M 0 DCA /E\n"
				+ "            H4 K4 L4 E4 N4 V4 Q4 T0\n"
				+ "5VA/NZ 7405 J4 C4 D4 I4 Y4 B4*AKLSYD 405P 540P 763 0 DCA /E\n"
				+ "            H4 K4 L4 E4 N4 V4 Q4 T0\n";
		//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，最后以\n换行结束
		String regex = "(?s)\\d{1}[A-Za-z]{2}/.{2}\\s.+?\\d\n";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(input);
		while (m.find()) {
			System.out.println(m.group() + "|||");
		}
	}
}
