package com.consumer.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.consumer.interceptor.CustomerInterceptor;
import com.consumer.interceptor.DuplicateSubmitInterceptor;
import com.consumer.interceptor.TokenInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
        		.addPathPatterns("/admin/**");
    }
    
    




}
