package com.pinyougou.jms.activemq;

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


public class MyMessageListener implements MessageListener {
	public void onMessage(Message message) {
	TextMessage textMessage=(TextMessage)message;		
		try {
			System.out.println("接收到消息："+textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}