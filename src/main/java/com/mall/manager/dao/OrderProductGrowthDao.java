package com.mall.manager.dao;

import com.mall.manager.domain.OrderProductGrowthDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 订单产品生长情况
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-12 12:13:06
 */
@Mapper
public interface OrderProductGrowthDao {

	OrderProductGrowthDO get(Integer id);
	
	List<OrderProductGrowthDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderProductGrowthDO orderProductGrowth);
	
	int update(OrderProductGrowthDO orderProductGrowth);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
