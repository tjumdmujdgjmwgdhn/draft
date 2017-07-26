package com.briup.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.briup.bean.Contactinfo;
import com.briup.bean.User;
import com.briup.common.exception.DataAccessException;

@Component
public interface IUserDao {
	//保存一个用户
	void saveUser(User user) throws DataAccessException;
	void saveContact(Contactinfo contact);
	
	//通过用户id查找一个用户
	User findUserByUserid(String userid) throws DataAccessException;
	
	//查找所有的用户
	List<User> findAllUser();
	
	/**
	 * 通过userid查找对应的详细信息
	 * @param userid
	 * @return
	 */
	Contactinfo findContact(String userid);
	
	//更新一个用户
	void updateUser(User user) throws DataAccessException;
	
	/**
	 * 更新用户的详细信息
	 * @param contact
	 */
	void updateContact(Contactinfo contact);

	

}
