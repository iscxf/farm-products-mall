package com.mall.manager.service;

import com.mall.manager.domain.OrderDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
public interface OrderService {
	
	OrderDO get(Integer id);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
