package com.mall.manager.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author chenxf
 * Created on 2020/4/13
 */
@Data
public class OrderVO {

    private Integer id;
    //购买客户id
    private Integer accountId;
    //购买客户id
    private String accountName;
    //商品id
    private Integer productId;
    //商品id
    private String productName;
    //商品价格
    private Double price;
    //数量
    private Double quantity;
    //订单总金额
    private Double orderAmount;
    //下单时间
    private Date orderTime;
    //状态
    private String status;
    //收货人
    private String shipName;
    //收货人联系电话
    private String shipPhone;
    //收货地址
    private String shipAddress;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}
