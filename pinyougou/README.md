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


##技术实现
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
```

##项目内容介绍
>   该项目是综合性大型商城，使用了dobbox,zookepper+ssm+maven+spring-security搭建分布式服务。本系统包含三个子系统，分别是商家后台管理，运营商管理系统，前台系统。前台系统的页面实现采用了bootstrap，angelarJS 前端MVC分离及select2插件技术分别实现购物车模块，用户中心，主页搜索，订单查询，地址管理模块，cas单点登录.商家后台包含商品增加，商品商品管理，商家登录注册，订单管理，账单管理，运营商管理系统包含商家审核，商品审核，用户管理，商品管理模块，广告类型管理，商品类型模板管理，订单管理。
>

> 购物车管理:    用户浏览商品详情页，通过商品详情页的id获取添加到购物车中后，获取购物车的商品列表，总价，件数，并写入redis中，当用户登录账号后，系统自动读取客户端cookie中的商品列表，和redis中的购物车商品进行判断，如果数据库中存在该商品，那就把cookie中的件数，更新到数据库中，如果不存在，则写入数据库中，合并购物车添加到redis中。
>

> 商品搜索管理:  后端首先配置spring-data-solr域字段，并在商品详情实体类中添加solr注解。读取数据库的商品详情表，将商品审核通过的商品写入redis,然后从redis中读取数据，放入solr中，当用在前台搜索商品名时，发出请求时，后端调用solr按照keywords关键字进行模糊查询读取数据，返回给前台，进行页面显示。
>

后台登录注册模快:运营商管理和商家管理的短信验证，首先搭建activemq消息中间件，配置在生产者的数据源，连接工厂，队列的bean，生产验证码发送给activemq队列，在消费方实现消息队列的监听类，并获取生产者给activemq传入的验证码，短信appkey，然后通过阿里大于短信服务远程调用实现真实的短信发送通知。

###1. 添加pom依赖配置文件

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.pinyou.gou</groupId>
  <artifactId>SSM-pinyougou</artifactId>
  <version>1.5.1-SNAPSHOT</version>
  <packaging>war</packaging>
  	<properties>
		<!-- spring版本号 -->
		<spring.version>4.2.4.RELEASE</spring.version>
		<!-- mybatis版本号 -->
		<mybatis.version>3.2.6</mybatis.version>
		<!-- log4j日志文件管理包版本 -->
		<slf4j.version>1.7.7</slf4j.version>
		<log4j.version>1.2.17</log4j.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<!-- 表示开发的时候引入，发布的时候不会加载此包 -->
			<scope>test</scope>
		</dependency>
		<!-- spring核心包 -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-web</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-oxm</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-tx</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<!-- mybatis核心包 -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.2.8</version>
		</dependency>
		<!-- mybatis/spring包 -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.2.2</version>
		</dependency>
		<!-- 导入java ee jar 包 -->
		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-api</artifactId>
			<version>7.0</version>
		</dependency>
		<!-- 导入Mysql数据库链接jar包 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.30</version>
		</dependency>
		<!-- 导入dbcp的jar包，用来在applicationContext.xml中配置数据库 -->
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.2.2</version>
		</dependency>
		<!-- JSTL标签类 -->
		<dependency>
			<groupId>jstl</groupId>
			<artifactId>jstl</artifactId>
			<version>1.2</version>
		</dependency>
		<!-- 日志文件管理包 -->
		<!-- log start -->
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>${log4j.version}</version>
		</dependency>
		
		<!-- 格式化对象，方便输出日志 -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
			<version>1.1.41</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>${slf4j.version}</version>
		</dependency>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>${slf4j.version}</version>
		</dependency>
		<!-- log end -->
		<!-- 映入JSON -->
		<dependency>
			<groupId>org.codehaus.jackson</groupId>
			<artifactId>jackson-mapper-asl</artifactId>
			<version>1.9.13</version>
		</dependency>
		<!-- 上传组件包 -->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.3.1</version>
		</dependency>
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.9</version>
		</dependency>
		 <!--  分页助手 --> 


		 <dependency>
		 	<groupId>com.github.wxpay</groupId>
		 	<artifactId>wxpay-sdk</artifactId>
		 	<version>0.0.3</version>
		 </dependency>
		 <dependency>
		 	<groupId>com.github.miemiedev</groupId>
		 	<artifactId>mybatis-paginator</artifactId>
		 	<version>1.2.15</version>
		 </dependency>
		 <dependency>
		 	<groupId>com.github.pagehelper</groupId>
		 	<artifactId>pagehelper</artifactId>
		 	<version>4.1.4</version>
		 </dependency>
  		<!-- 缓存 -->
		<dependency> 
		  <groupId>redis.clients</groupId> 
		  <artifactId>jedis</artifactId> 
		  <version>2.8.2</version> 
		</dependency> 
		<dependency> 
		  <groupId>org.springframework.data</groupId> 
		  <artifactId>spring-data-redis</artifactId> 
		  <version>1.7.2.RELEASE</version> 
		</dependency>	
		
	<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-solr</artifactId>
		<version>1.5.5.RELEASE</version>
	</dependency>
	<dependency>
		<groupId>org.freemarker</groupId>
		<artifactId>freemarker</artifactId>
		<version>2.3.23</version>
	</dependency>
	
	<dependency>
		<groupId>org.apache.activemq</groupId>
		<artifactId>activemq-client</artifactId>
		<version>5.13.4</version>
	 </dependency>
	
	
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-jms</artifactId>
		<version>4.2.4.RELEASE</version>
	</dependency>
	
	
		<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->

		<!-- https://mvnrepository.com/artifact/net.sourceforge.jexcelapi/jxl -->
		<!-- https://mvnrepository.com/artifact/com.lowagie/itext -->
		<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->

		<dependency>
			<groupId>com.aliyun</groupId>
			<artifactId>aliyun-java-sdk-dysmsapi</artifactId>
			<version>1.0.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>com.aliyun</groupId>
			<artifactId>aliyun-java-sdk-core</artifactId>
			<version>3.2.3</version>
		</dependency>
		<dependency>
			<groupId>dom4j</groupId>
			<artifactId>dom4j</artifactId>
			<version>1.6</version>
		</dependency>
		
		  <!-- 配置gson -->
	    <dependency>
	        <groupId>com.google.code.gson</groupId>
	        <artifactId>gson</artifactId>
	        <version>2.2.4</version>
	    </dependency>
	    
	        <!-- spring-security 相关依赖  -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-web</artifactId>
			<version>4.1.0.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
			<version>4.1.0.RELEASE</version>
		</dependency>

		<dependency>  
			   <groupId>org.springframework.security</groupId>  
			   <artifactId>spring-security-cas</artifactId>  
			   <version>4.1.0.RELEASE</version>  
		</dependency>     
		<dependency>  
		        <groupId>org.jasig.cas.client</groupId>  
		        <artifactId>cas-client-core</artifactId>  
		        <version>3.3.3</version>  
		        <exclusions>  
		            <exclusion>  
		                <groupId>org.slf4j</groupId>  
		                <artifactId>log4j-over-slf4j</artifactId>  
		            </exclusion>  
		        </exclusions>  
		</dependency> 
		<dependency>
				<groupId>xml-apis</groupId>
				<artifactId>xml-apis</artifactId>
				<version>1.4.01</version>
		</dependency>
	</dependencies>
	
	  <build>  
	  <plugins>
	      <plugin>  
	          <groupId>org.apache.maven.plugins</groupId>  
	          <artifactId>maven-compiler-plugin</artifactId>  
	          <version>2.3.2</version>  
	          <configuration>  
	              <source>1.7</source>  
	              <target>1.7</target>  
	          </configuration>  
	      </plugin>  
			<!-- 	      
			<plugin>
				<groupId>org.apache.tomcat.maven</groupId>
				<artifactId>tomcat7-maven-plugin</artifactId>
				<configuration>
					<port>9112</port>
					<path>/</path>
				</configuration>
	  	  </plugin>
	  	   -->
	  </plugins>  
    </build>
</project>
```

### 2. 添加数据库属性文件jdbc.properties配置信息

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql:///pinyougou?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true
username=root
password=
#\u5B9A\u4E49\u521D\u59CB\u8FDE\u63A5\u6570
initialSize=0
#\u5B9A\u4E49\u521D\u59CB\u8FDE\u63A5\u6570
maxActive=20
#\u5B9A\u4E49\u6700\u5927\u7A7A\u95F2
maxIdle=20
#\u5B9A\u4E49\u6700\u5C0F\u7A7A\u95F2
minIdle=1
#\u5B9A\u4E49\u6700\u957F\u7B49\u5F85\u65F6\u95F4
maxWait=60000
freemaker.itemdir=C:\\redis\\web\\
```

### 3. 配置web.xml文件信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">
	<display-name>SSM-pinyougou</display-name>

	<!-- 编码过滤器 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<async-supported>true</async-supported>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	<!-- Spring监听器 -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<!-- 防止Spring内存溢出监听器 -->
	<listener>
		<listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>
	</listener>
	
	<!--spring-secrity权限过滤监听-->
	 <filter>  
		<filter-name>springSecurityFilterChain</filter-name>  
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>  
	 </filter>  
	 <filter-mapping>  
		<filter-name>springSecurityFilterChain</filter-name>  
		<url-pattern>/*</url-pattern>  
	 </filter-mapping>
	
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/spring-security.xml</param-value>
	</context-param>

	<!-- Spring MVC配置 -->
	<servlet>
		<servlet-name>SpringMVC</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:spring/spring-*.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
		<async-supported>true</async-supported>
	</servlet>
	<servlet-mapping>
		<servlet-name>SpringMVC</servlet-name>
		<!-- 此处可以可以配置成*.do，对应struts的后缀习惯 -->
		<url-pattern>*.do</url-pattern>
	</servlet-mapping>

	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
	</welcome-file-list>

</web-app>
```

### 4. 添加spring-mvc.xml配置信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans  
                        http://www.springframework.org/schema/beans/spring-beans-4.1.xsd  
                        http://www.springframework.org/schema/context  
                        http://www.springframework.org/schema/context/spring-context-4.0.xsd  
                        http://www.springframework.org/schema/mvc  
                        http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">
	<!-- 自动扫描该包，使SpringMVC认为包下用了@controller注解的类是控制器 -->
	<context:component-scan base-package="com.pinyougou.controllter" />
	<!-- 配置注解驱动 -->
	<mvc:annotation-driven>
	  <mvc:message-converters register-defaults="true">
	    <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">  
	      <property name="supportedMediaTypes" value="application/json"/>
	      <property name="features">
	        <array>
	          <value>WriteMapNullValue</value>
	          <value>WriteDateUseDateFormat</value>
	        </array>
	      </property>
	    </bean>
	  </mvc:message-converters>  
	</mvc:annotation-driven>


	<!-- 定义跳转的文件的前后缀 ，视图模式配置-->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 这里的配置我的理解是自动给后面action的方法return的字符串加上前缀和后缀，变成一个 可用的url地址 -->
		<property name="prefix" value="/" />
		<property name="suffix" value=".html" />
	</bean>
	
	<!-- 配置文件上传，如果没有使用文件上传可以不用配置，当然如果不配，那么配置文件中也不必引入上传组件包 -->
	<bean id="multipartResolver"  
        class="org.springframework.web.multipart.commons.CommonsMultipartResolver">  
        <!-- 默认编码 -->
        <property name="defaultEncoding" value="utf-8" />  
        <!-- 文件大小最大值 -->
        <property name="maxUploadSize" value="10485760000" />  
        <!-- 内存中的最大值 -->
        <property name="maxInMemorySize" value="40960" />  
    </bean> 

</beans>
```



### 5. 添加spring-mybatis.xml文件整合mysql数据库连接及事务控制的配置信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans  
                        http://www.springframework.org/schema/beans/spring-beans-4.1.xsd  
                        http://www.springframework.org/schema/context  
                        http://www.springframework.org/schema/context/spring-context-4.1.xsd  
                        http://www.springframework.org/schema/mvc  
                        http://www.springframework.org/schema/mvc/spring-mvc-4.1.xsd">
	<!-- 自动扫描 -->
	<context:component-scan base-package="com.pinyougou" />
	<!-- 引入配置文件 -->
	<bean id="propertyConfigurer"
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="location" value="classpath:jdbc.properties" />
	</bean>

	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"
		destroy-method="close">
		<property name="driverClassName" value="${driver}" />
		<property name="url" value="${url}" />
		<property name="username" value="${username}" />
		<property name="password" value="${password}" />
		<!-- 初始化连接大小 -->
		<property name="initialSize" value="${initialSize}"></property>
		<!-- 连接池最大数量 -->
		<property name="maxActive" value="${maxActive}"></property>
		<!-- 连接池最大空闲 -->
		<property name="maxIdle" value="${maxIdle}"></property>
		<!-- 连接池最小空闲 -->
		<property name="minIdle" value="${minIdle}"></property>
		<!-- 获取连接最大等待时间 -->
		<property name="maxWait" value="${maxWait}"></property>
	</bean>

	<!-- spring和MyBatis完美整合，不需要mybatis的配置映射文件 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		
		<property name="dataSource" ref="dataSource" />
		<!-- 自动扫描mapping.xml文件 -->
		<property name="mapperLocations" value="classpath:mappers/*.xml"></property>
		 <property name="plugins" >
			<array>
				<bean  class="com.github.pagehelper.PageHelper">
						<property name="properties" >
								<value>
									dialect=mysql
									reasonable=true
								</value>
		   				</property>
				</bean>
			</array>
		</property>
		
	</bean>

	<!-- DAO接口所在包名，Spring会自动查找其下的类 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.pinyougou.mapper" />
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
	</bean>

	<!-- (事务管理)transaction manager, use JtaTransactionManager for global tx -->
	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
     <!--整合freemaker的配置信息-->
	<bean id="freemarkerConfig"	class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
			<property name="templateLoaderPath" value="/WEB-INF/ftl/" />
			<property name="defaultEncoding" value="UTF-8" />
    </bean>
</beans>
```

### 6. 添加spring-security.xml文件整合配置spring-security，CAS的组件信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:beans="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
						http://www.springframework.org/schema/context 
						http://www.springframework.org/schema/context/spring-context-2.5.xsd   
						http://www.springframework.org/schema/security 
						http://www.springframework.org/schema/security/spring-security-4.1.xsd">

	<!-- manager-admin匿名访问资源 -->
	<http pattern="/manager-admin/css/**" security="none"></http>
	<http pattern="/manager-admin/js/**" security="none"></http>
	<http pattern="/manager-admin/img/**" security="none"></http>
	<http pattern="/manager-admin/plugins/**" security="none"></http>
	<http pattern="/manager-admin/admin/*.html" security="none"></http>

	<!--web匿名访问资源 -->
	<http pattern="/index.html" security="none"></http>
	
	<http pattern="/web/css/**" security="none"></http>
	<http pattern="/web/js/**" security="none"></http>
	<http pattern="/web/img/**" security="none"></http>
	<http pattern="/web/plugins/**" security="none"></http>
	<http pattern="/web/index.html" security="none"></http>
	<http pattern="/web/register.html" security="none"></http>
	<http pattern="/web/search.html" security="none"></http>
	<http pattern="/web/item.html" security="none"></http>
	
	
	<!--shop_web匿名访问资源 -->
	<http pattern="/shop_web/css/**" security="none"></http>
	<http pattern="/shop_web/js/**" security="none"></http>
	<http pattern="/shop_web/img/**" security="none"></http>
	<http pattern="/shop_web/plugins/**" security="none"></http>
	<http pattern="/images/**" security="none"></http>
	<http pattern="/shop_web/admin/**" security="none"></http>
	<http pattern="/shop_web/register.html" security="none"></http>
<!-- 	
	
	<http pattern="/brand/*.do" security="none"></http>
	<http pattern="/contentCategory/*.do" security="none"></http>
	<http pattern="/content/*.do" security="none"></http>
	<http pattern="/goods/*.do" security="none"></http>
	<http pattern="/itemCat/*.do" security="none"></http>
	<http pattern="/itemsearch/*.do" security="none"></http>
	<http pattern="/seller/*.do" security="none"></http>
	<http pattern="/specification/*.do" security="none"></http>
	<http pattern="/typeTemplate/*.do" security="none"></http>
	<http pattern="/file/*.do" security="none"></http>
	<http pattern="/login/*.do" security="none"></http>
-->
	
	<!--   entry-point-ref  入口点引用 -->
	<http use-expressions="false" entry-point-ref="casProcessingFilterEntryPoint">  
        <intercept-url pattern="/cart/*.do" access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/brand/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/contentCategory/*.do"	access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/content/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/goods/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/itemCat/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/itemsearch/*.do" 		access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/seller/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/specification/*.do" 	access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/typeTemplate/*.do" 	access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/file/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/images/**" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>
		<intercept-url pattern="/item/*.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>		        
		<intercept-url pattern="/user/add.do" 			access="IS_AUTHENTICATED_ANONYMOUSLY"/>		        
		<intercept-url pattern="/user/findOne.do" 		access="IS_AUTHENTICATED_ANONYMOUSLY"/>	
		<intercept-url pattern="/user/sendCode.do" 		access="IS_AUTHENTICATED_ANONYMOUSLY"/>	        
        <intercept-url pattern="/user/searchEOne.do" 	access="IS_AUTHENTICATED_ANONYMOUSLY"/>
         <intercept-url pattern="/login/checkCode.do" 	access="IS_AUTHENTICATED_ANONYMOUSLY"/>
       
        <intercept-url pattern="/**" access="ROLE_USER"/>   
        <csrf disabled="true"/>  
        <!-- custom-filter为过滤器， position 表示将过滤器放在指定的位置上，before表示放在指定位置之前  ，after表示放在指定的位置之后  -->           
        <custom-filter ref="casAuthenticationFilter"  position="CAS_FILTER" />      
        <custom-filter ref="requestSingleLogoutFilter" before="LOGOUT_FILTER"/>  
        <custom-filter ref="singleLogoutFilter" before="CAS_FILTER"/>  
    </http>
    
  	<!-- CAS入口点 开始 -->
    <beans:bean id="casProcessingFilterEntryPoint" class="org.springframework.security.cas.web.CasAuthenticationEntryPoint">  
        <!-- 单点登录服务器登录URL -->  
        <beans:property name="loginUrl" value="http://localhost:8810/cas/login"/>  
        <beans:property name="serviceProperties" ref="serviceProperties"/>  
    </beans:bean>      
    <beans:bean id="serviceProperties" class="org.springframework.security.cas.ServiceProperties">  
        <!--service 配置自身工程的根地址+/login/cas   -->  
        <beans:property name="service" value="http://localhost:8888/SSM-pinyougou/login/cas"/>
    </beans:bean>  
    <!-- CAS入口点 结束 -->
   
    <!-- 认证过滤器 开始 -->
    <beans:bean id="casAuthenticationFilter" class="org.springframework.security.cas.web.CasAuthenticationFilter">  
        <beans:property name="authenticationManager" ref="authenticationManager"/>  
    </beans:bean>  
		<!-- 认证管理器 -->
	<authentication-manager alias="authenticationManager">
		<authentication-provider  ref="casAuthenticationProvider">
		</authentication-provider>
	</authentication-manager>
		<!-- 认证提供者 -->
	<beans:bean id="casAuthenticationProvider"     class="org.springframework.security.cas.authentication.CasAuthenticationProvider">  
        <beans:property name="authenticationUserDetailsService">  
            <beans:bean class="org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper">  
                <beans:constructor-arg ref="userDetailsService" />  
            </beans:bean>  
        </beans:property>  
        <beans:property name="serviceProperties" ref="serviceProperties"/>  
        <!-- ticketValidator 为票据验证器 -->
        <beans:property name="ticketValidator">  
            <beans:bean class="org.jasig.cas.client.validation.Cas20ServiceTicketValidator">  
                <beans:constructor-arg index="0" value="http://localhost:8810/cas"/>  
            </beans:bean>  
        </beans:property>  
        <beans:property name="key" value="an_id_for_this_auth_provider_only"/> 
    </beans:bean>        
   		 <!-- 认证类 -->
	<beans:bean id="userDetailsService" class="com.pinyougou.user.impl.UserDetailServiceImpl"/>  
	
	<!-- 认证过滤器 结束 -->
	<!-- 单点登出  开始  -->     
    <beans:bean id="singleLogoutFilter" class="org.jasig.cas.client.session.SingleSignOutFilter"/>          
    <beans:bean id="requestSingleLogoutFilter" class="org.springframework.security.web.authentication.logout.LogoutFilter">  
        <beans:constructor-arg value="http://localhost:8810/cas/logout?service=http://localhost:8888/SSM-pinyougou/web/index.html"/>  
        <beans:constructor-arg>  
            <beans:bean class="org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler"/>  
        </beans:constructor-arg>  
        <beans:property name="filterProcessesUrl" value="/logout/cas"/>  
    </beans:bean>  
    <!-- 单点登出  结束 -->  
</beans:beans>
```

### 7. 添加redis配置spring-redis.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans  
                        http://www.springframework.org/schema/beans/spring-beans-4.1.xsd  
                        http://www.springframework.org/schema/context  
                        http://www.springframework.org/schema/context/spring-context-4.1.xsd  
                        http://www.springframework.org/schema/mvc  
                        http://www.springframework.org/schema/mvc/spring-mvc-4.1.xsd>
			            http://www.springframework.org/schema/cache  
			            http://www.springframework.org/schema/cache/spring-cache-3.2.xsd">  
  
   <context:property-placeholder location="classpath:properties/redis-config.properties" />   

  <!-- redis连接池 -->
	<bean id="jedisConfig" class="redis.clients.jedis.JedisPoolConfig">
		<!-- 最大连接jedis数 -->
		<property name="maxTotal" value="30"></property>
		<!-- 最大空闲数 -->
		<property name="maxIdle" value="300"></property>
		<!-- 最长等待时间 -->
		<property name="maxWaitMillis" value="3000"></property>
		<!-- 获得一个jedis实例的时候是否检查连接可用性;如果为true，则得到的jedis实例均是可用的； -->
		<property name="testOnBorrow" value="true"></property>
	</bean>
	<!-- redis连接工厂 -->
	<bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
		<property name="hostName" value="127.0.0.1"></property>
		<property name="port" value="6379"></property>
		<property name="password" value=""></property>
		<property name="poolConfig" ref="jedisConfig"></property>
	</bean>
	<!-- redis操作模板，这里采用尽量面向对象的模板 -->
	<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
		<property name="connectionFactory" ref="connectionFactory" />
		 <property name="keySerializer"> 
		 	<bean class="org.springframework.data.redis.serializer.StringRedisSerializer" /> 
		 </property> 
		 <property name="valueSerializer">
		  	<bean class="org.springframework.data.redis.serializer.StringRedisSerializer" />
		</property>

	</bean>
	
	<!-- redis 相关配置
   <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">  
     <property name="maxIdle" value="${redis.maxIdle}" />   
     <property name="maxWaitMillis" value="${redis.maxWait}" />  
     <property name="testOnBorrow" value="${redis.testOnBorrow}" />  
   </bean>  
  
   <bean id="JedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" 
       p:host-name="${redis.host}" 
       p:port="${redis.port}" 
       p:password="${redis.pass}" 
       p:pool-config-ref="poolConfig"/>  
   
   <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">  
    	<property name="connectionFactory" ref="JedisConnectionFactory" />  
   </bean>  
      -->
</beans>  
```

### 8. 添加solr搜索引擎的配置spring-solr.xml文件信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:solr="http://www.springframework.org/schema/data/solr"
	xsi:schemaLocation="http://www.springframework.org/schema/data/solr 
  		http://www.springframework.org/schema/data/solr/spring-solr-1.0.xsd
		http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-4.1.xsd">
	
	<!-- 自动扫描 -->
	<context:component-scan base-package="com.pinyougou.search" />
	
	<!-- solr服务器地址 -->
	<solr:solr-server id="solrServer" url="http://127.0.0.1:8810/solr" />

   
	<!-- solr模板，使用solr模板可对索引库进行CRUD的操作 -->
	<bean id="solrTemplate" class="org.springframework.data.solr.core.SolrTemplate">
		<constructor-arg ref="solrServer" />
	</bean>
</beans>
```

### 9.添加ActiveMQ的生产者spring-jms-customer.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:solr="http://www.springframework.org/schema/data/solr"
	xsi:schemaLocation="http://www.springframework.org/schema/data/solr 
  		http://www.springframework.org/schema/data/solr/spring-solr-1.0.xsd
		http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-3.0.xsd">
		
		<!-- 自动扫描
	<context:component-scan base-package="com.pingyougou.*" />	 -->
		
	<!-- 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供-->  
	<bean id="targetConnectionFactory1" class="org.apache.activemq.ActiveMQConnectionFactory">  
	    <property name="brokerURL" value="tcp://127.0.0.1:61616"/>  
	</bean>
	
    <!-- Spring用于管理真正的ConnectionFactory的ConnectionFactory -->  
	<bean id="connectionFactory1" class="org.springframework.jms.connection.SingleConnectionFactory">  
		<!-- 目标ConnectionFactory对应真实的可以产生JMS Connection的ConnectionFactory -->  
	    <property name="targetConnectionFactory" ref="targetConnectionFactory1"/>  
	</bean> 

    <!--这个是队列目的地，点对点的  文本信息-->  
	<bean id="queueSolrTextDestination1" class="org.apache.activemq.command.ActiveMQQueue">  
	    <constructor-arg value="pinyougou_queue_solr"/>  
	</bean>
	  

    <!--这个是队列目的地，点对点的  文本信息-->  
	<bean id="queueSolrTextDestinationdelete1" class="org.apache.activemq.command.ActiveMQQueue">  
	    <constructor-arg value="pinyougou_queue_solr_delte"/>  
	</bean>

	<!-- Spring提供的JMS工具类，它可以进行消息发送、接收等 -->  
	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">  
	    <!-- 这个connectionFactory对应的是我们定义的Spring提供的那个ConnectionFactory对象 -->  
	    <property name="connectionFactory" ref="connectionFactory1"/>  
	</bean> 
	  
	 <!--这个是订阅模式  文本信息--> 
	<bean id="topicTextDestination" class="org.apache.activemq.command.ActiveMQTopic">  
	    <constructor-arg value="pinyougou_topic_pagesolr"/>  
	</bean>   
	   

	 <!--这个是订阅模式  文本信息--> 
	<bean id="topicDeleteDestination" class="org.apache.activemq.command.ActiveMQTopic">  
	    <constructor-arg value="pinyougou_topic_pagesolr_delete"/>  
	</bean>   


</beans>
```

###10.添加消费者者spring-jms-customer.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:solr="http://www.springframework.org/schema/data/solr"
	xsi:schemaLocation="http://www.springframework.org/schema/data/solr 
  		http://www.springframework.org/schema/data/solr/spring-solr-1.0.xsd
		http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context.xsd">
		
	<!-- 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供-->  
	<bean id="targetConnectionFactory2" class="org.apache.activemq.ActiveMQConnectionFactory">  
	    <property name="brokerURL" value="tcp://127.0.0.1:61616"/>  
	</bean>
	
    <!-- Spring用于管理真正的ConnectionFactory的ConnectionFactory -->  
	<bean id="connectionFactory2" class="org.springframework.jms.connection.SingleConnectionFactory">  
		<!-- 目标ConnectionFactory对应真实的可以产生JMS Connection的ConnectionFactory -->  
	    <property name="targetConnectionFactory" ref="targetConnectionFactory2"/>  
	</bean> 
	 	

	 <!--这个是队列目的地，点对点的  文本信息-->  
	<bean id="queueSolrTextDestination" class="org.apache.activemq.command.ActiveMQQueue">  
	    <constructor-arg value="pinyougou_queue_solr"/>  
	</bean> 	
	 	
	<!-- 消息监听容器 -->
	<bean  class="org.springframework.jms.listener.DefaultMessageListenerContainer">
		<property name="connectionFactory" ref="connectionFactory2" />
		<property name="destination" 	ref="queueSolrTextDestination" />
		<property name="messageListener" ref="itemSearchMyMessageListener" />
	</bean>	 


	<!--这个是队列目的地，点对点的  文本信息-->  
	<bean id="queueSolrTextDestinationdelete" class="org.apache.activemq.command.ActiveMQQueue">  
	    <constructor-arg value="pinyougou_queue_solr_delte"/>  
	</bean> 
	
	<!-- 消息监听容器 -->
	<bean  class="org.springframework.jms.listener.DefaultMessageListenerContainer">
		<property name="connectionFactory" ref="connectionFactory2"/>
		<property name="destination" 	ref="queueSolrTextDestinationdelete" />
		<property name="messageListener" ref="itemSearchDeleteMessageListener"/>
	</bean>	 
	
	 <!--这个是订阅模式  文本信息--> 
	<bean id="topicTextDestination" class="org.apache.activemq.command.ActiveMQTopic">  
	    <constructor-arg value="pinyougou_topic_pagesolr"/>  
	</bean>   
	
	<!-- 消息监听容器 -->
	<bean  class="org.springframework.jms.listener.DefaultMessageListenerContainer">
		<property name="connectionFactory" ref="connectionFactory2"/>
		<property name="destination" 	ref="topicTextDestination" />
		<property name="messageListener" ref="pageMessageListener"/>
	</bean>	 
	
	
	<!--这个是订阅模式  文本信息--> 
	<bean id="topicDeleteDestination" class="org.apache.activemq.command.ActiveMQTopic">  
	    <constructor-arg value="pinyougou_topic_pagesolr_delete"/>  
	</bean>   
	
	
	<!-- 消息监听容器 -->
	<bean  class="org.springframework.jms.listener.DefaultMessageListenerContainer">
		<property name="connectionFactory" ref="connectionFactory2"/>
		<property name="destination" 	ref="topicDeleteDestination" />
		<property name="messageListener" ref="pageDeleteMessageListener"/>
	</bean>	 
	
</beans>
```

### 11. 其他配置文件省略

------

## 运行效果图

###运营商后台

![后台登录与注册](.\截图20190805072325.png)

###进入商家后台

![添加商品基本信息](F:\svn_work\pinyougou\图片14.png)

​                                                                图6.4 新增商品基本信息

![上传商品图片信息](F:\svn_work\pinyougou\图片15.png)

​                                                                图6.5新增商品图片信息

![添加商品扩展信息](F:\svn_work\pinyougou\图片16.png)

​                                                                图6.6 新增商品扩展信息

![添加商品规格列表](F:\svn_work\pinyougou\图片17.png)

​                                                                图6.7 启用商品规格信息

![添加商品](F:\svn_work\pinyougou\图片18.png)

​                                                                图6.8 添加商品

###网站前台

![用户注册](F:\svn_work\pinyougou\图片22.png)

​                                                                图6.8 用户注册

![前台登录](F:\svn_work\pinyougou\图片20.png)

​                                                                图6.9 用户登录

![前台主页](F:\svn_work\pinyougou\图片19.png)

​                                                                图6.10 系统前台主页

![商品搜索](F:\svn_work\pinyougou\图片24.png)

​                                                                图6.11 商品搜索

![添加购物车](F:\svn_work\pinyougou\图片26.png)

​                                                                图6.12购物车



![用户下单](F:\svn_work\pinyougou\图片27.png)

​                                                                图6.13 用户下单

![添加收货地址](F:\svn_work\pinyougou\图片28.png)



​                                                                图6.13 添加收货地址