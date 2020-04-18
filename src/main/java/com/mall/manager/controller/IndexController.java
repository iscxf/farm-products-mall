package com.mall.manager.controller;

import com.mall.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
	String index(@RequestParam(value="keyword", required=false) String keyword,  Model model){
		model.addAttribute("keyword", keyword);
	    return "manager/index/main";
	}

//	@GetMapping("/search/{keyword}")
//	String keywordIndex(@PathVariable("keyword") String keyword, Model model){
//		model.addAttribute("keyword", keyword);
//		log.info("trace keyword:",keyword);
//		System.out.println("cssssssssssssssss");
//		return "manager/index/main";
//	}
}
