package com.briup.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.briup.bean.Category;
import com.briup.bean.Orderline;
import com.briup.bean.Product;
import com.briup.common.exception.DataAccessException;

@Component
public interface IProductDao {
	//查询所有产品
	List<Product> findAllProducts() throws DataAccessException;
	
	//通过产品id查找一个产品
	Product findProductById(Integer productid) throws DataAccessException;
	
	//通过产品id查找categoryid
	Integer findCategoryIdByProductid(String productid);

	//通过categoryid查找category
	Category findCategoryByCategoryid(Integer categoryid);

	Orderline findOrderlineByProductid(String productid);

	void setOrderline(Integer productid);
}
