### Spring

##### 1. Spring Ioc容器和Bean

> Ioc 控制反转，也称为依赖注入（DI）。

​	对象只能通过构造函数参数，工厂方法的参数或从工厂方法返回后在对象实例上设置的属性来定义它们的依赖关系,然后容器在创建bean时注入这些依赖项。此过程基本上是bean本身的逆（因此名称，控制反转），通过使用类的直接构造或诸如服务定位器模式的机制来控制其依赖关系的实例化或位置。



> Ioc容器的基础 `org.springframework.beans`包和`org.springframework.context`包

在Spring中，由Spring IoC容器管理的对象称为bean。bean是一个由Spring IoC容器实例化，组装和管理的对象。Bean及其之间的依赖关系反映在xml配置和注解中

##### 2. Container

> `org.springframework.context.ApplicationContext`接口代表Spring IoC容器，容器通过读取XML配置，Java注解或Java代码

`ApplicationContext`提供了几种接口实现。在应用程序中，通常会创建一个[`ClassPathXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.2.0.M1/javadoc-api/org/springframework/context/support/ClassPathXmlApplicationContext.html) 或 [`FileSystemXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.2.0.M1/javadoc-api/org/springframework/context/support/FileSystemXmlApplicationContext.html)的实例。



