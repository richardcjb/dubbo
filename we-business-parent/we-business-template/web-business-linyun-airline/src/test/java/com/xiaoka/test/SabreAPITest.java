/**
 * 
 * 
 * 
 * 
 * 授权:
 * 遵循OAuth 2.0开放授权
 * POST /v2/auth/token
 * account:brandorsabre
 * passwd:zxcv1234
 * 
 */
package com.xiaoka.test;

import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.linyun.airline.common.sabre.SabreConfig;
import com.linyun.airline.common.sabre.SabreTokenFactory;
import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.linyun.airline.common.sabre.dto.MACCitty;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.form.MACLookupForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.sabre.service.impl.SabreServiceImpl;
import com.linyun.airline.common.util.HttpClientUtil;
import com.linyun.airline.common.util.JsonPathGeneric;
import com.uxuexi.core.common.util.Util;

/**
 * 1,构造客户端ID
 * 形如:V1:mq2kolgs7tunkpe0:DEVCENTER:EXT
 * 2,Base64编码(Client_ID),Base64编码你的密码password 
 *   用冒号拼接，得到形如encodedId:encodedPasswd的值，对此值再次进行Base64编码得到用于获取
 *   access_token的凭据
 * 3，获取access_token
 * 
 * 4,使用access_token调用查询航班的API
 * 
 * 国际航空3字母代码
 * SYX(三亚)
 * BKK(曼谷)
 * 例:GET https://api.sabre.com/v1/shop/flights/cheapest/fares/DFW
 * @author   朱晓川
 * @Date	 2016年10月26日 	 
 */
public class SabreAPITest {

	//打log用
	static Log log = Logs.getLog(SabreAPITest.class);

	@Test
	public void getAccessToken() {
		int nullCnt = 0;
		for (int i = 0; i < 20; i++) {
			//从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
			SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
			String token = accessToken.getAccess_token();
			log.info(token);

			if (Util.isEmpty(token)) {
				nullCnt++;
			}
			Assert.assertEquals(nullCnt, 0);
		}
	}

	/**
	 * 1输入目的地直接查询(调试通过)
	 * GET /v1/shop/flights/cheapest/fares/{destination}
	 * 
	 * 2复杂查询
	 * A     /v1/shop/flights
	 * B     /v1/shop/flights/tags/{tagid}
	 * A接口指定参数enabletagging=true的时候会返回tagid
	 * 
	 * BJS   北京
	 * SDY   悉尼
	 */
	@Test
	public static void flightTo() {
		String searchUrl = SabreConfig.environment + "/v1/shop/flights/cheapest/fares/SYD";
		//		String searchUrl = test_environment + "/v1/shop/flights/cheapest/fares/BJS";

		String result = null;
		HttpGet httpget = new HttpGet(searchUrl);

		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpget.addHeader("Authorization", "Bearer " + token);

		log.info("executing request " + httpget.getRequestLine());
		result = HttpClientUtil.httpsGet(httpget).getResult();
		log.info(result);
	}

	public static void instaFlightsSearch() {
		InstaFlightsSearchForm form = new InstaFlightsSearchForm();
		form.setOrigin("ATL");
		form.setDestination("LAS");
		form.setDeparturedate("2017-03-05");
		form.setReturndate("2017-03-15");
		form.setPointofsalecountry("US");
		form.setOffset(1);
		form.setLimit(10);
		SabreService service = new SabreServiceImpl();
		SabreResponse resp = service.instaFlightsSearch(form);
	}

	/**
	 * 多机场城市代码查询
	 */
	public static void macLookup() {
		MACLookupForm form = new MACLookupForm();
		form.setCountry("CN");
		String searchUrl = SabreConfig.environment + SabreConfig.MAC_LOOKUP_URI + HttpClientUtil.getParams(form);
		String result = null;
		HttpGet httpget = new HttpGet(searchUrl);

		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpget.addHeader("Authorization", "Bearer " + token);

		log.info("executing request " + httpget.getRequestLine());
		result = HttpClientUtil.httpsGet(httpget).getResult();
		log.info(result);

		List<MACCitty> cities = new JsonPathGeneric().getGenericList(result, "$.Cities[*]");
		if (!Util.isEmpty(cities)) {
			log.info(cities.size());
		}
	}

	public static void main(String[] args) {
		//		macLookup();
		instaFlightsSearch();
	}
}
