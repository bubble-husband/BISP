package com.consumer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.commons.annotations.PassToken;
import com.commons.entity.User;

import net.sf.json.JSONObject;

@Controller
public class UserController {

	private static final String REST_URL_PREFIX = "http://localhost:8001";
	@Autowired
	private RestTemplate restTemplate;

	//登录首页
	@PassToken
	@RequestMapping("/consumer/user/show")
	public String show() {
		return "show";
	}
	
	// 登录页面
	@PassToken
	@RequestMapping("/consumer/user/login")
	public String login() {
		return "loginnew";
	}

	
	//登录成功，防止表单提交，可以重定向到user
	@PassToken
	@RequestMapping("/consumer/user/user")
	public String user() {
		return "user";
	}
	
	//登录成功，防止表单提交，可以重定向到
	@PassToken
	@RequestMapping("/consumer/user/administrators")
	public String administrators() {
		return "administrators";
	}		

	// 查看所有用户
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/user/selectAll")
	public String selectAll(Model m) {
		List<User> list = restTemplate.getForObject(REST_URL_PREFIX + "/user/selectAll", List.class);
		m.addAttribute("allList", list);
		return "selectAllUser";
	}

	// 登录主页面
	@PassToken
	@SuppressWarnings("unchecked")
	@PostMapping("/consumer/user/userLogin")
	public String userLogin(String username, String password, HttpSession session) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);	
		List<User> list = restTemplate.postForObject(REST_URL_PREFIX + "/user/userLogin", user, List.class);
		for (Object obj : list) {
			User users = (User) JSONObject.toBean(JSONObject.fromObject(obj), User.class);
			session.setAttribute("id", users.getUserId());
			if (users.getPower() == true) {// 普通用户
				return "user";
			} else {// 管理员
				return "administrators";
			}
		}
		return "loginnew";

	}

	// 注册
	@PassToken
	@RequestMapping("/consumer/user/register")
	public String register() {
		return "register";//模板
	}

	//获取验证码
	@PostMapping("/consumer/user/getCode")
	@ResponseBody
	@PassToken
	public String getCode(String phone,HttpSession session) {
		String a = restTemplate.getForObject(REST_URL_PREFIX + "/user/getCode/"+phone, String.class);
		session.setMaxInactiveInterval(300);
    	session.setAttribute("code", a);
		return a;
	}
	
	
	//注册主页面
	@PassToken
	@PostMapping("/consumer/user/userRegister")
	public String userRegister(String username,String password,String auth_code,HttpSession session) {
		String code =(String) session.getAttribute("code");
		System.out.println(code);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		int a = restTemplate.postForObject(REST_URL_PREFIX + "/user/userRegister/"+auth_code+"/"+code, user, int.class);
		System.out.println(username+password+auth_code);
		if (a == 1) {
			return "loginnew";
		} else {
			return "register";
		}
	}

	// 根据id删除用户
	
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/user/delById")
	public String deleteUserById(int userId,Model m) {
		restTemplate.getForObject(REST_URL_PREFIX + "/user/delById/"+userId, int.class);
		List<User> list = restTemplate.getForObject(REST_URL_PREFIX + "/user/selectAll", List.class);
		m.addAttribute("allList", list);
		return "selectAllUser";
	}

	// 修改密码
	@PassToken
	@PostMapping("/consumer/user/updatePassWord")
	public String updatePassword(String oldPassWord, String newPassWord, String newPassWord2, HttpSession session) {
		int id = (int) session.getAttribute("id");
		Map<String, String> map = new HashMap<>();
		map.put("oldPassWord ", oldPassWord);
		map.put("newPassWord ", newPassWord);
		map.put("newPassWord2 ", newPassWord2);
		List<String> result = new ArrayList<String>(map.values());
		int flag = restTemplate.postForObject(REST_URL_PREFIX + "/user/updatePassWord/" + id, result, int.class);
		if (flag == 1) {
			return "login";
		}
		return "error";
	}

}
