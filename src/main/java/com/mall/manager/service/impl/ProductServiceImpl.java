package com.mall.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.mall.manager.dao.ProductDao;
import com.mall.manager.domain.ProductDO;
import com.mall.manager.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Override
	public ProductDO get(Integer id){
		return productDao.get(id);
	}
	
	@Override
	public List<ProductDO> list(Map<String, Object> map){
		return productDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return productDao.count(map);
	}
	
	@Override
	public int save(ProductDO product){

		return productDao.save(product);
	}
	
	@Override
	public int update(ProductDO product){
		return productDao.update(product);
	}
	
	@Override
	public int remove(Integer id){
		return productDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return productDao.batchRemove(ids);
	}
	
}
