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
import com.commons.entity.Comment;
import com.github.pagehelper.PageInfo;

@Controller
public class CommentController {
	
	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;
	
	// 查询全部
	@PassToken
	@SuppressWarnings("unchecked")
	@RequestMapping("consumer/comment/list")
	public String list(Model model) {
		List<Comment> list = restTemplate.getForObject(REST_URL_PREFIX + "/comment/list", List.class);
		model.addAttribute("comment", list);
		return "Comment_list";
	}
	
	// 根据ID查询一条记录
	@PassToken
	@RequestMapping("consumer/comment/selectById")
	public String selectById(Integer commentId, Model model) {
		Comment comment = restTemplate.getForObject(REST_URL_PREFIX + "/comment/selectById/" + commentId, Comment.class);
		model.addAttribute("comment", comment);
		return "Comment_select";
	}

	// 添加一条记录
	@PassToken
	@RequestMapping("consumer/comment/add")
	public String addComment(String commentContent, Integer articleId, 
			Integer userId, Integer supCommentId, Byte authorRepty) {
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setArticleId(articleId);
		comment.setUserId(userId);
		comment.setSupCommentId(supCommentId);
		comment.setAuthorRepty(authorRepty);
		restTemplate.postForObject(REST_URL_PREFIX + "/comment/add", comment, int.class);
		return "redirect:list";
	}

	// 删除一条记录
	@PassToken
	@RequestMapping("consumer/comment/delete")
	public String deleteComment(Integer commentId) {
		restTemplate.postForObject(REST_URL_PREFIX + "/comment/delete", commentId, int.class);
		return "redirect:list";
	}

	// 更改一条记录
	@PassToken
	@RequestMapping("consumer/comment/update")
	public String updateComment(Integer commentId, String commentContent, Integer articleId, 
			Integer userId, Integer supCommentId, Byte authorRepty) {
		Comment comment = new Comment();
		comment.setCommentId(commentId);
		comment.setCommentContent(commentContent);
		comment.setArticleId(articleId);
		comment.setUserId(userId);
		comment.setSupCommentId(supCommentId);
		comment.setAuthorRepty(authorRepty);
		restTemplate.postForObject(REST_URL_PREFIX + "/comment/update", comment, int.class);
		return "redirect:list";
	}
	
	// -------6.16-----------
	// 分页查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/comment/listPage")
	public String listPage(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Comment> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/comment/listPage/" + pageNum, PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);
		return "CommentTable";
	}

	// 去更新
	@PassToken
	@GetMapping("/consumer/comment/toUpdate")
	public String toUpdateComment(@RequestParam Integer commentId, Model model) {
		Comment comment = restTemplate.getForObject(REST_URL_PREFIX + "/comment/selectById/" + commentId, Comment.class);
		model.addAttribute("comment", comment);
		return "CommentEdit";
	}

	// 去添加
	@PassToken
	@GetMapping("/consumer/comment/toAdd")
	public String toAddComment() {
		return "CommentAdd";
	}
	
}
