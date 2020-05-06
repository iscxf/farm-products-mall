package com.mall.manager.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 本地换成机制
 */
public class CacheUtil {

    public LoadingCache<String, String> loadingCache;

    //服务启动的时候初始化value值为"",缓存有效期15分钟
    @PostConstruct
    private void init(){
        loadingCache = CacheBuilder.newBuilder().
                expireAfterWrite(15, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return StringUtils.EMPTY;
            }
        });
    }

}
