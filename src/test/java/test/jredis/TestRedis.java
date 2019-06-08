package test.jredis;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-redis.xml")
public class TestRedis {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void TestSetValue() {
		redisTemplate.boundValueOps("name").set("xiaotang"); //存值
		System.out.println(redisTemplate.boundValueOps("name").get());//取值
		redisTemplate.delete("name"); //移除值
	}
	
	
	@Test
	public void SetValue() {
		redisTemplate.boundSetOps("stus").add("vsvf","vfsbfb","vsfvf","sdfsd"); //存值
		Set set=redisTemplate.boundSetOps("stus").members();//取值
		System.out.println(set);
		redisTemplate.boundSetOps("stus").remove("vsvf"); //移除一个值
		redisTemplate.delete("stus");//移除全部
	}
	
	
	@Test
	public void ListValue() {
		redisTemplate.boundListOps("vfev").rightPush("vsv发吃顿饭f");//右压栈存值
		redisTemplate.boundListOps("vfev").rightPush("vsfvfbfvf");//右压栈存值
		redisTemplate.boundListOps("vfev").rightPush("vs不方便vf");//右压栈存值；向后添加的元素向后排
		List list=redisTemplate.boundListOps("vfev").range(0,5);//取值，查询
		redisTemplate.boundListOps("vfev").leftPush("dfvf不得不代购vrv");//左压栈存值
		redisTemplate.boundListOps("vfev").leftPush("dfv多功能电脑fvrv");//左压栈存值
		redisTemplate.boundListOps("vfev").leftPush("方便");//左压栈存值  ；向后添加的元素向前排
		
		List list1=redisTemplate.boundListOps("vfev").range(0,5);//取值
		
		String str =(String) redisTemplate.boundListOps("vfev").index(1);//查询元素
		
		redisTemplate.boundListOps("vfev").remove(1, "fgergr");//移除i个，value值
		
		redisTemplate.boundListOps("vfev");
	}
	
	
	@Test
	public void HashMapValue() {
		redisTemplate.boundHashOps("vfvv").put("nbcdhcd", "frfer");//存值
		
		Set keys=redisTemplate.boundHashOps("vfvv").keys();//取key
		List values=redisTemplate.boundHashOps("vfvv").values();//取value
		
		redisTemplate.boundHashOps("vfvv").get("nbcdhcd");//key 取value
		
		redisTemplate.boundHashOps("vfvv").delete("","","","");//key 取value

}
}
