package com.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Userdetail;
import com.provider.mapper.UserdetailMapper;
import com.provider.service.IUserDetailService;

@Service
public class UserDetailServiceImpl implements IUserDetailService {
	@Autowired
	UserdetailMapper UserdetailMapper;

	// 管理员查看所有用户信息
	@Override
	public List<Userdetail> selectAll() {
		return UserdetailMapper.selectByExample(null);
	}

	// 用户查看个人信息
	@Override
	public Userdetail selectById(int userId) {
		/*
		 * UserdetailExample example = new UserdetailExample(); Criteria criteria =
		 * example.createCriteria(); criteria.andUserIdEqualTo(userId);
		 */
		return UserdetailMapper.selectByPrimaryKey(userId);
	}

//用户修改用户个人信息
	@Override
	public int userUpdate(Userdetail userdetail) {
		return UserdetailMapper.updateByPrimaryKeySelective(userdetail);
	}

//管理员删除一条用户信息
	@Override
	public int deleteById(int userdetailId) {
		return UserdetailMapper.deleteByPrimaryKey(userdetailId);
	}

//管理员修改用户信息
	@Override
	public int adminUpdate(Userdetail userdetail) {
		return UserdetailMapper.updateByPrimaryKeySelective(userdetail);
	}

}
