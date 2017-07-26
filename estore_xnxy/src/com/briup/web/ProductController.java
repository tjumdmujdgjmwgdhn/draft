package com.briup.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.briup.bean.Category;
import com.briup.bean.Order;
import com.briup.bean.Orderline;
import com.briup.bean.Product;
import com.briup.bean.ShopCart;
import com.briup.common.exception.ServiceException;
import com.briup.service.IProductService;

@Controller
public class ProductController {
	@Resource
	IProductService iproduct;
	
	//用于接收对应的Orderline单个数据
	Orderline orderline = new Orderline();
	ShopCart cart;
	//将orderline接收到的数据添加到list集合中，然后将list集合放到session容器中
	
	BigDecimal totalPrice;
	
	Iterator<Orderline> iter;
	
	//添加商品
	@RequestMapping("/AddProductToCart")
	public String AddProduct(HttpServletRequest req){
		System.out.println("----------product------------");
		String productid = req.getParameter("productid");
		Product product = new Product();
		cart = new ShopCart();
		List<Orderline> list = new ArrayList<Orderline>();
		HttpSession session = req.getSession();
		try {
			//通过productid将数据库中对应的product查出来
			product = iproduct.getProductByProductid(Integer.parseInt(productid));
			try {
				SetProduct(req);
				cart.addProduct(product);
			//	System.out.println(session.getAttribute("totalPrice")+"))))))))))))))))))))))))))))))))))))))))))");
				GetProduct(req);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index.jsp";
	}
	
	
	//删除商品
	@RequestMapping("/removeProduct")
	public String RemoveProduct(HttpServletRequest req){
		String productid = req.getParameter("productid");
		cart = new ShopCart();
		System.out.println("----------------------进入removeProduct------------------"+productid);
		try {
			SetProduct(req);
			cart.removeProduct(Integer.parseInt(productid));
			GetProduct(req);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "shopcart.jsp";
	}
	
	//修改商品
	@RequestMapping("updateProduct")
	public String UpdateProduct(HttpServletRequest req){
		String productid = req.getParameter("productid");
		cart = new ShopCart();
		System.out.println("----------------------进入UpdateProduct------------------"+productid);
		String number = req.getParameter("number");
		try {
			SetProduct(req);
			GetProduct(req);
			cart.updateProduct(Integer.parseInt(productid), Integer.parseInt(number));
			//SetProduct(req);
			GetProduct(req);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "shopcart.jsp";
	}
	
	//清空购物车
	@RequestMapping("removeAllProduct")
	public String RemoveAllProduct(HttpServletRequest req){
		cart = new ShopCart();
		SetProduct(req);
		try {
			cart.removeAllProducts();
			GetProduct(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "shopcart.jsp";
	}
	
	//将session中的数据存放到ShopCart的map当中
	private void SetProduct(HttpServletRequest req){
		HttpSession session = req.getSession();
		if(session.getAttribute("orderlines") != null){
			List<Orderline> list1 = (List<Orderline>) session.getAttribute("orderlines");
			int length = list1.size();
			while(length>0){
				Integer amount = list1.get(length-1).getAmount();
				System.out.println(list1.get(length-1).getProduct().toString()+"--------SetProduct-------"+amount);
				//当list集合中的Orderline类型中的product都一一放进cart的map集合当中（当Orderline的amount不为一时）
				while(amount != 0){
					try {
						cart.addProduct(list1.get(length-1).getProduct());
						list1.get(length-1).setAmount(--amount);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				--length;
			}
		}
	}
	
	//将map中的数据取出来放到session当中
	public void GetProduct(HttpServletRequest req){
		List<Orderline> list = new ArrayList<Orderline>();
		HttpSession session = req.getSession();
		BigDecimal totalPrice = null;
		try {
			iter = cart.getOrderlines();
			totalPrice = cart.getTotalPrice();
			while(iter.hasNext()){
				orderline = iter.next();
				System.out.println(orderline.getProduct().toString()+"-------------------GetProduct--------------------"+orderline.getAmount());
				list.add(orderline);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将集合中的数据放到session容器中
		session.removeAttribute("orderlines");
		session.setAttribute("orderlines", list);
		session.removeAttribute("totalPrice");
		session.setAttribute("totalPrice", totalPrice);
	}
	
	//显示商品的详情
	@RequestMapping("/ToProductDetail")
	public String ToProductDetail(HttpServletRequest req){
		System.out.println("---------------进入ToProductDetail-----------------");
		String productid = req.getParameter("productid");
		Product product = null;
		try {
			product = iproduct.getProductByProductid(Integer.parseInt(productid));
			Integer categoryid = iproduct.findCategoryIdByProductid(productid);
			Category category = iproduct.findCategoryByCategoryid(categoryid);
			product.setCategory(category);
			System.out.println(product.toString());
			HttpSession session = req.getSession();
			session.removeAttribute("product");
			session.setAttribute("product", product);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "productDetail.jsp";
	}
}


