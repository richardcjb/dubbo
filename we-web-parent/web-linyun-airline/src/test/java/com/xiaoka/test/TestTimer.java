/**
 * TestTimer.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO(测试定时器)
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年3月9日 	 
 */
public class TestTimer {

	public static void main(String[] args) {
		//timer1();  
		//timer2();
		//timer3();  
		timer4();
	}

	// 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)  
	public static void timer1() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 2000);// 设定指定的时间time,此处为2000毫秒  
	}

	// 第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行  
	// schedule(TimerTask task, long delay, long period)  
	public static void timer2() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 1000 * 30, 1000 * 60);
	}

	// 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。  
	// scheduleAtFixedRate(TimerTask task, long delay, long period)  
	public static void timer3() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 1000, 2000);
	}

	// 第四种方法：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．  
	// Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)  
	public static void timer4() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2017); //控制年
		calendar.set(Calendar.MONTH, 2); //控制月（从0开始）
		calendar.set(Calendar.DAY_OF_MONTH, 9); //控制天
		calendar.set(Calendar.HOUR_OF_DAY, 10); // 控制时  
		calendar.set(Calendar.MINUTE, 42); // 控制分  
		calendar.set(Calendar.SECOND, 30); // 控制秒  

		Date time = calendar.getTime(); // 得出执行任务的时间,此处为今天的12：00：00  

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, time, 1000 * 5);// 这里设定将延时每天固定执行  
	}
}
