/**
 * HttpClientUtilTest.java
 * com.xiaoka.test
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.util.HttpClientUtil;

/**
 * HttpClientUtil测试类
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月1日 	 
 */
public class HttpClientUtilTest {

	//打log用
	private static Log log = Logs.getLog(SabreAPITest.class);

	@Test
	public static void getParams() {
		InstaFlightsSearchForm paraBean = new InstaFlightsSearchForm();
		paraBean.setOrigin("ATL");
		paraBean.setDestination("LAS");
		String params = HttpClientUtil.getParams(paraBean);
		log.info(params);
		Assert.assertEquals(params, "?origin=ATL&destination=LAS");
	}
}
