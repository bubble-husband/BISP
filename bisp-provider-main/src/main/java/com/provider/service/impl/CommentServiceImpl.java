package com.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Comment;
import com.commons.entity.CommentExample;
import com.commons.entity.CommentExample.Criteria;
import com.provider.mapper.CommentMapper;
import com.provider.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService{

	@Autowired
	private CommentMapper mapper;
	
	@Override
	public int count() {
		return mapper.countByExample(null);
	}
	
	@Override
	public int countByUser(Integer userId) {
		CommentExample example = new CommentExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return mapper.countByExample(example);
	}
	
	@Override
	public int countByPrimaryKey(Integer commentId) {
		CommentExample example = new CommentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCommentIdEqualTo(commentId);
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer commentId) {
		return mapper.deleteByPrimaryKey(commentId);
	}

	@Override
	public int insert(Comment comment) {
		return mapper.insert(comment);
	}

	@Override
	public int insertSelective(Comment comment) {
		return mapper.insertSelective(comment);
	}

	@Override
	public List<Comment> selectAll() {
		return mapper.selectByExample(null);
	}

	@Override
	public Comment selectByPrimaryKey(Integer commentId) {
		return mapper.selectByPrimaryKey(commentId);
	}

	@Override
	public int updateByPrimaryKeySelective(Comment comment) {
		return mapper.updateByPrimaryKeySelective(comment);
	}

}
