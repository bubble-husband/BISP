package com.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.commons.entity.User;
import com.commons.entity.UserExample;
import com.provider.service.IUserService;

@RestController
public class UserController {

	@Autowired
	private IUserService Service;

	// 查看所有用户
	@GetMapping("/user/selectAll")
	public List<User> selectAll() {
		UserExample example = new UserExample();
		List<User> list = Service.selectByExample(example);
		return list;
	}


	// 登录主页面
	@RequestMapping("/user/userLogin")
	public List<User> userLogin(@RequestBody User user) {
		List<User> list = Service.selectByUsernameAndPwd(user.getUsername(), user.getPassword());
		return list;
	}
	
	// 根据用户为检验
	@PostMapping("/user/checkUsername")
	public boolean CU(@RequestBody User user) {
		return Service.selectByUsername(user.getUsername());
	}

	// 根据用户为检验
	@PostMapping("/user/checkPassword")
	public boolean CUP(@RequestBody User user) {
		return Service.checkByUsernameAndPwd(user.getUsername(), user.getPassword());
	}


	// 注册主页面2（提交表单后获得手机号、验证码以及其他信息进行验证）
	@RequestMapping("/user/userRegister/{auth_code}/{code}")
	public int userRegister(@RequestBody User user, @PathVariable("auth_code") String authCode,
			@PathVariable("code") String code) {
		if (authCode.equals(code)) {
			return Service.UserRegister(user);
		} else {
			return -1;
		}

	}

	// 根据id删除用户
	@RequestMapping("/user/delById/{userId}")
	public int deleteUserById(@PathVariable("userId") int userId) {
		return Service.deleteByPrimaryKey(userId);
	}
	
	// 根据名字查找User
	@GetMapping("/user/selectUser/{username}")
	public List<User> SU(@PathVariable("username") String username) {
		List<User> list = Service.SU(username);
		return list;
	}



	// 修改密码
	@RequestMapping("/user/updatePassWord/{id}")
	public int updatePassword(@PathVariable("id") int id, @RequestBody List<String> result) {
		// List<String> result = new ArrayList(map.values());
		String oldPassWord = result.get(0);
		String newPassWord = result.get(1);
		String newPassWord2 = result.get(2);
		return Service.updatePassword(oldPassWord, newPassWord, newPassWord2, id);
	}
}
