package com.briup.bean;



public class Orderline implements java.io.Serializable{

	//Fields
	
	private Integer lineid;
	private Order order;
	private Product product;
	private Integer amount;
	//private BigDecimal totalPrice;
	
	
	//Constructors
	
	/** default constructor*/
	public Orderline() {
	}
	
	/** minimal constructor*/
	public Orderline(Integer amount) {
		this.amount = amount;
	}
	
	/** full constructor*/
	public Orderline(Order order,Product product,Integer amount) {
		this.order = order;
		this.product = product;
		this.amount = amount;
	}

	//Property accessors
	
	public Integer getLineid() {
		return lineid;
	}

	public void setLineid(Integer lineid) {
		this.lineid = lineid;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Orderline [lineid=" + lineid + ", order=" + order
				+ ", product=" + product + ", amount=" + amount + "]";
	}
	
	
}
