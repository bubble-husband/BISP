package com.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer//EurekaServer服务器端启动�?,接受其它微服务注册进�?
public class EurekaServer7002_App
{
  public static void main(String[] args)
  {
   SpringApplication.run(EurekaServer7002_App.class, args);
  }
}