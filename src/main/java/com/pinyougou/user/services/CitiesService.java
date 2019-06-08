package com.pinyougou.user.services;
import java.util.List;
import com.pinyougou.pojo.TbCities;

import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface CitiesService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbCities> findAll();


	public List<TbCities> findByCities(String provinceid);
}
