package com.mall.manager.controller;

import com.mall.common.controller.BaseController;
import com.mall.common.utils.Result;
import com.mall.manager.domain.OrderDO;
import com.mall.manager.domain.ProductDO;
import com.mall.manager.service.OrderService;
import com.mall.manager.service.ProductService;
import com.mall.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @classname ClaimController
 * @description TODO
 * @date 2020-04-11 23:23
 * @versino v1.0
 */
@Controller
@RequestMapping("manager/claim")
public class ClaimController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * 申领产品接口
     * @param id 产品id
     * @param quantity 申领数量
     * @return
     */
    @GetMapping("/apply/{id}/{quantity}")
    public Result applyProduct(@PathVariable("id") Integer id, @PathVariable("quantity") double quantity) {
        UserDO userInfo = getUser();
        ProductDO productDO = productService.get(id);

        OrderDO orderDO = new OrderDO();
        orderDO.setAccountId(Integer.parseInt(userInfo.getUserId().toString()));
        orderDO.setProductId(productDO.getId());
        orderDO.setPrice(productDO.getPrice());
        orderDO.setQuantity(quantity);
        orderDO.setOrderAmount(productDO.getPrice() * quantity);
        orderDO.setOrderTime(new Date());
        orderDO.setStatus("0");

        if (orderService.save(orderDO) > 0) {
            productDO.setStock(productDO.getStock() - quantity);

            if (productService.update(productDO) > 0)
                return Result.ok();
        }

        return Result.error();
    }

    /**
     * 付款
     * @param orderDO 代付款订单信息
     * @return
     */
    @PostMapping("/pay")
    public Result pay(OrderDO orderDO) {

        orderDO.setStatus("2");

        if (orderService.update(orderDO) > 0) {
            return Result.ok();
        }

        return Result.error();
    }
}
