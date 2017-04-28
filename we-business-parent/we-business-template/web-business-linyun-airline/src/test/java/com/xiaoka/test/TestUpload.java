/**
 * testDate.java
 * com.xiaoka.test
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.xiaoka.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.impl.QiniuUploadServiceImpl;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2016年11月20日 	 
 */
public class TestUpload {

	public static void main(String[] args) throws Exception {
		String path = "F:\\青春记忆\\psb.jpg";
		File f = new File(path);

		UploadService uploadService = new QiniuUploadServiceImpl();
		InputStream is = new FileInputStream(f);
		String url = uploadService.uploadImage(is, "pdf", null);
		System.out.println(url);
	}

}
