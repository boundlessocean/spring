##Spring



####1. Spring Ioc容器和Bean

> Ioc 控制反转

​	对象只能通过构造函数参数，工厂方法的参数或从工厂方法返回后在对象实例上设置的属性来定义它们的依赖关系,然后容器在创建bean时注入这些依赖项。此过程基本上是bean本身的逆（因此名称，控制反转），通过使用类的直接构造或诸如服务定位器模式的机制来控制其依赖关系的实例化或位置。



> Ioc容器的基础 `org.springframework.beans`包和`org.springframework.context`包

在Spring中，由Spring IoC容器管理的对象称为bean。bean是一个由Spring IoC容器实例化，组装和管理的对象。Bean及其之间的依赖关系反映在xml配置和注解中



####2. Container

> `org.springframework.context.ApplicationContext`接口代表Spring IoC容器，容器通过读取XML配置，Java注解或Java代码来实例化对象

`ApplicationContext`  是 `BeanFactory` 的子子接口。`ApplicationContext`  接口由`ClassPathXmlApplicationContext` 和 `FileSystemXmlApplicationContext`的实现。

```Java
// 1.ApplicationContext加载xml创建 ，缺点：占用系统资源  优点：响应速度快
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

// 2.BeanFactory获取对象时创建 ，缺点：响应速度慢   优点：占用内存小
BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
```



####3. Bean

######3.1 作用域 

```java
<bean id="factory" class="ServiceFactoy" scope="singleton"/> 
```

| 范围        | 范围                                               |
| ----------- | -------------------------------------------------- |
| singleton   | 单个对象实例。                                     |
| prototype   | 多个对象实例。                                     |
| request     | 将单个bean定义范围限定为单个HTTP请求的生命周期     |
| session     | 将单个bean定义范围限定为HTTP`Session`的生命周期    |
| application | 将单个bean定义范围限定为`ServletContext`的生命周期 |
| websocket   | 将单个bean定义范围限定为`WebSocket`的生命周期      |



###### 3.2  Bean实例化3种方法

```java
// 1.构造函数实例化（默认）
<bean id="exampleBean" class="examples.ExampleBean"/>

// 2.静态工厂方法实例化
<bean id="clientService" class="examples.ClientService" factory-method="createInstance"/>

// 3.非静态工厂方法实例化
<bean id="clientService" factory-bean="exampleBean" factory-method="createClientServiceInstance"/>
```



###### 3.3 **bean**后处理器

```java
// 1.注册
<bean id="beanProcessor" class="org.meify.core.MyBeanPostProcessor"/>

// 2.实现BeanPostProcessor接口
public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// 初始化之前进行增强处理
		return bean;
	}
 
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// 初始化之后进行增强处理
		return bean;
	}
}
```



######3.4 bean生命周期

> BeanFactory和ApplicationContext是Spring两种很重要的容器,前者提供了最基本的依赖注入的支持，而后者在继承前者的基础进行了功能的拓展，例如增加了事件传播，资源访问和国际化的消息访问等功能。这里主要介绍了ApplicationContext和BeanFactory两种容器的Bean的生命周期。

![aa](https://upload-images.jianshu.io/upload_images/3131012-0fdb736b21c8cc31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/744/format/webp![image-20190424182146363])



ApplicationContext容器中，Bean的生命周期流程如上图所示，流程大致如下：

1.首先容器启动后，会对scope为singleton且非懒加载的bean进行实例化，

2.按照Bean定义信息配置信息，注入所有的属性，

3.如果Bean实现了BeanNameAware接口，会回调该接口的setBeanName()方法，传入该Bean的id，此时该Bean就获得了自己在配置文件中的id，

4.如果Bean实现了BeanFactoryAware接口,会回调该接口的setBeanFactory()方法，传入该Bean的BeanFactory，这样该Bean就获得了自己所在的BeanFactory，

5.如果Bean实现了ApplicationContextAware接口,会回调该接口的setApplicationContext()方法，传入该Bean的ApplicationContext，这样该Bean就获得了自己所在的ApplicationContext，

6.如果有Bean实现了BeanPostProcessor接口，则会回调该接口的postProcessBeforeInitialzation()方法，

7.如果Bean实现了InitializingBean接口，则会回调该接口的afterPropertiesSet()方法，

8.如果Bean配置了init-method方法，则会执行init-method配置的方法，

9.如果有Bean实现了BeanPostProcessor接口，则会回调该接口的postProcessAfterInitialization()方法，

10.经过流程9之后，就可以正式使用该Bean了,对于scope为singleton的Bean,Spring的ioc容器中会缓存一份该bean的实例，而对于scope为prototype的Bean,每次被调用都会new一个新的对象，期生命周期就交给调用方管理了，不再是Spring容器进行管理了

11.容器关闭后，如果Bean实现了DisposableBean接口，则会回调该接口的destroy()方法，

12.如果Bean配置了destroy-method方法，则会执行destroy-method配置的方法，至此，整个Bean的生命周期结束

```java
<bean id="person1" destroy-method="myDestroy" init-method="myInit" class="com.test.spring.life.Person">
```



###### 3.5 DI（依赖注入）

> A中的 成员 (setter注入)、构造函数参数、工厂方法参数 依赖B。
>
> 然后容器在创建bean时注入依赖项B的过程称为依赖注入。



###### 3.5.1 基于构造函数的依赖注入

```java
// 示例1 引用注入
public class ThingOne {
    public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {// ...}
}

<beans>
    <bean id="beanTwo" class="x.y.ThingTwo"/>
    <bean id="beanThree" class="x.y.ThingThree"/>
    <bean id="beanOne" class="x.y.ThingOne">
    	// 参数注入
        <constructor-arg ref="beanTwo"/>
        <constructor-arg ref="beanThree"/>
    </bean>
</beans>

// 示例2 普通类型值注入
public class ExampleBean {
    private int years;
    private String ultimateAnswer;
    // ConstructorProperties 配合3使用
    @ConstructorProperties({"years", "ultimateAnswer"}) 
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
<bean id="exampleBean" class="examples.ExampleBean">
    // 1.构造函数参数类型匹配
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
	// 2.构造函数参数索引
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
	// 3.构造函数参数名称 需配合@ConstructorProperties({"years", "ultimateAnswer"}) 使用
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>

// 示例3 静态构造函数注入 factory-method=...
public class ExampleBean {
    public static ExampleBean createInstance (AnotherBean anotherBean,
                                              YetAnotherBean yetAnotherBean,
                                              int i) {
        ExampleBean eb = new ExampleBean (...);
        return eb;
    }
}    
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>
<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```



###### 3.5.2  基于成员Setter注入

> 与构造函数参数注入的区别：
>
> 1.setter注入可选（使用@Required强制依赖），构造函数参数注入强制依赖
>
> 2.如果第三方类没有公开任何setter方法，那么构造函数注入可能是唯一可用的DI形式
>
> 3.setter注入可以解决循环注入问题



```java
<bean id="exampleBean" class="examples.ExampleBean">
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

