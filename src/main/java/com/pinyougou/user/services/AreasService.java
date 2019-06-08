package com.pinyougou.user.services;
import java.util.List;
import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbCities;

import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AreasService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbAreas> findAll();
	
	public List<TbAreas> findByAreas(String cityid);
	
}
