package com.pinyougou.admin.services;
import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.MgrAdmin;
import com.pinyougou.pojo.MgrAdminExample;
import com.pinyougou.pojo.TbBrand;

import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AdminService {
	 public  MgrAdmin findByName(String username);
}
