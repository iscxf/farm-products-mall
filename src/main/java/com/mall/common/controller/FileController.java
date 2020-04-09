package com.mall.common.controller;

import com.mall.common.config.FieldConfig;
import com.mall.common.domain.FileDO;
import com.mall.common.service.FileService;
import com.mall.common.utils.*;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author chenxf
 * @email iscxf@foxmail.com
 * @date 2017-09-19 16:02:20
 */
@Slf4j
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {

	@Autowired
	private FileService sysFileService;

	@Autowired
	private FieldConfig fieldConfig;

	@GetMapping()
//	@RequiresPermissions("common:sysFile:sysFile")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "common/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("common:sysFile:sysFile")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	// @RequiresPermissions("common:bComments")
	String add() {
		return "common/sysFile/add";
	}

	@GetMapping("/edit")
	// @RequiresPermissions("common:bComments")
	String edit(Long id, Model model) {
		FileDO sysFile = sysFileService.get(id);
		model.addAttribute("sysFile", sysFile);
		return "common/sysFile/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("common:info")
	public Result info(@PathVariable("id") Long id) {
		FileDO sysFile = sysFileService.get(id);
		return Result.ok().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
//	@RequiresPermissions("common:save")
	public Result save(FileDO sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("common:update")
	public Result update(@RequestBody FileDO sysFile) {
		sysFileService.update(sysFile);

		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	// @RequiresPermissions("common:remove")
	public Result remove(Long id, HttpServletRequest request) {
		String fileName = fieldConfig.getUploadPath() + sysFileService.get(id).getUrl().replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return Result.error("数据库记录删除成功，文件删除失败");
			}
			return Result.ok();
		} else {
			return Result.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
//	@RequiresPermissions("common:remove")
	public Result remove(@RequestParam("ids[]") Long[] ids) {
		sysFileService.batchRemove(ids);
		return Result.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), fieldConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return Result.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return Result.ok().put("fileName",sysFile.getUrl());
		}
		return Result.error();
	}


}
