/**
 * AirlinePolicyService.java
 * com.linyun.airline.admin.airlinepolicy.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.airlinepolicy.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Strings;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.AirlinePolicyEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.common.util.Office2PDF;
import com.linyun.airline.common.util.Word2Html;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlinePolicyEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TAirlinePolicyAddForm;
import com.linyun.airline.forms.TAirlinePolicyUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  孙斌
 * @Date	 2017年3月8日 	 
 */
public class AirlinePolicyService extends BaseService<TAirlinePolicyEntity> {
	@Inject
	private UploadService qiniuUploadService;//文件上传

	public Map<String, Object> findConditionList(HttpSession session) {

		Map<String, Object> map = Maps.newHashMap();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		String sqlString = sqlManager.get("airlinepolicy_datalist");
		Sql sql = Sqls.create(sqlString);
		/*查询航空公司*/
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("temp.airlineCompanyName", "is not", null);
		cnd.groupBy("airlineCompanyId");

		sql.setCondition(cnd);
		List<Record> list = dbDao.query(sql, cnd, null);

		//查询地区
		String sqlString1 = sqlManager.get("airlinepolicy_datalist");
		Sql sql1 = Sqls.create(sqlString1);
		Cnd cnd1 = Cnd.NEW();
		/*cnd1.groupBy("areaId");*/
		cnd1.and("companyId", "=", companyId);
		cnd1.and("areaName", "is not", null);
		cnd1.groupBy("areaName");
		sql1.setCondition(cnd1);
		List<Record> list1 = dbDao.query(sql1, cnd1, null);
		map.put("airlineCompanyList", list);
		map.put("areaList", list1);
		return map;

	}

	public TAirlinePolicyEntity addFile(TAirlinePolicyAddForm addForm, HttpSession session) {

		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		/*dbDao.fetch(ComDictInfoEntity.class, addForm.getAreaId());*/
		try {
			TAirlinePolicyEntity tap = dbDao.fetch(TAirlinePolicyEntity.class, Long.valueOf(addForm.getAreaName()));
			addForm.setAreaName(tap.getAreaName());
		} catch (Exception e1) {

		}
		addForm.setStatus(AirlinePolicyEnum.ENABLE.intKey());
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		addForm.setCompanyId(companyId);

		String url = addForm.getUrl();
		addForm.setUrl(url);
		String extend = null;
		if (!Util.isEmpty(url)) {
			extend = url.substring(url.lastIndexOf("."), url.length());

		}
		if (!Util.isEmpty(addForm.getFileRealName())) {
			if (!Util.isEmpty(extend)) {

				addForm.setFileName(addForm.getFileRealName() + extend);
			} else {

				addForm.setFileName(addForm.getFileRealName());
			}

		} else {

			addForm.setFileName(addForm.getFileName());
		}
		addForm.setFileSize(addForm.getFileSize());
		String extendName = url.substring(url.lastIndexOf(".") + 1, url.length());
		Word2Html word2Html = new Word2Html();
		String str = System.getProperty("java.io.tmpdir");
		if ("doc".equalsIgnoreCase(extendName) || "docx".equalsIgnoreCase(extendName)) {
			try {
				/*word2Html.docToHtml(url, str + File.separator + "12.html");
				HtmlToPdf.htmlConvertToPdf(str + File.separator + "12.html", str + File.separator + "12.pdf");*/
				Office2PDF.urlToFile(url, str + File.separator + "12.pdf");
				File file = new File(str + File.separator + "12.pdf");
				String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
						+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
				addForm.setPdfUrl(pdfUrl);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				File file1 = new File(str + File.separator + "12.html");
				File file2 = new File(str + File.separator + "12.pdf");
				if (file1.exists()) {
					file1.delete();
				}
				if (file2.exists()) {
					file2.delete();
				}
			}
		} else if ("xls".equalsIgnoreCase(extendName) || "xlsx".equalsIgnoreCase(extendName)) {
			try {
				/*POIReadExcelToHtml poiReadExcelToHtml = new POIReadExcelToHtml();
				poiReadExcelToHtml.excelConvertToPdf(url, str + File.separator + "12.html");*/

				/*HtmlToPdf.htmlConvertToPdf(str + File.separator + "12.html", str + File.separator + "12.pdf");*/
				Office2PDF.urlToFile(url, str + File.separator + "12.pdf");
				File file = new File(str + File.separator + "12.pdf");
				String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
						+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
				addForm.setPdfUrl(pdfUrl);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				File file1 = new File(str + File.separator + "12.html");
				File file2 = new File(str + File.separator + "12.pdf");
				if (file1.exists()) {
					file1.delete();
				}
				if (file2.exists()) {
					file2.delete();
				}
			}
		}
		return this.add(addForm);

	}

	public Object goAdd() {

		// TODO Auto-generated method stub
		return null;

	}

	public void addFileInfo(TAirlinePolicyAddForm addForm, HttpSession session) {
		this.addFile(addForm, session);
	}

	/**
	 * 根据输入显示航空公司
	 */
	//
	public Object selectAirlineCompanys(String findCompany, String companyName) {
		List<Record> companyNameList = getCompanyNameList(findCompany, companyName);
		List<Select2Option> result = transform2SelectOptions(companyNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> companyNameList) {
		return Lists.transform(companyNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("id"));
				op.setText(record.getString("dictCode") + "-" + record.getString("dictName"));
				return op;
			}
		});
	}

	/**
	 * 获取公司名称下拉框
	 * @param dictName
	 */
	public List<Record> getCompanyNameList(String findCompany, final String companyName) {
		String sqlString = sqlManager.get("airlinepolicy_select2_airlinecompany");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("typeCode", "=", "HKGS");
		cnd.and("(dictName", "like", Strings.trim(findCompany) + "%");
		cnd.or("dictCode", "like", "%" + Strings.trim(findCompany) + "%");
		if (!Util.isEmpty(companyName)) {
			cnd.and("id", "NOT IN", companyName);
		}
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 根据输入显示地区
	 * @param session 
	 */
	//
	public Object selectArea(String findCompany, String companyName, HttpSession session) {
		List<Record> companyNameList = getAreaList(findCompany, companyName, session);
		List<Select2Option> result = transform2SelectOptions1(companyNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions1(List<Record> companyNameList) {
		return Lists.transform(companyNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("id"));
				op.setText(record.getString("areaName"));
				return op;
			}
		});
	}

	/**
	 * 获取公司名称下拉框
	 * @param session 
	 * @param dictName
	 */
	public List<Record> getAreaList(String findCompany, final String companyName, HttpSession session) {
		String sqlString = sqlManager.get("airlinepolicy_select2_newarea");
		Sql sql = Sqls.create(sqlString);
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("areaName", "like", Strings.trim(findCompany) + "%");
		if (!Util.isEmpty(companyName)) {
			cnd.and("id", "NOT IN", companyName);
		}
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	public Object goUpdate(Long id) {
		Map<String, Object> map = Maps.newHashMap();
		TAirlinePolicyEntity airlinePolicy = dbDao.fetch(TAirlinePolicyEntity.class, id);
		if (!Util.isEmpty(airlinePolicy.getAirlineCompanyId())) {

			DictInfoEntity dictInfo = dbDao.fetch(DictInfoEntity.class,
					Long.valueOf(airlinePolicy.getAirlineCompanyId()));
			map.put("dictInfo", dictInfo);
		}
		/*if (!Util.isEmpty(airlinePolicy.getAreaId())) {
			TAreaEntity areaInfo = dbDao.fetch(TAreaEntity.class, Long.valueOf(airlinePolicy.getAreaId()));
			map.put("areaInfo", areaInfo);
		}*/
		map.put("airlinePolicy", airlinePolicy);
		return map;

	}

	public void updateFileInfo(TAirlinePolicyUpdateForm updateForm, HttpSession session, Long id) {
		String fileName = null;
		String url = updateForm.getUrl();
		String extend = null;
		TAirlinePolicyEntity airlinePolicy = dbDao.fetch(TAirlinePolicyEntity.class, id);
		try {
			TAirlinePolicyEntity tap = dbDao.fetch(TAirlinePolicyEntity.class, Long.valueOf(updateForm.getAreaName()));
			updateForm.setAreaName(tap.getAreaName());
		} catch (Exception e1) {

		}
		if (!Util.isEmpty(url)) {
			extend = url.substring(url.lastIndexOf("."), url.length());

		} else if (!Util.isEmpty(airlinePolicy.getUrl())) {
			extend = airlinePolicy.getUrl().substring(airlinePolicy.getUrl().lastIndexOf("."),
					airlinePolicy.getUrl().length());

		}
		if (!Util.isEmpty(updateForm.getFileRealName())) {
			if (!Util.isEmpty(extend)) {

				fileName = updateForm.getFileRealName() + extend;
			} else {
				fileName = updateForm.getFileRealName();

			}
		} else {

			fileName = updateForm.getFileName();

		}
		String type = updateForm.getType();
		String areaName = updateForm.getAreaName();
		Long airlineCompanyId = updateForm.getAirlineCompanyId();
		Chain chain = Chain.make("updateTime", new Date());
		String str = System.getProperty("java.io.tmpdir");
		if (!Util.isEmpty(url)) {
			chain.add("url", url);
			String extendName = url.substring(url.lastIndexOf(".") + 1, url.length());
			if ("doc".equalsIgnoreCase(extendName) || "docx".equalsIgnoreCase(extendName)) {
				try {
					/*word2Html.docToHtml(url, str + File.separator + "12.html");
					htmlToPdf.htmlConvertToPdf(str + File.separator + "12.html", str + File.separator + "12.pdf");*/
					Office2PDF.urlToFile(url, str + File.separator + "12.pdf");
					File file = new File(str + File.separator + "12.pdf");
					/*File file = new File(str + File.separator + "12.pdf");*/
					String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
							+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
					chain.add("pdfUrl", pdfUrl);
				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				} finally {
					File file1 = new File(str + File.separator + "12.html");
					File file2 = new File(str + File.separator + "12.pdf");
					if (file1.exists()) {
						file1.delete();
					}
					if (file2.exists()) {
						file2.delete();
					}
				}
			} else if ("xls".equalsIgnoreCase(extendName) || "xlsx".equalsIgnoreCase(extendName)) {
				try {
					/*POIReadExcelToHtml poiReadExcelToHtml = new POIReadExcelToHtml();
					poiReadExcelToHtml.excelConvertToPdf(url, str + File.separator + "12.html");*/
					Office2PDF.urlToFile(url, str + File.separator + "12.pdf");
					/*htmlToPdf.htmlConvertToPdf(str + File.separator + "12.html", str + File.separator + "12.pdf");*/
					File file = new File(str + File.separator + "12.pdf");
					String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
							+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
					chain.add("pdfUrl", pdfUrl);
				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				} finally {
					File file1 = new File(str + File.separator + "12.html");
					File file2 = new File(str + File.separator + "12.pdf");
					if (file1.exists()) {
						file1.delete();
					}
					if (file2.exists()) {
						file2.delete();
					}
				}
			}
		}
		if (!Util.isEmpty(fileName)) {
			chain.add("fileName", fileName);
		}
		if (!Util.isEmpty(type)) {
			chain.add("type", type);
		}
		if (!Util.isEmpty(areaName)) {
			chain.add("areaName", areaName);
		}
		if (!Util.isEmpty(airlineCompanyId)) {
			chain.add("airlineCompanyId", airlineCompanyId);
		}
		dbDao.update(TAirlinePolicyEntity.class, chain, Cnd.where("id", "=", id));

	}

	public void downloadById(long id, HttpServletResponse response, String url2, String fileName2,
			HttpServletRequest request) {
		//查看前台接收的是什么编码格式
		String resourceFile = null;
		String fileName = null;
		if (Util.isEmpty(url2) && Util.isEmpty(fileName2)) {

			TAirlinePolicyEntity airlinePolicy = dbDao.fetch(TAirlinePolicyEntity.class, id);
			resourceFile = airlinePolicy.getUrl();
			fileName = airlinePolicy.getFileName();
			/*try {
				fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}*/

		} else {
			resourceFile = url2;
			try {
				fileName = java.net.URLDecoder.decode(fileName2, "UTF-8");
				fileName = new String(fileName.getBytes(), "utf-8");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}
			/*try {
				fileName = java.net.URLDecoder.decode(fileName2, "UTF-8");
				fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}*/
		}
		String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			try {
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}
		} else {
			//非IE浏览器的处理：
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();

			}
		}

		InputStream is = null;
		OutputStream out = null;
		if (!Util.isEmpty(resourceFile)) {
			try {

				URL url = new URL(resourceFile);
				URLConnection connection = url.openConnection();
				is = connection.getInputStream();

				//new String(fileName.getBytes("utf-8"), "ISO8859-1")
				out = response.getOutputStream();
				response.reset();
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("utf-8");
				//response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
				response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
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

				e.printStackTrace();

			} finally {
				try {
					if (!Util.isEmpty(is)) {

						is.close();
					}
				} catch (IOException e) {

					e.printStackTrace();

				}
				try {
					if (!Util.isEmpty(out)) {

						out.close();
					}
				} catch (IOException e) {

					e.printStackTrace();

				}
			}
		}

	}
}
