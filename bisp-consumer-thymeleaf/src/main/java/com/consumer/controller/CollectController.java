package com.consumer.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.commons.entity.Collect;
import com.github.pagehelper.PageInfo;

@Controller
public class CollectController {

	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;

	// 查询全部
	@SuppressWarnings("unchecked")
	@RequestMapping("consumer/collect/list")
	public String list(Model model) {
		List<Collect> list = restTemplate.getForObject(REST_URL_PREFIX + "/collect/list", List.class);
		model.addAttribute("collect", list);
		return "Collect_list";
	}

	// 根据ID查询一条记录
	@RequestMapping("consumer/collect/selectById")
	public String selectById(Integer collectId, Model model) {
		Collect collect = restTemplate.getForObject(REST_URL_PREFIX + "/collect/selectById/" + collectId, Collect.class);
		model.addAttribute("collect", collect);
		return "Collect_select";
	}

	// 添加一条记录
	@RequestMapping("consumer/collect/add")
	public String addCollect(Integer articleId, Integer userId) {
		Collect collect = new Collect();
		collect.setArticleId(articleId);
		collect.setUserId(userId);
		Date date = new Date();
		collect.setCollectTime(date);
		restTemplate.postForObject(REST_URL_PREFIX + "/collect/add", collect, int.class);
		return "redirect:list";
	}

	// 删除一条记录
	@RequestMapping("consumer/collect/delete")
	public String deleteCollect(Integer collectId) {
		restTemplate.postForObject(REST_URL_PREFIX + "/collect/delete", collectId, int.class);
		return "redirect:list";
	}

	// 更改一条记录
	@RequestMapping("consumer/collect/update")
	public String updateCollect(Integer collectId, Integer articleId, Integer userId, Date collectTime) {
		Collect collect = new Collect();
		collect.setCollectId(collectId);
		collect.setArticleId(articleId);
		collect.setUserId(userId);
		collect.setCollectTime(collectTime);
		restTemplate.postForObject(REST_URL_PREFIX + "/collect/update", collect, int.class);
		return "redirect:list";
	}
	
	// -------6.16-----------
	// 分页查询
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/collect/listPage")
	public String listPage(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Collect> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/collect/listPage/" + pageNum, PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);
		return "CollectTable";
	}

	// 去更新
	@GetMapping("/consumer/collect/toUpdate")
	public String toUpdateCollect(@RequestParam Integer collectId, Model model) {
		Collect collect = restTemplate.getForObject(REST_URL_PREFIX + "/collect/selectById/" + collectId, Collect.class);
		model.addAttribute("collect", collect);
		return "CollectEdit";
	}

	// 去添加
	@GetMapping("/consumer/collect/toAdd")
	public String toAddCollect() {
		return "CollectAdd";
	}
	
	//-------6.19-----------
	//判断是否已经收藏（根据article_id和user_id查询记录）
	@PostMapping("/consumer/collect/selectByUidAndAid")
	@ResponseBody
	public String selectByUidAndAid(Integer articleId, HttpServletRequest request, HttpServletResponse response){
	//获取当前登录用户的id
	int userId = (int) request.getSession().getAttribute("remoteId");
	Collect collect = new Collect();
	collect.setArticleId(articleId);
	collect.setUserId(userId);
	int flag = restTemplate.postForObject(REST_URL_PREFIX+"/collect/selectByUidAndAid",collect,int.class);
	if(flag == 1){
		return "alreadyCollect";
	}
	else {return "noCollect";}
	}

    //判断用户是否登录
	@RequestMapping("/consumer/collect/findOne")
	@ResponseBody
	public String findOne(HttpServletRequest request, HttpServletResponse response){
		Object user = request.getSession().getAttribute("remoteId");
		System.out.println(user);
		if(user == null){
			return "noLogin";
		}
		else {
			return "alreadyLogin" ;
		}
	}

	// 删除一条记录(前端页面调用)
	@PostMapping("consumer/collect/deleteOne")
	@ResponseBody
	public String deleteCollectOne(Integer articleId,HttpServletRequest request, HttpServletResponse response) {
		int userId = (int) request.getSession().getAttribute("remoteId");
		Collect collect = new Collect();
		collect.setUserId(userId);
		collect.setArticleId(articleId);
		int flag = restTemplate.postForObject(REST_URL_PREFIX + "/collect/deleteOne", collect, int.class);
		if(flag == 1){
			return "delSuccess";
		}
		else{
			return "delFail";
		}
	}	
	
	
}
