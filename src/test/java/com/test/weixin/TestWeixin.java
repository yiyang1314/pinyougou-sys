package com.test.weixin;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-bean.xml")
public class TestWeixin {

	
	
	
	@Value("${appid}")
	private String appid;
	@Value("${partner}")
	private String partner;
	@Value("${partnerkey}")
	private String partnerkey;
	@Value("${notifyurl}")
	private String notifyurl;
	
	
	
	
	@Test
	public void TestSetValue() {

		System.out.println(appid);//取值
		System.out.println(partner);//取值
		System.out.println(partnerkey);//取值
		System.out.println(notifyurl);//取值
		
	}
	
	

}
