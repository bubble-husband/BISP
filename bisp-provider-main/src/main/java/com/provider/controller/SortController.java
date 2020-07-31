package com.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.Sort;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.provider.service.ISortService;


@RestController
public class SortController {
	
	@Autowired
	private ISortService service;

	// 分页查询
	@GetMapping("/sort/listPage/{pageNum}")
	public PageInfo<Sort> listPage(@PathVariable("pageNum") Integer pageNum) {
		// 引入PageHelper插件
		// 在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageHelper.startPage(pageNum, 10);
		List<Sort> listSort = service.selectAll();
		PageInfo<Sort> pageInfo = new PageInfo<Sort>(listSort);
		return pageInfo;
	}
	
	//根据主键统计数目
	@GetMapping("/sort/count")
	public int count() {
		int count = service.count();
		return count;
	}
	
	// 查询全部
	@RequestMapping(value="/sort/list",method=RequestMethod.GET)
	public List<Sort> list(){
		
		return service.selectAll();
	}
	
	
	//根据ID查询一条记录
	@RequestMapping(value="/sort/selectById/{sortId}",method=RequestMethod.GET)
	public Sort selectById(@PathVariable("sortId") Integer sortId) {
		
		return service.selectByPrimaryKey(sortId);
	}
	
	
	//根据分类名称查询一条记录
	@RequestMapping(value="/sort/selectByName/{sortName}",method=RequestMethod.GET)
	public List<Sort> selectByName(@PathVariable("sortName") String sortName) {
		
		return service.selectByName(sortName);
	}

	
	//添加一条记录
	@RequestMapping(value="/sort/add",method=RequestMethod.POST)
	public int addSort(@RequestBody Sort sort) {
		return service.insertSelective(sort);
	}
	

	
	//删除一条记录
	@RequestMapping(value="/sort/delete", method=RequestMethod.POST)
	public int deleteSort(@RequestBody Integer sortId) {
		return service.deleteByPrimaryKey(sortId);
	}
	

	
	//更改一条记录
	@RequestMapping(value="/sort/update", method=RequestMethod.POST)
	public int updateSort(@RequestBody Sort sort) {
		return service.updateByPrimaryKeySelective(sort);
	}


}
