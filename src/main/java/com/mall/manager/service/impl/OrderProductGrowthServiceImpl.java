package com.mall.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.mall.manager.dao.OrderProductGrowthDao;
import com.mall.manager.domain.OrderProductGrowthDO;
import com.mall.manager.service.OrderProductGrowthService;



@Service
public class OrderProductGrowthServiceImpl implements OrderProductGrowthService {
	@Autowired
	private OrderProductGrowthDao orderProductGrowthDao;
	
	@Override
	public OrderProductGrowthDO get(Integer id){
		return orderProductGrowthDao.get(id);
	}
	
	@Override
	public List<OrderProductGrowthDO> list(Map<String, Object> map){
		return orderProductGrowthDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return orderProductGrowthDao.count(map);
	}
	
	@Override
	public int save(OrderProductGrowthDO orderProductGrowth){
		return orderProductGrowthDao.save(orderProductGrowth);
	}
	
	@Override
	public int update(OrderProductGrowthDO orderProductGrowth){
		return orderProductGrowthDao.update(orderProductGrowth);
	}
	
	@Override
	public int remove(Integer id){
		return orderProductGrowthDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return orderProductGrowthDao.batchRemove(ids);
	}
	
}
