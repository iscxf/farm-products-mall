package com.mall.system.controller;

import com.mall.common.annotation.Log;
import com.mall.common.config.FieldConfig;
import com.mall.common.controller.BaseController;
import com.mall.common.domain.FileDO;
import com.mall.common.domain.Tree;
import com.mall.common.service.FileService;
import com.mall.common.utils.MD5Utils;
import com.mall.common.utils.RandomValidateCodeUtil;
import com.mall.common.utils.Result;
import com.mall.common.utils.ShiroUtils;
import com.mall.system.domain.MenuDO;
import com.mall.system.domain.RoleDO;
import com.mall.system.domain.UserDO;
import com.mall.system.service.MenuService;
import com.mall.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    Result ajaxLogin(String username, String password, String verify, HttpServletRequest request) {
        try {
            //从session中获取随机数
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (StringUtils.isEmpty(verify)) {
                return Result.error("请输入验证码");
            }
            if (random.equals(verify)) {
            } else {
                return Result.error("请输入正确的验证码");
            }
        } catch (Exception e) {
            logger.error("验证码校验失败", e);
            return Result.error("验证码校验失败");
        }
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

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }

}
