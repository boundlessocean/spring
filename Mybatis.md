# Mybatis



### 1.介绍

> MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。



###2.Mybatis xml配置

> MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。

在不使用Spring整合的情况下，对Mybatis xml配置如下（在整合Spring后 environments 不需要配置）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <mappers>
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```



###### 2.1节点顺序

> 配置必须按照以下位置顺序进行配置，节点位置顺序不能错乱。

configuration（配置）

- properties（属性）

- settings（设置）

- typeAliases（类型别名）

- typeHandlers（类型处理器）

- objectFactory（对象工厂）

- plugins（插件）

- environments（环境配置）

  - environment（环境变量）


  - transactionManager（事务管理器）
  - dataSource（数据源）


- databaseIdProvider（数据库厂商标识）
- mappers（映射器）



######2.2 properties 

1. 代码properties文件的配置属性

```Xml
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
```

如果属性在不只一个地方进行了配置，那么 MyBatis 将按照下面的顺序来加载：

- 在 properties 元素体内指定的属性首先被读取。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件或根据 url 属性指定的路径读取属性文件，并覆盖已读取的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖已读取的同名属性。

因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的是 properties 属性中指定的属性。



2. 设置默认值

这个特性默认是关闭的。如果你想为占位符指定一个默认值， 你应该添加一个指定的属性来开启这个特性。

```xml
<properties resource="org/mybatis/example/config.properties">
  <!-- 启用默认值特性 -->
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/>
</properties>
```

```xml
<dataSource type="POOLED">
  <!-- 如果属性 'username' 没有被配置，'username' 属性的值将为 'ut_user' -->
  <property name="username" value="${username:ut_user}"/> 
</dataSource>
```





<http://www.mybatis.org/mybatis-3/zh/configuration.html>



<http://www.mybatis.org/spring/zh/using-api.html>

