# Spring Boot



### 1.配置Spring应用

```java
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
```



* 1.**@SpringBootApplication **

  ```
  @SpringBootApplication  = @Configuration +@EnableAutoConfiguration + @ComponentScan
  ```

* 2.**Application** 

  ```java
  // 通常建议将应用的main类放到其他类所在包的顶层(root package)，并将@EnableAutoConfiguration注解到你的main类上，这样就隐式地定义了一个基础的包搜索路径。
  com
   +- example
       +- myproject
           +- Application.java
           |
           +- domain
           |   +- Customer.java
           |   +- CustomerRepository.java
           |
           +- service
           |   +- CustomerService.java
           |
           +- web
               +- CustomerController.java
  ```

* 3.**@Import**

  ```Java
  // 导入配置
  @Import({ MyConfig.class, MyAnotherConfig.class })
  ```

* 4.启动 `mvn spring-boot:run`

  ```xml
  <!--继承初始父项-->
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.0.0.RELEASE</version>
  </parent>

  <dependency> 
  	<dependency> 
  		<groupId> org.springframework.boot </ groupId> 
  		<artifactId> spring-boot-starter-web </ artifactId> 
  	</ dependency> 
  </ dependencies>
  ```

* 5.运行jar`java -jar target/xxx.jar`

* 6.打包`mvn package`

  ```xml
  <build>
  	<plugins>
  		<plugin>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-maven-plugin</artifactId>
  		</plugin>
  	</plugins>
  </build>
  ```

  ​



### 2.自定义Bannar

> 通过在classpath下添加一个banner.txt或设置banner.location来指定相应的文件可以改变启动过程中打印的banner.

| 变量                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ${application.version}                                       | MANIFEST.MF中声明的应用版本号，例如Implementation-Version: 1.0会打印1.0 |
| ${application.formatted-version}                             | MANIFEST.MF中声明的被格式化后的应用版本号（被括号包裹且以v作为前缀），用于显示，例如(v1.0) |
| ${spring-boot.version}                                       | 当前Spring Boot的版本号，例如1.4.1.RELEASE                   |
| ${spring-boot.formatted-version}                             | 当前Spring Boot被格式化后的版本号（被括号包裹且以v作为前缀）, 用于显示，例如(v1.4.1.RELEASE) |
| Ansi.Name  （或AnsiColor.NAME，AnsiBackground.NAME, AnsiStyle.NAME） | NAME代表一种ANSI编码，具体详情查看[AnsiPropertySource](https://github.com/spring-projects/spring-boot/tree/v1.4.1.RELEASE/spring-boot/src/main/java/org/springframework/boot/ansi/AnsiPropertySource.java) |
| ${application.title}                                         | MANIFEST.MF中声明的应用title，例如Implementation-Title: MyApp会打印MyApp |

```java
// 关闭Bannar
SpringApplication app = new SpringApplication(Application.class);
app.setBannerMode(Banner.Mode.OFF);
app.run(args);

// 或者
new SpringApplicationBuilder()
        .sources(Parent.class)
        .child(Application.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
```



###  3.Application事件和监听器



1.**Application**事件监听

```java
public class AppListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        
    }
}
```



2.注册监听器

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new AppListener());
        app.run(args);
    }
}
```

也可以在工程中添加一个META-INF/spring.factories文件，如下：

```java
org.springframework.context.ApplicationListener=com.example.project.MyListener
```



3.监听**Application**启动完成

> 如果需要在SpringApplication启动后执行一些特殊的代码，你可以实现ApplicationRunner或CommandLineRunner接口，这两个接口工作方式相同，都只提供单一的run方法，该方法仅在SpringApplication.run(…)完成之前调用。



```java
@Component
public class AppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动完成");
    }
}
```



4.监听**Application**退出

> org.springframework.boot.ExitCodeGenerator 如果bean 在SpringApplication.exit()调用时希望返回特定的退出代码，它们可以实现该接口

```java
public class AppExit implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 140;
    }
}
```











































<https://qbgbook.gitbooks.io/spring-boot-reference-guide-zh/content/II.%20Getting%20started/10.1.1.%20Maven%20installation.html>





<https://www.breakyizhan.com/springboot/3033.html>





<https://docs.spring.io/spring-boot/docs/2.1.4.RELEASE/reference/htmlsingle/#boot-features-external-config-validation>