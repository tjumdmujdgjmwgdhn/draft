package com.briup.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;


public class ShopCart implements IShopCart {
	
	private Map<Integer,Orderline> orderlines = new HashMap<Integer,Orderline>();
	
	public void addProduct(Product product) throws Exception{
		System.out.println("进入ShopCart的add()方法！");
		int number = 1;
	//	int lineid = 0;
		Integer id = product.getProductid();
		System.out.println("进入ShopCart的add()方法！   ------------------id     "+product.getProductid());
		//如果orderlines中存在这个id，说明该书籍已存在
		if(orderlines.containsKey(id)) {
			System.out.println("进入ShopCart的add()方法！-------------if--------------------------------");
			Orderline orderline = (Orderline) orderlines.get(id);	//通过该id查找其在Map中对应的Orderline
			number = orderline.getAmount()+1;						//将该书的数量加1
			orderline.setAmount(new Integer(number));				//将该书的最新数量设置为Orderline的总数量当中
		}else {
			System.out.println("进入ShopCart的add()方法！-------------else--------------------------------");
			Orderline orderline = new Orderline();					//该书不存在，重新设置一个Orderline
			orderline.setAmount(new Integer(number));				//设置新书的数量
			orderline.setProduct(product);							//保存该书到Orderline中
			//orderline.setLineid(orderlines.size()+1);
			orderlines.put(id, orderline);							//然后把其放到Map中
		}
		
	}

	public void removeProduct(Integer productid) throws Exception{
		System.out.println("进入ShopCart的removeProduct()方法！");
		orderlines.remove(productid);
	}

	public void removeAllProducts() throws Exception{
		System.out.println("进入ShopCart的removeAllProducts()方法！");
		orderlines.clear();
	}

	public void updateProduct(Integer productid, Integer number) throws Exception{
		System.out.println("进入ShopCart的updateProduct()方法！");
		Orderline orderline = orderlines.get(productid);
		System.out.println(orderline.getAmount()+orderline.getProduct().toString());
		orderline.setAmount(number);
		System.out.println(orderline.getAmount()+orderline.getProduct().toString());
	}

	public BigDecimal getTotalPrice() throws Exception{
		System.out.println("进入ShopCart的getTotalPrice()方法！");
		BigDecimal totalPrice = new BigDecimal(0);
		Iterator<Orderline> iter = getOrderlines();
		while(iter.hasNext()) {
			Orderline o = (Orderline) iter.next();
			Product p = o.getProduct();
			BigDecimal sum = new BigDecimal(p.getBaseprice()*o.getAmount());
			totalPrice = totalPrice.add(sum);
		}
		return totalPrice;
	}

	public Iterator<Orderline> getOrderlines() throws Exception{
		System.out.println("进入ShopCart的getOrderlines()方法！");
		return orderlines.values().iterator();
	}
}
