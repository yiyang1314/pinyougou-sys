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
<h1>项目内容介绍</h1>
  该项目是综合性大型商城，使用了dobbox,zookepper+ssm+maven+spring-security搭建分布式服务。本系统包含三个子系统，分别是商家后台管理，运营商管理系统，前台系统。前台系统的页面实现采用了bootstrap，angelarJS 前端MVC分离及select2插件技术分别实现购物车模块，用户中心，主页搜索，订单查询，地址管理模块，cas单点登录.商家后台包含商品增加，商品商品管理，商家登录注册，订单管理，账单管理，运营商管理系统包含商家审核，商品审核，用户管理，商品管理模块，广告类型管理，商品类型模板管理，订单管理。

购物车管理:    用户浏览商品详情页，通过商品详情页的id获取添加到购物车中后，获取购物车的商品列表，总价，件数，并写入redis中，当用户登录账号后，系统自动读取客户端cookie中的商品列表，和redis中的购物车商品进行判断，如果数据库中存在该商品，那就把cookie中的件数，更新到数据库中，如果不存在，则写入数据库中，合并购物车添加到redis中。

商品搜索管理:  后端首先配置spring-data-solr域字段，并在商品详情实体类中添加solr注解。读取数据库的商品详情表，将商品审核通过的商品写入redis,然后从redis中读取数据，放入solr中，当用在前台搜索商品名时，发出请求时，后端调用solr按照keywords关键字进行模糊查询读取数据，返回给前台，进行页面显示。

后台登录注册模快:运营商管理和商家管理的短信验证，首先搭建activemq消息中间件，配置在生产者的数据源，连接工厂，队列的bean，生产验证码发送给activemq队列，在消费方实现消息队列的监听类，并获取生产者给activemq传入的验证码，短信appkey，然后通过阿里大于短信服务远程调用实现真实的短信发送通知。
