/**
 * TurnOverViewService.java
 * com.linyun.airline.admin.turnover.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.turnover.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoServiceImpl;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.TurnOverStatusEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TTurnOverEntity;
import com.linyun.airline.forms.TTurnOverAddForm;
import com.linyun.airline.forms.TTurnOverFindForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月4日 	 
 */
@IocBean(name = "turnOverViewService")
public class TurnOverViewService extends BaseService<TTurnOverEntity> {

	/**
	 * list页面的查询
	 * @param session 
	 * @see com.uxuexi.core.web.base.service.BaseService#listPage4Datatables(com.uxuexi.core.web.form.DataTablesParamForm)
	 */
	@Inject
	private externalInfoServiceImpl externalInfoServiceImpl;

	public Map<String, Object> listPage4Datatables(@Param("..") final TTurnOverFindForm findForm,
			DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		/*String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("tu.status", "=", TurnOverStatusEnum.ENABLE.intKey());*/

		String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer status = findForm.getStatus();
		String bankCardName = findForm.getBankCardName();
		String projectName = findForm.getProjectName();
		String operation = findForm.getOperation();
		String currency = findForm.getCurrency();
		Date tradeDate = findForm.getTradeDate();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		String companyName = findForm.getCompanyName();
		if (!Util.isEmpty(status)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.status", "=", status);
		} else {

			cnd.and("tu.status", "=", TurnOverStatusEnum.ENABLE.intKey());
		}

		cnd.and("tb.companyId", "=", company.getId());
		if (!Util.isEmpty(companyName)) {
			cnd.and("tu.companyName", "like", "%" + companyName + "%");
		}
		if (!Util.isEmpty(bankCardName)) {
			cnd.and("tu.bankName", "=", bankCardName);
		}
		if (!Util.isEmpty(projectName)) {

			cnd.and("tu.projectName", "=", projectName);
		}
		if (!Util.isEmpty(operation)) {

			cnd.and("tu.purpose", "=", operation);
		}
		if (!Util.isEmpty(currency)) {

			cnd.and("tu.currency", "=", currency);
		}
		if (!Util.isEmpty(tradeDate)) {

			cnd.and("tu.tradeDate", "=", tradeDate);
		}
		/*cnd.and("tu.companyName", "=", companyName);*/
		/*cnd.orderBy("updateTime", "DESC");*/
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

	/**
	 * 
	 * 添加时的selected框的查询
	 */
	public Map<String, Object> addList(HttpSession session) {
		Map<String, Object> maps = Maps.newHashMap();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		/*Long id = 23L;*/
		//查询有哪些银行卡类型

		List<DictInfoEntity> bankCardTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YHKLX")
				.and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
		//查询有哪些项目
		/*	List<DictInfoEntity> projectList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "FKYT"), null);*/
		List<ComDictInfoEntity> projectList = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId())
						.and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
		//查询有哪些币种类型
		List<DictInfoEntity> currencyList = null;

		try {
			currencyList = externalInfoServiceImpl.findDictInfoByName("", "BZ");
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		//List<DictInfoEntity> currencyList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "BZ"), null);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		cnd.groupBy("cardName");
		List<TBankCardEntity> bankNameList = dbDao.query(TBankCardEntity.class, cnd, null);
		maps.put("bankCardTypeList", bankCardTypeList);
		maps.put("projectList", projectList);
		maps.put("currencyList", currencyList);
		maps.put("bankNameList", bankNameList);
		return maps;
	}

	/*保存流水*/
	public Object addTurnOver(@Param("..") TTurnOverAddForm addForm, final HttpSession session) {
		/**
		 * 获取公司的id
		 * 
		 */
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();

		/*Long companyId = 23l;*/
		//String companyName = company.getComName();
		Long companyNameId = addForm.getCompanyNameId();
		String companyName = null;
		if (!Util.isEmpty(companyNameId)) {

			TCompanyEntity companyAdd = dbDao.fetch(TCompanyEntity.class, companyNameId);
			companyName = companyAdd.getComName();
		} else {
			companyName = company.getComName();

		}
		String cardNum = addForm.getCardNum();
		String bankName = addForm.getBankName();
		String purpose = addForm.getPurpose();
		double money = addForm.getMoney();

		Cnd cnd = Cnd.NEW();
		cnd.and("cardNum", "=", cardNum);
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, cnd, null);
		if (!Util.isEmpty(bankCardList)) {
			addForm.setBankCardId(bankCardList.get(0).getId());
		}
		long backCardId = addForm.getBankCardId();
		//根据id查出银行卡的对象
		TBankCardEntity bankcard = dbDao.fetch(TBankCardEntity.class, backCardId);
		if ("支出".equalsIgnoreCase(purpose)) {
			//根据查出的金额进行更新
			bankcard.setBalance(bankcard.getBalance() - money);

			nutDao.update(bankcard);

		} else if ("收入".equalsIgnoreCase(purpose)) {
			bankcard.setBalance(bankcard.getBalance() + money);
			nutDao.update(bankcard);

		}
		addForm.setHistorymoney(bankcard.getBalance());
		addForm.setCompanyName(companyName);
		addForm.setStatus(TurnOverStatusEnum.ENABLE.intKey());
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());

		FormUtil.add(dbDao, addForm, TTurnOverEntity.class);
		return null;
	}

	/**
	 * 
	 * list页面上的搜索框的查询
	 */
	public Object findList(HttpSession session) {
		Map<String, Object> maps = Maps.newHashMap();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		/*Long id = 23L;*/
		//查询有哪些币种
		List<DictInfoEntity> currencyList = null;

		try {
			currencyList = externalInfoServiceImpl.findDictInfoByName("", "BZ");
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		/*List<DictInfoEntity> projectList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "FKYT"), null);*/
		List<ComDictInfoEntity> projectList = dbDao.query(ComDictInfoEntity.class,
				Cnd.where("comTypeCode", "=", ComDictTypeEnum.DICTTYPE_XMYT.key()).and("comId", "=", company.getId())
						.and("status", "=", DataStatusEnum.ENABLE.intKey()), null);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		cnd.groupBy("cardName");
		List<TBankCardEntity> bankNameList = dbDao.query(TBankCardEntity.class, cnd, null);
		maps.put("currencyList", currencyList);
		maps.put("projectList", projectList);
		maps.put("bankNameList", bankNameList);

		return maps;

	}

	public Object selectTurnOverByCondition(TTurnOverFindForm findForm, DataTablesParamForm sqlParamForm) {

		String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer status = findForm.getStatus();
		String bankCardName = findForm.getBankCardName();
		String projectName = findForm.getProjectName();
		String operation = findForm.getOperation();
		String currency = findForm.getCurrency();
		String companyName = findForm.getCompanyName();
		Date tradeDate = findForm.getTradeDate();
		if (!Util.isEmpty(status)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.status", "=", status);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(companyName)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.companyName", "like", "%" + companyName + "%");
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(bankCardName)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.bankName", "=", bankCardName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(projectName)) {

			/*cnd.and("companyId", "=", companyId);*/
			//cnd.and("tu.status", "=", projectName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(operation)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.purpose", "=", operation);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(currency)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.currency", "=", currency);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(tradeDate)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.tradeDate", "=", tradeDate);
		}
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

	public Object checkBankCardNumEnough(String cardNum, String purpose, double money) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cnd cnd = Cnd.NEW();
		cnd.and("cardNum", "=", cardNum);
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, cnd, null);
		boolean flag = false;
		if ("支出".equalsIgnoreCase(purpose)) {

			if (!Util.isEmpty(bankCardList)) {
				TBankCardEntity bankcard = bankCardList.get(0);
				if (bankcard.getBalance() >= money) {
					flag = true;
				}

			}
		} else if ("收入".equalsIgnoreCase(purpose)) {
			flag = true;
		} else {
			flag = true;
		}
		map.put("valid", flag);
		return map;

	}

	//其他地方验证
	public boolean checkBankCardNumEnoughOther(int bankCardId, String purpose, double money) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cnd cnd = Cnd.NEW();
		cnd.and("id", "=", bankCardId);
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, cnd, null);
		boolean flag = false;
		if ("支出".equalsIgnoreCase(purpose)) {

			if (!Util.isEmpty(bankCardList)) {
				TBankCardEntity bankcard = bankCardList.get(0);
				if (bankcard.getBalance() >= money) {
					flag = true;
				}

			}
		} else if ("收入".equalsIgnoreCase(purpose)) {
			flag = true;
		} else {
			flag = true;
		}

		return flag;

	}

	/*public Object selectCompanys(String findCompany, String companyName) {

		List<TCompanyEntity> list = new ArrayList<TCompanyEntity>();
		if (!Util.isEmpty(companyName)) {

			list = dbDao.query(TCompanyEntity.class, Cnd.where("comName", "like", "%" + companyName + "%"), null);
		}

		return list;

	}*/
	//
	public Object selectCompanys(String findCompany, String companyName) {
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
				op.setText(record.getString("comName"));
				return op;
			}
		});
	}

	/**
	 * 获取公司名称下拉框
	 * @param dictName
	 */
	public List<Record> getCompanyNameList(String findCompany, final String companyName) {
		String sqlString = sqlManager.get("turnoverlist_select2_comName");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("c.comName", "like", Strings.trim(findCompany) + "%");
		if (!Util.isEmpty(companyName)) {
			cnd.and("c.id", "NOT IN", companyName);
		}
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

}
