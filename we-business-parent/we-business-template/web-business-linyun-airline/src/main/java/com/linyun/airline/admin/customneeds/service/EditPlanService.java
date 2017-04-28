/**
 * EditPlanService.java
 * com.linyun.airline.admin.customneeds.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.admin.customneeds.form.CityAirlineJson;
import com.linyun.airline.admin.customneeds.form.EditPlanSqlForm;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.ExportExcel;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TPlanInfoEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUpOrderTicketEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TPlanInfoUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO编辑计划
 * <p>
 * TODO编辑计划service
 *
 * @author   刘旭利
 * @Date	 2016年12月20日 	 
 */
@IocBean
public class EditPlanService extends BaseService<TPlanInfoEntity> {

	private static final Log log = Logs.get();
	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private CompanyViewService companyViewService;
	//旅行社字典代码
	private static final String TRAVELCODE = "LXS";
	//航班号字典代码
	private static final String AIRLINECODE = "HBH";
	//城市字典代码
	private static final String CITYCODE = "CFCS";
	//航空公司字典代码
	private static final String AIRCOMCODE = "HKGS";
	private static final String QUANGUOLIANYUN = "全国联运";
	//表格标题
	private static final String EDITPLAN_EXCEL_TITLE = "编辑计划";
	//表格列标题
	private static final String[] DEDITPLAN_COLUM_TITLE = { "序号", "订单号", "航空公司", "去程日期", "出发城市", "回程日期", "抵达城市", "人数",
			"天数", "旅行社", "联运要求" };

	/**
	 * 导出编辑计划Excel模板
	 * <p>
	 * 出编辑计划Excel模板
	 *
	 * @param sqlForm
	 * @param session
	 * @param response
	 * @return TODO出编辑计划Excel模板
	 */
	public Object exportEditPlanExcel(EditPlanSqlForm sqlForm, HttpSession session, HttpServletResponse response) {
		try {
			//获取当前公司
			TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
			sqlForm.setCompanyid(company.getId());
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd/MMM", Locale.ENGLISH);
			//导出Excel标题
			String title = this.EDITPLAN_EXCEL_TITLE;
			//导出Excel的列标题
			String[] rowName = this.DEDITPLAN_COLUM_TITLE;
			//查询数据
			List<Record> query = dbDao.query(sqlForm.sql(sqlManager), null, null);
			//准备导出Excel的数据
			List<Object[]> dataList = new ArrayList<Object[]>();
			//序号
			int num = 1;
			for (Record record : query) {
				Object[] obj = { num, record.get("dingdanhao"), record.get("airlinename"),
						df.format(record.get("leavesdate")), record.get("leavescity"),
						df.format(record.get("backsdate")), record.get("backscity"), record.get("peoplecount"),
						record.get("dayscount"), record.get("travelname"), record.get("unioncity") };
				dataList.add(obj);
				num++;
			}
			//创建Excel表格对象
			ExportExcel excel = new ExportExcel(title, rowName, dataList, response);
			//导出Excel
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到编辑计划页面
	 * <p>
	 * 跳转到编辑计划页面
	 *
	 * @param id
	 * @param session 
	 * @return TODO跳转到编辑计划页面
	 */
	public Object editplanpage(long id, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//准备回显数据
			TPlanInfoEntity planInfoEntity = this.fetch(id);
			//获取关系表数据
			//TUpOrderTicketEntity orderTicket = dbDao.fetch(TUpOrderTicketEntity.class, Cnd.where("ticketid", "=", id));
			if (!Util.isEmpty(planInfoEntity.getOrdernumber())) {
				TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(planInfoEntity.getOrdernumber()));
				result.put("ordernum", order.getOrdersnum());
			} else {
				result.put("ordernum", null);
			}
			//获取订单信息
			result.put("planinfo", planInfoEntity);
			//获取航段信息
			List<TAirlineInfoEntity> airlineinfo = dbDao.query(TAirlineInfoEntity.class, Cnd.where("planid", "=", id)
					.orderBy("leavedate", "asc"), null);
			result.put("airlineinfo", airlineinfo);
			//准备城市下拉数据
			//List<DictInfoEntity> city = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", CITYCODE), null);
			List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
			//旅行社下拉
			//上游公司信息
			TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", company.getId()));
			result.put("travel",
					dbDao.query(TCustomerInfoEntity.class, Cnd.where("upComId", "=", upcompany.getId()), null));
			//航空公司下拉
			//result.put("aircom", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
			//城市下拉
			result.put("city", city);
			//航班号下拉
			result.put("airline", externalInfoService.findDictInfoByText("", AIRLINECODE));
			//准备联运城市下拉数据
			TDepartureCityEntity dictinfo = new TDepartureCityEntity();
			dictinfo.setId(0);
			dictinfo.setDictCode(QUANGUOLIANYUN);
			city.add(0, dictinfo);
			//联运下拉
			result.put("union", city);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * 编辑计划
	 * <p>
	 * 编辑计划
	 * @param updateForm
	 * @return 编辑计划
	 */
	public Object updateEditPlan(TPlanInfoUpdateForm updateForm) {
		TPlanInfoEntity planInfoEntity = this.fetch(updateForm.getId());
		//planInfoEntity.setAirlinename(updateForm.getAirlinename());
		planInfoEntity.setTravelname(updateForm.getTravelname());
		planInfoEntity.setPeoplecount(updateForm.getPeoplecount());
		planInfoEntity.setDayscount(updateForm.getDayscount());
		planInfoEntity.setUnioncity(updateForm.getUnioncity());
		//页面获取的数据转换为list<扩展类>
		List<CityAirlineJson> airlineJson = JsonUtil.fromJsonAsList(CityAirlineJson.class,
				updateForm.getCityairlinejson());
		//更新多航段
		//查询之前的list
		List<TAirlineInfoEntity> before = dbDao.query(TAirlineInfoEntity.class,
				Cnd.where("planid", "=", planInfoEntity.getId()), null);
		//组装list
		List<TAirlineInfoEntity> airlines = new ArrayList<TAirlineInfoEntity>();
		//添加多个航段
		for (CityAirlineJson cityAirlineJson : airlineJson) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(cityAirlineJson.getLeavescity());
			airline.setArrvicity(cityAirlineJson.getBackscity());
			airline.setAilinenum(cityAirlineJson.getLeaveairline());
			airline.setPlanid(planInfoEntity.getId());
			if (!Util.isEmpty(cityAirlineJson.getSetoffdate())) {
				airline.setLeavedate(DateUtil.string2Date(cityAirlineJson.getSetoffdate(), DateUtil.FORMAT_YYYY_MM_DD));
			}
			//添加时间
			if (!Util.isEmpty(cityAirlineJson.getSetofftime())) {
				String[] offtimes = cityAirlineJson.getSetofftime().split("/");
				if (offtimes.length > 0) {
					if (!Util.isEmpty(offtimes[0])) {
						airline.setLeavetime(offtimes[0]);
					}
					if (!Util.isEmpty(offtimes[1])) {
						airline.setArrivetime(offtimes[1]);
					}
				}
			}
			airlines.add(airline);
		}
		dbDao.updateRelations(before, airlines);
		if (!Util.isEmpty(planInfoEntity.getOrdernumber())) {
			//客户信息
			List<TCustomerInfoEntity> customInfo = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("shortName", "=", planInfoEntity.getTravelname()), null);
			if (!Util.isEmpty(customInfo) && !Util.isEmpty(planInfoEntity.getOrdernumber())) {
				TUpOrderEntity upOrderEntity = dbDao.fetch(TUpOrderEntity.class,
						Long.valueOf(planInfoEntity.getOrdernumber()));
				upOrderEntity.setUserid(new Long(customInfo.get(0).getId()).intValue());
				dbDao.update(upOrderEntity);
				TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class,
						Cnd.where("orderid", "=", upOrderEntity.getId()));
				//获取pnr信息
				if (!Util.isEmpty(pnrinfo.getId())) {
					List<TAirlineInfoEntity> ailineids = dbDao.query(TAirlineInfoEntity.class,
							Cnd.where("planid", "=", planInfoEntity.getId()), null);
					//设置航段信息为主航段
					List<TAirlineInfoEntity> updateairline = new ArrayList<TAirlineInfoEntity>();
					for (TAirlineInfoEntity airline : ailineids) {
						airline.setPnrid(pnrinfo.getId());
						updateairline.add(airline);
					}
					dbDao.update(updateairline);
				}
			}
		}
		return dbDao.update(planInfoEntity);
	}

	/**
	 * 单个关闭计划
	 * <p>
	 * 单个关闭计划
	 * @param id
	 * @return 单个关闭计划
	 */
	public Object closeEditPlan(long id) {
		TPlanInfoEntity planInfoEntity = this.fetch(id);
		planInfoEntity.setIsclose(1);
		return dbDao.update(planInfoEntity);
	}

	/**
	 * 批量关闭计划
	 * <p>
	 * 批量关闭计划
	 *
	 * @param ids
	 * @return 批量关闭计划
	 */
	public Object editPlanService(String ids) {
		List<TPlanInfoEntity> updatelist = new ArrayList<TPlanInfoEntity>();
		List<TPlanInfoEntity> planinfos = dbDao.query(TPlanInfoEntity.class, Cnd.where("id", "in", ids), null);
		for (TPlanInfoEntity tPlanInfo : planinfos) {
			tPlanInfo.setIsclose(1);
			updatelist.add(tPlanInfo);
		}
		return dbDao.update(updatelist);
	}

	/**
	 * 启用计划
	 * <p>
	 * 启用计划
	 * @param id
	 * @return 启用计划
	 */
	public Object enableEditPlan(long id) {
		TPlanInfoEntity planInfoEntity = this.fetch(id);
		planInfoEntity.setIsclose(0);
		return dbDao.update(planInfoEntity);
	}

	/**
	 * 生成订单
	 * <p>
	 * 生成订单
	 *
	 * @return 生成订单
	 */
	public synchronized Object insertOrderNum(HttpSession session, String planIds) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//格式化日期
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(new Date());
		//查询当前天订单号最大的一条
		Iterable<String> split = Splitter.on(",").split(planIds);
		for (String str : split) {
			long planId = Long.valueOf(str);
			//查询当前记录
			TPlanInfoEntity planinfo = this.fetch(planId);
			//客户信息
			List<TCustomerInfoEntity> customInfo = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("shortName", "=", planinfo.getTravelname()), null);
			List<TUpOrderTicketEntity> query = dbDao.query(TUpOrderTicketEntity.class,
					Cnd.where("ticketid", "=", planId), null);
			//如果不存在订单号则生成
			//获取计划信息
			TPlanInfoEntity planInfo = this.fetch(planId);
			if (Util.isEmpty(planinfo.getOrdernumber())) {
				//如果当前天最大值存在
				TUpOrderEntity insertOrder = new TUpOrderEntity();
				//订单信息
				TUpOrderEntity upOrderEntity = new TUpOrderEntity();
				upOrderEntity.setLoginUserId(new Long(user.getId()).intValue());
				upOrderEntity.setAmount(planInfo.getPrice());
				upOrderEntity.setCurrencyCode(planInfo.getCurrencycode());
				upOrderEntity.setCustomid(company.getId());
				upOrderEntity.setOrdersnum(generateOrderNum());
				upOrderEntity.setOrdersstatus(InternationalStatusEnum.SEARCH.intKey());
				upOrderEntity.setOrderstime(new Date());
				upOrderEntity.setOrderstype(OrderTypeEnum.TEAM.intKey());
				upOrderEntity.setCompanyId(new Long(company.getId()).intValue());
				upOrderEntity.setPeoplecount(planinfo.getPeoplecount());
				if (!Util.isEmpty(customInfo))
					upOrderEntity.setUserid(new Long(customInfo.get(0).getId()).intValue());
				insertOrder = dbDao.insert(upOrderEntity);
				//更新pnr、设置主航段
				TPnrInfoEntity pnrinfo = new TPnrInfoEntity();
				pnrinfo.setOrderid(insertOrder.getId());
				pnrinfo.setMainsection(1);
				TPnrInfoEntity insertPnr = dbDao.insert(pnrinfo);
				List<TAirlineInfoEntity> ailineids = dbDao.query(TAirlineInfoEntity.class,
						Cnd.where("planid", "=", planinfo.getId()), null);
				//设置航段信息
				List<TAirlineInfoEntity> updateairline = new ArrayList<TAirlineInfoEntity>();
				for (TAirlineInfoEntity airline : ailineids) {
					airline.setPnrid(insertPnr.getId());
					updateairline.add(airline);
				}
				dbDao.update(updateairline);
				//设置订单ID
				planinfo.setOrdernumber(String.valueOf(insertOrder.getId()));
				dbDao.update(planinfo);
			} else {
				TUpOrderEntity fetch = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(planinfo.getOrdernumber()));
				fetch.setAmount(planInfo.getPrice());
				fetch.setCurrencyCode(planInfo.getCurrencycode());
				fetch.setCustomid(company.getId());
				fetch.setOrdersnum(generateOrderNum());
				fetch.setOrdersstatus(InternationalStatusEnum.SEARCH.intKey());
				fetch.setOrderstime(new Date());
				fetch.setOrderstype(OrderTypeEnum.TEAM.intKey());
				fetch.setCompanyId(new Long(company.getId()).intValue());
				fetch.setPeoplecount(planinfo.getPeoplecount());
				if (!Util.isEmpty(customInfo)) {
					fetch.setUserid(new Long(customInfo.get(0).getId()).intValue());
				}
				dbDao.update(fetch);
			}
		}
		return 1;
	}

	/**
	 * 获取订单号
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String generateOrderNum() {
		String ordernum = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(new Date());
		String sqlStr = sqlManager.get("select_max_order_num");
		Sql sql = Sqls.create(sqlStr);
		List<Record> orders = dbDao.query(sql, Cnd.where("SUBSTR(ordersnum,1,8)", "=", dateStr), null);
		if (!Util.isEmpty(orders.get(0).get("maxnum"))) {
			String maxnum = (String) orders.get(0).get("maxnum");
			ordernum = dateStr + zeroize((Integer.valueOf(maxnum) + 1), 5);
		} else {
			ordernum = dateStr + zeroize(1, 5);
		}
		return ordernum;
	}

	/**
	 * 位数不足补零
	 * TODO(这里描述这个方法详情– 可选)
	 * @param num
	 * @param length
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	private String zeroize(int num, int length) {
		String value = String.valueOf(num);
		String zero = "";
		for (int i = 0; i < length - value.length(); i++) {
			zero += "0";
		}
		return zero + value;
	}

	public Object listEditPlanData(EditPlanSqlForm sqlForm, HttpSession session) {

		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(company.getId());
		Map<String, Object> listPageData = this.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		for (Record record : list) {
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("planid", "=", record.get("id")).orderBy("leavedate", "asc"), null);
			record.put("airinfo", query);
		}
		listPageData.remove("data");
		listPageData.put("data", list);
		return listPageData;
	}
}