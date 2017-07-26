package com.briup.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.briup.bean.Contactinfo;
import com.briup.bean.Product;
import com.briup.bean.User;
import com.briup.dao.IProductDao;
import com.briup.dao.IUserDao;

public class UserFilter implements HandlerInterceptor{
	@Autowired
	IUserDao iuser;
	String userid = null;
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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		//通过查询userid来判断该用户是否存在
		System.out.println("---------------------进入------------------------");
		String userid = request.getParameter("userid");
		User user = iuser.findUserByUserid(userid);
		System.out.println("--------------"+user);
		if(user!=null){
			Contactinfo contact = iuser.findContact(userid);
			//System.out.println("------------contact------------------"+contact.toString());
			user.setContactinfo(contact);
			System.out.println("----------拦截器-------------");
			//将数据库中的用户加载出来放到session容器当中
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			session.setAttribute("user", user);
		}
		return true;
	}

}
