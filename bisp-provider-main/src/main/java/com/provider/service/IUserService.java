package com.provider.service;

import java.util.List;

import com.commons.entity.User;
import com.commons.entity.UserExample;

public interface IUserService {

	// 查看所有用户
	List<User> selectByExample(UserExample example);

	// 根据id查找
	User selectByPrimaryKey(Integer userId);

	// 根据用户名和密码查找
	List<User> selectByUsernameAndPwd(String username, String password);

	// 用户登录（查找）
	int login(User record);

	// 根据id用户修改密码
	int updatePassword(String oldPassword, String newPassword, String newPassWord2, int id);

	// 用户注册（插入）
	int UserRegister(User record);

	// 根据id删除用户
	int deleteByPrimaryKey(Integer userId);

	//
	boolean selectByUsername(String username);

	boolean checkByUsernameAndPwd(String username, String password);

	List<User> SU(String username);

}
