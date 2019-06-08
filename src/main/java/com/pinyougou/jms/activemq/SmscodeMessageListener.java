package com.pinyougou.jms.activemq;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.pingyougou.utilscommons.SmsUtil;
@Component
public class SmscodeMessageListener implements MessageListener {
	
	@Autowired
	private SmsUtil smsUtil;
	
	public void onMessage(Message message) {
		try {
			// {"code":"684237","mobie":"18209579408","sign_name":"品优购商城","template_code":"SMS_160860645"}
			String map=((TextMessage)message).getText();	
			Gson gson = new Gson();
	        Map<String, Object> map2 = new HashMap<String, Object>();
	        
	        // {code=149659, sign_name=品优购商城, mobie=18209579408, template_code=SMS_160860645}
	        map2 = gson.fromJson(map, map2.getClass());
	        
	        // {"code":"149659"}
	        String param1="{\"code\":\""+map2.get("code")+"\"}";    
       
	        System.out.println("map2的值为:"+map2);
			SendSmsResponse response = smsUtil.sendSms(
					map2.get("mobie")+"", 
					map2.get("template_code")+"",
					map2.get("sign_name")+"",
					param1
			);
		    System.out.println("Code=" + response.getCode());
	        System.out.println("Message=" + response.getMessage());
	        System.out.println("RequestId=" + response.getRequestId());
	        System.out.println("BizId=" + response.getBizId());	
			//redisTemplate.boundHashOps("smscode").put(phone, code);
	        System.out.println("消息发送成功！" );
		} catch (ClientException e) {
			 System.out.println("客户端错误："+e.getErrMsg() );
			 System.out.println("客户端错误："+e.getCause() );
			 System.out.println("客户端错误："+e.getMessage());
		}catch (JMSException e) {
			System.out.println("JMSException错误："+e.getCause());
			System.out.println("JMSException错误："+e.getMessage());

		}
		
	}

}
