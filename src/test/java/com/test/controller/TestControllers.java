package com.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinyougou.admin.controller.*;
import com.pinyougou.mapper.TbAddressMapper;
import com.pinyougou.mapper.TbAreasMapper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.mapper.TbCitiesMapper;
import com.pinyougou.mapper.TbContentCategoryMapper;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.mapper.TbFreightTemplateMapper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.mapper.TbProvincesMapper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.mapper.TbUserMapper;

public class TestControllers {
	private ClassPathXmlApplicationContext ctx;
	
	public BrandController  brandController;
	public GoodsController  goodsController;
	public ItemCatController  itemCatController;
	//public LoginController  loginController;
	public SellerController  sellerController;
	public SpecificationController  specificationController;
	public TypeTemplateController  typeTemplateController;
	
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring/spring-mvc.xml",
		"spring/spring-mybatis.xml");
		
		 brandController   =ctx.getBean ("brandController",BrandController.class) ;
		 goodsController   =ctx.getBean ("goodsController",GoodsController.class) ;
		 itemCatController   =ctx.getBean ("itemCatController",ItemCatController.class) ;
		// loginController   =ctx.getBean ("loginController",LoginController.class) ;
		 sellerController   =ctx.getBean ("sellerController",SellerController.class) ;
		 specificationController   =ctx.getBean ("specificationController",SpecificationController.class) ;
		 typeTemplateController   =ctx.getBean ("typeTemplateController",TypeTemplateController.class) ;
		
	}
	
	
	@Test
	public void testMapperS(){
		System.out.println("-------------------------controller -START!----------------------------");
		System.out.println("brandController   		: "+ brandController.findAll().size());
		System.out.println("-------------------------brandController -end!-------------------------");
		System.out.println("goodsController   		: "+ goodsController.findAll().size());
		System.out.println("-------------------------goodsController -end!-------------------------");
		System.out.println("itemCatController 		: "+ itemCatController.findAll().size());
		System.out.println("-------------------------itemCatController -end!-----------------------");
		System.out.println("sellerController  		: "+ sellerController.findAll().size());
		System.out.println("-------------------------sellerController -end!------------------------");
		System.out.println("specificationController : "+ specificationController.findAll().size());
		System.out.println("-------------------------specificationController -end!------------------");
		System.out.println("typeTemplateController  : "+ typeTemplateController.findAll().size());
		System.out.println("---------------------typeTemplateController-----CLOSED05----------------");
	}
}
