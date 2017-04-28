/**
 * AirlinePolicyService.java
 * com.linyun.airline.admin.airlinepolicy.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.salary.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TSalaryEntity;
import com.linyun.airline.forms.TSalaryFindForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  孙斌
 * @Date	 2017年3月8日 	 
 */
@IocBean(name = "salaryViewService")
public class SalaryViewService extends BaseService<TSalaryEntity> {

	public Map<String, Object> listPage4Datatables(@Param("..") final TSalaryFindForm findForm,
			DataTablesParamForm sqlParamForm, final HttpSession session, HttpServletRequest request) {
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		String sqlString = sqlManager.get("salary_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer year = findForm.getYear();
		Integer month = findForm.getMonth();
		String drawer = findForm.getDrawer();
		if (!Util.isEmpty(year)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("years", "=", year);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(month)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("months", "=", month);
			//sql.setCondition(cnd);
		}

		if (!Util.isEmpty(drawer)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("drawer", "=", drawer);
			//sql.setCondition(cnd);
		}
		cnd.and("comId", "=", comId);
		cnd.orderBy("updateTime", "DESC");

		sql.setCondition(cnd);
		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		Map<String, Object> re = MapUtil.map();
		//根据年查月份
		/*if (!Util.isEmpty(year)) {

			String monthsString = sqlManager.get("salary_months");
			Sql monthsSql = Sqls.create(monthsString);
			Cnd cnd1 = Cnd.NEW();
			cnd1.and("comId", "=", comId);
			cnd1.and("ms.years", "=", year);
			monthsSql.setCondition(cnd1);
			List<Record> monthsList = dbDao.query(monthsSql, cnd1, null);
			re.put("monthsList1", monthsList);
			session.setAttribute("monthsList1", monthsList);
		}*/
		re.put("data", list);
		re.put("draw", sqlParamForm.getDraw());
		re.put("recordsTotal", pager.getPageSize());
		re.put("recordsFiltered", pager.getRecordCount());
		return re;

	}

	public Map<String, Object> selectCondition(HttpSession session) {
		String drawerString = sqlManager.get("salary_drawer");
		Sql drawerSql = Sqls.create(drawerString);
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		Cnd cnd = Cnd.NEW();
		cnd.and("comId", "=", companyId);
		drawerSql.setCondition(cnd);

		List<Record> drawerList = dbDao.query(drawerSql, cnd, null);
		String yearsString = sqlManager.get("salary_years");
		Sql yearsSql = Sqls.create(yearsString);
		yearsSql.setCondition(cnd);
		List<Record> yearsList = dbDao.query(yearsSql, cnd, null);
		String monthsString = sqlManager.get("salary_months");
		Sql monthsSql = Sqls.create(monthsString);
		monthsSql.setCondition(cnd);
		List<Record> monthsList = dbDao.query(monthsSql, cnd, null);
		Map<String, Object> re = MapUtil.map();
		re.put("drawerList", drawerList);
		re.put("yearsList", yearsList);
		re.put("monthsList", monthsList);
		return re;

	}

	public void addSalary() {
		String stringSql2 = sqlManager.get("salary_add_no");
		Sql sql2 = Sqls.create(stringSql2);
		addSalary1(sql2);
		String stringSql = sqlManager.get("salary_add");
		Sql sql = Sqls.create(stringSql);
		addSalary1(sql);
	}

	public void addSalary1(Sql sql2) {

		/**
		 * 获取公司的id
		 * 
		 */
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();*/
		/*String stringSql = sqlManager.get("salary_add");*/
		Sql sql = sql2;
		/*	Cnd cnd = Cnd.NEW();
			cnd.and("comId", "=", companyId);
			cnd.and("month", "=", "month(now())");
			cnd.and("year", "=", "year(now())");
			sql.setCondition(cnd);*/
		List<TSalaryEntity> salaryList = DbSqlUtil.query(dbDao, TSalaryEntity.class, sql);
		if (salaryList.size() > 0) {
			for (TSalaryEntity tSalary : salaryList) {
				tSalary.setActualCommission((tSalary.getIncomeTotal() - tSalary.getCostTotal())
						* tSalary.getCommission() / 100);
				double basePay = tSalary.getBasePay();
				double ActualCommission = tSalary.getActualCommission();
				double HeadCount = tSalary.getHeadCount();
				//计算工资相关
				tSalary.setSalaryTotal(basePay + ActualCommission + HeadCount);
				tSalary.setUpdateTime(new Date());
				tSalary.setCreateTime(new Date());
			}
			/*if (salaryList.size() > 0) {
				dbDao.insert(salaryList);
			}*/
			Calendar calendar = Calendar.getInstance();

			for (TSalaryEntity tSalaryEntity : salaryList) {
				Cnd cnd1 = Cnd.NEW();
				cnd1.and("comId", "=", tSalaryEntity.getComId());
				Date a = tSalaryEntity.getCreateTime();
				calendar.setTime(a);
				cnd1.and("month(createTime)", "=", calendar.get(Calendar.MONTH) + 1);
				cnd1.and("year(createTime)", "=", calendar.get(Calendar.YEAR));
				cnd1.and("drawerId", "=", tSalaryEntity.getDrawerId());
				TSalaryEntity s = dbDao.fetch(TSalaryEntity.class, cnd1);
				if (!Util.isEmpty(s)) {
					//说明存在记录，更新

					Chain chain = Chain.make("updateTime", new Date());
					chain.add("costTotal", tSalaryEntity.getCostTotal());
					chain.add("groupNumber", tSalaryEntity.getGroupNumber());
					chain.add("incomeTotal", tSalaryEntity.getIncomeTotal());
					chain.add("headCount", tSalaryEntity.getHeadCount());
					chain.add("drawer", tSalaryEntity.getDrawer());
					chain.add("basePay", tSalaryEntity.getBasePay());
					chain.add("commission", tSalaryEntity.getCommission());
					chain.add("comId", tSalaryEntity.getComId());
					chain.add("drawerId", tSalaryEntity.getDrawerId());
					chain.add("actualCommission", tSalaryEntity.getActualCommission());
					chain.add("salaryTotal", tSalaryEntity.getSalaryTotal());
					chain.add("updateTime", new Date());

					dbDao.update(TSalaryEntity.class, chain, Cnd.where("id", "=", s.getId()));
				} else {
					//说明不存在，添加
					dbDao.insert(tSalaryEntity);
				}

			}
		}

		/*for (TSalaryEntity tSalaryEntity : salaryList) {
			dbDao.insert(tSalaryEntity);
			System.out.println("====");
		}*/
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();*/
		/*		Long companyId = 23l;
				addForm.setCreateTime(new Date());
				addForm.setUpdateTime(new Date());
				double actualCommission = 124563;
				addForm.setActualCommission(actualCommission);
				double basePay = 0;
				addForm.setBasePay(basePay);
				String commission = null;
				addForm.setCommission(commission);
				double costTotal = 0;
				addForm.setCostTotal(costTotal);
				String drawer = null;
				addForm.setDrawer(drawer);
				int groupNumber = 0;
				addForm.setGroupNumber(groupNumber);
				int headCount = 0;
				addForm.setHeadCount(headCount);
				double incomeTotal = 0;
				addForm.setIncomeTotal(incomeTotal);
				String remark = null;
				addForm.setRemark(remark);
				double salaryTotal = 0;
				addForm.setSalaryTotal(salaryTotal);

				FormUtil.add(dbDao, addForm, TSalaryEntity.class);*/
	}

}
