package com.mall.manager.service.impl;

import com.mall.manager.dao.ProductDao;
import com.mall.manager.domain.ProductDO;
import com.mall.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.mall.manager.dao.OrderDao;
import com.mall.manager.domain.OrderDO;
import com.mall.manager.service.OrderService;



@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public OrderDO get(Integer id){
		return orderDao.get(id);
	}
	
	@Override
	public List<OrderDO> list(Map<String, Object> map){
		List<OrderDO> orderList = orderDao.list(map);
		for (OrderDO o : orderList){
			Integer accountId = o.getAccountId();
			//购买客户名称
			String accountName = userDao.get(Long.valueOf(accountId)).getName();
			o.setAccountName(accountName);
			//商品id
			Integer productId = o.getProductId();
			ProductDO product = productDao.get(productId);
			if (null != product) {
				o.setProductName(product.getName());
				o.setProductTitle(product.getTitle());
			}
		}
		return orderList;
	}
	
	@Override
	public int count(Map<String, Object> map){
		return orderDao.count(map);
	}
	
	@Override
	public int save(OrderDO order){
		//相应的减去产品的库存
		Integer productId = order.getProductId();
		ProductDO productDO = productDao.get(productId);
		if (null != productDO) {
			productDO.setStock(productDO.getStock() - order.getQuantity());
			productDao.update(productDO);
		}
		return orderDao.save(order);
	}
	
	@Override
	public int update(OrderDO order){
		return orderDao.update(order);
	}
	
	@Override
	public int remove(Integer id){
		return orderDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return orderDao.batchRemove(ids);
	}
	
}
