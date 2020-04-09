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
public class ProductDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//产品名称
	private String name;
	//页面展示标题
	private String title;
	//商品展示图片
	private String picture;
	//商品类别
	private String type;
	//价格
	private Double price;
	//库存
	private Double stock;
	//商品详情描述
	private String description;
	//商品所属负责人（农场）
	private String owner;
	//农场名称
	private String farm;
	//状态，0：正常上架； 1：已下架
	private String status;
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
	 * 设置：产品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：产品名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：页面展示标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：页面展示标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：商品展示图片
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * 获取：商品展示图片
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * 设置：商品类别
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：商品类别
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：库存
	 */
	public void setStock(Double stock) {
		this.stock = stock;
	}
	/**
	 * 获取：库存
	 */
	public Double getStock() {
		return stock;
	}
	/**
	 * 设置：商品详情描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：商品详情描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：商品所属负责人（农场）
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * 获取：商品所属负责人（农场）
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * 设置：农场名称
	 */
	public void setFarm(String farm) {
		this.farm = farm;
	}
	/**
	 * 获取：农场名称
	 */
	public String getFarm() {
		return farm;
	}
	/**
	 * 设置：状态，0：正常上架； 1：已下架
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态，0：正常上架； 1：已下架
	 */
	public String getStatus() {
		return status;
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
