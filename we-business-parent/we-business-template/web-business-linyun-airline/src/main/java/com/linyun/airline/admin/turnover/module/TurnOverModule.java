/**
 * TurnOverModule.java
 * com.linyun.airline.admin.turnover.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.turnover.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.turnover.service.TurnOverViewService;
import com.linyun.airline.common.enums.TurnOverStatusEnum;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TTurnOverEntity;
import com.linyun.airline.forms.TTurnOverAddForm;
import com.linyun.airline.forms.TTurnOverFindForm;
import com.linyun.airline.forms.TTurnOverForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月4日 	 
 */
@IocBean
@At("/admin/turnover")
public class TurnOverModule {
	@Inject
	private TurnOverViewService turnOverViewService;

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	/**
	 * 列表的跳转
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TTurnOverForm sqlParamForm, @Param("..") final Pager pager,
			final HttpSession session) {

		return this.turnOverViewService.findList(session);
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final TTurnOverFindForm findForm, @Param("..") final TTurnOverForm sqlForm,
			final HttpSession session) {

		return turnOverViewService.listPage4Datatables(findForm, sqlForm, session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(final HttpSession session) {
		return this.turnOverViewService.addList(session);
	}

	/**
	 * 添加流水
	 * 
	 */
	@At
	@POST
	public Object add(@Param("..") TTurnOverAddForm addForm, final HttpSession session) {
		turnOverViewService.addTurnOver(addForm, session);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 
	 * 根据银行卡的名字查找号码
	 */
	@At
	@POST
	public Object selectCardNum(@Param("bankName") final String bankName) {
		Cnd cnd = Cnd.NEW();
		cnd.and("cardName", "=", bankName);
		cnd.and("status", "=", TurnOverStatusEnum.ENABLE.intKey());
		dbDao.query(TBankCardEntity.class, cnd, null);
		return dbDao.query(TBankCardEntity.class, cnd, null);
	}

	/**
	 * 校验银行卡余额是否足够
	 */
	@At
	@POST
	public Object checkBankCardNumEnough(@Param("cardNum") final String cardNum,
			@Param("purpose") final String purpose, @Param("money") final double money) {
		return turnOverViewService.checkBankCardNumEnough(cardNum, purpose, money);
	}

	/**
	 * 
	 * 根据list搜索框上面的条件搜索流水
	 */
	@At
	@POST
	public Object selectTurnOverByCondition(@Param("..") final TTurnOverForm sqlForm,
			@Param("..") final TTurnOverFindForm findForm) {

		return this.turnOverViewService.selectTurnOverByCondition(findForm, sqlForm);
	}

	/**
	 * 执行'修改操作',将流水状态变为关闭
	 */
	@At
	@POST
	public Object update(@Param("id") final int id) {
		if (!Util.isEmpty(id)) {
			//更新用户表中状态
			dbDao.update(TTurnOverEntity.class, Chain.make("status", TurnOverStatusEnum.DISENABLE.intKey()),

			Cnd.where("id", "=", id));//禁用

			return JsonResult.success("关闭成功");
		}
		return JsonResult.success("关闭失败!");
	}

	/**
	 * 执行'修改操作',将流水状态变为关闭
	 */
	@At
	@POST
	public Object open(@Param("id") final int id) {
		if (!Util.isEmpty(id)) {
			//更新用户表中状态
			dbDao.update(TTurnOverEntity.class, Chain.make("status", TurnOverStatusEnum.ENABLE.intKey()),

			Cnd.where("id", "=", id));//禁用

			return JsonResult.success("开启成功");
		}
		return JsonResult.success("开启失败!");
	}

	/**
	 * 
	 * 根据输入显示公司名称
	 */
	@At
	@POST
	public Object selectCompanys(@Param("p") final String findCompany, @Param("companyName") final String companyName) {

		return this.turnOverViewService.selectCompanys(findCompany, companyName);
	}

}
