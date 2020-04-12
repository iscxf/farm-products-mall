package com.mall.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenxf
 * Created on 2020/4/10
 */
@Component
public class WebConfigurerAdaptor implements WebMvcConfigurer {

    @Autowired
    FieldConfig fieldConfig;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //ftp路径不用file前缀  用nginx更快
        if (fieldConfig.getUploadPath().contains("ftp:")) {
            registry.addResourceHandler("/files/**").addResourceLocations("http://39.108.136.154:8086/files/");
        }else {
            registry.addResourceHandler("/files/**").addResourceLocations("file:///" + fieldConfig.getUploadPath());
        }
    }
}
