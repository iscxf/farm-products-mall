package com.mall.manager.controller;

import com.mall.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2019-01-23 21:30:24
 */

@Slf4j
@Controller
@RequestMapping("/mall/index")
public class IndexController{

	@GetMapping()
	String index(){
	    return "manager/index/main";
	}

}
