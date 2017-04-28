/**
 * TestMail.java
 * com.xiaoka.test.mail
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.mail;

import java.util.Random;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年2月21日 	 
 */
public class TestMail {
	//根据邮件附件名获取PNR
	private static String getAttachmentName(String attachmentName) {
		//TODO
		Random random = new Random();
		int ss = random.nextInt();
		if (ss % 2 == 0) {
			return "X4WRZ8";
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getAttachmentName("a"));
	}
}
