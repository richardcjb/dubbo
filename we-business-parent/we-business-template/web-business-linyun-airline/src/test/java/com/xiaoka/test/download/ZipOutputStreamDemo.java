/**
 * ZipOutputStreamDemo.java
 * com.xiaoka.test.download
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年2月28日 	 
 */
public class ZipOutputStreamDemo {

	public static void main(String[] args) throws IOException {
		byte[] buffer = new byte[1024];
		//生成的ZIP文件名为Demo.zip  
		String strZipName = "Demo.zip";
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
		//需要同时下载的两个文件result.txt ，source.txt  
		File[] file1 = { new File("F:/result.txt"), new File("F:/source.txt") };
		for (int i = 0; i < file1.length; i++) {
			FileInputStream fis = new FileInputStream(file1[i]);
			out.putNextEntry(new ZipEntry(file1[i].getName()));
			int len;
			//读入需要下载的文件的内容，打包到zip文件  
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		}
		out.close();
		System.out.println("生成Demo.zip成功");
	}

}