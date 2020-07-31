package com.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.Collect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.provider.service.ICollectService;

@RestController
public class CollectController {

	@Autowired
	private ICollectService service;

	// 查询全部
	@RequestMapping(value="/collect/list", method = RequestMethod.GET)
	public List<Collect> list() {
		return service.selectAll();
	}
	
	//分页查询
	@GetMapping("/collect/listPage/{pageNum}")
	public PageInfo<Collect> listPage(@PathVariable("pageNum") Integer pageNum) {
	// 引入PageHelper插件
	// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
	PageHelper.startPage(pageNum, 10);
	List<Collect> listCollect = service.selectAll();
	PageInfo<Collect> pageInfo = new PageInfo<Collect>(listCollect);
	return pageInfo;
	}
	
	//统计数目
	@GetMapping("/collect/count")
	public int count() {
		int count = service.count();
		return count;
	}
	
	//根据用户ID返回收藏数目
	@GetMapping("/collect/countByUser/{userId}")
	public int countByUser(@PathVariable("userId") int userId) {
		int countByUser = service.countByUser(userId);
		return countByUser;
	}

	// 根据ID查询一条记录
	@RequestMapping(value="/collect/selectById/{collectId}", method = RequestMethod.GET)
	public Collect selectById(@PathVariable("collectId") Integer collectId) {
		return service.selectByPrimaryKey(collectId);
	}

	// 添加一条记录
	@RequestMapping(value="/collect/add", method = RequestMethod.POST)
	public int addCollect(@RequestBody Collect collect) {
		return service.insertSelective(collect);
	}
	

	// 删除一条记录
	@RequestMapping(value = "/collect/delete", method = RequestMethod.POST)
	public int deleteCollect(@RequestBody Integer collectId) {
		
		return service.deleteByPrimaryKey(collectId);
	}
	

	// 更改一条记录
	@RequestMapping(value = "/collect/update", method = RequestMethod.POST)
	public int updateCollect(@RequestBody Collect collect) {
		
		return service.updateByPrimaryKeySelective(collect);
	}
	
	//-------6.19-----------
	//根据article_id和user_id查询记录
	@RequestMapping(value = "/collect/selectByUidAndAid", method = RequestMethod.POST)
	public int selectByUidAndAid(@RequestBody Collect collect){
		int flag =service.selectByUidAndAid(collect);
		return flag;
	}
	
	// 删除一条记录（前端页面调用）
	@RequestMapping(value = "/collect/deleteOne", method = RequestMethod.POST)
	public int deleteCollectOne(@RequestBody Collect collect) {

		return service.deleteByPrimaryKeyAndUserId(collect);
	}

}
