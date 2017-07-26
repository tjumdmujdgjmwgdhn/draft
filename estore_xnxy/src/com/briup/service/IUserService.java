package com.briup.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.briup.bean.Contactinfo;
import com.briup.bean.User;
import com.briup.common.exception.ServiceException;


public interface IUserService {
	//更新用户信息
	void updateUserinfo(User user) throws ServiceException;
	
	//注册一个新用户
	void registerUser(User user) throws ServiceException;
	
	//登录
	User login(String userid,String password) throws ServiceException;

	void registerContactinfo(Contactinfo contact);
	
	/**
	 * 查看该用户是否存在
	 * @param userid
	 * @return
	 */
	User findUser(String userid);

	List<User> findAllUser();
	
	Contactinfo findContact(String userid);

	void updateContact(Contactinfo contact);
}
