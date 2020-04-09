package com.mall.manager.dao;

import com.mall.manager.domain.ProductDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
@Mapper
public interface ProductDao {

	ProductDO get(Integer id);
	
	List<ProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProductDO product);
	
	int update(ProductDO product);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
