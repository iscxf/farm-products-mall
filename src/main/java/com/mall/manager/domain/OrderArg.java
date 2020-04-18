package com.mall.manager.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxf
 * Created on 2020/4/13
 */
@Data
public class OrderArg implements Serializable {
    private static final long serialVersionUID = 1L;
    //购买客户id
    private Integer accountId;
    //商品id
    private Integer productId;
    //商品价格
    private Double price;
    //数量
    private Double quantity;
    //订单总金额
    private Double orderAmount;
    //状态
    private String status;
    //收货人
    private String shipName;
    //收货人联系电话
    private String shipPhone;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
    //详细地址
    private String address;
    //收货地址
    private String shipAddress;
    //备注
    private String remark;
}
