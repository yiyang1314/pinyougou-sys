package com.pinyougou.admin.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.admin.services.ContentService;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import com.pinyougou.pojo.TbContentExample.Criteria;


import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	
	@Autowired
	public RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */

	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbContent> page=   (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	public void add(TbContent content) {
		contentMapper.insert(content);		
		//清除缓存
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	}

	
	/**
	 * 修改
	 */

	public void update(TbContent content){
		//查询原来的分组ID
		Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
		//清除原分组的缓存
		redisTemplate.boundHashOps("content").delete(categoryId);
		
		contentMapper.updateByPrimaryKey(content);
		//清除现分组缓存
		if(categoryId.longValue()!=content.getCategoryId().longValue()){
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */

	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */

	public void delete(Long[] ids) {
		for(Long id:ids){
			//清除缓存
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();			
			redisTemplate.boundHashOps("content").delete(categoryId);
			
			contentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	

	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		if(content!=null){			
			if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		
		Page<TbContent> page= (Page<TbContent>)contentMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
		



	public List<TbContent> findByCategoryId(Long categoryId) {
		
		List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
		TbContentExample example1=new TbContentExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andCategoryIdGreaterThan(categoryId);
		if(list==null){
			System.out.println("从数据库中查询数据并放入缓存 ");
			TbContentExample example=new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);//指定条件:分类ID
			criteria.andStatusEqualTo("1");//指定条件：有效
			example.setOrderByClause("sort_order");//排序			
			list = contentMapper.selectByExample(example);
			redisTemplate.boundHashOps("content").put(categoryId, list);//放入缓存
		}else{
			System.out.println("从缓存中查询数据 ");
		}
				
		return list;
	}
	
}
