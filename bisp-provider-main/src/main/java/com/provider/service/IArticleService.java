package com.provider.service;

import java.util.Date;
import java.util.List;

import com.commons.entity.Article;

public interface IArticleService {

	// 统计数目
	int count();
		
	// 通过用户ID统计文章数目
	int countByUser(Integer userId);
	
	// 通过主键统计数目
	int countByPrimaryKey(Integer articleId);

	// 通过主键删除记录
	int deleteByPrimaryKey(Integer articleId);

	// 添加数据（需要填主键）
	int insert(Article article);

	// 添加数据（不需要填主键）
	int insertSelective(Article article);

	// 查询所有记录
	List<Article> selectAll();

	// 根据主键查找一条记录
	Article selectByPrimaryKey(Integer articleId);

	// 根据主键更新一条记录
	int updateByPrimaryKeySelective(Article article);
	
	//根据时间查询
	List<Article> selectByTime(Date start, Date end);

	List<Article> selectMonth();

	List<Article> selectDay();

	List<Article> selectYear();

	List<Article> selectBySortId(Integer sortId);


	List<Article> selectByClick();

	List<Article> selectByCollet();

}
