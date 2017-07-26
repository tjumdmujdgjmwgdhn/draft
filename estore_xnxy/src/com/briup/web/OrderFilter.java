package com.briup.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.briup.bean.Order;
import com.briup.bean.Payway;
import com.briup.bean.User;
import com.briup.dao.IOrderDao;

public class OrderFilter implements HandlerInterceptor{
	@Autowired
	IOrderDao iorder;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		//将订单提前加载出来
		System.out.println("--------------------------进入order拦截器-------------------");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user.toString());
		//System.out.println("----------------------user-------------------");
		List<Order> orders = iorder.findOrderByUserid(user.getUserid());
		//System.out.println("----------------------.findOrderByUserid-------------------------");
		int length = orders.size();
		while(length>0){
			Integer paywayid = iorder.finPaywayIdByOrderId(orders.get(length-1).getOrderid());
			Payway payway = iorder.findPaywayByPaywayid(paywayid);
			System.out.println(orders.get(length-1).getUser());
			orders.get(length-1).setUser(user);
			orders.get(length-1).setPayway(payway);
			System.out.println(orders.get(length-1).toString());
			//orders.get(length-1).setPayway(iorder.findPaywayByPaywayid(orders.get(length-1).getPayway().getPaywayid()));
			//orders.get(length-1).toString();
			--length;
		}
		session.removeAttribute("order");
		session.setAttribute("orders", orders);
		return true;
	}

}
