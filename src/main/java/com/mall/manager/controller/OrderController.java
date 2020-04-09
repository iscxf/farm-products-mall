package com.mall.manager.controller;

import java.util.List;
import java.util.Map;

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

import com.mall.manager.domain.OrderDO;
import com.mall.manager.service.OrderService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.common.utils.Result;

/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
 
@Controller
@RequestMapping("/manager/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
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
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
//	@RequiresPermissions("manager:order:add")
	String add(){
	    return "manager/order/add";
	}

	@GetMapping("/edit/{id}")
//	@RequiresPermissions("manager:order:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
	    return "manager/order/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
//	@RequiresPermissions("manager:order:add")
	public Result save( OrderDO order){
		if(orderService.save(order)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("manager:order:edit")
	public Result update( OrderDO order){
		orderService.update(order);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("manager:order:remove")
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
	@RequiresPermissions("manager:order:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		orderService.batchRemove(ids);
		return Result.ok();
	}
	
}
