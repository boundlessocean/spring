# Spring AOP



> 面向切面编程（AOP）通过提供另一种思考程序结构的方式来补充面向对象编程（OOP）。OOP中关键单元是类，而在AOP中，主要关注点是切面。AOP关注点跨越了Class和Object。

### 1.AOP代理



> Spring AOP默认使用AOP代理的标准JDK动态代理。这使得任何接口（或接口集）都可以被代理。
>
> Spring AOP也可以使用CGLIB代理。这是代理类而不是接口所必需的。默认情况下，如果业务对象未实现接口，则使用CGLIB。

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.9</version>
</dependency>
```



### 2.AOP组成部分

#### 2.1 目标类

```java
@Component()
public class xiaowang {
    public void say(String name){
        System.out.println(name);
    }
    public void eat(){
        System.out.println("吃饭");
    }
    public void sleep(){
        System.out.println("睡觉");
    }
}
```

#### 2.2 切面

```java
@Component()
// 切面
@Aspect
public class PerosonAop {
}
```



#### 2.3 切入点

```java
// execution
@Pointcut("execution(* com.boundless.xiaowang.say(..))")
private void needdo(){};

// within 在某个包内，可省略
@Pointcut("within(com.boundless.*)")
public void eatcut(){};

// target 必须明确指定目标  args(name) name代表传入的参数
@Pointcut("target(com.boundless.xiaowang) && args(name)")
private void targets(String name){};

// args 指定类型
@Pointcut("args(java.lang.String)")
private void argscut(){};

// args(name)传入参数
@AfterReturning("targets(name)")
public void aftereat(String name){
    System.out.println("after" + name);
}
```



#### 2.4 通知

```java
// 前置通知
@Before()
// 后置通知
@AfterReturning()
// 异常通知
@AfterThrowing()
// 最终通知
@After() 
// 环绕通知
@Around()
```

```java
@Component("personaop")
// 2.2切面
@Aspect 
public class PerosonAop {
    // 2.3切入点
    @Pointcut("execution(* com.boundless.xiaowang.say(..))")
    public void needdo(){};
    
    // 2.4通知
    @Around("needdo()")
    public Object  testaround(ProceedingJoinPoint joinPoint) {
        Object value = null;
        try {
            System.out.println("前置通知");
            value = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("后置通知");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知");
        } finally {
            System.out.println("最终通知");
        }
        return value;
    }
}
```



#### 2.5 Introduction引介

> **Introduction让一个切面给目标类添加额外接口**，而且为目标类提供接口的实现

```java
// 1.接口
public interface Animal {
    public void run();
}

// 2.实现类
public class AnimalImp implements Animal {
    @Override
    public void run() {
        System.out.println("跑");
    }
}

// 3.目标类
@Component()
public class xiaowang {}

// 4.切面
@Component()
@Aspect
public class PerosonAop {
	@DeclareParents(value = "com.boundless.xiaowang",defaultImpl = AnimalImp.class)
    private Animal animal;
}

// 5.调用 xiaowang run的功能
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
Animal  animal = (Animal) context.getBean(xiaowang.class);
animal.run();
```





#### 2.6 启用AspectJ支持

```xml
// 注解
@EnableAspectJAutoProxy

// xml
<aop:aspectj-autoproxy/>
```



