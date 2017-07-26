package com.briup.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.briup.bean.Category;
import com.briup.bean.Orderline;
import com.briup.bean.Product;
import com.briup.common.exception.DataAccessException;
import com.briup.common.exception.ServiceException;
import com.briup.dao.IProductDao;

@Service
public class IProductServiceImpl implements IProductService{
	@Resource
	IProductDao iproduct;

	@Override
	public List<Product> listAllProducts() throws ServiceException {
		// TODO Auto-generated method stub
		List<Product> products = null;
		try {
			products = iproduct.findAllProducts();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product getProductByProductid(Integer productid)
			throws ServiceException {
		// TODO Auto-generated method stub
		Product product = null;
		try {
			product = iproduct.findProductById(productid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Integer findCategoryIdByProductid(String productid) {
		// TODO Auto-generated method stub
		Integer categoryid = iproduct.findCategoryIdByProductid(productid);
		return categoryid;
	}

	@Override
	public Category findCategoryByCategoryid(Integer categoryid) {
		// TODO Auto-generated method stub
		Category category = iproduct.findCategoryByCategoryid(categoryid);
		return category;
	}

	@Override
	public Orderline findOrderlineByProductid(String productid) {
		// TODO Auto-generated method stub
		Orderline orderline = iproduct.findOrderlineByProductid(productid);
		return orderline;
	}

	@Override
	public void setOrderline(Integer productid) {
		// TODO Auto-generated method stub
		iproduct.setOrderline(productid);
	}

	/*@Override
	public void deleteOrderline(int parseInt) {
		// TODO Auto-generated method stub
		iproduct.deleteOrderline()
	}*/

}
