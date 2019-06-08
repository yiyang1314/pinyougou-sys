	package com.pinyougou.user.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbCitiesExample;
import com.pinyougou.pojo.TbCitiesExample.Criteria;
import com.pinyougou.user.services.CitiesService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/cities")
public class CitiesController {

	@Autowired
	private CitiesService citiesService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbCities> findAll(){			
		return citiesService.findAll();
	}
	@RequestMapping("/findCities")
	public List<TbCities> findByCities(String provinceid){
		List<TbCities> list = citiesService.findByCities(provinceid);
		return list;	
	}
	
}
