/**
 * FillInPdfModule.java
 * com.linyun.airline.admin.fillinpdf.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.fillinpdf.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.fillinpdf.form.FillPdfAddForm;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.util.FillIntoPdf;
import com.uxuexi.core.common.util.FileUtil;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年3月31日 	 
 */
@IocBean
@At("/admin/fillinpdf")
@Filters
public class FillInPdfModule {
	@Inject
	private UploadService qiniuUploadService;//文件上传
	private String phone;

	@At
	@Ok("jsp")
	public Object list(@Param("..") final Pager pager, final HttpSession session) {

		return null;
	}

	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	@Ok("json")
	public Object add(@Param("..") final FillPdfAddForm form, final HttpSession session,
			@Param("fileName") final File file, HttpServletRequest request, HttpServletResponse response) {
		String url11 = null;
		FileInputStream is = null;
		if (!Util.isEmpty(file)) {

			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			String ext = FileUtil.getSuffix(file);
			/*String str = file.getName();*/
			String shortUrl = qiniuUploadService.uploadImage(is, ext, null);
			url11 = CommonConstants.IMAGES_SERVER_ADDR + shortUrl;
		}
		System.out.println(url11);

		String phone = url11;
		FillIntoPdf.fillTemplate(form, phone);
		//生成的新文件路径
		String str = System.getProperty("java.io.tmpdir");
		String newPDFPath = str + File.separator + "1.pdf";
		String url = null;
		try {
			url = qiniuUploadService.uploadImage(new FileInputStream(new File(newPDFPath)), "pdf", null);
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		String url1 = CommonConstants.IMAGES_SERVER_ADDR + url;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url1);
		request.setAttribute("url1", url1);
		try {
			request.getRequestDispatcher("/admin/fillinpdf/list.html").forward(request, response);

		} catch (ServletException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return map;
	}

	/**
	 * 删除记录
	 */
	@At
	public Object download(@Param("id") final long id, final HttpServletResponse response,
			@Param("downloadurl") final String downloadurl) {

		String resourceFile = null;
		String fileName = "德国中英签证表.pdf";

		/*String fileName = "下载吧";*/
		InputStream is = null;
		OutputStream out = null;
		try {

			URL url = new URL(downloadurl);
			URLConnection connection = url.openConnection();
			is = connection.getInputStream();

			out = response.getOutputStream();
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1") + "\"");
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
			response.flushBuffer();
			out.close();
			is.close();

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				is.close();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			try {
				out.close();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return null;
	}

	/**
	 * 上传文件
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	@Ok("json")
	public Object uploadFile(@Param("Filedata") final File file, HttpServletRequest request) throws Exception {
		FileInputStream is = new FileInputStream(file);
		String ext = FileUtil.getSuffix(file);
		/*String str = file.getName();*/
		String shortUrl = qiniuUploadService.uploadImage(is, ext, null);
		String url = CommonConstants.IMAGES_SERVER_ADDR + shortUrl;
		System.out.println(url);
		this.phone = url;
		return url;
	}

	/*	*//**
			* 上传文件
			*/
	/*
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	@Ok("json")
	public Object uploadFile1(@Param("fileName") final File file, HttpServletRequest request) throws Exception {
	FileInputStream is = new FileInputStream(file);
	String ext = FileUtil.getSuffix(file);
	String str = file.getName();
	String shortUrl = qiniuUploadService.uploadImage(is, ext, null);
	String url = CommonConstants.IMAGES_SERVER_ADDR + shortUrl;
	System.out.println(url);
	this.phone = url;
	return url;
	}*/
}
