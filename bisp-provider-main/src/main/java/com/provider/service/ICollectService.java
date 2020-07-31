package com.provider.service;

import java.util.List;

import com.commons.entity.Collect;

public interface ICollectService {
	
	// 统计数目
	int count();
		
	//根据用户ID返回收藏数
	int countByUser(Integer userId);

	// 通过主键统计数目
	int countByPrimaryKey(Integer collectId);

	// 通过主键删除记录
	int deleteByPrimaryKey(Integer collectId);

	// 添加数据（需要填主键）
	int insert(Collect collect);

	// 添加数据（不需要填主键）
	int insertSelective(Collect collect);

	// 查询所有记录
	List<Collect> selectAll();


	// 根据主键查找一条记录
	Collect selectByPrimaryKey(Integer collectId);

	// 根据主键更新一条记录
	int updateByPrimaryKeySelective(Collect collect);

	int selectByUidAndAid(Collect collect);

	int deleteByPrimaryKeyAndUserId(Collect collect);

}
