package com.mall.manager.controller;

import com.mall.common.controller.BaseController;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.common.utils.Result;
import com.mall.manager.domain.OrderArg;
import com.mall.manager.domain.OrderDO;
import com.mall.manager.domain.ProductDO;
import com.mall.manager.service.OrderService;
import com.mall.manager.service.ProductService;
import com.mall.system.domain.UserDO;
import com.mall.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
 
@Controller
@RequestMapping("/manager/order")
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	@GetMapping()
//	@RequiresPermissions("manager:order:order")
	String Order(){
	    return "manager/order/order";
	}
	
	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("manager:order:order")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		List<Long> roles = getUserRole();
		Long userId = getUserId();
		if (!roles.contains(1L)) {
			params.put("owner", userId.toString());
		}
		Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		return new PageUtils(orderList, total);
	}

	@ResponseBody
	@GetMapping("/userOrder")
	public PageUtils userOrder(@RequestParam Map<String, Object> params){
		Long userId = getUserId();
		params.put("account_id", Integer.valueOf(userId.toString()));
		//查询列表数据
		Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "manager/order/add";
	}

	@GetMapping("/claim/{id}")
	String claim(@PathVariable("id") Integer productId,Model model){
		ProductDO productDO = productService.get(productId);
		if (null == productDO){
			return "manager/product/notExist";
		}
		UserDO userDO = getUser();
		if (null == userDO){
			return "/login";
		}
		model.addAttribute("user", getUser());
		model.addAttribute("productDO", productDO);
		return "manager/index/placeOrder";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
	    return "manager/order/edit";
	}

	@GetMapping("/video/get/{id}")
	String getVideo(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
		model.addAttribute("videoUrl", "http://vjs.zencdn.net/v/oceans.mp4");
		return "manager/orderProductGrowth/video";
	}

	@GetMapping("/video/add/{id}")
	@RequiresPermissions("manager:order:add")
	String addVideo(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
		model.addAttribute("videoUrl", "http://vjs.zencdn.net/v/oceans.mp4");
		return "manager/orderProductGrowth/addVideo";
	}
	
	/**
	 * 保存订单同时返回uuid
	 */
	@ResponseBody
	@PostMapping("/save")
	public Result save(OrderArg arg){
		arg.setShipAddress(arg.getCity() + arg.getProvince() + arg.getDistrict() + arg.getAddress());
		arg.setAccountId(Integer.valueOf(getUserId().toString()));
		return orderService.save(arg);
	}

	@GetMapping("/payCode/{uuid}")
	String getPayCode(@PathVariable("uuid") String uuid, Model model){
		String qrCodeUrl = orderService.getQrCodeUrl(uuid);
		model.addAttribute("qrCodeUrl", qrCodeUrl);
		return "manager/order/payCode";
	}

	@GetMapping("/pay/{uuid}")
	String getPay(@PathVariable("uuid") String uuid, Model model){
		OrderDO order = orderService.getOrderByQrCode(uuid);
		model.addAttribute("order", order);
		model.addAttribute("uuid", uuid);
		return "manager/order/pay";
	}

	/**
	 * 付款
	 */
	@ResponseBody
	@GetMapping("/payForOrder/{uuid}")
	public Result payForOrder(@PathVariable("uuid") String uuid){
		return orderService.pay(uuid);
	}

	/**
	 * 重新生成付款码
	 */
	@ResponseBody
	@GetMapping("/retryPay/{orderId}")
	public Result retryPay(@PathVariable("orderId") Integer orderId){
		return orderService.getUUIDAndCacheByOrderId(orderId);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Result update( OrderDO order){
		orderService.update(order);
		return Result.ok();
	}

	/**
	 * 取消订单
	 */
	@ResponseBody
	@RequestMapping("/cancelOrder/{orderId}")
	public Result cancelOrder(@PathVariable("orderId")Integer orderId) {
		Long userId = getUserId();
		return orderService.cancelOrder(userId, orderId);
	}

	/**
	 * 修改订单状态
	 */
	@PostMapping( "/changeStatus")
	@ResponseBody
	public Result changeStatus(Integer orderId, Integer status){
		if (null == orderId || null == status){
			return Result.error("参数错误！");
		}
		OrderDO order = new OrderDO();
		order.setId(orderId);
		order.setStatus(status.toString());
		orderService.update(order);
		return Result.ok();
	}
	@GetMapping("/harvest/{id}")
	String harvest(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
		return "manager/order/edit";
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove( Integer id){
		if(orderService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		orderService.batchRemove(ids);
		return Result.ok();
	}
	
}
