package com.pinyougou.admin.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.admin.services.ItemCatService;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.pojo.TbItemCatExample.Criteria;


import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	
	/**
	 * 查询全部
	 */
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);		
	}

	
	/**
	 * 修改
	 */
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	public void delete(Long[] ids) {
		for(Long id:ids){
			
			itemCatMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
	public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
			if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)itemCatMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	public List<TbItemCat> findByParentId(Long parentId) {
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		
		//查询数据
		List<TbItemCat> itemCatList = findAll();
		
		//每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
		List<TbItemCat> list = findAll();
		for(TbItemCat itemCat:list){
			redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
		}
		System.out.println("更新缓存:商品分类表");
		
		return itemCatMapper.selectByExample(example);
	}

	@Override
	public Map findBySubId(Long ids[]) {
		Map map =new HashMap();
		String subIds ="存在下级{";
		long num=0;
		for(Long id:ids) {
			Long count=itemCatMapper.selectBySubId(id);
			if(count.longValue()>0) {
				subIds+=id.longValue()+"["+count.longValue()+" 条], ";
			}else {
				num++;
			}
		}
		subIds+="}";
		if(ids.length==num) {
			subIds="已全部删除成功"+num+"条";
		}
		
		map.put("subIds", subIds);
		map.put("num", num);
		return map;
	}
	
	

}
