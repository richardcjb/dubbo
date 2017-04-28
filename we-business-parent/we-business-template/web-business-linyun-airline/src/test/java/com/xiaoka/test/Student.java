/**
 * Student.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年2月22日 	 
 */
@Data
public class Student {

	private String name;

	private static void test(Student student) {
		student.setName("bbbb");
	}

	public static void main(String[] args) {
		Student s = new Student();
		s.setName("qqq");
		s.test(s);
		System.out.println(s.getName());
	}

}
