/**
 * InfoModule.java
 * com.xiaoka.template.admin.dictionary.dirinfo.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComLoginNumEntity;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComThirdPayMentEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComAddTypeEnum;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoAddForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoSqlForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoUpdateForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComLoginNumAddForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComLoginNumSqlForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComLoginNumUpdateForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComThirdPayMentAddForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComThirdPayMentSqlForm;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComThirdPayMentUpdateForm;
import com.linyun.airline.admin.companydict.comdictinfo.service.ComInfoDictService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.form.AlterStatusForm;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
@At("/admin/companydict/comdictinfo")
public class ComInfoDictModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private ComInfoDictService comInfoDictService;

	/**
	 * 分页查询
	 * <P>
	 * 
	 * @param sqlForm  查询表单
	 * @param pager    分页对象
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final ComInfoSqlForm sqlForm) {
		Map<String, Object> map = MapUtil.map();
		map.put("dicttypelist", EnumUtil.enum2(ComDictTypeEnum.class));
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	@POST
	public Object comInfoListData(@Param("..") final ComInfoSqlForm sqlForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		sqlForm.setComId(comId);
		return comInfoDictService.comInfoListData(sqlForm);
	}

	/**
	 * @param sqlForm
	 * 登录账号列表数据
	 */
	@At
	public Object loginNumData(@Param("..") final ComLoginNumSqlForm sqlForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		sqlForm.setComId(comId);
		return comInfoDictService.listPage4Datatables(sqlForm);
	}

	/**
	 * 打开登录账号编辑页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateLoginNum(@Param("id") final Long id) {
		Map<String, Object> map = comInfoDictService.updateLoginNum(id);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 保存登录账号编辑操作
	 */
	@At
	@POST
	public Object updateLoginNum(@Param("..") final ComLoginNumUpdateForm updateForm) {
		comInfoDictService.updateLoginNum(updateForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 弹出添加登录账号页面
	 */
	@At
	@GET
	@Ok("jsp")
	public void addLoginNum() {
	}

	/**
	 * TODO 添加登录账号
	 * @param addForm
	 */
	@At
	@POST
	public Object addLoginNum(@Param("..") final ComLoginNumAddForm addForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//获得当前登陆公司id
		addForm.setComId(comId);
		addForm.setComTypeCode(ComDictTypeEnum.DICTTYPE_DLZH.key());//登录账号
		addForm.setCreateTime(new Date());
		String airlineId = addForm.getAirlineName();
		DictInfoEntity dictInfoEntity = dbDao.fetch(DictInfoEntity.class, Cnd.where("id", "=", airlineId));
		addForm.setAirlineName(dictInfoEntity.getDictName());
		addForm.setComDdictCode(dictInfoEntity.getTypeCode());
		FormUtil.add(dbDao, addForm, ComLoginNumEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * @param sqlForm
	 * 第三方支付列表数据
	 */
	@At
	public Object thirdPayMentData(@Param("..") final ComThirdPayMentSqlForm sqlForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		sqlForm.setComId(comId);
		return comInfoDictService.listPage4Datatables(sqlForm);
	}

	/**
	 * 打开第三方支付页面
	 */
	@At
	@GET
	@Ok("jsp")
	public void addThirdPayMent() {
	}

	/**
	 * 添加第三方支付操作
	 * @param addForm
	 * @param request
	 */
	@At
	@POST
	public Object addThirdPayMent(@Param("..") final ComThirdPayMentAddForm addForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//获得当前登陆公司id
		addForm.setComId(comId);
		addForm.setCreateTime(new Date());
		addForm.setComTypeCode(ComDictTypeEnum.DICTTYPE_DSFZF.key());//第三方支付
		FormUtil.add(dbDao, addForm, ComThirdPayMentEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 打开第三方支付编辑页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateThirdPayMent(@Param("id") final Integer id, final HttpSession session) {
		Map<String, Object> map = comInfoDictService.updateThirdPayMnet(id, session);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		return map;
	}

	/**
	 * 编辑保存第三方支付数据
	 */
	@At
	@POST
	public Object updateThirdPayMent(@Param("..") final ComThirdPayMentUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		FormUtil.modify(dbDao, updateForm, ComThirdPayMentEntity.class);
		return JsonResult.success("修改成功!");
	}

	/**
	 * @param airlineName
	 * 区域select2查询
	 */
	@At
	@POST
	public Object airLine(@Param("airline") final String airlineName, @Param("airlineIds") final String airlineIds,
			final HttpSession session) {
		return comInfoDictService.airLine(airlineName, airlineIds, session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return comInfoDictService.getDictTypeName();
	}

	/**
	 * TODO 添加
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object add(@Param("..") final ComInfoAddForm addForm, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		addForm.setComId(companyId);
		addForm.setDictTypeId(addForm.getId());
		addForm.setCreateTime(new Date());
		FormUtil.add(dbDao, addForm, ComDictInfoEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id, final HttpSession session) {
		Map<String, Object> map = comInfoDictService.findDirinfo(id, session);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));
		map.put("comdicttypeenum", EnumUtil.enum2(ComAddTypeEnum.class));
		return map;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final ComInfoUpdateForm modForm) {
		modForm.setUpdateTime(new Date());
		return comInfoDictService.update(modForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, ComDictInfoEntity.class, id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, ComDictInfoEntity.class, ids);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 更新删除状态
	 * TODO(这里用一句话描述这个方法的作用)
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@POST
	public Object updateDeleteStatus(@Param("..") AlterStatusForm form) {
		try {
			dbDao.update(ComDictInfoEntity.class, Chain.make("status", form.getStatus()),
					Cnd.where("id", "=", form.getId()));
			dbDao.update(ComLoginNumEntity.class, Chain.make("status", form.getStatus()),
					Cnd.where("id", "=", form.getId()));
			dbDao.update(ComThirdPayMentEntity.class, Chain.make("status", form.getStatus()),
					Cnd.where("id", "=", form.getId()));
			return JsonResult.success("操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("操作失败!");
		}
	}

	/**
	 * 校验字典代码
	 */
	@At
	@POST
	public Object checkTypeCodeExist(@Param("comTypeCode") final String comTypeCode,
			@Param("comDictCode") final String Code, @Param("id") final long id, final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComDictInfoEntity> listCode = dbDao.query(ComDictInfoEntity.class, Cnd.where("comDictCode", "=", Code)
				.and("comId", "=", comId).and("comTypeCode", "=", comTypeCode), null);
		List<ComDictInfoEntity> listCode2 = dbDao.query(ComDictInfoEntity.class, Cnd.where("comDictCode", "=", Code)
				.and("comId", "=", comId).and("comTypeCode", "=", comTypeCode).and("id", "=", id), null);
		if (!Util.isEmpty(listCode)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listCode2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验登录网址唯一性
	 */
	@At
	@POST
	public Object checkWebURlExist(@Param("webURl") final String webURl, @Param("id") final long id,
			final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComLoginNumEntity> listName = dbDao.query(ComLoginNumEntity.class,
				Cnd.where("webURl", "=", webURl).and("comId", "=", comId), null);
		List<ComLoginNumEntity> listName2 = dbDao.query(ComLoginNumEntity.class,
				Cnd.where("webURl", "=", webURl).and("comId", "=", comId).and("id", "=", id), null);
		if (!Util.isEmpty(listName)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listName2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验银行卡账户唯一性
	 */
	@At
	@POST
	public Object checkBankCardNumExist(@Param("bankCardNum") final String bankCardNum, @Param("id") final long id,
			final HttpSession session) {
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComThirdPayMentEntity> listName = dbDao.query(ComThirdPayMentEntity.class,
				Cnd.where("bankCardNum", "=", bankCardNum).and("comId", "=", comId), null);
		List<ComThirdPayMentEntity> listName2 = dbDao.query(ComThirdPayMentEntity.class,
				Cnd.where("bankCardNum", "=", bankCardNum).and("comId", "=", comId).and("id", "=", id), null);
		if (!Util.isEmpty(listName)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listName2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验字典信息名称
	 */
	@At
	@POST
	public Object checkDictNameExist(@Param("comDictName") final String Name, @Param("id") final long id,
			final HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		List<ComDictInfoEntity> listName = dbDao.query(ComDictInfoEntity.class, Cnd.where("comDictName", "=", Name)
				.and("comId", "=", comId), null);
		List<ComDictInfoEntity> listName2 = dbDao.query(ComDictInfoEntity.class, Cnd.where("comDictName", "=", Name)
				.and("id", "=", id).and("comId", "=", comId), null);
		if (!Util.isEmpty(listName)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listName2)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}
}
