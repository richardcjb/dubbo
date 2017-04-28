package com.linyun.airline.admin.authority.authoritymanage.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.authority.authoritymanage.form.TAuthoritySqlForm;
import com.linyun.airline.admin.authority.authoritymanage.service.AuthorityViewService;
import com.linyun.airline.admin.authority.job.form.DeptJobForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * 功能管理	控制类
* @ClassName: AuthorityModule
* @author 崔建斌 
* @date 2016年11月18日 上午11:35:11
 */
@IocBean
@At("/admin/authority/authoritymanage")
public class AuthorityModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	/**
	 * 注入容器中管理sql的对象，用于从sql文件中根据key取得sql
	 */
	@Inject
	private SqlManager sqlManager;

	@Inject
	private AuthorityViewService authorityViewService;

	/**
	 * 分页查询
	 * @param sqlForm 
	 * @param pager 
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TAuthoritySqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TAuthoritySqlForm sqlForm, final HttpSession session) {
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		sqlForm.setComId(companyId);
		return authorityViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 * @throws CloneNotSupportedException 
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(final HttpSession session) throws CloneNotSupportedException {
		return authorityViewService.findCompanyFunctions(CommonConstants.INVALID_DATA_ID, session);
	}

	/**
	 * 添加保存
	 */
	@At
	@POST
	public Object add(@Param("..") DeptJobForm addForm, final HttpSession session) {
		authorityViewService.saveDeptJobData(addForm, session);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 * @throws CloneNotSupportedException 
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final Long deptId, final HttpSession session) {
		return authorityViewService.loadJobJosn(deptId, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") DeptJobForm updateForm, @Param("deptId") final Long deptId,
			final HttpSession session) {
		try {
			authorityViewService.updateJobFunctions(updateForm, deptId, session);
		} catch (Exception e) {
			return JsonResult.error(e.getMessage());
		}
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("jobId") final long jobId, final HttpSession session) {
		//FormUtil.delete(dbDao, TDepartmentEntity.class, jobId);
		authorityViewService.deleteJob(jobId, session);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 校验部门名称
	 */
	@At
	@POST
	public Object checkDeptNameExist(@Param("deptName") final String deptName, @Param("id") final Long deptId,
			final HttpSession session) {
		return authorityViewService.checkDeptNameExist(deptName, deptId, session);
	}

	/**
	 * 校验职位名称
	 */
	@At
	@POST
	public Object checkJobNameExist(@Param("jobName") final String jobName, @Param("id") final Long jobId) {
		return authorityViewService.checkJobNameExist(jobName, jobId);
	}
}