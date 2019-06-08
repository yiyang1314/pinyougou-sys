# pinyougou-sys
本系统是一个综合性好的商城项目，分为三个子系统。分为后台管理系统，商家后台系统，网站前台。基于SSM+maven+tomcat7+redis+solr+cas+bootstrap+AngularJS技术实现。
**项目结构** 
```
SSM-pinyougou
├──LOG 日志
│  ├─ssm.log
├──resource 配置文件
│  ├─mappers 映射文件
│       ├─*mappers 实体类对应的映射文件
│  ├─properties 属性配置文件
│       ├─freemaker.properties 引擎模板属性配置
│       ├─redis-config.properties Redis配置
│       ├─wxpay.properties    微信支付对应的配置文件
│  ├─spring 配置文件
│       ├─spring-mvc.xml spring配置
│       ├─spring-bean.xml 
│       ├─spring-mybatis.xml  数据源配置
│       ├─spring-security.xml 权限配置
│       ├─spring-redis.xml    缓存配置
│       ├─spring-solr.xml     solr配置
│       ├─spring-activemq-customer.xml  消息队列消费者配置
│       ├─spring-activemq-producer.xml  生产者配置
│  ├─mybatis-config.xml mybatis文件整合配置
│  ├─log4j.properties 日志文件整合配置
│  ├─jdbc.properties  数据库连接配置

├──webapp
│  ├─manager-admin 后台子管理系统
│  ├─shop_web 商家后台子系统
│  ├─web 网站前台子系统
│  ├─WEB-INF

<br> 

<h1>技术实现</h1>
- 核心框架：Spring
- 安全框架：Spring-security,CAS
- 视图框架：Spring MVC
- 持久层框架：MyBatis
- 缓存技术：Redis
- 数据库连接池：Druid
- 消息中间：ActiveMQ
- 日志管理：Log4j
- 模版技术：FreeMarker
- 页面交互：BootStrap、AngularJs,Select2等
<br> 
