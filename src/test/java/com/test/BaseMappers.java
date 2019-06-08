package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinyougou.mapper.*;

/**
 * @since  1.6.2
 * @author tangyang
 * @category 测试所有数据库Mappers基类
 */
public class BaseMappers {
	private ClassPathXmlApplicationContext ctx;
	public TbAddressMapper  tbAddressMapper;
	public TbAreasMapper  tbAreasMapper;
	public TbBrandMapper  tbBrandMapper;
	public TbCitiesMapper  tbCitiesMapper;
	public TbContentCategoryMapper  tbContentCategoryMapper;
	public TbContentMapper  tbContentMapper;
	public TbFreightTemplateMapper  tbFreightTemplateMapper;
	public TbGoodsDescMapper  tbGoodsDescMapper;
	public TbGoodsMapper  tbGoodsMapper;
	public TbItemCatMapper  tbItemCatMapper;
	public TbItemMapper  tbItemMapper;
	public TbOrderItemMapper  tbOrderItemMapper;
	public TbOrderMapper  tbOrderMapper;
	public TbPayLogMapper  tbPayLogMapper;
	public TbProvincesMapper  tbProvincesMapper;
	public TbSellerMapper  tbSellerMapper;
	public TbSpecificationMapper  tbSpecificationMapper;
	public TbSpecificationOptionMapper  tbSpecificationOptionMapper;
	public TbTypeTemplateMapper  tbTypeTemplateMapper;
	public TbUserMapper  tbUserMapper;
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring/spring-mvc.xml",
		"spring/spring-mybatis.xml");
		
		tbAddressMapper  =ctx.getBean ("tbAddressMapper",TbAddressMapper.class) ;
		tbAreasMapper  =ctx.getBean ("tbAreasMapper",TbAreasMapper.class) ;
		tbBrandMapper  =ctx.getBean ("tbBrandMapper",TbBrandMapper.class) ;
		tbCitiesMapper  =ctx.getBean ("tbCitiesMapper",TbCitiesMapper.class) ;
		tbContentCategoryMapper  =ctx.getBean ("tbContentCategoryMapper",TbContentCategoryMapper.class) ;
		tbContentMapper  =ctx.getBean ("tbContentMapper",TbContentMapper.class) ;
		tbFreightTemplateMapper  =ctx.getBean ("tbFreightTemplateMapper",TbFreightTemplateMapper.class) ;
		tbGoodsDescMapper  =ctx.getBean ("tbGoodsDescMapper",TbGoodsDescMapper.class) ;
		tbGoodsMapper  =ctx.getBean ("tbGoodsMapper",TbGoodsMapper.class) ;
		tbItemCatMapper  =ctx.getBean ("tbItemCatMapper",TbItemCatMapper.class) ;
		tbItemMapper  =ctx.getBean ("tbItemMapper",TbItemMapper.class) ;
		tbOrderItemMapper  =ctx.getBean ("tbOrderItemMapper",TbOrderItemMapper.class) ;
		tbOrderMapper  =ctx.getBean ("tbOrderMapper",TbOrderMapper.class) ;
		tbPayLogMapper  =ctx.getBean ("tbPayLogMapper",TbPayLogMapper.class) ;
		tbProvincesMapper  =ctx.getBean ("tbProvincesMapper",TbProvincesMapper.class) ;
		tbSellerMapper  =ctx.getBean ("tbSellerMapper",TbSellerMapper.class) ;
		tbSpecificationMapper  =ctx.getBean ("tbSpecificationMapper",TbSpecificationMapper.class) ;
		tbSpecificationOptionMapper  =ctx.getBean ("tbSpecificationOptionMapper",TbSpecificationOptionMapper.class) ;
		tbTypeTemplateMapper  =ctx.getBean ("tbTypeTemplateMapper",TbTypeTemplateMapper.class) ;
		tbUserMapper  =ctx.getBean ("tbUserMapper",TbUserMapper.class) ;
		
	}
	
	@Test
	public void testMapperS(){
		System.out.println("--------------------------START!----------------------------");
		System.out.println(" TbAddressMapper : "+  tbAddressMapper.selectByExample(null));
		System.out.println(" TbAreasMapper : "+  tbAreasMapper.selectByExample(null));
		System.out.println(" TbBrandMapper : "+  tbBrandMapper.selectByExample(null));
		System.out.println(" TbCitiesMapper : "+  tbCitiesMapper.selectByExample(null));
		System.out.println("--------------------------SUCCESSFULL!01----------------------------");
		System.out.println(" TbContentCategoryMapper : "+  tbContentCategoryMapper.selectByExample(null));
		System.out.println(" TbContentMapper : "+  tbContentMapper.selectByExample(null));
		System.out.println(" TbFreightTemplateMapper : "+  tbFreightTemplateMapper.selectByExample(null));
		System.out.println(" TbGoodsDescMapper : "+  tbGoodsDescMapper.selectByExample(null));
		System.out.println(" TbGoodsMapper : "+  tbGoodsMapper.selectByExample(null));
		System.out.println("--------------------------SUCCESSFULL!02----------------------------");
		System.out.println(" TbItemCatMapper : "+  tbItemCatMapper.selectByExample(null));
		System.out.println(" TbItemMapper : "+  tbItemMapper.selectByExample(null));
		System.out.println(" TbOrderItemMapper : "+  tbOrderItemMapper.selectByExample(null));
		System.out.println(" TbOrderMapper : "+  tbOrderMapper.selectByExample(null));
		System.out.println(" TbPayLogMapper : "+  tbPayLogMapper.selectByExample(null));
		System.out.println("--------------------------SUCCESSFULL!03----------------------------");
		System.out.println(" TbProvincesMapper : "+  tbProvincesMapper.selectByExample(null));
		System.out.println(" TbSellerMapper : "+  tbSellerMapper.selectByExample(null));
		System.out.println(" TbSpecificationMapper : "+  tbSpecificationMapper.selectByExample(null));
		System.out.println("--------------------------SUCCESSFULL!04----------------------------");
		System.out.println(" TbSpecificationOptionMapper : "+  tbSpecificationOptionMapper.selectByExample(null));
		System.out.println(" TbTypeTemplateMapper : "+	tbTypeTemplateMapper.selectByExample(null));
		System.out.println(" TbUserMapper : "+  tbUserMapper.selectByExample(null));
		System.out.println("--------------------------CLOSED05----------------------------------");
	}

}
