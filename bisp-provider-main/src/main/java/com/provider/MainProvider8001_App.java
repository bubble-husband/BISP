package com.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication(scanBasePackages = {"com.provider"})
@MapperScan("com.provider.mapper")
@EnableEurekaClient                    //本服务启动后会自动注册进eureka服务中
//@EnableCircuitBreaker                  //对hystrixR熔断机制的支持
//@ComponentScan(basePackages = {"com.provider.interceptor"})
public class MainProvider8001_App {
	
//	  @Bean
//	  public ServletRegistrationBean getServlet() {
//	    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
//	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
//	    registrationBean.setLoadOnStartup(1);
//	    registrationBean.addUrlMappings("/hystrix.stream");
//	    registrationBean.setName("HystrixMetricsStreamServlet");
//	    return registrationBean;
//	  }
	
	public static void main(String[] args)
	  {
	   SpringApplication.run(MainProvider8001_App.class, args);
	  }

}
