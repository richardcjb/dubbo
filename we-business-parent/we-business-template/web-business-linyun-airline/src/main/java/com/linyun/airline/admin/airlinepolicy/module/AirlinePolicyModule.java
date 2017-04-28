/**
 * AirlinePolicyModule.java
 * com.linyun.airline.admin.airlinepolicy.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.airlinepolicy.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.airlinepolicy.service.AirlinePolicyService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TAirlinePolicyAddForm;
import com.linyun.airline.forms.TAirlinePolicyFindForm;
import com.linyun.airline.forms.TAirlinePolicyForm;
import com.linyun.airline.forms.TAirlinePolicyUpdateForm;
import com.uxuexi.core.common.util.FileUtil;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月8日 	 
 */
@IocBean
@At("/admin/airlinepolicy")
public class AirlinePolicyModule {

	@Inject
	private UploadService qiniuUploadService;//文件上传
	@Inject
	private AirlinePolicyService airlinePolicyService;

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final Pager pager, final HttpSession session) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		/*airlinePolicyService.findConditionList()*/
		return airlinePolicyService.findConditionList(session);
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final TAirlinePolicyForm sqlForm, final HttpSession session,
			TAirlinePolicyFindForm findForm) {
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		sqlForm.setCompanyId(companyId);
		return airlinePolicyService.listPage4Datatables(sqlForm);
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
		return url;
	}

	/**
	 * 保存上传文件
	 * @param addForm
	 */
	/*	@At
		public Object saveUploadFile(@Param("..") TAirlinePolicyAddForm addForm) {

			return airlinePolicyService.addFile(addForm);
		}*/

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		airlinePolicyService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return this.airlinePolicyService.goAdd();
	}

	/**
	 * 添加文件信息
	 * 
	 * 
	 */
	@At
	@POST
	public Object add(@Param("..") TAirlinePolicyAddForm addForm, final HttpSession session) {
		airlinePolicyService.addFileInfo(addForm, session);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 
	 * 根据输入显示航空公司名称
	 */
	@At
	@POST
	public Object selectAirlineCompanys(@Param("p") final String findCompany,
			@Param("companyName") final String companyName) {

		return this.airlinePolicyService.selectAirlineCompanys(findCompany, companyName);
	}

	/**
	 * 
	 * 根据输入显示地区名称
	 */
	@At
	@POST
	public Object selectArea(@Param("p") final String findCompany, @Param("companyName") final String companyName,
			final HttpSession session) {

		return this.airlinePolicyService.selectArea(findCompany, companyName, session);
	}

	/**
	 * 跳转到'编辑操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") Long id) {
		return this.airlinePolicyService.goUpdate(id);
	}

	/**
	 * 添加文件信息
	 * 
	 * 
	 */
	@At
	@POST
	public Object update(@Param("..") TAirlinePolicyUpdateForm updateForm, final HttpSession session,
			@Param("id") Long id) {
		airlinePolicyService.updateFileInfo(updateForm, session, id);
		return JsonResult.success("更新成功!");
	}

	/**
	 * 文件下载
	 */
	@At
	public Object download(@Param("id") final long id, final HttpServletResponse response,
			@Param("url") final String url, @Param("fileName") final String fileName, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		airlinePolicyService.downloadById(id, response, url, fileName, request);
		return null;
	}
}
