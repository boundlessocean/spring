##Spring Ioc和DI



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



###### 4.惰性加载Bean

```java
<bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>

@Lazy // 注解
```

###### 5.自动装配

| 模式          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| `no`          | （默认）无自动装配。Bean引用必须由`ref`元素定义。不建议对较大的部署更改默认设置，因为明确指定协作者可以提供更好的控制和清晰度。在某种程度上，它记录了系统的结构。 |
| `byName`      | 按属性名称自动装配。Spring查找与需要自动装配的属性同名的bean。例如，如果bean定义按名称设置为autowire并且它包含一个`master`属性（即，它有一个`setMaster(..)`方法），则Spring会查找名为bean的定义`master`并使用它来设置属性。 |
| `byType`      | 如果容器中只存在一个属性类型的bean，则允许属性自动装配。如果存在多个，则抛出致命异常，这表示您可能不会`byType`对该bean 使用自动装配。如果没有匹配的bean，则不会发生任何事情（该属性未设置）。 |
| `constructor` | 类似`byType`但适用于构造函数参数。如果容器中没有构造函数参数类型的一个bean，则会引发致命错误。 |

###### 6.方法注入

> spring提供两种机制去注入方法:
>
> 1.Lookup method inject只提供返回值注入
>
> 2.Arbitrary method replacement可以替换任意方法来达到注入

1. lookup

```java
public abstract class CommandManager{
    public Object process(Map commandState) {
        // 每次使用都调用createCommand去获取一个新实例
        Command command = createCommand();
        command.setState(commandState);
        return command.execute();
    }
    /**
     * Loopup的注释中的写明了需要返回的bean名字，如果没有写bean name，那么会根据createCommand的函数返回值类型去查找对应的bean
     * @return
     */
    @Lookup("command")
    protected abstract Command createCommand();
}
```

2.method replace

```java
public class ReplacementComputeValue implements MethodReplacer {
    /**
     * 当我们替换的方法被调用时，容器就会代理到这里，在这里执行我们要替换的执行逻辑
     * @param o   替换方法执行时对应的实例
     * @param m   替换方法
     * @param args 替换方法执行时传入的参数
     * @return
     * @throws Throwable
     */
    public Object reimplement(Object o, Method m, Object[] args) throws Throwable {
        String input = (String) args[0];
        ...
        return ...;
    }
}

<bean id="myValueCalculator" class="x.y.z.MyValueCalculator">
    <!-- 需要替换的方法 -->
    <replaced-method name="computeValue" replacer="replacementComputeValue">
        <arg-type>String</arg-type>
    </replaced-method>
</bean>

<bean id="replacementComputeValue" class="a.b.c.ReplacementComputeValue"/>
```



##### 3.6 bean继承

```java
// 如果 inheritedTestBean 没有class 则不能被实例化，只作为抽象父类Bean使用
<bean id="inheritedTestBean" abstract="true"
        class="org.springframework.beans.TestBean">
    <property name="name" value="parent"/>
    <property name="age" value="1"/>
</bean>

<bean id="inheritsWithDifferentClass"
        class="org.springframework.beans.DerivedTestBean"
        parent="inheritedTestBean" init-method="initialize">  
    <property name="name" value="override"/>
</bean>

```



#### 4.基于注解的容器配置

* 1. `@Autowired` = `@Inject`（jsr330规范） 默认按Type装配，可以装配成员，方法参数，set方法。如果我们想使用按名称装配，可以结合
* 2. `@Required` 强制注入对应`@Autowired(required=false) `和阐述中的`Optional`、`@Nullable`

```java
public class SimpleMovieLister {

    @Autowired
    public void setMovieFinder1(Optional<MovieFinder> movieFinder) {
        ...
    }
    
    @Autowired
    public void setMovieFinder2(@Nullable MovieFinder movieFinder) {
        ...
    }
}
```

* 3. `@Primary`：自动装配时当出现多个Bean候选者时，被注解为`@Primary`的`Bean`将作为首选者，否则将抛出异常

```java
@Configuration
public class MovieConfiguration {
    @Bean
    @Primary
    public MovieCatalog firstMovieCatalog() { ... }

    @Bean
    public MovieCatalog secondMovieCatalog() { ... }
}

public class MovieRecommender {
    @Autowired
    private MovieCatalog movieCatalog;
}
```

* 4. `@Qualifier` 、`@Genre`配合`@Autowired` 存在多个Bean使用

```java
@Configuration
public class MovieConfiguration {
    @Bean
    @Qualifier("firstMovieCatalog")
    public MovieCatalog firstMovieCatalog() { ... }

    @Bean
    @Genre("secondMovieCatalog")
    public MovieCatalog secondMovieCatalog() { ... }
}

public class MovieRecommender {
    @Autowired
    public void prepare(@Qualifier("firstMovieCatalog")MovieCatalog movieCatalog,
            CustomerPreferenceDao customerPreferenceDao) {
        this.movieCatalog = movieCatalog;
        this.customerPreferenceDao = customerPreferenceDao;
    }
    
    @Autowired
    public void dosomething(@Genre("secondMovieCatalog")MovieCatalog movieCatalog,
            CustomerPreferenceDao customerPreferenceDao) {
        this.movieCatalog = movieCatalog;
        this.customerPreferenceDao = customerPreferenceDao;
    }
}
```

* 5. `@Resource`的作用相当于`@Autowired`，只不过`@Autowired`按byType自动注入，而`@Resource`默认按 byName自动注入

```java
@Configuration
public class MovieConfiguration {
    @Bean
    public MovieCatalog firstMovieCatalog() { ... }

    @Bean
    public MovieCatalog secondMovieCatalog() { ... }
}

public class MovieRecommender {
    @Resource("secondMovieCatalog")
    // @Resource(name="nameA") @Resource(type="A.Class")
    private MovieCatalog movieCatalog;
}
```

* 6. `@Configuration` +配合工厂方法头上`@Bean` = xml 配置的Bean

```java
@Configuration
public class MainConfig {
	public String appid;
    @Bean
    public void service() {...}
}
```

* 7. `@PostConstruct`、`@Bean(initMethod = "init")`  =（init-method）

     与`@PreDestroy`  、`@Bean(destroyMethod = "cleanup")` =（destroy-method）

```java
public class CachingMovieLister {
    @PostConstruct // <bean id="..." class="..." init-method="...">
    public void populateMovieCache() {...}
    @PreDestroy // <bean id="..." class="..." destroy-method="...">
    public void clearMovieCache() {...}
}

@Configuration
public class AppConfig {
    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }

    @Bean(destroyMethod = "cleanup")
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```



* 8. `@PropertySource`、`@Value`

```java
// 1.普通类型注入
@Component
public class Person{
    @Value("normal")
    private String normal; // 注入普通字符串

    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName; // 注入操作系统属性

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //注入表达式结果

    @Value("#{beanInject.another}")
    private String fromAnotherBean; // 注入其他Bean属性：注入beanInject对象的属性another

    @Value("classpath:com/hry/spring/configinject/config.txt")
    private Resource resourceFile; // 注入文件资源

    @Value("http://www.baidu.com")
    private Resource testUrl; // 注入URL资源
}

// 2. application.propertie文件配置注入
@PropertySource("classpath:mail.properties")
// 多个配置时使用
@PropertySource({"classpath:config/my.properties","classpath:config/config.properties"})
public class TaskController {
    @Value("${mail.smtp.auth}")
    private String userName;
    
    @Value("${mail.from}")
    private String password;
}

```



* 9. `@EnableAsync `、` @Async `

```java
@Configuration
@EnableAsync 
public class MainConfig {
    @Bean
    public void service() {...}
    @Async
    public void do(){...}
    
    @Async
    public String do(){
       return new AsyncResult<String>("hello world !!!!");
    }
}
```

* 10. `@Scope`注解 参考Bean作用域
* 11. `@Lazy(true)` 表示延迟初始化
* 12. `@DependsOn`：定义Bean初始化及销毁时的顺序
* 13. `@Service`业务层组件、 `@Controller`控制层组件、`@Repository` DAO组件、`@Component`泛指组件、`@Named` JSR-330
* 14. `@EnableLoadTimeWeaving` 参考[Load-time Weaving with AspectJ in the Spring Framework](https://docs.spring.io/spring/docs/5.2.0.M1/spring-framework-reference/core.html#aop-aj-ltw)


#### 5.组件路径扫描

```java
// 1.org.example包及子包
<context:component-scan base-package="org.example"/>  // xml配置, 
@ComponentScan(basePackages = "org.example")  // 注解 
    
// 2.org.example.* 子包
@ComponentScan(basePackages = "org.example.*")
    
// 3.@Filter定义
@Configuration
@ComponentScan(basePackages = "org.example",
        includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
        excludeFilters = @Filter(Repository.class))
public class AppConfig {}

<beans>
    <context:component-scan base-package="org.example">
        <context:include-filter type="regex"
                expression=".*Stub.*Repository"/>
        <context:exclude-filter type="annotation"
                expression="org.springframework.stereotype.Repository"/>
    </context:component-scan>
</beans>
```



#### 6.本地化

###### 6.1本地化工具

java.util包中提供了几个支持本地化的格式化操作工具类：`NumberFormat`、`DateFormat`、`MessageFormat`

```java
// 1.NumberFormat
NumberFormat currFmt = NumberFormat.getCurrencyInstance(Locale.FRANCE);
System.out.println(currFmt.format(123456.78));
// 输出
123 456,78 €
```

```java
// 2.DateFormat
DateFormat date1 = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.GERMAN);
System.out.println(date1.format(new Date()));
```

```java
// 3.MessageFormat
//①信息格式化串
String pattern1 = "{0}，你好！你于{1}在工商银行存入{2} 元。";
String pattern2 = "At {1,time,short} On{1,date,long}，{0} paid {2,number, currency}.";

//②用于动态替换占位符的参数
Object[] params = {"John", new GregorianCalendar().getTime(),1.0E3};
//③使用默认本地化对象格式化信息
String msg1 = MessageFormat.format(pattern1,params);

//④使用指定的本地化对象格式化信息
MessageFormat mf = new MessageFormat(pattern2,Locale.US);
String msg2 = mf.format(params);
```



###### 6.2 ResourceBoundle

> ResourceBoundle为加载及访问资源文件提供便捷的操作

```java
ResourceBundle rb = ResourceBundle.getBundle("com/pageage/...",  Locale.US)  
rb.getString("greeting.common") 
```

`ResourceBundle`   配合  `MessageFormat`

```java
// .properties 资源文件
greeting.common=How are you!{0},today is {1}

Object[] params = {"John", new GregorianCalendar().getTime()};
String str1 = new MessageFormat(rb.getString("greeting.common"),Locale. US).format(params); 
```



###### 6.3 MessageSource

> spring中定义了一个MessageSource接口，以用于支持信息的国际化和包含参数的信息的替换

```java
 public interface MessageSource {
    String getMessage(String code, Object[] args, String defaultMessage, Locale locale);
    String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;
    String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;
    }
```

```xml
<!--①注册资源Bean,其Bean名称只能为messageSource ，根据反射机制从BeanDefinitionRegistry中找出名称为“messageSource”-->  
<bean id="messageSource"   
      class="org.springframework.context.support.ResourceBundleMessageSource">  
  <property name="basenames">  
     <list>  
       <value>com/baobaotao/i18n/fmt_resource</value>  
     </list>  
  </property>  
</bean>  
```

```java
/
ApplicationContext ctx = new ClassPathXmlApplicationContext("com...");  
Object[] params = {"John", new GregorianCalendar().getTime()};  
String str1 = ctx.getMessage("greeting.common",params,Locale.US);  
String str2 = ctx.getMessage("greeting.morning",params,Locale.CHINA);     
System.out.println(str1);  
System.out.println(str2);  
```

 注入MessageSource

> 1.直接注入

```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	<property name="basename" value="message"/> 
</bean> 
<bean id="hello" class="com.app.Hello">
	<property name="messageSource" ref="messageSource"/>
</bean>
```

```java
public class Hello { 

	private MessageSource messageSource; 
	
	public void doSomething() { 
		String appName = this.messageSource.getMessage("appName", null, null); 
		System.out.println(appName); 
	} 
	
	public void setMessageSource(MessageSource messageSource) { 
		this.messageSource = messageSource; 
	} 
}
```

> 2.实现MessageSourceAware接口

```java
public class Hello implements MessageSourceAware {

	private MessageSource messageSource;
	
	public void doSomething() {
		String appName = this.messageSource.getMessage("appName", null, null);
		System.out.println(appName);
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
```

