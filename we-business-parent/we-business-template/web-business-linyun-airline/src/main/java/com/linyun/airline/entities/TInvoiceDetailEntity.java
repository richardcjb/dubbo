package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_invoice_detail")
public class TInvoiceDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("发票信息id")
	private Integer invoiceinfoid;

	@Column
	@Comment("发票号")
	private String invoicenum;

	@Column
	@Comment("发票金额")
	private Double invoicebalance;

	@Column
	@Comment("发票图片url")
	private String invoiceurl;

	@Column
	@Comment("发票图片名称")
	private String imagename;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("操作时间")
	private Date optime;

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
		TInvoiceDetailEntity other = (TInvoiceDetailEntity) obj;
		if (imagename == null) {
			if (other.imagename != null)
				return false;
		} else if (!imagename.equals(other.imagename))
			return false;
		if (invoicebalance == null) {
			if (other.invoicebalance != null)
				return false;
		} else if (!invoicebalance.equals(other.invoicebalance))
			return false;
		if (invoiceinfoid == null) {
			if (other.invoiceinfoid != null)
				return false;
		} else if (!invoiceinfoid.equals(other.invoiceinfoid))
			return false;
		if (invoicenum == null) {
			if (other.invoicenum != null)
				return false;
		} else if (!invoicenum.equals(other.invoicenum))
			return false;
		if (invoiceurl == null) {
			if (other.invoiceurl != null)
				return false;
		} else if (!invoiceurl.equals(other.invoiceurl))
			return false;
		if (opid == null) {
			if (other.opid != null)
				return false;
		} else if (!opid.equals(other.opid))
			return false;
		if (optime == null) {
			if (other.optime != null)
				return false;
		} else if (!optime.equals(other.optime))
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
		result = prime * result + ((imagename == null) ? 0 : imagename.hashCode());
		result = prime * result + ((invoicebalance == null) ? 0 : invoicebalance.hashCode());
		result = prime * result + ((invoiceinfoid == null) ? 0 : invoiceinfoid.hashCode());
		result = prime * result + ((invoicenum == null) ? 0 : invoicenum.hashCode());
		result = prime * result + ((invoiceurl == null) ? 0 : invoiceurl.hashCode());
		result = prime * result + ((opid == null) ? 0 : opid.hashCode());
		result = prime * result + ((optime == null) ? 0 : optime.hashCode());
		return result;
	}

}