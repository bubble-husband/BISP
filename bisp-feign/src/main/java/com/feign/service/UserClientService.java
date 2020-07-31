package com.feign.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commons.entity.User;


@FeignClient(value = "http://BISP-PROVIDER" )
public interface UserClientService {
	
	@RequestMapping(value = "/user/selectAll",method = RequestMethod.GET)
	public List<User> list();

	
	
//	@RequestMapping(value = "/user/login")
//	public void login();
	
	


}