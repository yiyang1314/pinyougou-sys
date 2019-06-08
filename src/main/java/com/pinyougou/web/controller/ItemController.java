 package com.pinyougou.web.controller;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinyougou.admin.services.GoodsService;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.web.services.ItemPageServices;

@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ItemPageServices itemPageServices;
	
	@RequestMapping("/findOne")
	public Map findOne(@Param("itemId")Long itemId) {
		System.out.println(itemId);
		System.out.println("itemId");
		return itemPageServices.findOne(itemId);
		
	}
	@RequestMapping("/delete")
	public boolean delete(@Param("itemIds") Long[] itemIds) {
		return itemPageServices.delete(itemIds);
		
	}
	@RequestMapping("/findAll")
	public List<TbItem> findAll(){
		return itemPageServices.findAll();
	}
}
