package com.provider.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.Article;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.provider.service.IArticleService;

@RestController
public class ArticleController {

	@Autowired
	private IArticleService service;

	// 分页查询
	@GetMapping("/article/listPage/{pageNum}")
	public PageInfo<Article> listPage(@PathVariable("pageNum") Integer pageNum) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageHelper.startPage(pageNum,3);
		List<Article> listArt = service.selectAll();
		PageInfo<Article> pageInfo = new PageInfo<Article>(listArt,5);
		return pageInfo;
	}
	
	
	//根据日期查询
	@GetMapping("/article/selectByDate/{start}/{end}")
	public List<Article> selectByDate(@PathVariable("start") String start ,@PathVariable("end") String end) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(start);
		System.out.println(startDate);
		Date endDate = sdf.parse(end);
		System.out.println(endDate);
		List<Article> list = service.selectByTime(startDate, endDate);
		return list;
	}
	

	// 统计本月份
	@GetMapping("/article/listmonth")
	public List<Article> listByMonth() {
		List<Article> list = service.selectMonth();
		return list;
	}

	// 统计本年
	@GetMapping("/article/listyear")
	public List<Article> listByYear() {
		List<Article> list = service.selectYear();
		return list;
	}

	// 统计本日
	@GetMapping("/article/listday")
	public List<Article> listByDay() {
		List<Article> list = service.selectDay();
		return list;
	}

	// 查询所有
	@GetMapping("/article/list")
	public List<Article> list() {
		List<Article> list = service.selectAll();
		return list;
	}
	
	// 查询所有
	@GetMapping("/article/listByClick")
	public List<Article> listByClick() {
		List<Article> list = service.selectByClick();
		return list;
	}
	
	// 查询所有
	@GetMapping("/article/listByCollect")
	public List<Article> listByCollect() {
		List<Article> list = service.selectByCollet();
		return list;
	}

	// 根据ID查找一条记录
	@GetMapping("/article/selectById/{articleId}")
	public Article selectById(@PathVariable Integer articleId) {
		Article article = service.selectByPrimaryKey(articleId);
		return article;
	}

	// 根据查找
	@GetMapping("/article/selectBySortId/{sortId}")
	public List<Article> selectBySortId(@PathVariable Integer sortId) {
		List<Article> list = service.selectBySortId(sortId);
		return list;
	}

	// 添加一条记录
	@PostMapping("/article/add")
	public int addArticle(@RequestBody Article article) {
		Date createTime = new Date();
		Timestamp cts = new Timestamp(createTime.getTime());
		article.setCreateTime(cts);
		Date updateTime = new Date();
		Timestamp uts = new Timestamp(updateTime.getTime());
		article.setUpdateTime(uts);
		return service.insertSelective(article);
	}

	// 删除一条记录
	@GetMapping("/article/delete/{articleId}")
	public int deleteArticle(@PathVariable Integer articleId) {

		return service.deleteByPrimaryKey(articleId);
	}

	// 更新一条记录
	@PostMapping("/article/update")
	public int updateArticle(@RequestBody Article article) {
		Date updateTime = new Date();
		article.setUpdateTime(updateTime);
		
		return service.updateByPrimaryKeySelective(article);
	}
	
	//文章数目
	@GetMapping("/article/count")
	public int count() {
		int count = service.count();
		return count;
	}
	
	//根据用户ID返回文章数目
	@GetMapping("/article/countByUser/{userId}")
	public int countByUser(@PathVariable("userId") Integer userId) {
		int countByUser = service.countByUser(userId);
		return countByUser;
	}

}
