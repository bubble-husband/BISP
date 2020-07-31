package com.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.commons.annotations.PassToken;
import com.commons.entity.Article;

@Controller
public class ArchivesController {

	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;
	
	//归档主页查询
	@PassToken
	@SuppressWarnings("unchecked")
	@RequestMapping("/consumer/archives/listArchives")
	public String listSort(Model model) {
		List<Article> listArticle = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		model.addAttribute("article", listArticle);
		return "archives";
	}
	
	//根据时间查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("consumer/article/selectByDate")
	public String selectByDate(@RequestParam("start") String start ,@RequestParam("end") String end, Model model) {
		System.out.println("consumer:"+start+","+end);
		List<Article> article = restTemplate.getForObject(REST_URL_PREFIX + "/article/selectByDate/" + start+ "/" + end, List.class);
		model.addAttribute("article", article);
		return "archives";
	}
	

	// 查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/archives/listday")
	public String LD(Model model) {
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/listday", List.class);
		model.addAttribute("article", list);
		return "archives";
	}
	
	// 查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/archives/listmonth")
	public String LM(Model model) {
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/listmonth", List.class);
		model.addAttribute("article", list);
		return "archives";
	}
	
	// 查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/archives/listyear")
	public String LY(Model model) {
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/listyear", List.class);
		model.addAttribute("article", list);
		return "archives";
	}
}
