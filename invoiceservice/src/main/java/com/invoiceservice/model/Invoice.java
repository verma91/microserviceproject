package com.invoiceservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="invoice_tbl")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long invoiceId;
	
	@Size(min=3,max=20)
	@Column(name="payee_name")
	private String payeeName;
	
	@Column(name="invoice_generation_date")
	private Date invoiceGenerationDate;
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="amount_paid")
	private Float amountPaid;

	public Invoice() {
		super();
	}

	public Invoice(String payeeName, Date invoiceGenerationDate, Long orderId, Float amountPaid) {
		super();
		this.payeeName = payeeName;
		this.invoiceGenerationDate = invoiceGenerationDate;
		this.orderId = orderId;
		this.amountPaid = amountPaid;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Date getInvoiceGenerationDate() {
		return invoiceGenerationDate;
	}

	public void setInvoiceGenerationDate(Date invoiceGenerationDate) {
		this.invoiceGenerationDate = invoiceGenerationDate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Float amountPaid) {
		this.amountPaid = amountPaid;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", payeeName=" + payeeName + ", invoiceGenerationDate="
				+ invoiceGenerationDate + ", orderId=" + orderId + ", amountPaid=" + amountPaid + "]";
	}
	
	
	
	
}
