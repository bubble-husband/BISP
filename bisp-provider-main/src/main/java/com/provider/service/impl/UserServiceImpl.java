package com.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commons.entity.ArticleExample;
import com.commons.entity.CollectExample;
import com.commons.entity.CommentExample;
import com.commons.entity.User;
import com.commons.entity.UserExample;

import com.commons.entity.Userdetail;
import com.commons.entity.UserdetailExample;
import com.commons.entity.UserExample.Criteria;
import com.provider.mapper.ArticleMapper;
import com.provider.mapper.CollectMapper;
import com.provider.mapper.UserMapper;
import com.provider.mapper.UserdetailMapper;
import com.provider.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserdetailMapper userdetailMapper;
	@Autowired
	private CollectMapper collectMapper;
	@Autowired
	private ArticleMapper articleMapper;

	// 查看所有用户
	public List<User> selectByExample(UserExample example) {
		return userMapper.selectByExample(example);
	}

	// 根据id查找
	public User selectByPrimaryKey(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	// 根据名字和密码查找
	@Override
	public List<User> selectByUsernameAndPwd(String username, String password) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		return userMapper.selectByExample(example);

	}
	
	
	// 根据名字和密码查找
	@Override
	public boolean checkByUsernameAndPwd(String username, String password) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		List<User> u = userMapper.selectByExample(example);
		if (u.size() > 0) {
			return true;
		}
		return false;

	}

	// 根据名字
	@Override
	public boolean selectByUsername(String username) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> u = userMapper.selectByExample(example);
		if (u.size() > 0) {
			return true;
		}
		return false;


	}
	
	// 根据名字
	@Override
	public List<User> SU(String username) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		return userMapper.selectByExample(example);


	}
	// 用户登录（查找）
	public int login(User record) {
		// 1.输入用户名--判断用户名格式是否正确
		// 2.输入密码--判断密码格式是否正确
		/*
		 * 3.登录--根据用户名和密码查找是否存在这个用户， 如果不存在，判断是否是用户名输入错误（根据用户名查找一条记录），否则是密码输入错误；
		 * 如果存在，判断权限，返回权限值
		 */
		int p = -1;
		String username = record.getUsername();
		String pwd = record.getPassword();
		List<User> list = selectByUsernameAndPwd(username, pwd);
		if (list.size() != 0) {// 不为空,存在此用户
			if (list.get(0).getPower() == false) {
				p = 0;
			} else {
				p = 1;
			}
		}
		return p;
	}

	// 用户注册（插入）
	@Override
	public int UserRegister(User record) {
		@SuppressWarnings("unused")
		String password = record.getPassword();
		String username = record.getUsername();
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);// 判断是否存在相同的用户名
		List<User> list = userMapper.selectByExample(example);
		if (list.size() == 0) {// 如果不存在，则可以注册
			record.setPower(true);
			userMapper.insert(record);// 注册
			List<User> user = userMapper.selectByExample(example);
			Userdetail userdetail = new Userdetail();
			userdetail.setUserId(user.get(0).getUserId());
			userdetailMapper.insert(userdetail);
			return 1;
		} else {
//			IMOOCJSONResult.errorUsername("用户名已存在！");
			return -1;
		}
	}

	// 根据id用户修改密码
	public int updateByPrimaryKeySelective(User record) {
		// TODO 自动生成的方法存根
		return userMapper.updateByPrimaryKey(record);
	}

	// 根据id删除用户
	@Transactional
	@Override
	public int deleteByPrimaryKey(Integer userId) {
		// 先删除userdetail
		UserdetailExample userdetailExample = new UserdetailExample();
		com.commons.entity.UserdetailExample.Criteria criteria = userdetailExample.createCriteria();
		criteria.andUserIdEqualTo(userId);
		userdetailMapper.deleteByExample(userdetailExample);
		// 再删除collect
		CollectExample collectExample = new CollectExample();
		com.commons.entity.CollectExample.Criteria criteria1 = collectExample.createCriteria();
		criteria1.andUserIdEqualTo(userId);
		collectMapper.deleteByExample(collectExample);
		// 删除comment
		CommentExample commentExample = new CommentExample();
		com.commons.entity.CommentExample.Criteria criteria2 = commentExample.createCriteria();
		criteria2.andUserIdEqualTo(userId);
		collectMapper.deleteByExample(collectExample);
		// 删除article
		ArticleExample articleExample = new ArticleExample();
		com.commons.entity.ArticleExample.Criteria criteria3 = articleExample.createCriteria();
		criteria3.andUserIdEqualTo(userId);
		articleMapper.deleteByExample(articleExample);
		return userMapper.deleteByPrimaryKey(userId);
	}

	// 修改密码
	@Override
	public int updatePassword(String oldPassWord, String newPassWord, String newPassWord2, int id) {
		UserExample example = new UserExample();
		User user = new User();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		// 判断密码是否输入正确
		String passWord = userMapper.selectByPrimaryKey(id).getPassword();
		if (oldPassWord.equals(passWord)) {
			if (newPassWord.equals(newPassWord2)) {
				user.setPassword(newPassWord);
				userMapper.updateByExampleSelective(user, example);
				return 1;
			}
		}
		return 0;
	}

}
