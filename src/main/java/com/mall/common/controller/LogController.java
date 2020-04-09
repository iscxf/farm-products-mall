package com.mall.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.common.domain.LogDO;
import com.mall.common.domain.PageDO;
import com.mall.common.service.LogService;
import com.mall.common.utils.Query;
import com.mall.common.utils.Result;

@RequestMapping("/common/log")
@Controller
public class LogController {
	@Autowired
	LogService logService;
	String prefix = "common/log";

	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	PageDO<LogDO> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageDO<LogDO> page = logService.queryList(query);
		return page;
	}
	
	@ResponseBody
	@PostMapping("/remove")
    Result remove(Long id) {
		if (logService.remove(id)>0) {
			return Result.ok();
		}
		return Result.error();
	}

	@ResponseBody
	@PostMapping("/batchRemove")
    Result batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchRemove(ids);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}
}
