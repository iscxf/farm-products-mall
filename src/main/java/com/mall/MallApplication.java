package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.mall.*.dao")
@SpringBootApplication
@EnableCaching
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
        System.out.println(" ......................start success......................");
    }

}

