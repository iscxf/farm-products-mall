package com.mall.system.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long userId;
    //用户名
    private String username;
    //姓名
    private String name;
    //密码
    private String password;
    // 部门
    private Long deptId;
    private String deptName;

    //邮箱
    private String email;
    //手机号
    private String mobile;
    //状态 0:禁用，1:正常
    private String status;
    //性别
    private String sex;

    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    //角色
    private List<Long> roleIds;
    //出生日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
    //详细地址
    private String address;
    //账号类型
    private String type;
    //身份证
    private String identity;
    //农场名称
    private String storeName;
    //规模
    private String scope;
    //简介
    private String introduction;
    //省份
    private String storeProvince;
    //所在城市
    private String storeCity;
    //所在地区
    private String storeDistrict;
    //详细地址
    private String storeAddress;


}
