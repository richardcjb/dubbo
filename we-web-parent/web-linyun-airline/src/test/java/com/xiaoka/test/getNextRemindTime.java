/**
 * getNextRemindTime.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年3月9日 	 
 */
public class getNextRemindTime {

	public static void main(String[] args) {
		Date date = new Date();// 新建此时的的系统时间
		System.out.println(getNextDay(date));// 返回明天的时间
		twoDateMinutes();

	}

	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, +30);//当前时间加30分钟
		date = calendar.getTime();
		return date;
	}

	public static long twoDateMinutes() {
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		dateOne.setTime(new Date());
		dateTwo.set(2017, 2, 9, 14, 50, 00);
		long timeOne = dateOne.getTimeInMillis();
		long timeTwo = dateTwo.getTimeInMillis();
		long minute = (timeOne - timeTwo) / (1000 * 60);
		System.out.println("相隔" + minute + "分钟");
		return minute;
	}

}
