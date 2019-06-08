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
import com.pinyougou.web.services.ItemPageServices;

@Component
public class PageMessageListener implements MessageListener {
	
	@Autowired
	private ItemPageServices itemPageServices;
	
	
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage)message;		
		try {
			String text = textMessage.getText();
			System.out.println("已监听到goodsid："+text);
			boolean isB=itemPageServices.getItemHtml(Long.parseLong(text));
			System.out.println("页面生成结果为！  "+isB);
		} catch (JMSException e) {
			System.out.println("移除失败消息!");
		}
	}
}