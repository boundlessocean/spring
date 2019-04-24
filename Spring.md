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
@PostConstruct 	// 注解init
@PreDestroy		// 注解destroy
```



###### 3.5 DI（依赖注入）

> A中的 成员 (setter注入)、构造函数参数、工厂方法参数 依赖B。
>
> 然后容器在创建bean时注入依赖项B的过程称为依赖注入。

```java
// Person 类
public class Person {
    // 普通类型注入
    private String name;
    private Integer age;
    private Date  brithday;
    // 集合类型注入
    private String[] strings;
    private List<String> list;
    private Set <String> set;
    private Map<String,String> map;
    private Properties properties;
    // 构造函数
    public Person(String name, Integer age, Date brithday) {
        this.name = name;
        this.age = age;
        this.brithday = brithday;
    }
}
```



###### 3.5.1 基于构造函数的依赖注入

> <constructor-arg /> 中属性 (type、name、index)选1、(ref、value)选1

```java
<bean id="person" class="com.boundless.person.Person">
        <constructor-arg type="java.lang.String" value="zhangsan"/>
        <constructor-arg name="age" value="12"/>
        <constructor-arg index="2" ref="myDate" />
</bean>
<bean id="myDate" class="java.util.Date"/>

// 命名空间
xmlns:c="http://www.springframework.org/schema/c"
<bean id="person" class="com.boundless.person.Person"
    c:name="zhangsan"
	c:age="20"
	c:brithday-ref="myDate">
</bean>
```



###### 3.5.2  基于成员Setter注入

> 与构造函数参数注入的区别：
>
> 1.setter注入可选（使用@Required强制依赖），构造函数参数注入强制依赖
>
> 2.如果第三方类没有公开任何setter方法，那么构造函数注入可能是唯一可用的DI形式
>
> 3.setter注入可以解决循环注入问题



######1.普通类型注入

```java
<bean id="person" class="com.boundless.person.Person">
        <property name="age" value="20"/>
        <property name="brithday" ref="myDate"/>
        <property name="name" value="alice"/>
</bean>

// 命名空间
xmlns:p="http://www.springframework.org/schema/p"
<bean id="person" class="com.boundless.person.Person"
          p:name="zhangshan"
          p:age="20"
          p:brithday-ref="myDate">
</bean>
```

###### 2.集合类型注入

```java
<bean id="person" class="com.boundless.person.Person">
            <property name="strings">
                <array>
                    <value> aaa</value>
                </array>
            </property>
            <property name="list">
                <list>
                    <value> 111</value>
                </list>
            </property>
            <property name="set">
                <set>
                    <value>set1</value>
                </set>
            </property>

            <property name="map">
                <map>
                    <entry key="name" value="张三"/>
                </map>
            </property>

            <property name="properties">
                <props>
                    <prop key="xiaoli">20</prop>
                </props>
            </property>

        </bean>
</beans>
```

######  3. `depends-on` 和 `<ref>` 区别

> ref 常用的情况是这个bean作为当前bean的属性
>
> depends-on 通常在属于一种不强的依赖。比如A依赖B初始化后的某个Unit.data值,并不正真依赖B对象。

