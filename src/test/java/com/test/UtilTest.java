package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class UtilTest {

	
	@Test
	public void testInsertSelective(){

		//y 代表年
        //M 代表月
        //d 代表日
        //H 代表24进制的小时
        //h 代表12进制的小时
        //m 代表分钟
        //s 代表秒
        //S 代表毫秒
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmssSSS" );
        Date d= new Date();
        String str = sdf.format(d);
        System.out.println("当前时间通过 yyyyMMddHHmmssSSS 格式化后的输出: "+str);
        System.out.println("length: "+str.length());
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyyMMdd" );
        Date d1= new Date();
        String str1 = sdf1.format(d1);
        System.out.println("当前时间通过 yyyyMMdd 格式化后的输出: "+str1);
	
	}
}
