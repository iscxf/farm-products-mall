package com.mall.manager.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2020-04-07 14:23:05
 */
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
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
	//下单时间
	private Long orderTime;
	//状态，0：申请审核中；1：审核通过待付款；2：已付款待确认；3：待发货；4：已完成
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

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：购买客户id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取：购买客户id
	 */
	public Integer getAccountId() {
		return accountId;
	}
	/**
	 * 设置：商品id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：商品价格
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：商品价格
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：数量
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	/**
	 * 获取：数量
	 */
	public Double getQuantity() {
		return quantity;
	}
	/**
	 * 设置：订单总金额
	 */
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	/**
	 * 获取：订单总金额
	 */
	public Double getOrderAmount() {
		return orderAmount;
	}
	/**
	 * 设置：下单时间
	 */
	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * 获取：下单时间
	 */
	public Long getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置：状态，0：申请审核中；1：审核通过待付款；2：已付款待确认；3：待发货；4：已完成
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态，0：申请审核中；1：审核通过待付款；2：已付款待确认；3：待发货；4：已完成
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：收货人
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	/**
	 * 获取：收货人
	 */
	public String getShipName() {
		return shipName;
	}
	/**
	 * 设置：收货人联系电话
	 */
	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}
	/**
	 * 获取：收货人联系电话
	 */
	public String getShipPhone() {
		return shipPhone;
	}
	/**
	 * 设置：收货地址
	 */
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	/**
	 * 获取：收货地址
	 */
	public String getShipAddress() {
		return shipAddress;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
