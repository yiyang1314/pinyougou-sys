package com.pinyougou.web.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;
import com.pinyougou.web.services.ItemPageServices;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class ItemPageServiceImpl implements ItemPageServices {

	@Value("${freemaker.itemdir}")
	private String pagedir;
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
		
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	

	public boolean getItemHtml(Long goodsId){				
		try {

			
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map dataModel=new HashMap<>();	
			//1.加载商品表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			
			dataModel.put("goods", goods);	

			//2.加载商品扩展表数据			
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			
			dataModel.put("goodsDesc", goodsDesc);	

			//3.商品分类
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();

			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);

			//4.SKU列表			
			TbItemExample example=new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo("1");//状态为有效
			criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
			example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认			
			List<TbItem> itemList = itemMapper.selectByExample(example);

			dataModel.put("itemList", itemList);
			
			
			Writer out=new FileWriter(pagedir+goodsId+".html");
			template.process(dataModel, out);
			System.out.println("页面生成为:  "+pagedir+goodsId+".html");
			out.close();
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean deleteItemHtml(Long[] goodsId) {
		try {
				for(Long id:goodsId) {
					new File(pagedir+goodsId+".html").delete();
				}
				System.out.println("页面删除成功！ ");
				return true;
		}catch (Exception e) {
			System.out.println("页面删除失败！ ");
			return false;
		}
	}
	


	@Override
	public Map findOne(Long itemId) {
		System.out.println(itemId);
		TbItem item=itemMapper.selectByPrimaryKey(itemId);
		System.out.println(item);
		Long goodsId=item.getGoodsId();
		Map dataModel=new HashMap<>();	
		//1.加载商品表数据
		TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
		
		dataModel.put("goods", goods);	

		//2.加载商品扩展表数据			
		TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
		
		dataModel.put("goodsDesc", goodsDesc);	

		//3.商品分类
		String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();

		String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
		
		String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
		dataModel.put("itemCat1", itemCat1);
		dataModel.put("itemCat2", itemCat2);
		dataModel.put("itemCat3", itemCat3);
		//4.SKU列表			
		TbItemExample example=new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");//状态为有效
		criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
		criteria.andBrandEqualTo(item.getBrand());
		//criteria.andCategoryEqualTo(item.getCategory());
		//criteria.andSellerEqualTo(item.getSellerId());
		example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认			
		
		List<TbItem> itemList = itemMapper.selectByExample(example);

		dataModel.put("itemList", itemList);
		
		
		return dataModel;
	}


	@Override
	public boolean delete(Long[] itemIds) {
		int n=0;
		for(Long itemId:itemIds){
			n+=itemMapper.deleteByPrimaryKey(itemId);
		}
		return n<itemIds.length?false:true;
	}


	@Override
	public List<TbItem> findAll() {
		
		return itemMapper.selectByExample(null);
	}




}
