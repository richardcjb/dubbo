package com.linyun.airline.admin.drawback.grabreport.service;

import java.text.DecimalFormat;
import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabreport.entity.TGrabReportEntity;
import com.linyun.airline.admin.drawback.grabreport.entity.TReportDto;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabreportViewService extends BaseService<TGrabReportEntity> {

	/**
	 * 编辑时回显数据
	 * @param id
	 */
	public Object updateFilePreview(long id) {
		Map<String, Object> obj = Maps.newHashMap();
		obj.put("reportList", dbDao.fetch(TGrabReportEntity.class, id));
		return obj;
	}

	/**
	 * 打开附件预览时查询url
	 * @param pid
	 */
	public Object addFilePreview(long pid) {
		Map<String, Object> obj = Maps.newHashMap();
		TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, pid);
		obj.put("fileurl", fetch);
		return obj;
	}

	/**
	 * 报表数据保存
	 * @param addForm
	 */
	public Object saveFilePreview(TGrabReportAddForm addForm) {
		//double数据四舍五入保留小数点后两位
		TGrabReportEntity report = new TGrabReportEntity();
		DecimalFormat df = new DecimalFormat("#.00");
		String pnr = addForm.getPNR();
		Sql sql = Sqls.create(sqlManager.get("grab_report_list"));
		sql.params().set("pnr", pnr);
		TReportDto fetchPnr = DbSqlUtil.fetchEntity(dbDao, TReportDto.class, sql);
		Double costprice = 0.0;//成本单价
		Integer peoplecount = 0;//人数
		Double price = 0.0;//实收单价(销售价)
		if (!Util.isEmpty(fetchPnr)) {
			if (!Util.isEmpty(costprice)) {
				costprice = fetchPnr.getCostprice();//成本单价
			} else {
				costprice = 0.0;
			}
			if (!Util.isEmpty(peoplecount)) {
				peoplecount = fetchPnr.getPeoplecount();//人数
			} else {
				peoplecount = 0;
			}
			if (!Util.isEmpty(price)) {
				price = fetchPnr.getPrice();//实收单价(销售价)
			} else {
				price = 0.0;
			}
		}
		report.setPNR(pnr);//PNR
		report.setExciseTax1(Double.parseDouble(df.format(addForm.getExciseTax1())));//消费税
		report.setBackStatus(addForm.getBackStatus());//退税状态
		report.setInAustralianTime(addForm.getInAustralianTime());//入澳时间
		report.setOutAustralianTime(addForm.getOutAustralianTime());//出澳时间
		report.setSwipe(Double.parseDouble(df.format(addForm.getSwipe())));//刷卡费
		report.setRemark(addForm.getRemark());//备注
		Double tax = Double.parseDouble(df.format(addForm.getTax()));//税金/杂项
		Double remit = Double.parseDouble(df.format(addForm.getRemit()));//汇款
		report.setTax(tax);
		report.setRemit(remit);
		if (!Util.isEmpty(costprice)) {
			//1、备用金额=[(税金/杂项)+汇款-(人数*成本单价)]
			Double depositBalance = Double.parseDouble(df.format((tax + remit) - (peoplecount * costprice)));
			report.setDepositBalance(depositBalance);
		} else {
			Double depositBalance = Double.parseDouble(df.format((tax + remit)));
			report.setDepositBalance(depositBalance);
		}
		//2、代理费=SUM(票价<含行李>*代理返点)
		Double ticketPrice = Double.parseDouble(df.format(addForm.getTicketPrice()));//票价<含行李>
		Double agentRebate = Double.parseDouble(df.format(addForm.getAgentRebate()));//代理返点
		Double agencyFee = Double.parseDouble(df.format(ticketPrice * agentRebate));//代理费
		report.setTicketPrice(ticketPrice);
		report.setAgencyFee(agencyFee);
		report.setAgentRebate(Double.parseDouble(df.format(addForm.getAgentRebate())));
		//3、税返点=SUM(代理费*10%)
		Double taxRebate = Double.parseDouble(df.format(agencyFee * 0.1));//税返点
		report.setTaxRebate(taxRebate);
		if (!Util.isEmpty(price) && !Util.isEmpty(peoplecount)) {
			//4、实收单价(含操作费)=SUM(实收单价)
			Double realIncome = price;//实收单价(含操作费)
			report.setRealIncome(realIncome);
			//5、实收合计(含操作费)=SUM[实收单价(含操作费)*人数]
			Double realTotal = (realIncome * peoplecount);//实收合计(含操作费)
			report.setRealTotal(realTotal);
			/**6、代理费2=实收票价(含行李)*代理返点(realTotal-(realTotal/11)-tax)*agentRebate
			 * 实收票价(含行李)=实收合计(含操作费)-消费税2-税金/杂项 =realTotal-(realTotal/11)-tax
			 * 消费税2=SUM[实收合计(含操作费)/11]=(realTotal/11)
			 */
			Double agencyFee2 = Double.parseDouble(df.format((realTotal - (realTotal / 11) - tax) * agentRebate));//代理费2
			report.setAgencyFee2(agencyFee2);
		} else {
			//4、实收单价(含操作费)=SUM(实收单价)
			Double realIncome = 0.0;//实收单价(含操作费)
			report.setRealIncome(realIncome);
			//5、实收合计(含操作费)=SUM[实收单价(含操作费)*人数]
			Double realTotal = 0.0;//实收合计(含操作费)
			report.setRealTotal(realTotal);
			/**6、代理费2=实收票价(含行李)*代理返点(realTotal-(realTotal/11)-tax)*agentRebate
			 * 实收票价(含行李)=实收合计(含操作费)-消费税2-税金/杂项 =realTotal-(realTotal/11)-tax
			 * 消费税2=SUM[实收合计(含操作费)/11]=(realTotal/11)
			 */
			Double agencyFee2 = Double.parseDouble(df.format((realTotal - (realTotal / 11) - tax) * agentRebate));//代理费2
			report.setAgencyFee2(agencyFee2);
		}
		return dbDao.insert(report);
	}
}