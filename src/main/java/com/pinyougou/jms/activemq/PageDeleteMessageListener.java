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
public class PageDeleteMessageListener implements MessageListener {
	
	@Autowired
	private ItemPageServices itemPageServices;
	
	
	public void onMessage(Message message) {
		ObjectMessage textMessage=(ObjectMessage)message;		
		try {
			Long []ids = (Long[]) textMessage.getObject();
			System.out.println("已监听到goodsid："+ids);
			boolean isB=itemPageServices.deleteItemHtml(ids);
			System.out.println("页面删除成功  ");
		} catch (JMSException e) {
			System.out.println("页面删除失败  ");
		}
	}
}