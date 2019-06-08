package com.test.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinyougou.jms.activemq.QueueProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/springJms-*.xml")
public class TestQueue {

	@Autowired
	private QueueProducer queueProducer;
	
	@Test
	public void testSend(){
		queueProducer.sendTextMessage("Spring-Jms-点对点");
	}	
}