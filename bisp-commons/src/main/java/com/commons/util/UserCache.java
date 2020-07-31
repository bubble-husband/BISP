package com.commons.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class UserCache {

    /**
     * 表单重复提交cache，有效期2秒.
     *
     * @return the cache
     */
    @Bean
    public Cache<String,String> getUserCache(){
        return CacheBuilder.newBuilder().expireAfterAccess(2L,TimeUnit.SECONDS).build();
    }
}
