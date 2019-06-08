package com.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinyougou.mapper.TbAddressMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;

/**
 * @since  1.6.2
 * @author tangyang
 * @category 功能测试所有数据库dao接口
 */
public class TestCase {
	private ClassPathXmlApplicationContext ctx;
	private Object tbBrand;
	private TbUserMapper user;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring/spring-mvc.xml",
		"spring/spring-mybatis.xml");
		
		
		tbBrand=null; tbBrand=ctx.getBean("tbSpecificationMapper",TbSpecificationMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbSpecificationOptionMapper",TbSpecificationOptionMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbTypeTemplateMapper",TbTypeTemplateMapper.class);
		
		tbBrand=null; tbBrand=ctx.getBean("tbProvincesMapper",TbProvincesMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbOrderItemMapper",TbOrderItemMapper.class);
		
		tbBrand=null; tbBrand=ctx.getBean("tbAreasMapper",TbAreasMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbBrandMapper",TbBrandMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbCitiesMapper",TbCitiesMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbContentCategoryMapper",TbContentCategoryMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbContentMapper",TbContentMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbFreightTemplateMapper",TbFreightTemplateMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbGoodsMapper",TbGoodsMapper.class);
		
		tbBrand=null; tbBrand=ctx.getBean("tbItemCatMapper",TbItemCatMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbItemMapper",TbItemMapper.class);

		tbBrand=null; tbBrand=ctx.getBean("tbOrderMapper",TbOrderMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbPayLogMapper",TbPayLogMapper.class);

		tbBrand=null; tbBrand=ctx.getBean("tbSellerMapper",TbSellerMapper.class);
		tbBrand=null; tbBrand=ctx.getBean("tbAddressMapper",TbAddressMapper.class);
		user=ctx.getBean("tbUserMapper",        TbUserMapper.class);
	
	
	}

	
}
