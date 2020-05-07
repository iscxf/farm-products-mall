package com.mall.system.controller;

import com.google.common.collect.Lists;
import com.mall.common.annotation.Log;
import com.mall.common.controller.BaseController;
import com.mall.common.domain.Tree;
import com.mall.common.service.DictService;
import com.mall.common.utils.*;
import com.mall.system.domain.DeptDO;
import com.mall.system.domain.RoleDO;
import com.mall.system.domain.UserDO;
import com.mall.system.service.RoleService;
import com.mall.system.service.UserService;
import com.mall.system.vo.UserVO;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictService dictService;

	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@GetMapping("/register")
	String register(Model model) {
//		List<RoleDO> roles = roleService.list();
//		model.addAttribute("roles", roles);
		return  "register";
	}

	@GetMapping("/merchantRegister")
	String merchantRegister(Model model) {
		return  "merchantRegister";
	}

	@RequiresPermissions("sys:user:add")
	@GetMapping("/add")
	String add(Model model) {
		List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<RoleDO> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	Result save(UserDO user) {
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		if (userService.save(user) > 0) {
			return Result.ok();
		}
		return Result.error();
	}


	/**
	 * 商家注册
	 * @param user
	 * @return
	 */
	@PostMapping("/register/merchant")
	@ResponseBody
	String saveMerchantRegister(UserDO user) {
		String passwd = user.getPassword();
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		user.setRoleIds(Lists.newArrayList(3L));
		user.setDeptId(9L);
		user.setStatus("1");
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		user.setName(user.getUsername());
		user.setType("2");
		if (userService.save(user) > 0) {
			//注册完自动登录
			passwd = MD5Utils.encrypt(user.getUsername(), passwd);
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), passwd);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			UserDO userDO =(UserDO)SecurityUtils.getSubject().getPrincipal();
			List<RoleDO> roleDO = roleService.list(userDO.getUserId());
			Session session = subject.getSession(true);
			session.setAttribute("userid",userDO.getUserId());
			session.setAttribute("username",userDO.getUsername());
			session.setAttribute("role",roleDO.get(0));
			return "success";
		}
		return "false";
	}

	/**
	 * 一般用户注册
	 * @param user
	 * @return
	 */
	@PostMapping("/register/general")
	@ResponseBody
	String saveGeneralRegister(UserDO user) {
		String passwd = user.getPassword();
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		user.setRoleIds(Lists.newArrayList(2L));
		user.setDeptId(8L);
		user.setStatus("1");
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		user.setName(user.getUsername());
		user.setType("1");
		if (userService.save(user) > 0) {
			//注册完自动登录 agriculturalProductsMall
			passwd = MD5Utils.encrypt(user.getUsername(), passwd);
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), passwd);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			UserDO userDO =(UserDO)SecurityUtils.getSubject().getPrincipal();
			List<RoleDO> roleDO = roleService.list(userDO.getUserId());
			Session session = subject.getSession(true);
			session.setAttribute("userid",userDO.getUserId());
			session.setAttribute("username",userDO.getUsername());
			session.setAttribute("role",roleDO.get(0));

			return "success";
		}
		return "false";
	}

	@Log("用户名检查")
	@PostMapping("/register/check")
	@ResponseBody
	String registerCheck(String userName) {
		if (userService.getByName(userName) != null) {
			return "no";
		}
		return "yes";
	}

	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	Result update(UserDO user) {
		if (userService.update(user) > 0) {
			return Result.ok();
		}
		return Result.error();
	}


	//个人更新也会走此接口，不设权限
	@Log("更新用户")
	@PostMapping("/updatePersonal")
	@ResponseBody
	Result updatePersonal(UserDO user) {
		if (userService.updatePersonal(user) > 0) {
			return Result.ok();
		}
		return Result.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	Result remove(Long id) {
		if (userService.remove(id) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	Result batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}


	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	Result resetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return Result.ok();
		}catch (Exception e){
			return Result.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("sys:user:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	Result adminResetPwd(UserVO userVO) {
		try{
			userService.adminResetPwd(userVO);
			return Result.ok();
		}catch (Exception e){
			return Result.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@Log("资料详细")
	@GetMapping("/personal")
	String personal(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/personal";
	}

	@Log("用户中心")
	@GetMapping("/usercenter")
	String userCenter(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/user_center";
	}

	@GetMapping("/usercenter/{type}")
	String userCenterBytype(@PathVariable(value = "type",required = false) String type,Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("type",type);
		model.addAttribute("user",userDO);
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/user_center";
	}
}
