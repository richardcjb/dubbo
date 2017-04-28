/**
 * HttpClientUtil.java
 * com.linyun.airline.common.util
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.nutz.lang.Mirror;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.linyun.airline.common.result.HttpResult;
import com.uxuexi.core.common.util.CollectionUtil;
import com.uxuexi.core.common.util.Util;

/**
 * http请求工具类
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
public class HttpClientUtil {

	//打log用
	private static Log log = Logs.getLog(HttpClientUtil.class);

	private static PoolingHttpClientConnectionManager connMgr;

	private static RequestConfig requestConfig;

	private static final int MAX_TIMEOUT = 10000;

	/**
	 * HTTP请求成功状态码
	 */
	public static final int SUCCESS_CODE = 200;

	static {
		// 设置连接池  
		connMgr = new PoolingHttpClientConnectionManager();

		// 设置连接池大小  
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时  
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时  
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时  
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用  
		configBuilder.setStaleConnectionCheckEnabled(true);

		//设置代理抓包
		requestConfig = configBuilder.build();
	}

	public static HttpResult httpsPost(HttpPost httpPost) {
		HttpResult hr = new HttpResult();
		String result = "";
		try {
			//ssl连接,sabre接口必须走https
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
					.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			log.info("statusCode:" + statusCode);

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");

			hr.setStatusCode(statusCode);
			hr.setResult(result);
		} catch (ConnectionPoolTimeoutException e) {
			log.error("http get throw ConnectionPoolTimeoutException(wait time out)");
		} catch (ConnectTimeoutException e) {
			log.error("http get throw ConnectTimeoutException");
		} catch (SocketTimeoutException e) {
			log.error("http get throw SocketTimeoutException");
		} catch (Exception e) {
			log.error("http get throw Exception");
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		return hr;
	}

	public static HttpResult httpsGet(HttpGet httpGet) {
		HttpResult hr = new HttpResult();
		String result = "";
		try {
			//ssl连接,sabre接口必须走https
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
					.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
			HttpResponse response = httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();
			log.info("statusCode:" + statusCode);

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");

			hr.setStatusCode(statusCode);
			hr.setResult(result);
		} catch (ConnectionPoolTimeoutException e) {
			log.error("http get throw ConnectionPoolTimeoutException(wait time out)");
		} catch (ConnectTimeoutException e) {
			log.error("http get throw ConnectTimeoutException");
		} catch (SocketTimeoutException e) {
			log.error("http get throw SocketTimeoutException");
		} catch (Exception e) {
			log.error("http get throw Exception");
			e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		return hr;
	}

	/** 
	 * 创建SSL安全连接 
	 * @return 
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	/**
	 * 从实体类得到http-get请求参数字符串(形如:?p1=v1&p2=v2..)
	 */
	public static String getParams(final Object paramForm) {
		Mirror<?> mirror = Mirror.me(paramForm);
		Field[] fields = mirror.getFields();
		List<String> fieldNames = Lists.transform(CollectionUtil.list(fields), new Function<Field, String>() {
			@Override
			public String apply(Field f) {
				Object val = mirror.getValue(paramForm, f);
				if (Util.isEmpty(val)) {
					return null;
				}
				return f.getName() + "=" + val;
			}
		});

		StringBuffer sb = new StringBuffer("?");
		sb.append(Joiner.on("&").skipNulls().join(fieldNames));
		return sb.toString();
	}

}
