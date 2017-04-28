package com.linyun.airline.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.collect.Lists;
import com.linyun.airline.common.util.ZFile;
import com.linyun.airline.common.util.ZipUtils;

@IocBean
@At("/multiupload")
@Filters
public class MultiUploadDemoModule {

	private static final Log log = Logs.get();

	/**
	 * 多图上传,webuploader实际上是单图上传多次调用了后台地址
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object demo(@Param("::form.") final Object form, @Param("file") final TempFile[] fts) {
		log.info("fts:" + fts);
		log.info("form:" + form);
		return null;
	}

	@At
	public void download(HttpServletResponse resp) throws IOException, URISyntaxException {
		/*下载到的文件名*/
		String downloadName = "test.zip";
		ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());

		resp.reset();
		// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		resp.addHeader("Content-Disposition", "attachment;filename="
				+ new String(downloadName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
		resp.setContentType("application/octet-stream");

		List<ZFile> files = Lists.newArrayList();

		ZFile zf1 = new ZFile();
		zf1.setFileName("aa.xlsx");
		zf1.setInput(new FileInputStream(new File("E:/files/a/aa.xlsx")));
		zf1.setRelativePathInZip("files/a/b/c/");
		files.add(zf1);

		ZFile zf2 = new ZFile();
		zf2.setFileName("b.xlsx");
		zf2.setInput(new FileInputStream(new File("E:/files/a/b.xlsx")));
		zf2.setRelativePathInZip("files/a/d/");
		files.add(zf2);

		URL url = new URL("http://oluwc01ms.bkt.clouddn.com/67997426-2220-4807-b409-b22f9cb1edf2.pdf");
		ZFile zf3 = new ZFile();

		zf3.setFileName("a.jpg");
		zf3.setRelativePathInZip("files/a/d/");
		zf3.setInput(url.openStream());
		files.add(zf3);

		ZipUtils.zipFile(files, zos);

		zos.flush();
		zos.close();
	}

}
