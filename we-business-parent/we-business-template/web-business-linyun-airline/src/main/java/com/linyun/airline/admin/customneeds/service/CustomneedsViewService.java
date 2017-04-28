package com.linyun.airline.admin.customneeds.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.TempFile;

import com.linyun.airline.admin.customneeds.form.TCustomNeedsSqlForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.util.ExcelReader;
import com.linyun.airline.common.util.ExportExcel;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerneedsEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCustomerneedsAddForm;
import com.linyun.airline.forms.TCustomerneedsUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CustomneedsViewService extends BaseService<TCustomerneedsEntity> {
	private static final Log log = Logs.get();

	private static final String EXCEL_PATH = "download";
	private static final String FILE_EXCEL_NAME = "客户需求导入模板.xlsx";
	private static final String[] EXCEL_COLUMN_TITLE = { "航空公司名称", "去程日期", "去程航段", "回程日期", "回程航段", "人数", "天数", "旅行社名称",
			"联运要求" };
	private static final String EXCEL_TITLE = "客户需求";

	/**
	 *
	 * TODO添加客户需求信息
	 * <p>
	 * TODO客户需求
	 *
	 * @param addForm
	 * @param session 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("deprecation")
	public Object addCustomNeedsInfo(TCustomerneedsAddForm addForm, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		if (!Util.isEmpty(addForm.getLeavedateString())) {
			addForm.setLeavedate(DateUtil.string2Date(addForm.getLeavedateString(), DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(addForm.getBackdateString())) {
			addForm.setBackdate(DateUtil.string2Date(addForm.getBackdateString(), DateUtil.FORMAT_YYYY_MM_DD));
		}
		addForm.setOptime(new Date());
		addForm.setCompanyid(company.getId());
		return this.add(addForm);
	}

	/**
	 * 
	 * TODO修改客户需求信息
	 * <p>
	 * TODO修改客户需求信息
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("deprecation")
	public Object updateCustomNeedsInfo(TCustomerneedsUpdateForm updateForm) {
		if (!Util.isEmpty(updateForm.getLeavedateString())) {
			updateForm.setLeavedate(DateUtil.string2Date(updateForm.getLeavedateString(), DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(updateForm.getBackdateString())) {
			updateForm.setBackdate(DateUtil.string2Date(updateForm.getBackdateString(), DateUtil.FORMAT_YYYY_MM_DD));
		}
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}

	/**
	 * 
	 * TODO关闭客户需求信息
	 * <p>
	 * TODO关闭客户需求信息
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object closeCustomNeeds(long id) {
		TCustomerneedsEntity customerneedsEntity = this.fetch(id);
		customerneedsEntity.setIsclose(1);
		return dbDao.update(customerneedsEntity);
	}

	/**
	 * 
	 * TODO导入Excel文件
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param file
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object inportExcelData(TempFile file, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		try {
			InputStream is = new FileInputStream(file.getFile());
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);
			List<TCustomerneedsEntity> customerneedsEntities = new ArrayList<TCustomerneedsEntity>();
			for (int i = 1; i <= map.size(); i++) {
				String[] row = map.get(i);
				TCustomerneedsEntity customerneeds = new TCustomerneedsEntity();
				//航空公司名称
				customerneeds.setAirline(row[0]);
				//去程日期
				customerneeds.setLeavedate(DateUtil.string2Date(row[1], DateUtil.FORMAT_YYYY_MM_DD));
				//去程航段
				customerneeds.setLeavecity(row[2]);
				//去程航班
				customerneeds.setLeaveflight(row[3]);
				//回程日期
				customerneeds.setBackdate(DateUtil.string2Date(row[4], DateUtil.FORMAT_YYYY_MM_DD));
				//返回城市
				customerneeds.setBackcity(row[5]);
				//回程航班
				customerneeds.setBackflight(row[6]);
				//人数
				customerneeds.setTotalcount(Float.valueOf(row[7]).intValue());
				//天数
				customerneeds.setTotalday(Float.valueOf(row[8]).intValue());
				//旅行社
				customerneeds.setTravel(row[9]);
				//联运要求
				customerneeds.setUniontransport(row[10]);
				//操作时间
				customerneeds.setOptime(new Date());
				//设置操作人所属公司
				customerneeds.setCompanyid(company.getId());
				customerneedsEntities.add(customerneeds);
			}
			//导入数据库
			dbDao.insert(customerneedsEntities);
			//删除备份文件
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();

		}
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * 
	 * 下载客户需求Excel导入模板
	 * <p>
	 * TODO下载客户需求Excel导入模板
	 *
	 * @param request
	 * @param response
	 * @return TODO下载客户需求Excel导入模板
	 * @throws Exception 
	 */
	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			String filepath = request.getServletContext().getRealPath(EXCEL_PATH);
			String path = filepath + File.separator + FILE_EXCEL_NAME;
			File file = new File(path);// path是根据日志路径和文件名拼接出来的
			String filename = file.getName();// 获取日志文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		return null;

	}

	/**
	 * 客户需求导出Excel
	 * <p>
	 * 客户需求导出Excel
	 *
	 * @param response
	 * @param sqlParamForm
	 * @return 客户需求导出Excel
	 */
	public Object exportCustomNeedsExcel(HttpServletResponse response, TCustomNeedsSqlForm sqlParamForm) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd/MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = EXCEL_COLUMN_TITLE;
			//设置Excel表格标题
			String title = EXCEL_TITLE;
			//为Excel准备数据
			String sqlString = EntityUtil.entityCndSql(TCustomerneedsEntity.class);
			Sql sql = Sqls.create(sqlString);
			sql.setCondition(sqlParamForm.cnd());
			sql.setCallback(Sqls.callback.records());
			nutDao.execute(sql);
			@SuppressWarnings("unchecked")
			List<Record> rerultList = (List<Record>) sql.getResult();
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (Record record : rerultList) {
				Object[] obj = { record.get("airline"), df.format(record.get("leavedate")),
						record.get("leavecity") + "-" + record.get("backcity"), df.format(record.get("backdate")),
						record.get("backcity") + "-" + record.get("leavecity"), record.get("totalcount"),
						record.get("totalday"), record.get("travel"), record.get("uniontransport") };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 启用客户需求
	 * <p>
	 * 启用客户需求
	 *
	 * @param id
	 * @return 启用客户需求
	 */
	public Object enableCustomNeeds(long id) {
		TCustomerneedsEntity customerneedsEntity = this.fetch(id);
		customerneedsEntity.setIsclose(0);
		return dbDao.update(customerneedsEntity);

	}
}