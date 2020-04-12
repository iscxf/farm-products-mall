package com.mall.manager.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单产品生长情况
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-12 12:13:06
 */
@Data
public class OrderProductGrowthDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//订单id
	private Integer orderId;
	//产品id
	private Integer productId;
	//生长情况描述
	private String description;
	//图片地址
	private String picture;
	//
	private Date createTime;
	//
	private Date updateTime;

}
