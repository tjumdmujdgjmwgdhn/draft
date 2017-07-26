package com.briup.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.briup.bean.Contactinfo;
import com.briup.bean.User;
import com.briup.common.exception.ServiceException;
import com.briup.service.IUserService;
import com.briup.service.IUserServiceImpl;

//自动注入（配置处理器）
@Controller
public class UserController {
	@Resource
	IUserService iuser;
	//判断该用户名是否存在
	@RequestMapping("/checkUserName")
	public void CheckUserName(HttpServletRequest req,HttpServletResponse resp){
		String password = null;
		String userid = req.getParameter("userid");
		System.out.println(userid);
		User jUser = iuser.findUser(userid);
		System.out.println(jUser);
	       //用户名是否存在的标志   如果通过用户名能查的到密码，就说明存在，如果查不出，就不存在
		if(jUser == null){
			password = null;
		}else{
			password = jUser.getPassword();
		}
		//String password = jUser.getPassword();
		System.out.println(password);
        Map<String,Object> map = new HashMap<String,Object>();    
        map.put("flag", password);
	    String json = JSONObject.fromObject(map).toString();          
	    //将数据返回  
	    resp.setCharacterEncoding("UTF-8");  
        try {
	        resp.flushBuffer();
	    	resp.getWriter().write(json);  
	    	resp.getWriter().flush();    
	    	resp.getWriter().close();			
	       } catch (IOException e1) {
	    	   // TODO Auto-generated catch block
	    	   e1.printStackTrace();
	    }
	}
	@RequestMapping("/ToLogin")
	public String Login(HttpServletRequest req){
		System.out.println("tologin");
		return "login.jsp";
	}
	//登录
	@RequestMapping("/login.action")
	public String ToLogin(String userid,String password,HttpServletRequest req,HttpServletResponse resp){		//,String userid,String password
		User user = iuser.findUser(userid);
		System.out.println("----------------LoginServlet----------------");
		System.out.println("000000000000000000000");
		//HttpSession session = null;
		if(user!=null&&userid.equals(user.getUserid()) && password.equals(user.getPassword())){
			//session = req.getSession();
			//session.setAttribute("userid", userid);
			return "index.jsp";
			}
		return "login.jsp";
		//session.removeAttribute("userid");
	}
	
	//注册
	@RequestMapping("/ToRegister")
	public String toRegister(HttpServletRequest req,HttpServletResponse resp){
		//先判断该用户在系统中是否存在
		return "register.jsp";
	}
	
	//注册新用户
	@RequestMapping("/register.action")
	public String Register(User user,Contactinfo contact,HttpServletRequest req,HttpServletResponse resp){
		//先判断该用户在系统中是否存在
		System.out.println("--------------------------注册-------------------------------");
		String userid = req.getParameter("userid");
		System.out.println(userid);
        	contact.setUser(user);
    		System.out.println(contact.toString());
    		try {
    			iuser.registerUser(user);
    			iuser.registerContactinfo(contact);
    		} catch (ServiceException e) {
    			e.printStackTrace();
    		}
		return "success.jsp";
	}
	
	@RequestMapping("/ToIndex")
	public String Index(){
		return "index.jsp";
	}
	
	@RequestMapping("/ToModify")
	public String ModifyInfo(User user,Contactinfo contact){
		System.out.println("-----------modify------------");
		System.out.println(user);
		contact.setUser(user);
		System.out.println(contact);
		try {
			iuser.updateUserinfo(user);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iuser.updateContact(contact);
		return "modify.action";
	}
	@RequestMapping("/modify.action")
	public String ModifyJump(){
		return "modifysuccess.jsp";
	}
	
}
