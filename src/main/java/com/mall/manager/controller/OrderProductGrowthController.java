package com.mall.manager.controller;

import java.util.List;
import java.util.Map;

import com.mall.common.controller.BaseController;
import com.mall.manager.domain.OrderDO;
import com.mall.manager.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.manager.domain.OrderProductGrowthDO;
import com.mall.manager.service.OrderProductGrowthService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.common.utils.Result;

/**
 * 订单产品生长情况
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-12 12:13:06
 */
 
@Controller
@RequestMapping("/manager/orderProductGrowth")
public class OrderProductGrowthController extends BaseController {
	@Autowired
	private OrderProductGrowthService orderProductGrowthService;
	@Autowired
	private OrderService orderService;

	
	@GetMapping()
	@RequiresPermissions("manager:order:add")
	String OrderProductGrowth(){
	    return "manager/orderProductGrowth/orderProductGrowth";
	}

	@GetMapping("/{orderId}")
	@RequiresPermissions("manager:order:add")
	String getOrderProductGrowthByOrderId(@PathVariable("orderId") Integer orderId, @RequestParam Map<String, Object> params, Model model){
		model.addAttribute("orderId", orderId);
		return "manager/orderProductGrowth/userOrderProductGrowth";
	}


	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderProductGrowthDO> orderProductGrowthList = orderProductGrowthService.list(query);
		int total = orderProductGrowthService.count(query);
		PageUtils pageUtils = new PageUtils(orderProductGrowthList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/list/{orderId}")
	public PageUtils listByOrderId(@PathVariable("orderId") Integer orderId, @RequestParam Map<String, Object> params, Model model){
		params.put("orderId",orderId);
		//查询列表数据
		Query query = new Query(params);
		List<OrderProductGrowthDO> orderProductGrowthList = orderProductGrowthService.list(query);
		int total = orderProductGrowthService.count(query);
		PageUtils pageUtils = new PageUtils(orderProductGrowthList, total);
		return pageUtils;
	}
	
	@GetMapping("/add/{id}")
	@RequiresPermissions("manager:order:add")
	String add(@PathVariable("id") Integer orderId, Model model){
		OrderDO order = orderService.get(orderId);
		model.addAttribute("order", order);
	    return "manager/orderProductGrowth/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("manager:order:add")
	String edit(@PathVariable("id") Integer id,Model model){
		OrderProductGrowthDO orderProductGrowth = orderProductGrowthService.get(id);
		model.addAttribute("orderProductGrowth", orderProductGrowth);
	    return "manager/orderProductGrowth/edit";
	}


	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("manager:order:add")
	public Result save( OrderProductGrowthDO orderProductGrowth){
		if(orderProductGrowthService.save(orderProductGrowth)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("manager:order:add")
	public Result update( OrderProductGrowthDO orderProductGrowth){
		orderProductGrowthService.update(orderProductGrowth);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove( Integer id){
		if(orderProductGrowthService.remove(id)>0){
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
		orderProductGrowthService.batchRemove(ids);
		return Result.ok();
	}
	
}
