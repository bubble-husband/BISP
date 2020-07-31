package com.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = {"com.consumer"},exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
//@EnableFeignClients(basePackages= {"com.commons.feign"})
//@ComponentScan(basePackages = {"com.commons"})
public class Consumer80_App extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }

	
	
	public static void main(String[] args)
	  {
	   SpringApplication.run(Consumer80_App.class, args);
	  }

}
