package com.briup.web;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.briup.bean.Order;
import com.briup.bean.Orderline;
import com.briup.bean.Orderstatus;
import com.briup.bean.Payway;
import com.briup.bean.User;
import com.briup.common.exception.ServiceException;
import com.briup.service.IOrderService;
import com.briup.service.IProductService;

@Controller
public class OrderController {
	@Resource
	IOrderService iorder;
	
	Set<Order> orderSet = new HashSet();
	
	Order order;
	
	@RequestMapping("/user/saveOrder")
	public String SaveOrder(HttpServletRequest req,HttpServletResponse resp){
		System.out.println("--------------------------进入SaveOrder---------------------");
		order = new Order();
		//查找该用户对应的订单
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();
		try {
			List<Order> orders = iorder.listOrdersOfUser(userid);
			int length = orders.size();
			while(length>0){
				orderSet.add(orders.get(length-1));
				--length;
			}
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Orderstatus orderstatus = new Orderstatus();
		List<Orderline> orderlines = (List<Orderline>) session.getAttribute("orderlines");
		Set orderlineSet = new HashSet();
		int length = orderlines.size();
		while(length>0){
			orderlineSet.add(orderlines.get(length-1));
			--length;
		}
		
		String paywayid = req.getParameter("payway");
		Payway payway = iorder.findPaywayByPaywayid(Integer.parseInt(paywayid));
		BigDecimal totalPrice =    (BigDecimal) session.getAttribute("totalPrice");
		orderstatus.setStatusid(1);
		order.setOrderlines(orderlineSet);
		order.setCost(totalPrice.doubleValue());
		order.setOrderstatus(orderstatus);
		order.setFinished(0);
		order.setName("name");
		order.setCardno("cardno");
		order.setPayway(payway);
		order.setUser(user);
		orderSet.add(order);
		//user.setOrders(orderSet);
		System.out.println("------------order-----------"+order.toString());
		try {
			iorder.saveOrder(order);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resp.sendRedirect("order.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "/ToOrder";
	}
	
	@RequestMapping("/ToOrder")
	public String ToOrder(){
		System.out.println("------------------进入ToOrder-------------");
		return "user/order.jsp";
	}
	
	@RequestMapping("/user/removeOrder")
	public String RemoveOrder(HttpServletRequest req){
		String orderid = req.getParameter("orderid");
		try {
			iorder.removeOrder(Integer.parseInt(orderid));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/ToOrder";
	}
	
	

	

}
