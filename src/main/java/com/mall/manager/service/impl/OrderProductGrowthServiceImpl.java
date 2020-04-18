package com.mall.manager.service.impl;

import com.mall.manager.dao.ProductDao;
import com.mall.manager.domain.ProductDO;
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
	@Autowired
	private ProductDao productDao;
	
	@Override
	public OrderProductGrowthDO get(Integer id){
		return orderProductGrowthDao.get(id);
	}
	
	@Override
	public List<OrderProductGrowthDO> list(Map<String, Object> map){
		List<OrderProductGrowthDO> orderProductGrowthList = orderProductGrowthDao.list(map);
		for (OrderProductGrowthDO o : orderProductGrowthList){
			ProductDO productDO = productDao.get(o.getProductId());
			if (null != productDO){
				o.setProductName(productDO.getName());
			}
		}
		return orderProductGrowthList;
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
