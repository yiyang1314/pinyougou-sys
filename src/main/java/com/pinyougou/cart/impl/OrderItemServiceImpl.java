package com.pinyougou.cart.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.cart.services.OrderItemService;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.TbOrderItemExample;
import com.pinyougou.pojo.TbOrderItemExample.Criteria;


import entity.PageResult;

/**
 * 服务实现层
 * @author tangyang
 * @category OrderItemServiceImpl 订单明细业务处理
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrderItem> findAll() {
		return orderItemMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbOrderItem> page=   (Page<TbOrderItem>) orderItemMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbOrderItem orderItem) {
		orderItemMapper.insert(orderItem);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrderItem orderItem){
		orderItemMapper.updateByPrimaryKey(orderItem);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrderItem findOne(Long id){
		return orderItemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			orderItemMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbOrderItem orderItem, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbOrderItemExample example=new TbOrderItemExample();
		Criteria criteria = example.createCriteria();
		
		if(orderItem!=null){			
			if(orderItem.getId()!=null && orderItem.getId().longValue()>0){

				criteria.andIdEqualTo(orderItem.getId().longValue());
			}
			if(orderItem.getOrderId()!=null && orderItem.getOrderId().longValue()>0){
				criteria.andOrderIdEqualTo(orderItem.getOrderId().longValue());
			}
			if(orderItem.getGoodsId()!=null && orderItem.getGoodsId().longValue()>0){
				criteria.andOrderIdEqualTo(orderItem.getGoodsId().longValue());
			}			
			if(orderItem.getItemId()!=null && orderItem.getItemId().longValue()>0){
				criteria.andOrderIdEqualTo(orderItem.getItemId().longValue());
			}
			if(orderItem.getSellerId()!=null && orderItem.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+orderItem.getSellerId()+"%");
			}
			if(orderItem.getTitle()!=null &&orderItem.getTitle().length()>0){
				criteria.andTitleLike("%"+orderItem.getTitle()+"%");
			}
	
		}
		
		Page<TbOrderItem> page= (Page<TbOrderItem>)orderItemMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
