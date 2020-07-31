package com.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Article;
import com.commons.entity.Collect;
import com.commons.entity.CollectExample;
import com.commons.entity.CollectExample.Criteria;
import com.provider.mapper.ArticleMapper;
import com.provider.mapper.CollectMapper;
import com.provider.service.ICollectService;

@Service
public class CollectServiceImpl implements ICollectService{

	@Autowired
	private CollectMapper mapper;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Override
	public int count() {	
		return mapper.countByExample(null);
	}
	
	@Override
	public int countByUser(Integer userId) {
		CollectExample example = new CollectExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return mapper.countByExample(example);
	}
	
	@Override
	public int countByPrimaryKey(Integer collectId) {
		CollectExample example = new CollectExample();
		Criteria criteria = example.createCriteria();
		criteria.andCollectIdEqualTo(collectId);		
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer collectId) {
		return mapper.deleteByPrimaryKey(collectId);
	}

	@Override
	public int insert(Collect collect) {
		return mapper.insert(collect);
	}

	@Override
	public int insertSelective(Collect collect) {
		return mapper.insertSelective(collect);
	}

	@Override
	public List<Collect> selectAll() {
		return mapper.selectByExample(null);
	}

	@Override
	public Collect selectByPrimaryKey(Integer collectId) {
		return mapper.selectByPrimaryKey(collectId);
	}

	@Override
	public int updateByPrimaryKeySelective(Collect collect) {
		return mapper.updateByPrimaryKeySelective(collect);
	}
	@Override
	public int selectByUidAndAid(Collect collect) {
	int articleId = collect.getArticleId();
    int userId = collect.getUserId();
	 CollectExample collectExample = new CollectExample();
	 Criteria criteria = collectExample.createCriteria();
	 criteria.andArticleIdEqualTo(articleId);
	 criteria.andUserIdEqualTo(userId);
	 List<Collect> list =mapper.selectByExample(collectExample);
	 if(!list.isEmpty())    {
		return 1;    }
	 else{
	return 0;   }
	}

	//取消收藏，先删除收藏表的数据，再更新文章表的字段
	@Override
	public int deleteByPrimaryKeyAndUserId(Collect collect) {
		//先删除收藏表的数据
		CollectExample collectExample = new CollectExample();
		Criteria criteria = collectExample.createCriteria();
		int userId = collect.getUserId();
		int articleId = collect.getArticleId();
		criteria.andUserIdEqualTo(userId);
		criteria.andArticleIdEqualTo(articleId);
		mapper.deleteByExample(collectExample);
		//统计collect_num
		CollectExample example = new CollectExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andArticleIdEqualTo(articleId);
		int num =mapper.countByExample(example);
		//查找出对应文章表中的数据
		Article  article =articleMapper.selectByPrimaryKey(articleId);
		//更新字段
		article.setCommentNum(num);
		return articleMapper.updateByPrimaryKeySelective(article);
	}

}
