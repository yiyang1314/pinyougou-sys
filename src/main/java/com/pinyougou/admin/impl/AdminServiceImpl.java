package com.pinyougou.admin.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.admin.services.AdminService;
import com.pinyougou.admin.services.BrandService;
import com.pinyougou.mapper.MgrAdminMapper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.MgrAdmin;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;


import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
//@Transactional
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private MgrAdminMapper mgrAdminMapper;
	
	
	@Override
	public MgrAdmin findByName(String username) {
		System.out.println(username);
		return mgrAdminMapper.selectByPrimaryKey(username);
	}

	
}
