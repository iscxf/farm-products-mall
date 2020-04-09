package com.mall.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
class WebConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	FieldConfig fieldConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//ftp路径不用file前缀
		if (fieldConfig.getUploadPath().contains("ftp:")) {
			registry.addResourceHandler("/files/**").addResourceLocations(fieldConfig.getUploadPath());
		}else {
			registry.addResourceHandler("/files/**").addResourceLocations("file:///" + fieldConfig.getUploadPath());
		}
	}

}