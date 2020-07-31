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
import com.commons.entity.Sort;
import com.github.pagehelper.PageInfo;



@Controller
public class SortController {
	
	private static final String REST_URL_PREFIX = "http://localhost:8001";
	
	@Autowired
	private RestTemplate restTemplate;


	
	// 查询全部
	@PassToken
	@SuppressWarnings("unchecked")
	@RequestMapping("/consumer/sort/list")
	public String list(Model model) {
		List<Sort> list = restTemplate.getForObject(REST_URL_PREFIX + "/sort/list", List.class);
		model.addAttribute("sort", list);
		return "Sort_list";
	}
	
	// 查询分类列表
	@PassToken
	@SuppressWarnings("unchecked")
	@RequestMapping("/consumer/sort/listSort")
	public String listSort(Model model) {
		List<Article> listArticle = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		model.addAttribute("article", listArticle);
		List<Sort> listSort = restTemplate.getForObject(REST_URL_PREFIX + "/sort/list", List.class);
		model.addAttribute("sort", listSort);
		return "categories";
	}
	
	
	// 根据ID查询
	@SuppressWarnings("unchecked")
	@RequestMapping("consumer/sort/selectById")
	public String selectById(Integer sortId, Model model) {
		List<Sort> listSort = restTemplate.getForObject(REST_URL_PREFIX + "/sort/list", List.class);
		model.addAttribute("sort", listSort);
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/selectBySortId/"+sortId, List.class);
		model.addAttribute("article", list);
		return "categories";
	}
	
	//根据分类名称查询一条记录
	@SuppressWarnings("unchecked")
	@GetMapping("consumer/sort/selectByName")
	public String selectByName(String sortName, Model model) {
		List<Sort> list = restTemplate.getForObject(REST_URL_PREFIX + "/sort/selectByName/"+sortName, List.class);
		model.addAttribute("sort", list);
		return "Sort_select";
	}
	
	//添加一条记录
	@RequestMapping("consumer/sort/add")
	public String addSort(String sortName) {
		Sort sort = new Sort();
		sort.setSortName(sortName);
		sort.setArticleNum(0);
		restTemplate.postForObject(REST_URL_PREFIX + "/sort/add", sort, int.class);
		return "redirect:list";
	}
	
	//删除一条记录
	@RequestMapping("consumer/sort/delete")
	public String deleteSort(Integer sortId) {
		restTemplate.postForObject(REST_URL_PREFIX + "/sort/delete", sortId, int.class);
		System.out.println("After Delete");
		return "redirect:list";
	}
		
	//更改一条记录
	@RequestMapping("consumer/sort/update")
	public String updateSort(Integer sortId, String sortName, Integer articleNum) {
		Sort sort = new Sort();
		sort.setSortId(sortId);
		sort.setSortName(sortName);
		sort.setArticleNum(articleNum);
		restTemplate.postForObject(REST_URL_PREFIX + "/sort/update", sort, int.class);
		return "redirect:list";
	}

	//-------6.16-----------
	//分页查询
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/sort/listPage")
	public String listPage(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
		//引入PageHelper插件
	    //在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Sort> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/sort/listPage/"+pageNum, PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);	
		return "SortTable";
	}
	//去更新
	@GetMapping("/consumer/sort/toUpdate")
	public String toUpdateSort(@RequestParam Integer sortId, Model model) {
		Sort sort = restTemplate.getForObject(REST_URL_PREFIX + "/sort/selectById/" + sortId, Sort.class);
		model.addAttribute("sort", sort);
		return "SortEdit";
	}
	
	//去添加
	@GetMapping("/consumer/sort/toAdd")
	public String toAddSort() {
		return "SortAdd";
	}
}
