package com.pinyougou.user.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbCities;
import com.pinyougou.user.services.AreasService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/areas")
public class AreasController {

	@Autowired
	private AreasService areasService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbAreas> findAll(){			
		return areasService.findAll();
	}
	
	@RequestMapping("/findAreas")
	public List<TbAreas> findByCities(String cityid){
		System.out.println(cityid);
		List<TbAreas> list = areasService.findByAreas(cityid);
		return list;	

	}
	
}
