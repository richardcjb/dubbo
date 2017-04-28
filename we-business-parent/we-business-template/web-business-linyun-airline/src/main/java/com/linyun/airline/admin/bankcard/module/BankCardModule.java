/**
 * BankCardModule.java
 * com.linyun.airline.admin.bankcard.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.bankcard.module;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.bankcard.service.BankCardViewService;
import com.linyun.airline.admin.dictionary.external.externalInfoServiceImpl;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.forms.TBankCardAddForm;
import com.linyun.airline.forms.TBankCardForm;
import com.linyun.airline.forms.TBankCardUpdateForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月2日 	 
 */
@IocBean
@At("/admin/bankcard")
public class BankCardModule {
	@Inject
	private BankCardViewService bankCardViewService;
	@Inject
	private externalInfoServiceImpl externalInfoServiceImpl;
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
	public Object list(@Param("..") final TBankCardForm sqlParamForm, @Param("..") final Pager pager) {

		return null;
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final TBankCardForm sqlForm, final HttpSession session) {

		return bankCardViewService.listPage4Datatables(sqlForm, session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return this.bankCardViewService.list();
	}

	/**
	 * 添加银行卡
	 * 
	 */
	@At
	@POST
	public Object add(@Param("..") TBankCardAddForm addForm, final HttpSession session) {
		bankCardViewService.addBankCard(addForm, session);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 校验银行卡号
	 */
	@At
	@POST
	public Object checkBankCardNumExist(@Param("cardNum") final String cardNum, @Param("id") final String id) {
		return bankCardViewService.checkBankCardNumExist(cardNum, id);
	}

	/**
	 * 添加时查询有哪些银行
	 */
	@At
	public Object selectBankName() {
		//查询有哪些银行
		return dbDao.query(DictInfoEntity.class,
				Cnd.where("typeCode", "=", "YH").and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
	}

	/**
	 * 添加时查询有哪些银行卡类型
	 */
	@At
	public Object selectBankCardType() {
		//查询有哪些银行卡类型
		return dbDao.query(DictInfoEntity.class,
				Cnd.where("typeCode", "=", "YHKLX").and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
	}

	/**
	 * 添加时查询有哪些币种
	 */
	@At
	public Object selectCurrency() {
		//查询有哪些币种
		List<DictInfoEntity> currencyList = null;

		try {
			currencyList = externalInfoServiceImpl.findDictInfoByName("", "BZ");
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return currencyList;
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final int id, final HttpSession session) {
		return bankCardViewService.updateBankCardinfo(id, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final TBankCardUpdateForm updateForm, final HttpSession session) {
		bankCardViewService.updateData(updateForm, session);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("bankCardId") final int bankCardId) {
		bankCardViewService.deleteUserInfo(bankCardId);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 
	 * 根据输入显示公司名称
	 */
	@At
	@POST
	public Object selectBankCardNames(@Param("p") final String findCompany,
			@Param("companyName") final String companyName) {

		return this.bankCardViewService.selectBankCardNames(findCompany, companyName);
	}
}
