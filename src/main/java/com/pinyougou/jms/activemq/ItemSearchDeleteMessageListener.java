package com.pinyougou.jms.activemq;

import java.util.Arrays;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinyougou.admin.services.ItemSearchService;
import com.pinyougou.pojo.TbItem;

@Component
public class ItemSearchDeleteMessageListener implements MessageListener {
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	
	public void onMessage(Message message) {
		ObjectMessage textMessage=(ObjectMessage)message;		
		try {
			Long []ids =(Long[]) textMessage.getObject();
			System.out.println("已监听到ids："+ids);
			itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			System.out.println("移除成功！");
		} catch (JMSException e) {
			System.out.println("移除失败消息!");
		}
	}
}