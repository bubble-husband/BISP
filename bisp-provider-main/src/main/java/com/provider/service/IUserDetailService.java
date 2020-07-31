package com.provider.service;

import java.util.List;

import com.commons.entity.Userdetail;

public interface IUserDetailService {
//管理员查看所有用户信息
	 List<Userdetail> selectAll();
//管理员删除一条用户信息
	 int deleteById(int userdetailId);
//管理员修改用户信息
	 int adminUpdate(Userdetail userdetail);
//用户修改个人信息
	 int userUpdate(Userdetail userdetail);
//用户查看个人信息
	 Userdetail selectById(int userId);
}
