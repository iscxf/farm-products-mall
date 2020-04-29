package com.mall.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.controller.BaseController;
import com.mall.system.domain.UserDO;
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

import com.mall.manager.domain.ProductDO;
import com.mall.manager.service.ProductService;
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
@RequestMapping("/manager/product")
public class ProductController extends BaseController {
	@Autowired
	private ProductService productService;
	
	@GetMapping()
//	@RequiresPermissions("manager:product:product")
	String Product(){
	    return "manager/product/product";
	}

	@GetMapping("/open/{id}")
	String getProductDetail(@PathVariable("id") Integer productId, Model model) {
		ProductDO productDO = productService.get(productId);
		if (null == productDO){
			return "manager/product/notExist";
		}

		model.addAttribute("productDO", productDO);
		return "manager/product/detail";
	}

	/**
	 * 商城首页的商品查询
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/mall/list")
//	@RequiresPermissions("manager:product:product")
	public PageUtils mallList(@RequestParam Map<String, Object> params){
		//查询列表数据 修改测试
        Query query = new Query(params);
		List<ProductDO> productList = productService.list(query);
		int total = productService.count(query);
		PageUtils pageUtils = new PageUtils(productList, total);
		return pageUtils;
	}

	/**
	 * 商城首页的商品查询
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/mall/list/{keyword}")
//	@RequiresPermissions("manager:product:product")
	public PageUtils mallKeywordList(@PathVariable("keyword") String keyword, @RequestParam Map<String, Object> params){
		params.put("name",keyword);
		//查询列表数据 修改测试
		Query query = new Query(params);
		List<ProductDO> productList = productService.list(query);
		int total = productService.count(query);
		PageUtils pageUtils = new PageUtils(productList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("manager:product:product")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//只能看自己的商品
		List<Long> roles = getUserRole();
		Long userId = getUserId();
		if (!roles.contains(1L)) {
			params.put("owner", userId.toString());
		}
		//查询列表数据
		Query query = new Query(params);
		List<ProductDO> productList = productService.list(query);
		int total = productService.count(query);
		PageUtils pageUtils = new PageUtils(productList, total);
		return pageUtils;
	}

	/**
	 * @return
	 */
	@ResponseBody
	@GetMapping("/mall/hotProductList")

	public PageUtils hotProductList(){

		List<ProductDO> productList = productService.hotProductList();

		PageUtils pageUtils = new PageUtils(productList, 5);

		return pageUtils;
	}




	@GetMapping("/add")
	@RequiresPermissions("manager:product:add")
	String add(){
	    return "manager/product/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("manager:product:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ProductDO product = productService.get(id);
		//只能看自己的商品
		List<Long> roles = getUserRole();
		UserDO userInfo = getUser();
		if (!userInfo.getUserId().toString().equals(product.getOwner()) && !roles.contains(1L)){
			return "/error/403";
		}
		model.addAttribute("product", product);
	    return "manager/product/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("manager:product:add")
	public Result save( ProductDO product){
		UserDO userInfo = getUser();
		if (null == userInfo){
			return Result.error("获取用户信息失败！请重新登录重试！");
		}
		product.setFarm(userInfo.getStoreName());
		product.setOwner(userInfo.getUserId().toString());
		product.setStatus("0");
		if(productService.save(product)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("manager:product:edit")
	public Result update( ProductDO product){
		productService.update(product);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("manager:product:remove")
	public Result remove( Integer id){
		if(productService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("manager:product:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		productService.batchRemove(ids);
		return Result.ok();
	}
	
}
