package com.pinyougou.user.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbAreasMapper;
import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbAreasExample;
import com.pinyougou.pojo.TbAreasExample.Criteria;
import com.pinyougou.user.services.AreasService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class AreasServiceImpl implements AreasService {

	@Autowired
	private TbAreasMapper areasMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbAreas> findAll() {
		return areasMapper.selectByExample(null);
	}

	@Override
	public List<TbAreas> findByAreas(String cityid) {
		TbAreasExample example =new TbAreasExample();
		Criteria criteria = example .createCriteria();
		if(cityid!="" && cityid.length()>0){
			criteria.andCityidLike("%"+cityid+"%");
		}
		return areasMapper.selectByExample(example);
	}

	
}
