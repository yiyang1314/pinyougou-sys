package com.pinyougou.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.pinyougou.admin.services.ItemSearchService;
import com.pinyougou.pojo.TbItem;
/**
 * 
 * @author tangyang
 * @category 商品列表
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;
	

	public Map search(Map searchMap) {
		Map map=new HashMap();
		
		String keywords=(String) searchMap.get("keywords");
		searchMap.put("keywords", keywords.replace(" ", ""));
		//1.查询列表
		map.putAll(searchList(searchMap));
		
		System.out.println(map);
		//2.分组查询 商品分类列表
		List<String> categoryList = searchCategoryList(searchMap);
		System.out.println(categoryList);
		map.put("categoryList", categoryList);
		//3.查询品牌和规格列表
		String category= (String) searchMap.get("category");
		if(!category.equals("")){

			map.putAll(searchBrandAndSpecList(category));
		}else{

			if(categoryList.size()>0){	
				map.putAll(searchBrandAndSpecList(categoryList.get(0)));
			}	
		}

		return map;
	}

	//查询列表
	private Map searchList(Map searchMap){
		Map map=new HashMap();
		//高亮选项初始化
		HighlightQuery query=new SimpleHighlightQuery();		
		HighlightOptions highlightOptions=new HighlightOptions().addField("item_title");//高亮域
		highlightOptions.setSimplePrefix("<em style='color:red'>");//前缀
		highlightOptions.setSimplePostfix("</em>");		
		query.setHighlightOptions(highlightOptions);//为查询对象设置高亮选项
		
		//1.1 关键字查询
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);//query中添加关键字条件
		
		//1.2 按商品分类过滤
		if(!"".equals(searchMap.get("category"))  )	{//如果用户选择了分类 
			FilterQuery filterQuery=new SimpleFilterQuery();
			Criteria filterCriteria=new Criteria("item_category").is(searchMap.get("category"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);	//按照商品分类进行条件过滤		
		}
		
		//1.3 按品牌过滤
		if(!"".equals(searchMap.get("brand"))  )	{//如果用户选择了品牌
			FilterQuery filterQuery=new SimpleFilterQuery();
			Criteria filterCriteria=new Criteria("item_brand").is(searchMap.get("brand"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);	//品牌过滤		
		}
		//1.4 按规格过滤
		if(searchMap.get("spec")!=null){		//规格过滤	
			Map<String,String> specMap= (Map<String, String>) searchMap.get("spec");
			System.out.println("specMap: "+specMap);
			for(String key :specMap.keySet()){
				System.out.println("key: "+key);
				FilterQuery filterQuery=new SimpleFilterQuery();//规格在solrhome的schema.xml为动态域
				Criteria filterCriteria=new Criteria("item_spec_"+key).is( specMap.get(key)  );
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);					
				
			}		
			
		}
		
		
		//1.5 价格过滤
		if(!"".equals(searchMap.get("price"))){			
			String[] prices= ((String) searchMap.get("price")).split("-");
			System.out.println("prices: "+prices);
			//判断最低价格部位0，则
			if(!prices[0].equals("0")) {
				FilterQuery filterQuery=new SimpleFilterQuery();
				Criteria filterCriteria=new Criteria("item_price").greaterThanEqual(prices[0]);
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);					
				
			}//最高价格不为*，则
			if(!prices[1].equals("*")) {
				FilterQuery filterQuery=new SimpleFilterQuery();
				Criteria filterCriteria=new Criteria("item_price").lessThanEqual(prices[1]);
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);					
				
			}			
		}
		
		//1.6分页功能
		Integer PageIndex=(Integer) searchMap.get("pageIndex");//获取分页索引
		if(PageIndex==null) {
			PageIndex=1;
		}
		
		Integer PageSize=(Integer) searchMap.get("pageSize");//获取分页大小
		if(PageSize==null) {
			PageIndex=20;
		}
		query.setOffset((PageIndex-1)*PageSize);//设置索引
		query.setRows(PageSize);//设置页面大小

		/*-----------------------------------------------------------------------------------------------*/
		//1.7价格排序排序
		String sortvaue=(String) searchMap.get("sort");
		String sortfeild=(String) searchMap.get("sortFeild");
		if(sortvaue!=null&&!sortvaue.equals("")) {
			if(sortvaue.equals("ASC")) {
				Sort sort=new Sort(Sort.Direction.ASC,"item_"+sortfeild);
				query.addSort(sort);
			}
			if(sortvaue.equals("DESC")) {
				Sort sort=new Sort(Sort.Direction.DESC,"item_"+sortfeild);
				query.addSort(sort);
			}
		}

		//***********  获取高亮结果集  ***********
		//高亮页对象
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
		//高亮入口集合(每条记录的高亮入口)
		List<HighlightEntry<TbItem>> entryList = page.getHighlighted();		
		for(HighlightEntry<TbItem> entry:entryList  ){
			//获取高亮列表(高亮域的个数)
			List<Highlight> highlightList = entry.getHighlights();
			/*
			for(Highlight h:highlightList){
				List<String> sns = h.getSnipplets();//每个域有可能存储多值
				System.out.println(sns);				
			}*/			
			if(highlightList.size()>0 &&  highlightList.get(0).getSnipplets().size()>0 ){
				TbItem item = entry.getEntity();
				item.setTitle(highlightList.get(0).getSnipplets().get(0));			
			}			
		}
		map.put("rows", page.getContent());
		map.put("totalPages", page.getTotalPages());
		map.put("total", page.getTotalElements());
		return map;
		
	}
	
	/**
	 * 分组查询（查询商品分类列表）
	 * @return
	 */
	private List<String> searchCategoryList(Map searchMap){
		List<String> list=new ArrayList();
		
		Query query=new SimpleQuery("*:*");
		//根据关键字查询  
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));// where ...
		query.addCriteria(criteria);
		//设置分组选项
		GroupOptions groupOptions=new GroupOptions().addGroupByField("item_category");  //group by ...
		query.setGroupOptions(groupOptions);
		//获取分组页
		GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
		//获取分组结果对象
		GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
		//获取分组入口页
		Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
		//获取分组入口集合
		List<GroupEntry<TbItem>> entryList = groupEntries.getContent();

		for(GroupEntry<TbItem> entry:entryList  ){
			list.add(entry.getGroupValue()	);	//将分组的结果添加到返回值中
		}
		return list;
		
	}
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询品牌和规格列表
	 * @param category 分类名称
	 * @return
	 */
	private Map searchBrandAndSpecList(String category){
		System.out.println("TypeTemplateServiceImpl：searchBrandAndSpecList(String category)");
		Map map=new HashMap();		
		Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);//获取分类名称
		if(typeId!=null){
			//根据模板ID查询品牌列表
			System.out.println("品牌条数："+typeId);
			List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
			map.put("brandList", brandList);//返回值添加品牌列表
			System.out.println("品牌条数："+brandList);
			//根据模板ID查询规格列表
			List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
			map.put("specList", specList);		
			System.out.println("规格条数："+specList.size());
		}			
		return map;
	}
	
	
	public void importList(List list) {
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}

	public void deleteByGoodsIds(List goodsIdList) {
		System.out.println("删除商品ID"+goodsIdList);
		Query query=new SimpleQuery();		
		Criteria criteria=new Criteria("item_goodsid").in(goodsIdList);
		query.addCriteria(criteria);
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
}
