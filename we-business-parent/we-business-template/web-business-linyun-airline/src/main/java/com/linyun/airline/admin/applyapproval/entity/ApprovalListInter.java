/**
 * ApprovalListInter.java
 * com.linyun.airline.admin.applyapproval.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.applyapproval.entity;

import java.util.Date;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月23日 	 
 */
public class ApprovalListInter {
	private Integer id;
	private Integer orderstype;
	/*private String PNR;*/
	private Integer orderPnrStatus;
	private Double costpricesum;
	private String ordersnum;
	private Date optime;
	private Integer purpose;
	private Integer purposer;
	private String shortName;
	private Integer fundType;
	private Double payFees;
	private Integer payCurrency;
	private Integer isInvioce;
	private Date approveTime;
	private Integer approveResult;
	private Integer usingId;
	private Integer userId;
	private Integer orderstatusid;
	private String orderstatus;
	/*private Integer orderId;*/
	private String userName;
	private String fullName;
	private String currencyStr;
	private String purposeStr;
	private String fundTypeStr;
	private Integer companyId;
	private Integer paystatus;
	private Double amount;
	private Date orderstime;
	/****以下为减免需要处理的字段***/
	private String customname;
	private Integer customeid;
	private Double account;
	private String accountupper;
	private String currency;
	private Integer applyid;
	private Integer approvelid;
	private Integer applyResult;
	private Integer orderid;
	private Integer ordertype;
	/*判断是否为减免的业务*/
	private String isReduce;

	public String getIsReduce() {
		return isReduce;
	}

	public void setIsReduce(String isReduce) {
		this.isReduce = isReduce;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderstype() {
		return orderstype;
	}

	public void setOrderstype(Integer orderstype) {
		this.orderstype = orderstype;
	}

	public Integer getOrderPnrStatus() {
		return orderPnrStatus;
	}

	public void setOrderPnrStatus(Integer orderPnrStatus) {
		this.orderPnrStatus = orderPnrStatus;
	}

	public Double getCostpricesum() {
		return costpricesum;
	}

	public void setCostpricesum(Double costpricesum) {
		this.costpricesum = costpricesum;
	}

	public String getOrdersnum() {
		return ordersnum;
	}

	public void setOrdersnum(String ordersnum) {
		this.ordersnum = ordersnum;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public Integer getPurpose() {
		return purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	public Integer getPurposer() {
		return purposer;
	}

	public void setPurposer(Integer purposer) {
		this.purposer = purposer;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public Double getPayFees() {
		return payFees;
	}

	public void setPayFees(Double payFees) {
		this.payFees = payFees;
	}

	public Integer getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(Integer payCurrency) {
		this.payCurrency = payCurrency;
	}

	public Integer getIsInvioce() {
		return isInvioce;
	}

	public void setIsInvioce(Integer isInvioce) {
		this.isInvioce = isInvioce;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}

	public Integer getUsingId() {
		return usingId;
	}

	public void setUsingId(Integer usingId) {
		this.usingId = usingId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrencyStr() {
		return currencyStr;
	}

	public void setCurrencyStr(String currencyStr) {
		this.currencyStr = currencyStr;
	}

	public String getPurposeStr() {
		return purposeStr;
	}

	public void setPurposeStr(String purposeStr) {
		this.purposeStr = purposeStr;
	}

	public String getFundTypeStr() {
		return fundTypeStr;
	}

	public void setFundTypeStr(String fundTypeStr) {
		this.fundTypeStr = fundTypeStr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getOrderstime() {
		return orderstime;
	}

	public void setOrderstime(Date orderstime) {
		this.orderstime = orderstime;
	}

	public String getCustomname() {
		return customname;
	}

	public void setCustomname(String customname) {
		this.customname = customname;
	}

	public Integer getCustomeid() {
		return customeid;
	}

	public void setCustomeid(Integer customeid) {
		this.customeid = customeid;
	}

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public String getAccountupper() {
		return accountupper;
	}

	public void setAccountupper(String accountupper) {
		this.accountupper = accountupper;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getApplyid() {
		return applyid;
	}

	public void setApplyid(Integer applyid) {
		this.applyid = applyid;
	}

	public Integer getApprovelid() {
		return approvelid;
	}

	public void setApprovelid(Integer approvelid) {
		this.approvelid = approvelid;
	}

	public Integer getApplyResult() {
		return applyResult;
	}

	public void setApplyResult(Integer applyResult) {
		this.applyResult = applyResult;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getOrderstatusid() {
		return orderstatusid;
	}

	public void setOrderstatusid(Integer orderstatusid) {
		this.orderstatusid = orderstatusid;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
