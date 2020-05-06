package com.mall.manager.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mall.common.config.FieldConfig;
import com.mall.common.utils.Result;
import com.mall.manager.dao.ProductDao;
import com.mall.manager.domain.OrderArg;
import com.mall.manager.domain.ProductDO;
import com.mall.manager.service.ProductService;
import com.mall.manager.utils.QRCodeUtil;
import com.mall.manager.utils.UUIDUtil;
import com.mall.system.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.mall.manager.dao.OrderDao;
import com.mall.manager.domain.OrderDO;
import com.mall.manager.service.OrderService;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;

	@Autowired
	FieldConfig fieldConfig;

	@Value("${host}")
	private String host;

	@Autowired
	private ProductService productService;

	private LoadingCache<String, Map<String, Object>> loadingCache;

	//服务启动的时候初始化value值为"",缓存有效期15分钟
	@PostConstruct
	private void init(){
		loadingCache = CacheBuilder.newBuilder().
				expireAfterWrite(15, TimeUnit.MINUTES).build(new CacheLoader<String, Map<String, Object>>() {
			@Override
			public Map<String, Object> load(String key) {
				return null;
			}
		});
	}

	private static final String ORDER_NAME_SPACE = "order_";
	
	@Override
	public OrderDO get(Integer id){
		return orderDao.get(id);
	}

	@Override
	public String getQrCodeUrl(String uuid) {
		return "/files/qrCode/" + uuid + ".jpg";
	}

	@Override
	public OrderDO getOrderByQrCode(String uuid) {
		Map<String, Object> orderCache = getCache(ORDER_NAME_SPACE, uuid);
		if (CollectionUtils.isEmpty(orderCache)){
			return null;
		}
		Integer orderId= (Integer)orderCache.get("orderId");
		return get(orderId);
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
	public Result save(OrderArg arg){
		ProductDO productDO = productService.get(arg.getProductId());
		arg.setOrderAmount(productDO.getPrice() * arg.getQuantity());
		OrderDO order = new OrderDO();
		BeanUtils.copyProperties(arg, order);
		order.setOrderTime(new Date());
		order.setPrice(productDO.getPrice());
		//默认未付款 -1
		order.setStatus("-1");
		//相应的减去产品的库存
		productDO.setStock(productDO.getStock() - order.getQuantity());
		productDao.update(productDO);
		order.setOwner(productDO.getOwner());
		log.info("trace order info:[{}]",order);
		Integer orderResult = orderDao.save(order);
		log.info("trace order orderResult:[{}]",orderResult);
		Integer orderId = order.getId();
		log.info("trace order after:[{}]",order);
		if (null == orderId){
			return Result.error();
		}
		//生成uuid缓存起来
		String uuid = UUIDUtil.getUUID();
		Map<String, Object> cacheObject = new HashMap<>(16);
		cacheObject.put("uuid", uuid);
		cacheObject.put("orderId", orderId);
		log.info("trace cacheObject:[{}]",cacheObject);
		Result cacheResult = putCache(ORDER_NAME_SPACE, uuid, cacheObject);
		if (!cacheResult.get("code").equals(0)){
			return Result.error();
		}
		String text = host + "/manager/order/pay/" + uuid;
		try {
			//获取logo的路径
			URL url = ClassLoader.getSystemResource("static/img/farm_mall_logo.jpg");
			String logoPath = url.getPath();
			//生成二维码
			QRCodeUtil.encode(text, logoPath, fieldConfig.getUploadPath() + "qrCode/", uuid, true);
		}catch (Exception e){
			log.error("create QRCode error!e",e);
			return Result.error(e.getMessage());
		}
		return Result.ok().put("uuid", uuid);
	}

	/**
	 * 付款，清除缓存
	 * @param uuid
	 * @return
	 */
	@Override
	public Result pay(String uuid) {
		Map<String, Object> orderCache = getCache(ORDER_NAME_SPACE, uuid);
		if (CollectionUtils.isEmpty(orderCache)){
			return Result.error("二维码已过期请重新获取！");
		}
		Integer orderId= (Integer)orderCache.get("orderId");
		OrderDO order = new OrderDO();
		order.setId(orderId);
		order.setStatus("0");
		update(order);
		invalidateCache(ORDER_NAME_SPACE, uuid);
		return Result.ok();
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

	private Map<String, Object> getCache(String nameSpace, String key) {
		if (StringUtils.isEmpty(key)){
			return null;
		}
		try {
			String cacheKey = nameSpace.concat(key);
			Map<String, Object> cacheString = loadingCache.get(cacheKey);
			if (cacheString.isEmpty()){
				return null;
			}else {
				return cacheString;
			}
		} catch (ExecutionException e) {
			log.error("trace checkCache error!nameSpace:[{}], key:[{}], e:", nameSpace, key, e);
			return null;
		}
	}

	private Result putCache(String nameSpace, String key, Map<String, Object> value) {
		if (StringUtils.isEmpty(key)){
			return Result.error(-1, "PARAMETER_ERROR");
		}
		try {
			String cacheKey = nameSpace.concat(key);
			loadingCache.put(cacheKey, value);
		} catch (Exception e) {
			log.error("trace putCache error!nameSpace:[{}], key:[{}], e:", nameSpace, key, e);
			return Result.error(-2, "SYSTEM_ERROR");
		}
		return Result.ok();
	}

	//清除缓存
	private void invalidateCache(String nameSpace, String key) {
		if (StringUtils.isEmpty(key)){
			log.error("trace invalidateCache key is null!");
			return;
		}
		loadingCache.invalidate(nameSpace.concat(key));
	}


}
