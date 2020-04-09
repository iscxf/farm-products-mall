package com.mall.system.controller;

import com.mall.common.annotation.Log;
import com.mall.common.config.FieldConfig;
import com.mall.common.controller.BaseController;
import com.mall.common.domain.FileDO;
import com.mall.common.domain.Tree;
import com.mall.common.service.FileService;
import com.mall.common.utils.MD5Utils;
import com.mall.common.utils.Result;
import com.mall.common.utils.ShiroUtils;
import com.mall.system.domain.MenuDO;
import com.mall.system.domain.RoleDO;
import com.mall.system.domain.UserDO;
import com.mall.system.service.MenuService;
import com.mall.system.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    FieldConfig fieldConfig;
    @Autowired
    RoleService roleService;

    @GetMapping({"/", ""})
    String welcome(Model model) {
        return "redirect:/mall/index";
    }

    @Log("请求访问主页")
    //这个权限先这样，后面再改看看
//    @RequiresPermissions("common:dict:dict")
    @RequiresPermissions("manager:product:add")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("picUrl", "/img/photo_s.jpg");
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", fieldConfig.getUsername());
        model.addAttribute("password", fieldConfig.getPassword());
//        model.addAttribute("username", "admin");
//        model.addAttribute("password", "111111");
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    Result ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            UserDO userDO =(UserDO)SecurityUtils.getSubject().getPrincipal();
            //获取角色id
            List<Long> rolesIds = roleService.listRole(userDO.getUserId());
            //这里是返回所有角色的，先不用这个
            List<RoleDO> roleDOList = roleService.list(userDO.getUserId());
//            RoleDO roleDO = roleService.get(userDO.getUserId());
            RoleDO roleDO = roleDOList.get(0);

            Session session = subject.getSession(true);
            session.setAttribute("userid",userDO.getUserId());
            session.setAttribute("username",userDO.getUsername());
            session.setAttribute("storeName",userDO.getStoreName());
            session.setAttribute("role",rolesIds.get(0));
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

}
