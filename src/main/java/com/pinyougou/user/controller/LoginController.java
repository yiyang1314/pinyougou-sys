package com.pinyougou.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pinyougou.admin.services.AdminService;
import com.pinyougou.admin.services.SellerService;
import com.pinyougou.mapper.MgrAdminMapper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.MgrAdmin;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.pojo.TbSellerExample.Criteria;
import com.pinyougou.user.services.UserService;

import entity.Result;

@RestController
@RequestMapping("/login")
public class LoginController {	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private HttpSession session;
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	@RequestMapping("/name")
	public Map showName(){
		String name =SecurityContextHolder.getContext().getAuthentication().getName();//得到登陆人账号
		Map map=new HashMap<>();
		map.put("loginName", name);
		session.setAttribute("username", name);
		return map;		
	}	
	
	@RequestMapping("/seller")
	public Result loginSeller(@Param("username")String username,String password) {
		Map mp = sellerService.findByName(username, password);
		return new Result((boolean) mp.get("status"),mp.get("message").toString());
	}
	
	@RequestMapping("/user")
	public Result loginUsers(String username,String password) {
		MgrAdmin admin = adminService.findByName(username);
		if(admin==null) {
			return new Result(false,"用户名不存在");
		}
		if(password.equals(admin.getPassword())) {
			redisTemplate.boundHashOps("admin").put("username", admin);
			return new Result(true,admin.getUsername()+"登录成功！");
		}
		return new Result(false,"用户名或密码错误");
	}
	
	@RequestMapping("/admin")
	public MgrAdmin getAdmin() {
		MgrAdmin admin = (MgrAdmin) redisTemplate.boundHashOps("admin").get("username");
		System.out.println("admin:"+admin);
		return admin==null?new MgrAdmin():admin;
	}
	
	@RequestMapping("/checkCode")
	public Result checkCode(String phone,String smscode) {
		boolean isboo = userService.checkCode(phone, smscode);
		if(!isboo) {
			return new Result(false,"验证码错误");
		}
		return new Result(true,"验证码正确");
	}
	
	@RequestMapping("/admin_logout")
	public Result ad_logout() {
		redisTemplate.boundHashOps("admin").delete("username");
		System.out.println("admin:"+redisTemplate.boundHashOps("admin").get("username"));;
		return new Result(true,"注销成功");
	}
	
	@RequestMapping("/seller_logout")
	public Result seller_logout() {
		redisTemplate.boundHashOps("seller").delete("username");
		System.out.println("seller:"+redisTemplate.boundHashOps("seller").get("username"));;
		return new Result(true,"注销成功");
	}
}
