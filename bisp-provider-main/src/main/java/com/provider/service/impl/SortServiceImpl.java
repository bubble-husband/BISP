package com.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Sort;
import com.commons.entity.SortExample;
import com.commons.entity.SortExample.Criteria;
import com.provider.mapper.SortMapper;
import com.provider.service.ISortService;




@Service
public class SortServiceImpl implements ISortService{

	@Autowired
	private SortMapper mapper;

	//根据
	@Override
	public int count() {
		return mapper.countByExample(null);
	}
	
	//根据
	@Override
	public int countByPrimaryKey(Integer sortId) {
		SortExample example = new SortExample();
		Criteria criteria = example.createCriteria();
		criteria.andSortIdEqualTo(sortId);		
		return mapper.countByExample(example);
	}

	//根据分类名称删除
	@Override
	public int deleteByName(String sortName) {
		SortExample example = new SortExample();
		Criteria criteria = example.createCriteria();
		criteria.andSortNameEqualTo(sortName);
		return mapper.deleteByExample(example);
	}

	//根据主键删除
	@Override
	public int deleteByPrimaryKey(Integer sortId) {
		
		return mapper.deleteByPrimaryKey(sortId);
	}

	@Override
	public int insert(Sort sort) {
		
		return mapper.insert(sort);
	}

	@Override
	public int insertSelective(Sort sort) {
		
		return mapper.insertSelective(sort);
	}

	@Override
	public List<Sort> selectAll() {

		return mapper.selectByExample(null);
	}

	@Override
	public List<Sort> selectByName(String sortName) {
		SortExample example = new SortExample();
		Criteria criteria = example.createCriteria();
		criteria.andSortNameEqualTo(sortName);
		return mapper.selectByExample(example);
	}

	@Override
	public Sort selectByPrimaryKey(Integer sortId) {
		
		return mapper.selectByPrimaryKey(sortId);
	}

	@Override
	public int updateByPrimaryKeySelective(Sort sort) {
		
		return mapper.updateByPrimaryKeySelective(sort);
	}
	
	

}
