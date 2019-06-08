package com.test.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.pinyougou.admin.impl.*;

public class testServices {
	private ClassPathXmlApplicationContext ctx;
	
	public    BrandServiceImpl          brandServiceImpl;
	public    GoodsServiceImpl          goodsServiceImpl;
	public    ItemCatServiceImpl        itemCatServiceImpl;
	public    SellerServiceImpl         sellerServiceImpl;
	public    SpecificationServiceImpl  specificationServiceImpl;
	public    TypeTemplateServiceImpl   typeTemplateServiceImpl;
	//public    UserServicesImpl          userServicesImpl;
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring/spring-mvc.xml",
		"spring/spring-mybatis.xml");
		
		brandServiceImpl = ctx.getBean ("brandServiceImpl",BrandServiceImpl.class);
		goodsServiceImpl = ctx.getBean ("goodsServiceImpl",GoodsServiceImpl.class);
		itemCatServiceImpl = ctx.getBean ("itemCatServiceImpl",ItemCatServiceImpl.class);
		sellerServiceImpl = ctx.getBean ("sellerServiceImpl",SellerServiceImpl.class);
		specificationServiceImpl = ctx.getBean ("specificationServiceImpl",SpecificationServiceImpl.class);
		typeTemplateServiceImpl = ctx.getBean ("typeTemplateServiceImpl",TypeTemplateServiceImpl.class);
		//userServicesImpl = ctx.getBean ("userServicesImpl",UserServicesImpl.class);
	}
	
	
	@Test
	public void testMapperS(){
		System.out.println("--------------------------START!----------------------------");
		System.out.println(" brandServiceImpl : "+  brandServiceImpl.findAll());

		System.out.println("--------------------------CLOSED05----------------------------------");
	}
	
	
	
	
	
	
	
	
}


