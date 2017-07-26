package com.briup.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.briup.bean.Category;
import com.briup.bean.Orderline;
import com.briup.bean.Product;
import com.briup.common.exception.ServiceException;

@Component
public interface IProductService {
	//列出所有产品
	List<Product> listAllProducts() throws ServiceException;
	
	//通过产品id得到一个产品
	Product getProductByProductid(Integer productid) throws ServiceException;

	//通过产品id得到一个categoryid
	Integer findCategoryIdByProductid(String productid);

	//通过一个categoryid查找category
	Category findCategoryByCategoryid(Integer categoryid);

	Orderline findOrderlineByProductid(String productid);

	void setOrderline(Integer productid);

	//void deleteOrderline(int parseInt);
}


