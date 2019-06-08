package com.pinyougou.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingyougou.utilscommons.PhoneFormatCheckUtils;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.services.UserService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbUser> findAll(){			
		return userService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return userService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbUser user){
		
		try {
			userService.add(user);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbUser user){
		try {
			userService.update(user);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbUser findOne(Long id){
		return userService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			userService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbUser user, int page, int rows  ){
		return userService.findPage(user, page, rows);		
	}
	
	@RequestMapping("/sendCode")
	public Result  sendCode(String phone) {
		if(!PhoneFormatCheckUtils.isPhoneLegal(phone)) {
			return new Result(false, "发手机格式不合法！");
		}
		try {
			userService.createRandomNumber(phone);
			return new Result(true, "发送验证码成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "发送验证码失败");
		}
	}
	
	/**
	 *  用户登录成功后根据用户获取用户信息
	 * @return
	 */
	@RequestMapping("/searchOne")
	public PageResult searchOne(){
		String username =SecurityContextHolder.getContext().getAuthentication().getName();
		TbUser user=new TbUser();
		user.setUsername(username);;
		return userService.findPage(user, 1, 1);		
	}
	/**
	 * 用户注册时根据用户名查找是否存在存在数据库中
	 * @param user
	 * @return
	 */
	@RequestMapping("/searchEOne")
	public PageResult searchEOne(@RequestBody TbUser user){
		System.out.println(user);
		return userService.findPage(user, 1, 1);		
	}
}
