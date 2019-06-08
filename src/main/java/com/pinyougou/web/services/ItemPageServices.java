package com.pinyougou.web.services;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbItem;

/**
 * @category 商品详情页
 * @author tangyang
 *
 */
public interface ItemPageServices {
	/**
	 * 生成商品详细页
	 * @param goodsId
	 */
		public boolean getItemHtml(Long goodsId);
		
		/**
		 * 生成商品详细页
		 * @param goodsId
		 */
		public boolean deleteItemHtml(Long[] goodsId);
		
		
		
		public Map findOne(Long goodsId);
		
		public boolean delete(Long[] goodsId);
		
		public List<TbItem> findAll();
		
	
}
