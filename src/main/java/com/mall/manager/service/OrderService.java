package com.mall.manager.service;

import com.mall.common.utils.Result;
import com.mall.manager.domain.OrderArg;
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

	String getQrCodeUrl(String uuid);

	OrderDO getOrderByQrCode(String uuid);

	Result getUUIDAndCacheByOrderId(Integer orderId);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);

	Result save(OrderArg arg);

	Result pay(String uuid);
	
	int update(OrderDO order);

	Result cancelOrder(Long userId, Integer orderId);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
