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



![1](/Users/apple/Desktop/1.png)

###### ![2](https://ws4.sinaimg.cn/large/006tNc79gy1g2rsfdjylej30q90bhabq.jpg)

#### 4. PlatformTransactionManager 事务管理器

> Spring事务管理器的接口是org.springframework.transaction.PlatformTransactionManager，如上图所示，Spring并不直接管理事务，通过这个接口，Spring为各个平台如JDBC、Hibernate等都提供了对应的事务管理器，也就是将事务管理的职责委托给Hibernate或者JTA等持久化机制所提供的相关平台框架的事务来实现。

![3](https://ws3.sinaimg.cn/large/006tNc79gy1g2rslhb2qaj308t027wef.jpg)

```java
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



<https://www.cnblogs.com/ysocean/p/7617620.html>

