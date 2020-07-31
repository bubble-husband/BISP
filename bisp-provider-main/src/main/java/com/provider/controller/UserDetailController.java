package com.provider.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.Userdetail;
import com.provider.service.IUserDetailService;




@RestController
// @RequestMapping("UserDetail")
public class UserDetailController {
	@Autowired
	private IUserDetailService Service;

	// 管理员查看所有用户信息
	@RequestMapping("userDetail/selectAll")
	public List<Userdetail> selectAll() {
		return Service.selectAll();
	}

	// 管理员删除一条用户信息
	@RequestMapping("userDetail/deleteById")
	public int deleteById(@RequestBody int userdetailId) {
		return Service.deleteById(userdetailId);
	}

	// 管理员修改用户信息
	@RequestMapping("userDetail/adminUpdate")
	public int adminUpdate(@RequestBody Userdetail userDetail) {
		return Service.adminUpdate(userDetail);
	}

	// 用户查看个人信息
	@RequestMapping("userDetail/selectById/{id}")
	public Userdetail selectById(@PathVariable("id") Integer id) {
		return Service.selectById(id);
	}

	// 用户修改个人信息
	@RequestMapping("userDetail/userUpdate")
	public int userUpdate(@RequestBody Userdetail userDetail) {
		return Service.userUpdate(userDetail);
	}
}
