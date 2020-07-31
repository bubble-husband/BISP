package com.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.provider.service.ICommentService;

@RestController
public class CommentController {

	@Autowired
	private ICommentService service;

	//分页查询
	@GetMapping("/comment/listPage/{pageNum}")
	public PageInfo<Comment> listPage(@PathVariable("pageNum") Integer pageNum) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageHelper.startPage(pageNum, 10);
		List<Comment> listComment = service.selectAll();
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(listComment);
		return pageInfo;
	}
	
	//统计数目
	@GetMapping("/comment/count")
	public int count() {
		int count = service.count();
		return count;
	}
	
	//根据用户ID返回评论数目
	@GetMapping("/comment/countByUser/{userId}")
	public int countByUser(@PathVariable("userId") Integer userId) {
		int countByUser = service.countByUser(userId);
		return countByUser;
	}

	// 查询全部
	@RequestMapping(value="/comment/list", method = RequestMethod.GET)
	public List<Comment> list() {
		return service.selectAll();
	}

	// 根据ID查询一条记录
	@RequestMapping(value="/comment/selectById/{commentId}", method = RequestMethod.GET)
	public Comment selectById(@PathVariable("commentId") Integer commentId) {
		return service.selectByPrimaryKey(commentId);
	}

	// 添加一条记录
	@RequestMapping(value="/comment/add", method = RequestMethod.POST)
	public int addComment(@RequestBody Comment comment) {
		
		return service.insertSelective(comment);
	}

	// 删除一条记录
	@RequestMapping(value="/comment/delete", method = RequestMethod.POST)
	public int deleteComment(@RequestBody Integer commentId) {
		
		return service.deleteByPrimaryKey(commentId);
	}

	// 更改一条记录
	@RequestMapping("/comment/update")
	public int updateComment(@RequestBody Comment comment) {
		
		return service.updateByPrimaryKeySelective(comment);
	}

}
