package com.orderservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="order_tbl")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Size(min=3,max=20)
	@Column(name="customer_name")
	private String customerName;
	
	@Min(1)
	@Column(name="order_quantity")
	private Integer orderQuantity;
	
	@Column(name="order_creation_date")
	private Date creationDate;
	
	@Column(name="order_cancel_date")
	private Date orderCancelDate;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="order_status")
	private String orderStatus;

	public Order() {
		super();
	}

	public Order(@Size(min = 3, max = 20) String customerName, @NotBlank @Min(1) Integer orderQuantity,
			Date creationDate, Date orderCancelDate, Long productId, String orderStatus) {
		super();
		this.customerName = customerName;
		this.orderQuantity = orderQuantity;
		this.creationDate = creationDate;
		this.orderCancelDate = orderCancelDate;
		this.productId = productId;
		this.orderStatus = orderStatus;
	}



	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getOrderCancelDate() {
		return orderCancelDate;
	}

	public void setOrderCancelDate(Date orderCancelDate) {
		this.orderCancelDate = orderCancelDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerName=" + customerName + ", orderQuantity=" + orderQuantity
				+ ", creationDate=" + creationDate + ", orderCancelDate=" + orderCancelDate + ", productId=" + productId
				+ ", orderStatus=" + orderStatus + "]";
	}

	
	
	
	
}
