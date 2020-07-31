package com.provider.service;

import java.util.List;


import com.commons.entity.Sort;


public interface ISortService {
	//通过主键统计数目
	int count();
	
	//通过主键统计数目
	int countByPrimaryKey(Integer sortId);

	//根据分类名称删除
    int deleteByName(String sortName);

    //通过主键删除记录
    int deleteByPrimaryKey(Integer sortId);

    //添加数据（需要填主键）
    int insert(Sort sort);

    //添加数据（不需要填主键）
    int insertSelective(Sort sort);

    //查询所有记录
    List<Sort> selectAll();
    
    //根据分类名称查找一条记录
    List<Sort> selectByName(String sortName);

    //根据主键查找一条记录
    Sort selectByPrimaryKey(Integer sortId);

    //根据主键更新一条记录
    int updateByPrimaryKeySelective(Sort sort);



}
