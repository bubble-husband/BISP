package com.consumer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.commons.annotations.PassToken;
import com.commons.entity.Article;
import com.commons.entity.Comment;
import com.github.pagehelper.PageInfo;

@Controller

public class HomeController {
	
	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;

	// 关于我们
	@PassToken
	@GetMapping("/consumer/home/aboutme")
	public String test() {
		return "aboutme";
	}
	
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/home/index")
	public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model,HttpServletRequest request) {
		//引入PageHelper插件
	    //在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Article> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/article/listPage/"+pageNum, PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);	
		List<Comment> listCom = restTemplate.getForObject(REST_URL_PREFIX + "/comment/list", List.class);
		model.addAttribute("comment", listCom);
		List<Article> listbyclick = restTemplate.getForObject(REST_URL_PREFIX + "/article/listByClick", List.class);
		model.addAttribute("listclick", listbyclick);
		List<Article> listbycollect = restTemplate.getForObject(REST_URL_PREFIX + "/article/listByCollect", List.class);
		model.addAttribute("listcollect", listbycollect);
		return "index";
	}
	

}
