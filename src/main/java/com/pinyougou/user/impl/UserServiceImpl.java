package com.pinyougou.user.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pingyougou.utilscommons.SmsUtil;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.TbUserExample;
import com.pinyougou.pojo.TbUserExample.Criteria;
import com.pinyougou.user.services.UserService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbUser> page=   (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbUser user) {
		System.out.println("userservices'add()");
		Date updated =new Date();
		user.setCreated(updated);
		user.setUpdated(updated);
		user.setSourceType("1");
		String pwd=DigestUtils.md5Hex(user.getPassword());
		user.setPassword(pwd);
		userMapper.insert(user);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbUser user){
		userMapper.updateByPrimaryKey(user);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Long id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			userMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(user!=null){			
			if(user.getUsername()!=null && user.getUsername().length()>0){
				criteria.andUsernameLike(user.getUsername());
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getPhone()!=null && user.getPhone().length()>0){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(user.getEmail()!=null && user.getEmail().length()>0){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(user.getSourceType()!=null && user.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(user.getNickName()!=null && user.getNickName().length()>0){
				criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(user.getName()!=null && user.getName().length()>0){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(user.getStatus()!=null && user.getStatus().length()>0){
				criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(user.getHeadPic()!=null && user.getHeadPic().length()>0){
				criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(user.getQq()!=null && user.getQq().length()>0){
				criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(user.getIsMobileCheck()!=null && user.getIsMobileCheck().length()>0){
				criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(user.getIsEmailCheck()!=null && user.getIsEmailCheck().length()>0){
				criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(user.getSex()!=null && user.getSex().length()>0){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
	
		}
		
		Page<TbUser> page= (Page<TbUser>)userMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Autowired
		private RedisTemplate redisTemplate;
		
		@Autowired
		private JmsTemplate jmsTemplate;	
		@Autowired
		private Destination smscodeDecrition;
	
		@Value("SMS_160860645")
		private String template_code;	
		@Value("品优购商城")
		private String sign_name;		
		
		/**
		 *  生成验证码
		 */
		public void createRandomNumber( final String phone) {
			//生成6位验证码
			long  d= (long) (Math.random()*1000000);
			d=d>100000?d:d+100000;
			 final String code=d+"";
			System.out.println("你的验证码是:    "+code);
			redisTemplate.boundHashOps("smscode").put(phone, code);
	
			//发送验证码给ActiveMQ
			jmsTemplate.send(smscodeDecrition, new MessageCreator() {
			
				public Message createMessage(Session session) throws JMSException {
					System.out.println("session.createMapMessage()-----------------");
					Map map=new HashMap<>();
					map.put("code", code);
					map.put("mobie",phone);
					map.put("template_code",template_code);
					map.put("sign_name", sign_name);
					System.out.println("session.createMapMessage()！"+map);
					return session.createTextMessage(JSON.toJSONString(map));
				}
				
			});
			System.out.println("smscodeDecritionb你的验证码是:    "+code);
//			Map message = new HashMap();
//			message.put("mobie",phone);
//			message.put("template_code",template_code);
//			message.put("sign_name", sign_name);
//			Map param=new HashMap<>();
//			param.put("code", code);
//			message.put("param", JSON.toJSONString(param));
//			jmsMessagingTemplate.convertAndSend("smscode",message);
//			//jmsMessagingTemplate.convertAndSend(message);
		}

		/**
		 * 检查验证码
		 */
		public boolean checkCode(String phone, String code) {
			String syscode=(String) redisTemplate.boundHashOps("smscode").get(phone);
			if(syscode==null) {
				return false;
			}
			if(!syscode.equals(code)) {
				return false;
			}
			return true;
		}


		/**
		 * 用户登录的实现
		 */
		public Map findByName(String username,String password) {
			System.out.println("UserServiceImpl'findByName(String name, String password)");
			System.out.println("name= " +username+" password="+password);
			Map map=new HashMap();
			TbUser u = userMapper.findByName(username);
			System.out.println("登录失败！"+password.equals(u.getPassword()));
			if(!password.equals(u.getPassword())) {
				map.put("status", false);
				map.put("message", "用户名或者密码错误！");
				return map;
			}
			map.put("status", true);
			map.put("message",username+ "登录成功！");
			map.put("user", u);
			return map;
		}
	
}
