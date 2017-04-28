/**
 * TestPassword.java
 * com.xiaoka.test.sign
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月26日 	 
 */
public class TestPassword {

	public static void main(String[] args) {
		String pass = MD5.sign("1", AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
		System.out.println(pass);
	}

}
