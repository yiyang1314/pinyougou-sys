package com.pinyougou.user.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbCitiesMapper;
import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbCitiesExample;
import com.pinyougou.pojo.TbCitiesExample.Criteria;
import com.pinyougou.user.services.CitiesService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class CitiesServiceImpl implements CitiesService {

	@Autowired
	private TbCitiesMapper citiesMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbCities> findAll() {
		return citiesMapper.selectByExample(null);
	}


	

	public List<TbCities> findByCities(String provinceid){
		TbCitiesExample example=new TbCitiesExample();
		Criteria criteria = example.createCriteria();
		if(provinceid!="" && provinceid.length()>0){
			criteria.andProvinceidLike("%"+provinceid+"%");
		}
		return citiesMapper.selectByExample(example);	
	}
	
}
