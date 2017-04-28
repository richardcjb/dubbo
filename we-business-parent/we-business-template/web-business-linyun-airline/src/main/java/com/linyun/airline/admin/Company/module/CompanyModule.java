package com.linyun.airline.admin.Company.module;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.SqlManager;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.Company.form.TCompanySqlForm;
import com.linyun.airline.admin.Company.form.TCompanyUserSqlForm;
import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.linyun.airline.forms.TUserAddForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/Company")
public class CompanyModule {

	@Inject
	private CompanyViewService companyViewService;
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

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(HttpSession session) {
		return companyViewService.getUpCompanyAndAgentCount(session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCompanyAddForm addForm, @Param("..") TUserAddForm userAddForm) {
		return companyViewService.addCompany(addForm, userAddForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return companyViewService.getCompanyPageInfo(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCompanyUpdateForm updateForm) {
		try {
			companyViewService.updateCompany(updateForm);
			return JsonResult.success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("修改失败");
		}
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		try {
			companyViewService.deleteById(id);
			return JsonResult.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("删除失败");
		}
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		companyViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 查询公司下员工的列表
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlForm
	 * @param pager
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@Ok("jsp")
	public Object userList(@Param("..") final TCompanyUserSqlForm sqlForm, @Param("..") final Pager pager) {
		Map<String, Object> obj = MapUtil.map();
		obj.put("companyuser", sqlForm);
		List<Record> deplist = companyViewService.getCompanyDepartment(sqlForm.getId());
		obj.put("deplist", deplist);
		return obj;
	}

	/**
	 * 更新删除状态
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@POST
	public Object updateDeleteStatus(@Param("..") TCompanyUpdateForm updateForm) {
		try {
			TCompanyEntity companyEntity = companyViewService.fetch(updateForm.getId());
			companyEntity.setDeletestatus("1");
			dbDao.update(companyEntity);
			return JsonResult.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("删除失败");
		}
	}

	/**
	 * 分页查询公司信息
	 */
	@At
	public Object listData(@Param("..") final TCompanySqlForm paramForm, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		paramForm.setLogincompanyid(company.getId());
		return companyViewService.listPage4Datatables(paramForm);
	}

	/**
	 * 分页查询公司员工
	 */
	@At
	public Object userListData(@Param("..") final TCompanyUserSqlForm paramForm) {
		TCompanyEntity company = companyViewService.fetch(paramForm.getId());
		paramForm.setAdminid(company.getAdminId());
		return companyViewService.listPage4Datatables(paramForm);
	}

	/**
	 * 验证公司名称唯一
	 */
	@At
	@POST
	public Object validatorCompanyName(@Param("comName") String comName) {
		return companyViewService.validatorCompanyName(comName);
	}

	/**
	 * 异步加载公司数量
	 */
	@At
	@POST
	public Object loadCompanyCount(HttpSession session) {
		return companyViewService.getUpCompanyAndAgentCount(session);
	}

	/**
	 * 移除员工
	 */
	@At
	public Object removeUser(@Param("id") final long id) {
		return companyViewService.removeUser(id);
	}
}
