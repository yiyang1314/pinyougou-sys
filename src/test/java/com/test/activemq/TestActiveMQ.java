package com.test.activemq;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TestActiveMQ {
	private Session session;
	private Connection connection;
	private MessageProducer producer;
	private MessageConsumer consumer;
	public String keyName;
	
	public TestActiveMQ() {}
	public TestActiveMQ(String tcp) {
		try {
			this.session =this.getSession(tcp);
		} catch (JMSException e) {
			System.out.println("tcp:"+tcp+"地址无效，连接出错！");
			e.printStackTrace();
		}
		
	}

	private Session getSession(String tcp) throws JMSException {
		//1.创建连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(tcp);
		//2.获取连接
		connection = connectionFactory.createConnection();
		//3.启动连接
		connection.start();
		/**
		 * AUTO_ACKNOWLEDGE = 1;
		 * CLIENT_ACKNOWLEDGE = 2;
		 * DUPS_OK_ACKNOWLEDGE = 3;
		 * SESSION_TRANSACTED = 0;
		 */
		//4.获取session  (参数1：是否启动事务,参数2：消息确认模式)
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);	
		return session;
		
	}
	public void createProducerQueue(String queueName) throws JMSException {
		//5.创建队列对象
		Queue queue = session.createQueue(queueName);
		//6.创建消息生产者
		producer = session.createProducer(queue);
		String name="张三";
		//7.创建消息
		TextMessage textMessage = session.createTextMessage(name+",欢迎来到神奇的品优购世界!");
		//8.发送消息
		producer.send(textMessage);
		System.out.println("已发送！");
		//9.关闭资源

	}
	
	public void createConsumerQueue(String queueName) throws JMSException {
		//5.创建队列对象
		Queue queue = session.createQueue(queueName);
		//6.创建消息消费
		consumer = session.createConsumer(queue);
		
		//7.监听消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage)message;
				System.out.println("已接收！");
				try {
					System.out.println("张三："+textMessage.getText());
				} catch (JMSException e) {
					System.out.println("系统错误！");
					e.printStackTrace();
				}
			}
		});	
	}
	
	public void createProducerTopic(String topicName) throws JMSException {
		//5.创建主题对象
		Topic topic = session.createTopic(topicName);
		//6.创建消息生产者
		MessageProducer producer = session.createProducer(topic);
		//7.创建消息
		TextMessage textMessage = session.createTextMessage("欢迎来到神奇的品优购世界");
		//8.发送消息
		producer.send(textMessage);
	}
	
	public void createConsumerTopic(String topicName) throws JMSException, IOException {
		//5.创建主题对象
		//Queue queue = session.createQueue("test-queue");
		Topic topic = session.createTopic(topicName);
		//6.创建消息消费
		MessageConsumer consumer = session.createConsumer(topic);
		
		//7.监听消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage)message;
				try {
					System.out.println("接收到消息："+textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//8.等待键盘输入
		System.in.read();
	}
	
	
	
	
	
	
	
	
	
	
	
	public void close() {
		try {
			//9.关闭资源
			session.close();
			connection.close();
			producer.close();
			System.out.println("关闭成功！");
		} catch (JMSException e) {
			System.out.println("关闭失败！");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws JMSException {
		Scanner scn=new Scanner(System.in);
		System.out.println("请输入你连接的tcp地址：");
		String tcp=scn.nextLine();
		TestActiveMQ mq=new TestActiveMQ(tcp);
		System.out.println("连接成功！：");
		System.out.println("请输入你要获取或设置的主题名：");
		String primaryName=scn.nextLine();
		
		
		mq.createProducerQueue(primaryName);
		System.out.println("------------------------------------------");
		mq.createConsumerQueue(primaryName);
		mq.close();
	}

}
