package com.pinyougou.jms.activemq;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
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
public class ItemSearchMyMessageListener implements MessageListener {
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	
	public void onMessage(Message message) {
		
	TextMessage textMessage=(TextMessage)message;		
		try {
			String text=textMessage.getText();
			System.out.println(text);
			List<TbItem> itemlist=JSON.parseArray(text, TbItem.class);
			itemSearchService.importList(itemlist);
			for(TbItem i:itemlist) {
				System.out.println("TbItem："+i);
			}
			System.out.println("接收到消息："+textMessage.getText());
		} catch (JMSException e) {
			System.out.println("接收失败消息!");
			
		}
	}
}