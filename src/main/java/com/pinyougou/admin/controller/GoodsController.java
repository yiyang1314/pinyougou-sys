package com.pinyougou.admin.controller;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pinyougou.admin.services.GoodsService;
import com.pinyougou.admin.services.ItemSearchService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.web.services.ItemPageServices;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	

	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	
	
//	@Autowired
//	private ItemSearchService itemSearchService;
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	@Autowired
    private Destination queueSolrTextDestination1;
	
	@Autowired
    private Destination topicTextDestination;
	
	@Autowired
	private ItemPageServices itemPageService;
	
	@RequestMapping("/updateStatus")
	public Result updateStatus(final Long[] ids, String status){
		try {
			goodsService.updateStatus(ids, status);
			if("1".equals(status)) {
				List<TbItem> itemlist = goodsService.findItemListByGoodsIdandStatus(ids,status);
				final String jsonstring=JSON.toJSONString(itemlist);
				System.out.println(jsonstring.length());
				jmsTemplate.send(queueSolrTextDestination1, new MessageCreator(){
			
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(jsonstring);
					}
					
				});
				System.out.println(jsonstring.length());
				for(final Long goodsId:ids){			
					//itemSearchService.importList(itemlist);
					jmsTemplate.send(topicTextDestination, new MessageCreator(){
					
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(""+goodsId);
						}
					});
				}
			
			}		
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "失败");
		}		
	}
	
	/****************************************************************************************/
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		//获取商家ID
//        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmssSSS" );
//        Date d= new Date();
//        String str1 = sdf.format(d);
//        Long id =Long.parseLong(str1);
//		goods.getGoods().setId(id );
		
		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	


	/** 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/updateShop")
	public Result updateShop(@RequestBody Goods goods){
		//当前商家ID
	//	String sellerId = "qiandu";//SecurityContextHolder.getContext().getAuthentication().getName();
		
		//首先判断商品是否是该商家的商品 
		Goods goods2 = goodsService.findOne(goods.getGoods().getId());
		if(!goods2.getGoods().getSellerId().equals(goods.getGoods().getSellerId()) || !goods.getGoods().getSellerId().equals(goods.getGoods().getSellerId()) ){
			return new Result(false, "非法操作");
		}		
		
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/searches")
	public PageResult searches(@RequestBody TbGoods goods, int page, int rows  ){
//		//获取商家ID
//		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
//		goods.setSellerId(sellerId);
		return goodsService.findPage(goods, page, rows);		
	}
	
	
	
	@Autowired
    private Destination queueSolrTextDestinationdelete1;
	
	@Autowired
    private Destination topicDeleteDestination;
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(final Long [] ids){
		try {
			goodsService.delete(ids);
			//itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			
			//从索引solr库中移除
			jmsTemplate.send(queueSolrTextDestinationdelete1, new MessageCreator(){
				
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(ids);
				}
				
			});
			
			//从索引solr库中移除
			jmsTemplate.send(topicDeleteDestination, new MessageCreator(){

				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(ids);
				}
				
			});
			
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	
	@Autowired
	private ItemPageServices itemPageServices;
	/**
	 * 生成静态页（测试）
	 * @param goodsId
	 */
	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId){
		itemPageServices.getItemHtml(goodsId);	
	}
}

