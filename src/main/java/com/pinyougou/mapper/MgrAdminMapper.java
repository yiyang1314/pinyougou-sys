package com.pinyougou.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pinyougou.pojo.MgrAdmin;
import com.pinyougou.pojo.MgrAdminExample;
@Repository
public interface MgrAdminMapper {
    MgrAdmin selectByPrimaryKey(String username);

}