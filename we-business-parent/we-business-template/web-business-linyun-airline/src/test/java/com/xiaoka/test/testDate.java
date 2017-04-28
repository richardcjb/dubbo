/**
 * testDate.java
 * com.xiaoka.test
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.xiaoka.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2016年11月20日 	 
 */
public class testDate {

	public static void main(String[] args) throws Exception {
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTimeUtil.nowDateTime());
		System.out.println(format);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format));
		System.out.println(DateTimeUtil.nowDateTime());
		System.out.println(DateTimeUtil.nowDate());
		System.out.println(DateTimeUtil.now());

		System.out.println("----------------------------");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format2 = sdf.format(DateTimeUtil.nowDateTime());
		Date date = sdf.parse(format2); //你要的日期格式
		System.out.println(sdf.format(date));

		System.out.println(DateUtil.addMonth(new Date(), 1));
	}
}
