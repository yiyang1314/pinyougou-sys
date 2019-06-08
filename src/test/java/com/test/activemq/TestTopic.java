package com.test.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinyougou.jms.activemq.TopicProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/springJms-*.xml")
public class TestTopic {
	@Autowired
	private TopicProducer topicProducer;
	@Test
	public void sendTextQueue(){		
		topicProducer.sendTextMessage("xvqucvwhgcvw");
	}	
}