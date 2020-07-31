package com.provider.service;

import java.util.List;

import com.commons.entity.Comment;

public interface ICommentService {

	// 统计数目
	int count();
	
	//根据用户ID返回数目
	int countByUser(Integer userId);
	
	// 通过主键统计数目
	int countByPrimaryKey(Integer commentId);

	// 通过主键删除记录
	int deleteByPrimaryKey(Integer commentId);

	// 添加数据（需要填主键）
	int insert(Comment comment);

	// 添加数据（不需要填主键）
	int insertSelective(Comment comment);

	// 查询所有记录
	List<Comment> selectAll();

	// 根据主键查找一条记录
	Comment selectByPrimaryKey(Integer commentId);

	// 根据主键更新一条记录
	int updateByPrimaryKeySelective(Comment comment);

}
