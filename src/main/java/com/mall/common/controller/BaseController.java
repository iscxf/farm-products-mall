package com.mall.common.controller;

import com.mall.system.service.RoleService;
import com.mall.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.mall.common.utils.ShiroUtils;
import com.mall.system.domain.UserDO;

import java.util.List;

@Controller
public class BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public UserDO getUser() {
		if (null != ShiroUtils.getUser()){
			return ShiroUtils.getUser();
		}else{
			return null;
		}

	}

	public UserDO getAllUserInfo() {
		Long userId = getUserId();
		if (null != userId){
			return userService.get(userId);
		}
		return null;
	}

	public Long getUserId() {
		if (null != getUser() && null != getUser().getUserId() ) {
			return getUser().getUserId();
		}else{
			return null;
		}
	}

	public List<Long> getUserRole() {
		Long userId = getUserId();
		return roleService.listRole(userId);
	}

	public String getUsername() {
		if (null != getUser() && null != getUser().getUsername()) {
			return getUser().getUsername();
		}else{
			return null;
		}
	}
}