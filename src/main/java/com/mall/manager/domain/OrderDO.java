package com.mall.manager.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
@Data
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//购买客户id
	private Integer accountId;
	//所属负责人（农场）
	private String owner;
	//购买客户名称
	private String accountName;
	//商品id
	private Integer productId;
	//商品名称
	private String productName;
	//商品名称
	private String productTitle;
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
	//监控视频地址
	private String videoUrl;
	//收货地址
	private String shipAddress;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;

}
