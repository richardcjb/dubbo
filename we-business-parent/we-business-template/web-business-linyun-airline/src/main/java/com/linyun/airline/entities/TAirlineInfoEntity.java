package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_airline_info")
public class TAirlineInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航空公司代码")
	private String aircom;

	@Column
	@Comment("出发城市")
	private String leavecity;

	@Column
	@Comment("到达城市")
	private String arrvicity;

	@Column
	@Comment("去程日期")
	private Date leavedate;

	@Column
	@Comment("回程日期")
	private Date backdate;

	@Column
	@Comment("原价(单价)")
	private Double formprice;

	@Column
	@Comment("售价(单价)")
	private Double price;

	@Column
	@Comment("结算货币代码")
	private String currencyCode;

	@Column
	@Comment("数量")
	private Integer passengercount;

	@Column
	@Comment("乘客类型")
	private Integer passengertype;

	@Column
	@Comment("机票类型")
	private Integer tickettype;

	@Column
	@Comment("旅程类型")
	private Integer traveltype;

	@Column
	@Comment("需求id")
	private Integer needid;

	@Column
	@Comment("计划id")
	private Integer planid;

	@Column
	@Comment("出发时间")
	private String leavetime;

	@Column
	@Comment("抵达时间")
	private String arrivetime;

	@Column
	@Comment("航班号")
	private String ailinenum;

	@Column
	@Comment("pnrid")
	private Integer pnrid;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAirlineInfoEntity other = (TAirlineInfoEntity) obj;
		if (ailinenum == null) {
			if (other.ailinenum != null)
				return false;
		} else if (!ailinenum.equals(other.ailinenum))
			return false;
		if (aircom == null) {
			if (other.aircom != null)
				return false;
		} else if (!aircom.equals(other.aircom))
			return false;
		if (arrivetime == null) {
			if (other.arrivetime != null)
				return false;
		} else if (!arrivetime.equals(other.arrivetime))
			return false;
		if (arrvicity == null) {
			if (other.arrvicity != null)
				return false;
		} else if (!arrvicity.equals(other.arrvicity))
			return false;
		if (backdate == null) {
			if (other.backdate != null)
				return false;
		} else if (!backdate.equals(other.backdate))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (formprice == null) {
			if (other.formprice != null)
				return false;
		} else if (!formprice.equals(other.formprice))
			return false;
		if (leavecity == null) {
			if (other.leavecity != null)
				return false;
		} else if (!leavecity.equals(other.leavecity))
			return false;
		if (leavedate == null) {
			if (other.leavedate != null)
				return false;
		} else if (!leavedate.equals(other.leavedate))
			return false;
		if (leavetime == null) {
			if (other.leavetime != null)
				return false;
		} else if (!leavetime.equals(other.leavetime))
			return false;
		if (needid == null) {
			if (other.needid != null)
				return false;
		} else if (!needid.equals(other.needid))
			return false;
		if (passengercount == null) {
			if (other.passengercount != null)
				return false;
		} else if (!passengercount.equals(other.passengercount))
			return false;
		if (passengertype == null) {
			if (other.passengertype != null)
				return false;
		} else if (!passengertype.equals(other.passengertype))
			return false;
		if (planid == null) {
			if (other.planid != null)
				return false;
		} else if (!planid.equals(other.planid))
			return false;
		if (pnrid == null) {
			if (other.pnrid != null)
				return false;
		} else if (!pnrid.equals(other.pnrid))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (tickettype == null) {
			if (other.tickettype != null)
				return false;
		} else if (!tickettype.equals(other.tickettype))
			return false;
		if (traveltype == null) {
			if (other.traveltype != null)
				return false;
		} else if (!traveltype.equals(other.traveltype))
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ailinenum == null) ? 0 : ailinenum.hashCode());
		result = prime * result + ((aircom == null) ? 0 : aircom.hashCode());
		result = prime * result + ((arrivetime == null) ? 0 : arrivetime.hashCode());
		result = prime * result + ((arrvicity == null) ? 0 : arrvicity.hashCode());
		result = prime * result + ((backdate == null) ? 0 : backdate.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((formprice == null) ? 0 : formprice.hashCode());
		result = prime * result + ((leavecity == null) ? 0 : leavecity.hashCode());
		result = prime * result + ((leavedate == null) ? 0 : leavedate.hashCode());
		result = prime * result + ((leavetime == null) ? 0 : leavetime.hashCode());
		result = prime * result + ((needid == null) ? 0 : needid.hashCode());
		result = prime * result + ((passengercount == null) ? 0 : passengercount.hashCode());
		result = prime * result + ((passengertype == null) ? 0 : passengertype.hashCode());
		result = prime * result + ((planid == null) ? 0 : planid.hashCode());
		result = prime * result + ((pnrid == null) ? 0 : pnrid.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((tickettype == null) ? 0 : tickettype.hashCode());
		result = prime * result + ((traveltype == null) ? 0 : traveltype.hashCode());
		return result;
	}

}