/**
 * TreeSetTest.java
 * com.xiaoka.test
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.linyun.airline.entities.DictInfoEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2016年11月29日 	 
 */
public class TreeSetTest {

	@Test
	public void testTreeSet() {
		Set<DictInfoEntity> set = Sets.newTreeSet();
		DictInfoEntity dict1 = new DictInfoEntity();
		dict1.setId(1);
		dict1.setDictName("123");

		DictInfoEntity dict2 = new DictInfoEntity();
		dict2.setId(2);
		dict2.setDictName("456");

		List<DictInfoEntity> lst = Lists.newArrayList();
		lst.add(dict1);
		lst.add(dict2);
		set.addAll(lst);

		Assert.assertEquals(set.size(), 2);

	}

	@Test
	public static void testTreeSetAdd() {
		Set<DictInfoEntity> set = Sets.newTreeSet();
		DictInfoEntity dict1 = new DictInfoEntity();
		dict1.setId(1);
		dict1.setDictName("123");

		DictInfoEntity dict2 = new DictInfoEntity();
		dict2.setId(2);
		dict2.setDictName("456");

		boolean add1 = set.add(dict1);
		boolean add2 = set.add(dict2);

		Assert.assertTrue(add1);
		Assert.assertTrue(add2);
	}

}
