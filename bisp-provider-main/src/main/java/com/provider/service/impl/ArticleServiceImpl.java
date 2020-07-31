package com.provider.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Article;
import com.commons.entity.ArticleExample;
import com.commons.entity.ArticleExample.Criteria;
import com.commons.entity.CollectExample;
import com.provider.mapper.ArticleMapper;
import com.provider.mapper.CollectMapper;
import com.provider.service.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService{

	@Autowired
	private ArticleMapper mapper;
	@Autowired
	private CollectMapper collectMapper;
	
	@Override
	public List<Article> selectMonth(){
		return mapper.selectByMonth();
	}
	
	@Override
	public List<Article> selectYear(){
		return mapper.selectByYear();
	}
	
	@Override
	public List<Article> selectDay(){
		return mapper.selectByDay();
	}
	
	@Override
	public List<Article> selectBySortId(Integer sortId) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andSortIdEqualTo(sortId);
		return mapper.selectByExample(example);
	}
	
	@Override
	public int count() {
		return mapper.countByExample(null);
	}
	
	@Override
	public int countByUser(Integer userId) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return mapper.countByExample(example);
	}
	
	@Override
	public int countByPrimaryKey(Integer articleId) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer articleId) {
		return mapper.deleteByPrimaryKey(articleId);
	}

	@Override
	public int insert(Article article) {
		return mapper.insert(article);
	}

	@Override
	public int insertSelective(Article article) {
		return mapper.insertSelective(article);
	}

	@Override
	public List<Article> selectAll() {
		return mapper.selectByExample(null);
	}
	
	@Override
	public List<Article> selectByClick() {
		ArticleExample example = new ArticleExample();
		example.setOrderByClause("click_num DESC");
		return mapper.selectByExample(example);
	}
	
	@Override
	public List<Article> selectByCollet() {
		ArticleExample example = new ArticleExample();
		example.setOrderByClause("collect_num DESC");
		return mapper.selectByExample(example);
	}



	@Override
	public int updateByPrimaryKeySelective(Article article) {
		return mapper.updateByPrimaryKeySelective(article);
	}
	
	@Override
	public Article selectByPrimaryKey(Integer articleId) {
		Article article =mapper.selectByPrimaryKey(articleId);
		//1.从收藏表中获取每篇文章的收藏数
		CollectExample collectExample = new CollectExample();
		CollectExample.Criteria criteria = collectExample.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		int num = collectMapper.countByExample(collectExample);
		//2.赋值给文章表的collect_num
		article.setCollectNum(num);
		//3.更新
		mapper.updateByPrimaryKeySelective(article);
		return mapper.selectByPrimaryKey(articleId);
	}
	
	@Override
	public List<Article> selectByTime(Date start, Date end) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateTimeBetween(start, end);
		return mapper.selectByExample(example);
	}

}
