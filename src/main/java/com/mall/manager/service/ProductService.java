package com.mall.manager.service;

import com.mall.manager.domain.ProductDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
public interface ProductService {
	
	ProductDO get(Integer id);
	
	List<ProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProductDO product);
	
	int update(ProductDO product);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
