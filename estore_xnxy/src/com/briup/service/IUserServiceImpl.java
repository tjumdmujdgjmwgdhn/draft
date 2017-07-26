package com.briup.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.briup.bean.Contactinfo;
import com.briup.bean.User;
import com.briup.common.exception.DataAccessException;
import com.briup.common.exception.ServiceException;
import com.briup.dao.IUserDao;

@Service
public class IUserServiceImpl implements IUserService{
	@Resource
	IUserDao iuser;

	@Override
	public void updateUserinfo(User user) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			iuser.updateUser(user);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void registerUser(User user) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			iuser.saveUser(user);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void registerContactinfo(Contactinfo contact) {
		iuser.saveContact(contact);
	}
	
	//通过用户id查找一个用户
	public User findUser(String userid) {
		User user = null;
		try {
			user = iuser.findUserByUserid(userid);
			//System.out.println();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User login(String userid, String password) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllUser() {
		List<User> user = iuser.findAllUser();
		return user;
	}

	@Override
	public Contactinfo findContact(String userid) {
		// TODO Auto-generated method stub
		Contactinfo contact = iuser.findContact(userid);
		return contact;
	}

	//更新用户的详细信息
	@Override
	public void updateContact(Contactinfo contact) {
		// TODO Auto-generated method stub
		iuser.updateContact(contact);
	}

	

	

}
