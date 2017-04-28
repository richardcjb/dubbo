/**
 * TreeRecursionMain.java
 * com.xiaoka.test.recursion
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.recursion;

import java.util.ArrayList;
import java.util.List;

/**递归查询所有子节点测试
 * @author   崔建斌
 * @Date	 2017年3月3日 	 
 */

public class TreeRecursionMain {

	//子节点  
	static List<TreeEntityTest> childMenu = new ArrayList<TreeEntityTest>();

	/** 
	* 获取某个父节点下面的所有子节点 
	* @param menuList 
	* @param pid 
	* @return 
	*/
	public static List<TreeEntityTest> treeMenuList(List<TreeEntityTest> menuList, int pid) {
		for (TreeEntityTest te : menuList) {
			//遍历出父id等于参数的id，add进子节点集合  
			if (Integer.valueOf(te.getPid()) == pid) {
				//递归遍历下一级  
				treeMenuList(menuList, Integer.valueOf(te.getId()));
				childMenu.add(te);
			}
		}
		return childMenu;
	}

	public static void main(String[] args) {
		List<TreeEntityTest> menuList = new ArrayList<TreeEntityTest>();
		TreeEntityTest te = new TreeEntityTest();
		te.setId("1");
		te.setName("目录");
		te.setPid("0");
		TreeEntityTest te1 = new TreeEntityTest();
		te1.setId("2");
		te1.setName("目录1");
		te1.setPid("1");
		TreeEntityTest te2 = new TreeEntityTest();
		te2.setId("3");
		te2.setName("目录1.1");
		te2.setPid("2");
		TreeEntityTest te3 = new TreeEntityTest();
		te3.setId("4");
		te3.setName("目录1.2");
		te3.setPid("2");
		TreeEntityTest te4 = new TreeEntityTest();
		te4.setId("5");
		te4.setName("目录2");
		te4.setPid("1");
		TreeEntityTest te5 = new TreeEntityTest();
		te5.setId("6");
		te5.setName("目录2.1");
		te5.setPid("5");
		TreeEntityTest te6 = new TreeEntityTest();
		te6.setId("7");
		te6.setName("目录2.2");
		te6.setPid("5");
		TreeEntityTest te7 = new TreeEntityTest();
		te7.setId("8");
		te7.setName("目录2.2.1");
		te7.setPid("7");
		menuList.add(te);
		menuList.add(te1);
		menuList.add(te2);
		menuList.add(te3);
		menuList.add(te4);
		menuList.add(te5);
		menuList.add(te6);
		menuList.add(te7);

		List<TreeEntityTest> childList = treeMenuList(menuList, 0);
		for (TreeEntityTest m : childList) {
			System.out.println(m.getId() + "   " + m.getName());
		}
	}

}
