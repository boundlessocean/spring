# Spring Transaction Manager

#### 1.事物介绍

>事务（Transaction），是一系列的动作，它们综合在一起才是一个完整的工作单元，这些动作必须全部完成，如果有一个失败的话，那么事务就会回滚到最开始的状态，仿佛什么都没发生过一样。 在企业级应用程序开发中，事务管理是必不可少的技术，用来确保数据的完整性和一致性。



#### 2. 事务的四个特性

1.  **原子性（Atomicity）**：事务是一个原子操作，由一系列动作组成。事务的原子性确保动作要么全部完成，要么完全不起作用。
2. **一致性（Consistency）**：一旦事务完成（不管成功还是失败），系统必须确保它所建模的业务处于一致的状态，而不会是部分完成部分失败。在现实中的数据不应该被破坏。
3. **隔离性（Isolation）**：可能有许多事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏。
4. **持久性（Durability）**：一旦事务完成，无论发生什么系统错误，它的结果都不应该受到影响，这样就能从任何系统崩溃中恢复过来。通常情况下，事务的结果被写到持久化存储器中。





#### 3. 事物管理核心接口

######  Spring提供的事物管理接口

> PlatformTransactionManager
>
> TransactionDefinition
>
> TransactionStatus



###### ![2](https://ws4.sinaimg.cn/large/006tNc79gy1g2rsfdjylej30q90bhabq.jpg)

#### 4. PlatformTransactionManager 事务管理器

> Spring事务管理器的接口是org.springframework.transaction.PlatformTransactionManager，如上图所示，Spring并不直接管理事务，通过这个接口，Spring为各个平台如JDBC、Hibernate等都提供了对应的事务管理器，也就是将事务管理的职责委托给Hibernate或者JTA等持久化机制所提供的相关平台框架的事务来实现。

![3](https://ws3.sinaimg.cn/large/006tNc79gy1g2rslhb2qaj308t027wef.jpg)

```JAVA
public interface PlatformTransactionManager {
    // 通过TransactionDefinition，获得“事务状态”，从而管理事务。
    TransactionStatus getTransaction(@Nullable TransactionDefinition var1) throws TransactionException;
    //  根据状态提交
    void commit(TransactionStatus var1) throws TransactionException;
    //  根据状态回滚!
    void rollback(TransactionStatus var1) throws TransactionException;
}
```

当我们导入` spring-jdbc`后 `PlatformTransactionManager`多了`DataSourceTransactionManager实现类`

`MyBatis-Spring` 借助了 Spring 中的 `DataSourceTransactionManager` 来实现事务管理。

![屏幕快照 2019-05-06 下午6.55.18](https://ws2.sinaimg.cn/large/006tNc79gy1g2rsrdhg1wj30l30550uj.jpg)



#### 5. TransactionStatus　事务状态

> `TransactionStatus`提供了一些方法来控制事务执行和查询事务状态

```java
public interface TransactionStatus extends SavepointManager {
    // 是否是新的事务
    boolean isNewTransaction();
    // 是否有保存点
    boolean hasSavepoint();
    // 设置回滚
    void setRollbackOnly();
    // 是否回滚
    boolean isRollbackOnly();
    // 刷新
    void flush();
    // 是否完成
    boolean isCompleted();
}
```



#### 6. TransactionDefinition　基本事务属性的定义

```java
public interface TransactionDefinition {
    // 事务传播行为
    int PROPAGATION_REQUIRED = 0;
    int PROPAGATION_SUPPORTS = 1;
    int PROPAGATION_MANDATORY = 2;
    int PROPAGATION_REQUIRES_NEW = 3;
    int PROPAGATION_NOT_SUPPORTED = 4;
    int PROPAGATION_NEVER = 5;
    int PROPAGATION_NESTED = 6;
    // 事务隔离级别
    int ISOLATION_DEFAULT = -1;
    int ISOLATION_READ_UNCOMMITTED = 1;
    int ISOLATION_READ_COMMITTED = 2;
    int ISOLATION_REPEATABLE_READ = 4;
    int ISOLATION_SERIALIZABLE = 8;
    // 事务超时时间
    int TIMEOUT_DEFAULT = -1;

	// 1.事务传播行为
    int getPropagationBehavior();
    // 2.事务隔离级别
    int getIsolationLevel();
    // 3.超时时间
    int getTimeout();
    // 4.是否只读
    boolean isReadOnly();
    // 5.事务名称
    @Nullable
    String getName();
}
```



######6.1 PropagationBehavior 传播行为

> 当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。

在 Spring 事务管理中，为我们定义了如下的传播行为：

1. **PROPAGATION_REQUIRED** ：required , 必须。默认值，A如果有事务，B将使用该事务；如果A没有事务，B将创建一个新的事务。
2. **PROPAGATION_SUPPORTS**：supports ，支持。A如果有事务，B将使用该事务；如果A没有事务，B将以非事务执行。
3. **PROPAGATION_MANDATORY**：mandatory ，强制。A如果有事务，B将使用该事务；如果A没有事务，B将抛异常。
4. **PROPAGATION_REQUIRES_NEW** ：requires_new，必须新的。如果A有事务，将A的事务挂起，B创建一个新的事务；如果A没有事务，B创建一个新的事务。
5. **PROPAGATION_NOT_SUPPORTED** ：not_supported ,不支持。如果A有事务，将A的事务挂起，B将以非事务执行；如果A没有事务，B将以非事务执行。
6. **PROPAGATION_NEVER **：never，从不。如果A有事务，B将抛异常；如果A没有事务，B将以非事务执行。
7. **PROPAGATION_NESTED** ：nested ，嵌套。A和B底层采用保存点机制，形成嵌套事务。

 

######6.2 IsolationLevel 隔离级别

> 定义了一个事务可能受其他并发事务影响的程度。
>
> **并发事务引起的问题：**
>
> 在典型的应用程序中，多个事务并发运行，经常会操作相同的数据来完成各自的任务。并发虽然是必须的，但可能会导致以下的问题。
>
> 1. **脏读**（Dirty reads）——脏读发生在一个事务读取了另一个事务改写但尚未提交的数据时。如果改写在稍后被回滚了，那么第一个事务获取的数据就是无效的。
>
> 2. **不可重复读**（Nonrepeatable read）——不可重复读发生在一个事务执行相同的查询两次或两次以上，但是每次都得到不同的数据时。这通常是因为另一个并发事务在两次查询期间进行了更新。
>
> 3. **幻读**（Phantom read）——幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务（T2）插入了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录。
>
>    **注意：不可重复读重点是修改，而幻读重点是新增或删除。**

 

在 Spring 事务管理中，为我们定义了如下的隔离级别：

1. **ISOLATION_DEFAULT**：使用后端数据库默认的隔离级别
2. **ISOLATION_READ_UNCOMMITTED**：最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读
3. **ISOLATION_READ_COMMITTED**：允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生
4. **ISOLATION_REPEATABLE_READ**：对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生
5. **ISOLATION_SERIALIZABLE**：最高的隔离级别，完全服从ACID的隔离级别，确保阻止脏读、不可重复读以及幻读，也是最慢的事务隔离级别，因为它通常是通过完全锁定事务相关的数据库表来实现的

　　上面定义的隔离级别，在 Spring 的 TransactionDefinition.class 中也分别用常量 -1,0,1,2,4,8表示。比如 ISOLATION_DEFAULT = -1;



######6.3 ReadOnly  只读

 　　这是事务的第三个特性，是否为只读事务。如果事务只对后端的数据库进行该操作，数据库可以利用事务的只读特性来进行一些特定的优化。通过将事务设置为只读，你就可以给数据库一个机会，让它应用它认为合适的优化措施。

 

######6.4 Timeout 事务超时

　　为了使应用程序很好地运行，事务不能运行太长的时间。因为事务可能涉及对后端数据库的锁定，所以长时间的事务会不必要的占用数据库资源。事务超时就是事务的一个定时器，在特定时间内事务如果没有执行完毕，那么就会自动回滚，而不是一直等待其结束。

 

###### 6.5 rollback-for \ no-rollback-for 回滚规则

　　默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚（这一行为与EJB的回滚行为是一致的） 。但是你可以声明事务在遇到特定的检查型异常时像遇到运行期异常那样回滚。同样，你还可以声明事务遇到特定的异常不回滚，即使这些异常是运行期异常。



#### 7. 使用XML配置Spring事务管理

只需要在xml中配置事务管理，对代码无侵入

```Xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx" 
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
          
<!-- 1.数据源 -->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/boundless"/>
        <property name="username" value="root"/>
        <property name="password" value="88888888"/>
    </bean> 

<!-- 2.jdbc模版 -->
<bean id="jdbcTemp" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
</bean>
                                                    
<!-- 3.事务管理 -->                                                 
<bean id="tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
  
<!-- 4.事务管理模版 --> 
<bean id="txtemp" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="tx"/>
    </bean>
                                                    
<!-- 5.事务管理通知 --> 
<tx:advice id="transactionInterceptor" transaction-manager="tx">
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
        <tx:method name="find*" read-only="true" propagation="SUPPORTS"/>
    </tx:attributes>
</tx:advice>
    
<!-- 6.事务管理观察者 --> 
<aop:config>
  <aop:advisor advice-ref="transactionInterceptor" pointcut="execution(* com.user.user.xmltx())"/>
</aop:config>
                                                                                                
</beans>
```



#### 8. 使用注解配置Spring事务管理

需要在代码中配置事务，有侵入性

1. xml配置

```Xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx" 
       xmlns:context="http://www.springframework.org/schema/context
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
          
<!-- 1.数据源 -->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/boundless"/>
        <property name="username" value="root"/>
        <property name="password" value="88888888"/>
    </bean> 

<!-- 2.jdbc模版 -->
<bean id="jdbcTemp" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
</bean>
                                                    
<!-- 3.事务管理 -->                                                 
<bean id="tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
                                                    
<!-- 4.事务管理驱动 -->  
<tx:annotation-driven transaction-manager="tx"/>

<!-- 5.扫描包 -->  
<context:component-scan base-package="com.tx"/>                                               
                                             
</beans>
```



2.注解

`Transactional`可配置在类或者方法上，配置在类上表示所以方法都接收事务控制

```java
@Component("user")
public class user {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void txtest(){
        jdbcTemplate.update("update user set userpass = '1234567' where id = 5");
        jdbcTemplate.update("update user set userpass = '1234567' where id = 7");
        jdbcTemplate.execute("update user 'userpass' = '123456' where id = 8");
    }


    @Transactional(readOnly = false,propagation = Propagation.SUPPORTS)
    public void txtest1(){
        jdbcTemplate.update("update user set userpass = '7654321' where id = 5");
        jdbcTemplate.update("update user set userpass = '7654321' where id = 7");
        jdbcTemplate.execute("update user 'userpass' = '123456' where id = 8");
    }
}

```





#### 9.事务监听器

从Spring 4.2开始，事件的监听器可以绑定到事务的一个阶段。典型示例是在事务成功完成时处理事件。这样做可以在当前事务的结果对于监听器实际上很重要时更灵活地使用事件。

您可以使用`@EventListener`注释注册常规事件侦听器。如果需要将其绑定到事务，请使用`@TransactionalEventListener`。执行此操作时，默认情况下，侦听器将绑定到事务的提交阶段。

下一个例子显示了这个概念。假设一个组件发布一个订单创建的事件，并且我们想要定义一个只应该在发布它的事务成功提交后才应该处理该事件的监听器。以下示例设置此类事件侦听器：

```java
@Component
public class MyComponent {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(CreationEvent<Order> creationEvent) {
    }
}
```

