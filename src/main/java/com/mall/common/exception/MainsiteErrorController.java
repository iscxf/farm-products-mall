package com.mall.common.exception;


import com.mall.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainsiteErrorController implements ErrorController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String ERROR_PATH = "/error";

    @Autowired
    ErrorAttributes errorAttributes;

    @RequestMapping(
            value = {ERROR_PATH},
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        if (404 == code) {
            return new ModelAndView("error/404");
        } else if (403 == code) {
            return new ModelAndView("error/403");
        } else if (401 == code) {
            return new ModelAndView("login");
        } else {
            return new ModelAndView("error/500");
        }

    }

    @RequestMapping(value = ERROR_PATH)
    public Result handleError(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        int code = response.getStatus();
        if (404 == code) {
            return Result.error(404, "未找到资源");
        } else if (403 == code) {
            return Result.error(403, "没有访问权限");
        } else if (401 == code) {
            return Result.error(403, "登录过期");
        } else {
            return Result.error(500, "服务器错误");
        }
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }
}