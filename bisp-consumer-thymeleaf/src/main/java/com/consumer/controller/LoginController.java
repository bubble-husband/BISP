package com.consumer.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.commons.annotations.PassToken;
import com.commons.annotations.UserLoginToken;
import com.commons.entity.ApiResponse;
import com.commons.entity.Article;
import com.commons.entity.Comment;
import com.commons.entity.User;
import com.commons.util.ApiResponseUtil;
import com.commons.util.JWTUtil;
import com.consumer.util.GetCookieToken;
import com.consumer.util.CookieUtil;
import com.github.pagehelper.PageInfo;
import com.commons.enums.ApiResponseEnum;

@Controller
public class LoginController {

	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;

	// 去登录
	@PassToken
	@RequestMapping("/consumer/token/login")
	public String login() {
		return "login";
	}

	// 判断账号密码
	@PassToken
	@PostMapping("/consumer/token/check")
	public @ResponseBody String CH(@RequestBody String json) {
		JSONObject object = JSONObject.parseObject(json);
		String username = object.getString("username");
		String password = object.getString("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//判断用户名是否存在
		if(restTemplate.postForObject(REST_URL_PREFIX + "/user/checkUsername", user,boolean.class)) {
			//判断密码是否正确
			if(restTemplate.postForObject(REST_URL_PREFIX + "/user/checkPassword", user,boolean.class)) {
				String status = "{\"status\":\"1\"}";
				return status;
			}else {
				//密码错误
				String status = "{\"status\":\"2\"}";
				return status;
			}
		}else {
			//用户不存在
			String status = "{\"status\":\"0\"}";
			return status;
		}
		
	}

	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/token/index")
	public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model,HttpServletRequest request) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Article> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/article/listPage/" + pageNum,
				PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);
		List<Comment> listCom = restTemplate.getForObject(REST_URL_PREFIX + "/comment/list", List.class);
		model.addAttribute("comment", listCom);
		List<Article> listbyclick = restTemplate.getForObject(REST_URL_PREFIX + "/article/listByClick", List.class);
		model.addAttribute("listclick", listbyclick);
		List<Article> listbycollect = restTemplate.getForObject(REST_URL_PREFIX + "/article/listByCollect", List.class);
		model.addAttribute("listcollect", listbycollect);
		return "index";
	}

	@PassToken
	@SuppressWarnings("unchecked")
	@PostMapping("/consumer/token/userLogin")
	public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		// 存储账号密码
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		// 身份验证是否成功
		List<User> list = restTemplate.postForObject(REST_URL_PREFIX + "/user/userLogin", user, List.class);
		User users = null;
		for (Object obj : list) {
			users = (User) JSONObject.parseObject(JSONObject.toJSONString(obj), User.class);
		}
		if (users != null) {
			// 返回token
			String token;
			try {
				token = JWTUtil.sign(users.getUsername(), users.getUserId());
				if (token != null) {
					ApiResponse as = ApiResponseUtil.getApiResponse(token);
					Cookie cookie = new Cookie("token", (String) as.getData());
					response.addCookie(cookie);
					HttpSession session = request.getSession();
					session.setAttribute("remoteUser", JWTUtil.getUsername(token));
					session.setAttribute("remoteId", JWTUtil.getUserId(token));
					if (users.getPower() == true) {
						// 普通用户
						return "redirect:index";
					} else {
						// 管理员
						return "administrators";

					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		ApiResponse ae = ApiResponseUtil.getApiResponse(ApiResponseEnum.LOGIN_FAIL);
		System.out.println(ae);
		return "login";
	}

	@PassToken
	@GetMapping("/consumer/logout")
	public String LG(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			SessionStatus sessionStatus) {
		// 查询cookie
		Cookie cookie = CookieUtil.get(request, "token");
		if (cookie != null) {
			// 清除cookie,设置过期时间为0
			CookieUtil.set(response, "token", null, 0);
		}
		session.invalidate();
		sessionStatus.setComplete();
		return "redirect:/consumer/token/index";
	}

	@UserLoginToken
	@GetMapping("/consumer/token/admin")
	public String test(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = GetCookieToken.GT(cookies);
		System.out.println(token);
		return "/admin/NewFile";
	}

}
