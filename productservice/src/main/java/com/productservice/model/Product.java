package com.productservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_id")
	public Long prodId;
	
	@Size(min=3,max=20)
	@Column(name="prod_name")
	private String prodName;
	
	@DecimalMin(value="0.0" , inclusive=false)
	@Digits(integer=3, fraction=1)
	@Column(name="prod_price")
	private Float prodPrice;
	
	@Size(min=3,max=20)
	@Column(name="prod_category")
	private String prodCategory;
	
	@Size(min=3,max=20)
	@Column(name="prod_honor_name")
	private String honorName;
	
	@Email
	@NotEmpty
	@Column(name="prod_honor_emailId")
	private String honorEmailId;
	
	@Column(name="prod_creation_date")
	private Date creationDate;

	public Product() {
		super();
	}

	public Product(String prodName, Float prodPrice, String prodCategory, String honorName, String honorEmailId,
			Date creationDate) {
		super();
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.prodCategory = prodCategory;
		this.honorName = honorName;
		this.honorEmailId = honorEmailId;
		this.creationDate = creationDate;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Float getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Float prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdCategory() {
		return prodCategory;
	}

	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}

	public String getHonorName() {
		return honorName;
	}

	public void setHonorName(String honorName) {
		this.honorName = honorName;
	}

	public String getHonorEmailId() {
		return honorEmailId;
	}

	public void setHonorEmailId(String honorEmailId) {
		this.honorEmailId = honorEmailId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodCategory="
				+ prodCategory + ", honorName=" + honorName + ", honorEmailId=" + honorEmailId + ", creationDate="
				+ creationDate + "]";
	}
	
	
}
