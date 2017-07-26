package com.briup.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.briup.bean.Order;
import com.briup.bean.Payway;
import com.briup.bean.User;
import com.briup.common.exception.DataAccessException;
import com.briup.common.exception.ServiceException;
import com.briup.dao.IOrderDao;

@Service
public class IOrderServiceImpl implements IOrderService{
	@Resource
	IOrderDao iorder;

	@Override
	public List listOrdersOfUser(String userid) throws ServiceException {
		// TODO Auto-generated method stub
		List<Order> orders = null;
		try {
			orders = iorder.findOrderByUserid(userid);
			int length = orders.size();
			System.out.println("-------------进入orderimpl--------------");
			while(length>0){
				System.out.println(orders.get(length-1));
				--length;
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public void removeOrder(Integer orderid) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			iorder.deleteOrder(orderid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveOrder(Order order) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			iorder.saveOrder(order);
			System.out.println("-----------orderimpl-----------"+order.toString());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map listAllPayways() throws ServiceException {
		// TODO Auto-generated method stub
		Map payways = null;
		try {
			payways = iorder.findAllPayway();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payways;
	}

	@Override
	public Order listOrderByOrderid(Integer orderid) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payway findPaywayByPaywayid(Integer paywayid) {
		// TODO Auto-generated method stub
		Payway payway = iorder.findPaywayByPaywayid(paywayid);
		return payway;
	}

	@Override
	public Integer finPaywayIdByOrderId(Integer orderid) {
		// TODO Auto-generated method stub
		Integer payway = iorder.finPaywayIdByOrderId(orderid);
		return payway;
	}

}
