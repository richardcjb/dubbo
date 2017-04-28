package com.linyun.airline.admin.drawback.grabfile.service;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.util.ZFile;
import com.linyun.airline.common.util.ZipUtils;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabfileViewService extends BaseService<TGrabFileEntity> {

	//添加时根据pid查询数据
	public Map<String, Object> superFolder(long pid) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TGrabFileEntity dirfolder = dbDao.fetch(TGrabFileEntity.class,
				Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("id", "=", pid));
		obj.put("dirfolder", dirfolder);
		return obj;
	}

	//文件移动
	public Object getFileId(long id) {
		HashMap<Object, Object> obj = Maps.newHashMap();
		List<TGrabFileEntity> allModule = dbDao.query(TGrabFileEntity.class, null, null);
		obj.put("list", allModule);
		obj.put("parentId", id);
		return obj;
	}

	/** 
	 * 递归获取当前节点下面的所有子节点
	 */
	private void getChildFiles(List<TGrabFileEntity> fileList, long parentId) {
		// 获取直接子节点
		List<TGrabFileEntity> chidren = dbDao.query(TGrabFileEntity.class, Cnd.where("parentId", "=", parentId), null);
		if (!Util.isEmpty(chidren)) {
			fileList.addAll(chidren);
			for (TGrabFileEntity f : chidren) {
				//递归子节点
				getChildFiles(fileList, f.getId());
			}
		}
	}

	/**
	 * 根据文件id获取文件所在目录路径
	 */
	private String getFileFullPath(long fileId) {
		String path = "";
		List<String> pathLst = Lists.newArrayList();
		recursiveGetPath(pathLst, fileId);
		if (!Util.isEmpty(pathLst)) {
			List<String> reverse = Lists.reverse(pathLst);//排序
			for (int i = 0; i < reverse.size(); i++) {
				String p = reverse.get(i);
				if (i == 0) {
					path += p;
				} else {
					path += (File.separator + p);
				}

			}
		}
		return path + (File.separator);
	}

	/**
	 * 递归查询文件路径，将每一级路径保存到集合中
	 */
	private void recursiveGetPath(List<String> pathLst, long fileId) {
		Sql sql = Sqls.create(sqlManager.get("grab_mail_filepath"));
		sql.params().set("fileId", fileId);
		TGrabFileEntity parent = DbSqlUtil.fetchEntity(dbDao, TGrabFileEntity.class, sql);
		if (!Util.isEmpty(parent)) {
			pathLst.add(parent.getFileName());
			recursiveGetPath(pathLst, parent.getId());
		}
	}

	private void getChidrenFiles(List<TGrabFileEntity> fileList, long parentId) {
		//添加当前节点
		TGrabFileEntity current = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", parentId));
		fileList.add(current);
		getChildFiles(fileList, parentId);

	}

	/**
	 * 向下递归查询文件的完整路径
	 */

	/**
	 * 文件打包下载
	 * @param tempFilePath 待创建临时压缩文件
	 * @param files        待压缩文件集合
	 * @param request      请求
	 * @param response     响应
	 * @return response
	 * @throws Exception 异常
	 */
	public void downLoadZipFiles(long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String downloadName = "myfile.zip";
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		response.reset();
		// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(downloadName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
		response.setContentType("application/octet-stream");
		try {
			//1、把多个文件打包成一个文件
			List<TGrabFileEntity> gFiles = Lists.newArrayList();
			getChidrenFiles(gFiles, id);
			//把1中得到的路径(pathList)转为文件(file)
			List<ZFile> files = Lists.newArrayList();
			for (TGrabFileEntity gf : gFiles) {
				//判断文件类型
				int fileType = gf.getType();
				if (FileTypeEnum.FILE.intKey() == fileType) {//文件
					ZFile zf = new ZFile();
					URL url = new URL(gf.getUrl());
					zf.setInput(url.openStream());
					zf.setFileName(gf.getFileName());
					String fileFullPath = getFileFullPath(gf.getId());
					zf.setRelativePathInZip(fileFullPath);
					files.add(zf);
				}
			}
			//将文件进行打包(将数据写入zos)
			ZipUtils.zipFile(files, zos);
			zos.flush();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}