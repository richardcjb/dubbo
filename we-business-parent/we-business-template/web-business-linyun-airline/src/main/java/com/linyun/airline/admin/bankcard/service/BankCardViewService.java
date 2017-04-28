/**
 * BankCardViewService.java
 * com.linyun.airline.admin.bankcard.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.bankcard.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.Param;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.dictionary.external.externalInfoServiceImpl;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TBankCardAddForm;
import com.linyun.airline.forms.TBankCardUpdateForm;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.form.DataTablesParamForm;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月2日 	 
 */
@IocBean
public class BankCardViewService extends BaseService<TBankCardEntity> {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;
	@Inject
	private externalInfoServiceImpl externalInfoServiceImpl;

	public Map<String, Object> list() {
		Map<String, Object> maps = Maps.newHashMap();
		//查询有哪些银行
		List<DictInfoEntity> bankList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YH"), null);
		//查询有哪些银行卡类型
		/*List<DictInfoEntity> bankCardTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YHKLX"),
				null);*/
		List<DictInfoEntity> bankCardTypeList = null;
		//查询有哪些币种
		List<DictInfoEntity> currencyList = null;

		try {
			bankCardTypeList = externalInfoServiceImpl.findDictInfoByName("", "YHKLX");
			currencyList = externalInfoServiceImpl.findDictInfoByName("", "BZ");
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		//List<DictInfoEntity> moneyTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "BZ"), null);
		maps.put("bankList", bankList);
		maps.put("bankCardTypeList", bankCardTypeList);
		maps.put("moneyTypeList", currencyList);
		return maps;
	}

	public Object updateBankCardinfo(int userId, HttpSession session) {

		//按id查询
		Map<String, Object> obj = this.list();
		TBankCardEntity bankCardInfo = dbDao.fetch(TBankCardEntity.class, userId);
		DictInfoEntity dictInfoEntity = dbDao.fetch(DictInfoEntity.class,
				Cnd.where("dictName", "=", bankCardInfo.getBankName()));
		obj.put("bankCardInfo", bankCardInfo);
		obj.put("dictInfoEntity", dictInfoEntity);
		return obj;

	}

	/**
	 * (non-Javadoc)
	 * @param session 
	 * @see com.uxuexi.core.web.base.service.BaseService#listPage4Datatables(com.uxuexi.core.web.form.DataTablesParamForm)
	 */

	public Map<String, Object> listPage4Datatables(DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		String sqlString = sqlManager.get("bankcardmanager_find_money");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		/*Long companyId = 23l;*/
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		sql.setCondition(cnd);
		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> re = MapUtil.map();
		re.put("data", list);
		re.put("draw", sqlParamForm.getDraw());
		re.put("recordsTotal", pager.getPageSize());
		re.put("recordsFiltered", pager.getRecordCount());
		return re;

	}

	/*保存银行卡*/
	public Object addBankCard(@Param("..") TBankCardAddForm addForm, final HttpSession session) {
		/**
		 * 获取公司的id
		 * 
		 */
		String bankNameId = addForm.getBankName();
		DictInfoEntity di = dbDao.fetch(DictInfoEntity.class, Long.valueOf(bankNameId));
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		//Long companyId = 23l;
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		addForm.setCompanyId(companyId);
		addForm.setInitialAmount(addForm.getBalance());
		addForm.setStatus(BankCardStatusEnum.ENABLE.intKey());
		addForm.setBankName(di.getDictName());
		addForm.setBankNameId(di.getId());
		FormUtil.add(dbDao, addForm, TBankCardEntity.class);
		return null;
	}

	public Object updateData(TBankCardUpdateForm updateForm, HttpSession session) {

		TBankCardEntity bankCard = dbDao.fetch(TBankCardEntity.class, updateForm.getId());
		String bankNameId = updateForm.getBankName();
		DictInfoEntity di = null;
		if (!Util.isEmpty(bankNameId)) {

			di = dbDao.fetch(DictInfoEntity.class, Long.valueOf(bankNameId));
		}
		Map<String, Object> obj = Maps.newHashMap();
		if (!Util.isEmpty(updateForm)) {
			updateForm.setStatus(BankCardStatusEnum.ENABLE.intKey());
			updateForm.setUpdateTime(new Date());
		}
		if (!Util.isEmpty(updateForm.getBankName())) {
			updateForm.setBankName(di.getDictName());
			updateForm.setBankNameId(di.getId());
		}
		BeanUtil.copyProperties(updateForm, bankCard);
		updateIgnoreNull(bankCard);//更新银行卡表中的数据
		return obj;
	}

	public Object deleteUserInfo(int bankCardId) {

		TBankCardEntity bankCard = dbDao.fetch(TBankCardEntity.class, bankCardId);
		//校验,如果银行卡状态是处于启用,那么更新银行卡中用户的状态为禁用
		if (!Util.isEmpty(bankCard)) {
			//更新用户表中状态
			nutDao.update(TBankCardEntity.class, Chain.make("status", BankCardStatusEnum.DISENABLE.intKey()),

			Cnd.where("id", "=", bankCardId));//禁用

			return JsonResult.success("删除成功");
		}
		return JsonResult.error("删除失败");

	}

	//校验银行卡号
	public Object checkBankCardNumExist(String cardNum, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(id)) {
			//添加时校验
			count = nutDao.count(TBankCardEntity.class, Cnd.where("cardNum", "=", cardNum));
		} else {
			//更新时校验
			count = nutDao.count(TBankCardEntity.class, Cnd.where("cardNum", "=", cardNum).and("id", "!=", id));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//银行名称select2
	public Object selectBankCardNames(String findCompany, String companyName) {
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
				op.setText(record.getString("dictName"));
				return op;
			}
		});
	}

	/**
	 * 获取公司名称下拉框
	 * @param dictName
	 */
	public List<Record> getCompanyNameList(String findCompany, final String companyName) {
		String sqlString = sqlManager.get("bankcardmanager_find_bankName");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("dictName", "like", "%" + Strings.trim(findCompany) + "%");
		cnd.and("typeCode", "=", "YH");
		cnd.and("status", "=", DataStatusEnum.ENABLE.intKey());
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

}
