package com.linyun.airline.admin.customneeds.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.nutz.lang.Strings;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.admin.customneeds.form.CityAirlineJson;
import com.linyun.airline.admin.customneeds.form.PlanMakeSqlForm;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.util.ExportExcel;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TPlanInfoEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TPlanInfoAddForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月6日 	 
 */
@IocBean
public class PlanMakeService extends BaseService<TPlanInfoEntity> {

	@Inject
	private CompanyViewService companyViewService;
	@Inject
	private externalInfoService externalInfoService;
	//旅行社字典代码
	private static final String TRAVELCODE = "LXS";
	//航班号字典代码
	private static final String AIRLINECODE = "HBH";
	//城市字典代码
	private static final String CITYCODE = "CFCS";
	//航空公司字典代码
	private static final String AIRCOMCODE = "HKGS";
	private static final String QUANGUOLIANYUN = "全国联运";
	//东航模板列标题
	private static final String[] EXCEL_DONGHANG_COLUMN_TITLE = { "去程日期", "去程航班", "行程", "回程日期", "回程航班", "人数", "组团社",
			"航程", "价格", "订单号" };
	//东航标题
	private static final String EXCEL_DONGHANG_TITLE = "东航";
	//南航列标题
	private static final String[] EXCEL_NANHANG_COLUMN_TITLE = { "区域", "日期段", "合作旅行社", "行程", "去程航班", "回程航班",
			"去程班期（周几）", "回程班期（周几）", "天数(以国际航班起飞时间计算)", "每月周期第几周发团", "座位数", "团队个数/月", "去程日期", "回程日期", "申请编号", "价格", "TC" };
	//南航标题
	private static final String EXCEL_NANHANG_TITLE = "南航";
	//国泰列标题
	private static final String[] EXCEL_GUOTAI_COLUMN_TITLE = { "代理", "出发日期", "回程日期", "目的地", "人数", " ", "价格", "航班备注" };
	//国泰标题
	private static final String EXCEL_GUOTAI_TITLE = "国泰";
	private static final String[] EXCEL_GUOTAI_FIRST_DATA = { "Agent", "Dep Date", "Return Date", "Dest", "TCP",
			"RLOC", "Fare", "RMP(Optional)" };
	//凌云列标题
	private static final String[] EXCEL_LINGYUN_COLUMN_TITLE = { "航空公司名称", "去程日期", "去程航段", "去程航班", "回程日期", "回程航段",
			"回程航班", "人数", "天数", "旅行社", "联运要求" };
	//凌云
	private static final String EXCEL_LINGYUN_TITLE = "凌云";

	/**
	 * 获取旅行社名称下拉
	 * <p>
	 * 获取旅行社名称下拉
	 * @param session 
	 * @param name 
	 *
	 * @return 返回旅行社名称的下拉列表
	 */
	public Object getTravelNameSelect(String TravelName, HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//上有公司信息
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", company.getId()));
		List<TCustomerInfoEntity> travelSelect = new ArrayList<TCustomerInfoEntity>();
		try {
			travelSelect = dbDao.query(
					TCustomerInfoEntity.class,
					Cnd.where("upComId", "=", upcompany.getId()).and("shortName", "like",
							Strings.trim(TravelName) + "%"), null);
			if (travelSelect.size() > 5) {
				travelSelect = travelSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return travelSelect;
	}

	/**
	 * 
	 * 获取航班号下拉框
	 * <p>
	 * 获取航班号下拉框
	 *
	 * @param airlinename
	 * @param exname 
	 * @return 获取航班号下拉框
	 */
	public Object getAirLineSelect(String airlinename, String exname) {
		//List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		/*List<TFlightInfoEntity> airlineSelect = new ArrayList<TFlightInfoEntity>();
		try {
			//airlineSelect = externalInfoService.findDictInfoByName(airlinename, this.AIRLINECODE);
			airlineSelect = dbDao.query(TFlightInfoEntity.class,
					Cnd.where("airlinenum", "like", Strings.trim(airlinename) + "%"), null);
			TFlightInfoEntity exinfo = new TFlightInfoEntity();
			for (TFlightInfoEntity tFlightInfoEntity : airlineSelect) {
				if (!Util.isEmpty(exname) && tFlightInfoEntity.getAirlinenum().equals(exname)) {
					exinfo = tFlightInfoEntity;
				}
			}
			airlineSelect.remove(exinfo);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//
		List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		List<Map> airlist = new ArrayList<Map>();
		try {
			airlineSelect = externalInfoService.findDictInfoByText(airlinename, AIRLINECODE);
			DictInfoEntity exinfo = new DictInfoEntity();
			for (DictInfoEntity dictInfo : airlineSelect) {
				if (!Util.isEmpty(exname) && dictInfo.getDictName().equals(exname)) {
					exinfo = dictInfo;
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("airlinenum", dictInfo.getDictName());
					airlist.add(map);
				}
			}
			/*airlineSelect.remove(exinfo);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}*/
			if (airlist.size() > 5) {
				airlist = airlist.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlist;
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCitySelect(String cityname, String exname) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findCityByCode(cityname, CITYCODE);
			//移除的城市
			TDepartureCityEntity removeinfo = new TDepartureCityEntity();
			for (TDepartureCityEntity dictInfoEntity : citySelect) {
				if (!Util.isEmpty(exname) && dictInfoEntity.getDictCode().equals(exname)) {
					removeinfo = dictInfoEntity;
				}
			}
			citySelect.remove(removeinfo);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 获取联运城市下拉
	 * <p>
	 * 获取联运城市下拉
	 *
	 * @param cityname
	 * @return 返回联运城市下拉列表
	 */
	public Object getUnionCitySelect(String cityname) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findCityByCode(cityname, CITYCODE);
			if (QUANGUOLIANYUN.indexOf(Strings.trim(cityname)) != -1) {
				TDepartureCityEntity dictInfoEntity = new TDepartureCityEntity();
				//dictInfoEntity.setDictName(this.QUANGUOLIANYUN);
				dictInfoEntity.setId(0);
				dictInfoEntity.setDictCode(QUANGUOLIANYUN);
				citySelect.add(0, dictInfoEntity);
			}
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 计划制作
	 * <p>
	 * 计划制作
	 * @param addForm
	 * @param session 
	 * @return 根据页面提交的数据进行航空公司模块的计划制作
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public Object airlineMakePlan(TPlanInfoAddForm addForm, HttpSession session) {
		//List<TPlanInfoEntity> planInfos = new ArrayList<TPlanInfoEntity>();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//格式化日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//获取出发城市、抵达城市、航班号、出发日期、时间的list
		List<CityAirlineJson> airlineJson = JsonUtil
				.fromJsonAsList(CityAirlineJson.class, addForm.getCityairlinejson());
		//页面选择的起始日期
		Date startdate = null;
		if (!Util.isEmpty(addForm.getStartdate())) {
			startdate = DateUtil.string2Date(addForm.getStartdate(), DateUtil.FORMAT_YYYY_MM_DD);
		}
		//页面选择的结束日期
		Date enddate = null;
		if (!Util.isEmpty(addForm.getEnddate())) {
			enddate = DateUtil.string2Date(addForm.getEnddate(), DateUtil.FORMAT_YYYY_MM_DD);
		}
		List<Integer> weeklist = new ArrayList<Integer>();
		if (!Util.isEmpty(addForm.getWeekday())) {
			String[] weeks = addForm.getWeekday().split(",");
			for (String str : weeks) {
				weeklist.add(Integer.valueOf(str));
			}
		}
		//根据航班号获取航空公司名称
		//String airLineName = (String) this.getAirCompanyByAirLine(addForm.getLeaveairline()).get("dictCode");
		//如果制作系列团
		if (addForm.getTeamtype() == 1) {
			//自由制作计划
			if (!Util.isEmpty(addForm.getCalenderdate())) {
				//循环小日历的日期
				Iterable<String> calenders = Splitter.on(",").split(addForm.getCalenderdate());
				for (String str : calenders) {
					//选择的日期
					Date calendate = DateUtil.string2Date(str);
					//if (DateUtil.dateBetween(calendate, startdate, enddate)) {
					TPlanInfoEntity planInfoEntity = new TPlanInfoEntity();
					planInfoEntity.setTravelname(addForm.getTravelname());
					planInfoEntity.setPeoplecount(addForm.getPeoplecount());
					planInfoEntity.setDayscount(addForm.getDayscount());
					planInfoEntity.setUnioncity(addForm.getUnioncity());
					planInfoEntity.setTeamtype(addForm.getTeamtype());
					planInfoEntity.setOpid(user.getId());
					planInfoEntity.setCompanyid(company.getId());
					planInfoEntity.setTimetype(addForm.getTimetype());
					planInfoEntity.setStarttime(startdate);
					planInfoEntity.setEndtime(enddate);
					planInfoEntity.setFoc(addForm.getFoc());
					//planInfos.add(planInfoEntity);
					//}
					TPlanInfoEntity insertplanInfo = dbDao.insert(planInfoEntity);
					List<TAirlineInfoEntity> airlines = new ArrayList<TAirlineInfoEntity>();
					//添加多个航段
					int flag = 1;
					for (CityAirlineJson cityAirlineJson : airlineJson) {
						Date setoffdates = null;
						//系列团第一个日期为出发日期，第二个日期为返回日期，返回日期=出发日期+天数
						if (flag > 1) {
							setoffdates = DateUtil.addDay(calendate, addForm.getDayscount());
						} else {
							setoffdates = calendate;
						}
						TAirlineInfoEntity airline = new TAirlineInfoEntity();
						airline.setLeavecity(cityAirlineJson.getLeavescity());
						airline.setArrvicity(cityAirlineJson.getBackscity());
						airline.setAilinenum(cityAirlineJson.getLeaveairline());
						airline.setPlanid(insertplanInfo.getId());
						airline.setLeavedate(setoffdates);
						//添加时间
						if (!Util.isEmpty(cityAirlineJson.getSetofftime())) {
							String[] offtimes = cityAirlineJson.getSetofftime().split("/");
							if (!Util.isEmpty(offtimes[0])) {
								airline.setLeavetime(offtimes[0]);
							}
							if (!Util.isEmpty(offtimes[1])) {
								airline.setArrivetime(offtimes[1]);
							}
						}
						airlines.add(airline);
						flag++;
					}
					//保存多个航段
					dbDao.insert(airlines);
				}
			}
			//如果选择每周
			if (addForm.getTimetype() == 1) {
				//Date setoffdate = DateUtil.string2Date(airlineJson.get(0).getSetoffdate(), DateUtil.FORMAT_YYYY_MM_DD);
				Date setoffdate = DateUtil.string2Date(addForm.getStartdate(), DateUtil.FORMAT_YYYY_MM_DD);
				for (Date date = setoffdate; DateUtil.dateBetween(date, DateUtil.addDay(startdate, -1),
						DateUtil.addDay(enddate, 1)); date = DateUtil.addDay(date, 1)) {
					TPlanInfoEntity insertPlan = new TPlanInfoEntity();
					//若日期符合所要的周几  则该计划制作
					if (weeklist.contains(this.dayForWeek(date))) {
						TPlanInfoEntity planInfoEntity = new TPlanInfoEntity();
						planInfoEntity.setTravelname(addForm.getTravelname());
						planInfoEntity.setLeavesdate(date);
						planInfoEntity.setPeoplecount(addForm.getPeoplecount());
						planInfoEntity.setDayscount(addForm.getDayscount());
						planInfoEntity.setUnioncity(addForm.getUnioncity());
						planInfoEntity.setTeamtype(addForm.getTeamtype());
						planInfoEntity.setOpid(user.getId());
						planInfoEntity.setCompanyid(company.getId());
						planInfoEntity.setTimetype(addForm.getTimetype());
						planInfoEntity.setStarttime(startdate);
						planInfoEntity.setEndtime(enddate);
						planInfoEntity.setFoc(addForm.getFoc());
						insertPlan = dbDao.insert(planInfoEntity);
						List<TAirlineInfoEntity> airlines = new ArrayList<TAirlineInfoEntity>();
						//添加多个航段
						int flag = 1;
						for (CityAirlineJson cityAirlineJson : airlineJson) {
							@SuppressWarnings("unused")
							//航班日期
							Date setoffdates = null;
							//系列团第一个日期为出发日期，第二个日期为返回日期，返回日期=出发日期+天数
							if (flag > 1) {
								setoffdates = DateUtil.addDay(date, addForm.getDayscount());
							} else {
								setoffdates = date;
							}
							TAirlineInfoEntity airline = new TAirlineInfoEntity();
							airline.setLeavecity(cityAirlineJson.getLeavescity());
							airline.setArrvicity(cityAirlineJson.getBackscity());
							airline.setAilinenum(cityAirlineJson.getLeaveairline());
							airline.setPlanid(insertPlan.getId());
							airline.setLeavedate(setoffdates);
							//添加时间
							if (!Util.isEmpty(cityAirlineJson.getSetofftime())) {
								String[] offtimes = cityAirlineJson.getSetofftime().split("/");
								if (!Util.isEmpty(offtimes[0])) {
									airline.setLeavetime(offtimes[0]);
								}
								if (!Util.isEmpty(offtimes[1])) {
									airline.setArrivetime(offtimes[1]);
								}
							}
							airlines.add(airline);
						}
						//保存多个航段
						dbDao.insert(airlines);
					}
				}
			}
		} else {
			TPlanInfoEntity planInfoEntity = new TPlanInfoEntity();
			planInfoEntity.setTravelname(addForm.getTravelname());
			planInfoEntity.setPeoplecount(addForm.getPeoplecount());
			planInfoEntity.setDayscount(addForm.getDayscount());
			planInfoEntity.setUnioncity(addForm.getUnioncity());
			planInfoEntity.setTeamtype(addForm.getTeamtype());
			planInfoEntity.setOpid(user.getId());
			planInfoEntity.setCompanyid(company.getId());
			planInfoEntity.setTimetype(addForm.getTimetype());
			planInfoEntity.setStarttime(startdate);
			planInfoEntity.setEndtime(enddate);
			planInfoEntity.setFoc(addForm.getFoc());
			//planInfos.add(planInfoEntity);
			//}
			TPlanInfoEntity insertplanInfo = dbDao.insert(planInfoEntity);
			List<TAirlineInfoEntity> airlines = new ArrayList<TAirlineInfoEntity>();
			//添加多个航段
			for (CityAirlineJson cityAirlineJson : airlineJson) {
				TAirlineInfoEntity airline = new TAirlineInfoEntity();
				airline.setLeavecity(cityAirlineJson.getLeavescity());
				airline.setArrvicity(cityAirlineJson.getBackscity());
				airline.setAilinenum(cityAirlineJson.getLeaveairline());
				airline.setPlanid(insertplanInfo.getId());
				airline.setLeavedate(DateUtil.string2Date(cityAirlineJson.getSetoffdate(), DateUtil.FORMAT_YYYY_MM_DD));
				//添加时间
				if (!Util.isEmpty(cityAirlineJson.getSetofftime())) {
					String[] offtimes = cityAirlineJson.getSetofftime().split("/");
					if (!Util.isEmpty(offtimes[0])) {
						airline.setLeavetime(offtimes[0]);
					}
					if (!Util.isEmpty(offtimes[1])) {
						airline.setArrivetime(offtimes[1]);
					}
				}
				airlines.add(airline);
			}
			//保存多个航段
			dbDao.insert(airlines);
		}
		return JsonResult.success("制作成功");

	}

	/**
	 * 判断日期是周几
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 * @param pTime
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	private static int dayForWeek(Date pTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(pTime);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 每月第几周
	 */
	@SuppressWarnings("unused")
	private static int weekOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		return weekOfMonth;
	}

	/**
	 * 获取航班号所在的航空公司
	 */
	@SuppressWarnings("unused")
	private Record getAirCompanyByAirLine(String airLine) {
		Record result = new Record();
		String sqlstring = sqlManager.get("airline_company_info");
		Sql sql = Sqls.create(sqlstring);
		sql.setCondition(Cnd.where("t.airlinenum", "=", airLine));
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	/**
	 * 保存计划
	 * <p>
	 * 保存计划
	 * @param session 
	 * @return 保存计划
	 */
	public Object savePlanData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()));
		List<TPlanInfoEntity> planInfoEntities = this.listPageBean(TPlanInfoEntity.class, sql, null);
		for (TPlanInfoEntity tPlanInfoEntity : planInfoEntities) {
			tPlanInfoEntity.setIssave(1);
		}
		return dbDao.update(planInfoEntities);
	}

	/**
	 * 导出东航模板
	 * EXCEL导出东航模板
	 * @param response
	 * @param session 
	 * @return Excel导出东航模板
	 */
	public Object exportDongHangTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd/MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = EXCEL_DONGHANG_COLUMN_TITLE;
			//设置Excel表格标题
			String title = EXCEL_DONGHANG_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (Record record : rerultList) {
				Object[] obj = { df.format(record.get("leavesdate")), record.get("leaveairline"),
						record.get("dayscount"), df.format(record.get("backsdate")), record.get("backairline"),
						record.get("peoplecount"), record.get("travelname"),
						record.get("leavescity") + "-" + record.get("backscity") + "-" + record.get("leavescity") };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取计划制作导出Excel数据
	 * @param session 
	 */
	private List<Record> getMakePlanData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("issave", "=", 0).and("companyid", "=", company.getId())
				.orderBy("leavesdate", "asc"));
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> rerultList = (List<Record>) sql.getResult();
		return rerultList;
	}

	/**
	 * 获取计划制作导出Excel数据
	 * @param session 
	 */
	private List<TPlanInfoEntity> getMakePlansData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<TPlanInfoEntity> rerultList = dbDao.query(TPlanInfoEntity.class,
				Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()).orderBy("leavesdate", "asc"), null);
		return rerultList;
	}

	/**
	 * 获取城市代码
	 */
	private String getCityCode(String cityName) {
		String result = "";
		List<DictInfoEntity> DictInfo = new ArrayList<DictInfoEntity>();
		;
		try {
			DictInfo = externalInfoService.findDictInfoByName(cityName, CITYCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!Util.isEmpty(DictInfo)) {
			result = DictInfo.get(0).getDictCode();
		}
		return result;
	}

	/**
	 * 导出南航模板
	 * <p>
	 * 导出南航模板
	 *
	 * @param response
	 * @return 导出南航Excel模板
	 */
	public Object exportNanHangTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = EXCEL_NANHANG_COLUMN_TITLE;
			//设置Excel表格标题
			String title = EXCEL_NANHANG_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (Record record : rerultList) {
				Object[] obj = { " ", " ", record.get("travelname"),
						record.get("leavescity") + "//" + record.get("backscity"), record.get("leaveairline"),
						record.get("backairline"), dayForWeek((Date) record.get("leavesdate")),
						dayForWeek((Date) record.get("leavesdate")), record.get("dayscount"),
						weekOfMonth((Date) record.get("leavesdate")), record.get("peoplecount"), " ",
						df.format(record.get("leavesdate")), df.format(record.get("backsdate")) };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出国泰模板
	 * <p>
	 * 导出国泰模板
	 * @param response
	 * @param session 
	 * @return 导出国泰模板
	 */
	public Object exportGuoTaiTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
			DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日");
			//定义Excel表格的列标题
			String[] excelColumnTitle = EXCEL_GUOTAI_COLUMN_TITLE;
			//设置Excel表格标题
			String title = EXCEL_GUOTAI_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			excelData.add(EXCEL_GUOTAI_FIRST_DATA);
			for (Record record : rerultList) {
				Object[] obj = { record.get("travelname"), df1.format((Date) record.get("leavesdate")),
						df1.format((Date) record.get("backsdate")),
						record.get("leavescity") + "//" + record.get("backscity"), record.get("peoplecount") };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出凌云模板
	 * <p>
	 * 导出凌云模板
	 *
	 * @param response
	 * @param session
	 * @return 导出凌云模板
	 */
	public Object exportLingYunTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd/MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = EXCEL_LINGYUN_COLUMN_TITLE;
			//设置Excel表格标题
			String title = EXCEL_LINGYUN_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<TPlanInfoEntity> rerultList = getMakePlansData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (TPlanInfoEntity record : rerultList) {
				Object[] obj = { record.getAirlinename(), df.format(record.getLeavesdate()),
						record.getLeavescity() + "-" + record.getBackscity(), record.getLeaveairline(),
						df.format(record.getBacksdate()), record.getBackscity() + "-" + record.getLeavescity(),
						record.getBackairline(), record.getPeoplecount(), record.getDayscount(),
						record.getTravelname(), record.getUnioncity() };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否存在未保存的计划
	 * <p>
	 *判断是否存在未保存的计划
	 *
	 * @return 判断是否存在未保存的计划
	 */
	public boolean isHavePlanData(HttpSession session) {
		boolean result = false;
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<TPlanInfoEntity> makeplaninfo = dbDao.query(TPlanInfoEntity.class,
				Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()).orderBy("leavesdate", "asc"), null);
		if (makeplaninfo.size() > 0) {
			result = true;
		}
		return result;
	}

	/**离开页面删除临时制作信息
	 * <p>
	 *离开页面删除临时制作信息
	 * @param session
	 * @return 离开页面删除临时制作信息
	 */
	public Object deleteMakePlanData(HttpSession session) {
		//获取当前公司
		int result = 0;
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<TPlanInfoEntity> makeplaninfo = dbDao.query(TPlanInfoEntity.class,
				Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()).orderBy("leavesdate", "asc"), null);
		if (makeplaninfo.size() > 0) {
			result = dbDao.delete(makeplaninfo);
		}
		return result;
	}

	public Object getAirComSelect(String aircom) {

		List<DictInfoEntity> aircomSelect = new ArrayList<DictInfoEntity>();
		try {
			aircomSelect = externalInfoService.findDictInfoByText(aircom, AIRCOMCODE);
			if (aircomSelect.size() > 5) {
				aircomSelect = aircomSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aircomSelect;

	}

	public Object listPlanMakeData(PlanMakeSqlForm sqlForm, HttpSession session) {
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
