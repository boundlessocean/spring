#Spring MVC



### 1.介绍

>Spring MVC是一个基于Servlet API构建的轻量级Web框架，通过把Model，View，Controller分离，将web层进行职责解耦，把复杂的web应用分成逻辑清晰的几部分，简化开发，减少出错，方便组内开发人员之间的配合。

![1](https://ws3.sinaimg.cn/large/006tNc79gy1g344ws4r21j30wo0gfjsy.jpg)





### 2. Web.xml 配置Servlet

> 当我们启动一个WEB项目容器时，容器包括(JBoss,Tomcat等)。首先会去读取web.xml配置文件里的配置，web.xml主要用来配置Filter、Listener、Servlet等。web.xml并不是必须的，一个web工程可以没有web.xml文件。



> Servlet是一个特殊的Java类，创建Servlet类自动继承HttpServlet。客户端通常只有GET和POST两种请求方式，Servlet为了响应这两种请求，必须重写doGet()和doPost()方法。SpringMVC 中DispatcherServlet继承至HttpServlet，来处理servlet请求。



**配置Servlet的3种方式**

###### 2.1使用 web.xml配置Servlet

```xml
<web-app>
  <servlet>
   	 <servlet-name>DispatcherServlet</servlet-name>
  	 <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <!-- springmvc配置文件位置-->
     <init-param>
          <param-name>contextConfigLocation</param-name>
         <!-- 默认查找配置文件的地址：/WEB-INF/${servletName}-servlet.xml。-->
          <param-value>WEB-INF/DispatcherServlet-servlet.xml</param-value>
     </init-param>
      <!-- servlet 加载启动-->
     <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>DispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
<web-app/>
```



###### 2.2 实现WebApplicationInitializer 配置Servlet

```java
public class MyAppContextInit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext xac = new XmlWebApplicationContext();
        xac.setConfigLocation("WEB-INF/DispatcherServlet-servlet.xml");

        DispatcherServlet servlet = new DispatcherServlet(xac);
        
        ServletRegistration.Dynamic registration = servletContext.addServlet("app",servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
```



######2.3 实现AbstractDispatcherServletInitializer 配置servlet

```java
public class WebApplicationInt extends AbstractDispatcherServletInitializer {


    @Override
    protected WebApplicationContext createServletApplicationContext() {
         XmlWebApplicationContext cxt = new XmlWebApplicationContext();
         cxt.setConfigLocation("WEB-INF/DispatcherServlet-servlet.xml");
         return cxt;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
```



###3.DispatcherServlet 

> DispatcherServlet是前端控制器设计模式的实现，提供Spring Web MVC的集中访问点，而且负责职责的分派，而且与Spring IoC容器无缝集成，从而可以获得Spring的所有好处。
>
> DispatcherServlet使用Spring的配置来找到HandlerMapping、HandlerAdapter、ViewResolver等



在 **2.2** 和 **2.3** 中我们可以看的，`DispatcherServlet`的创建依赖一个Spring配置文件，当然这个配置文件可以是一个java类，也可以是一个xml文件。

```xml
<web-app>
  <servlet>
   	 <servlet-name>DispatcherServlet</servlet-name>
  	 <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     <init-param>
         
         <!-- 
			1.spring 配置为xml
			默认查找配置文件的地址：/WEB-INF/${servletName}-servlet.xml。
		 -->
         <param-name>contextConfigLocation</param-name>
         <param-value>WEB-INF/DispatcherServlet-servlet.xml</param-value>
         <!-- 
			2.spring 配置为java类
		 -->
         <param-name>contextClass</param-name>
         <param-value>com.boundless.Config</param-value>
     </init-param>
  </servlet>
<web-app/>
```



######注册驱动

```xml
<!-- spring 配置文件，注册MVC驱动 与 @EnableWebMvc 注解等效-->
<mvc:annotation-driven/>
```

`<mvc:annotation-driven/>` 注册了许多SpringMVC的重要Bean



| Bean                                  | 说明                                                        |
| ------------------------------------- | ----------------------------------------------------------- |
| HandlerMapping                        | 将请求映射到 **处理器** 和 **拦截器列表**                   |
| HandlerAdapter                        | 处理请求获得**ModelAndView**                                |
| HandlerExceptionResolver              | 解决异常的策略                                              |
| ViewResolver                          | 将**ViewAndModel**解析为**View** 用于呈现给响应的实际视图。 |
| LocaleResolver、LocaleContextResolver | 本地化                                                      |
| ThemeResolver                         | 主题                                                        |
| MultipartResolver                     | 文件解析器                                                  |
| FlashMapManager                       | 重定向是属性传递                                            |



### 4.HandlerMapping

> 在DispatcherServlet通过Spring配置初始化时，会读取Spring配置文件来获取所有的 **处理器映射器**，从而得到后的**handlerMappings** 就存储在DispatcherServlet中

```java
public class DispatcherServlet extends FrameworkServlet {
    ... 
	@Nullable
	private List<HandlerMapping> handlerMappings;

	/** List of HandlerAdapters used by this servlet. */
	@Nullable
	private List<HandlerAdapter> handlerAdapters;

	/** List of ViewResolvers used by this servlet. */
	@Nullable
	private List<ViewResolver> viewResolvers;
    
    ...
}
```



**HandlerMapping** 接口有以下几种实现类型

- **BeanNameUrlHandlerMapping** ：通过实现Controller的映射类型
- **SimpleUrlHandlerMapping** ：通过实现HttpRequestHandler 的映射类型
- **RequestMappingHandlerMapping** ：注解配置@RequestMapping 的映射类型
- **~~DefaultAnnotationHandlerMapping~~** : 已废弃 

```java
public interface HandlerMapping {
@Nullable
   HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
```

以上前3个类型是实现Controller的3中方法，分别对应以上的3种映射类型

```java

// 1. 实现Controller 
public class myControllerEmp implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("message", "HelloWorld");
        return modelAndView;
    }
}

// 2. 实现 HttpRequestHandler
public class HttpRequestHandlerImp implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", "张三");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

// 3. @RequestMapping 注解
@Controller
public class personController {
    @RequestMapping("hello")
    public String getPersonName(){
        return "name";
    }
}
```

```xml
<!-- 实现Controller 的 Spring 配置 -->
<bean name="/name" class="com.boundless.myControllerEmp"/>

<!-- 实现 HttpRequestHandler Spring 配置 -->
<bean id="httpRequestHandlerImp" class="com.boundless.HttpRequestHandlerImp"/>
<bean id="simpleUrlHandlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
        <props>
        <prop key="index">httpRequestHandlerImp</prop>
        </props>
    </property>
</bean>
```



执行步骤：

1. **DispatcherServlet**收到请求调用自己的`getHandler()`
2. `getHandler()`遍历**handlerMappings** 得到 **HandlerMapping**对象
3. **HandlerMapping** 调用 `getHandler(request)`方法, 此方法由**AbstractHandlerMapping**实现。
4. **AbstractHandlerMapping** 的`getHandler`中调用`getHandlerInternal` 获取`handle`处理器
5. `getHandlerInternal`是一个抽象方法(由**AbstractHandlerMethodMapping**和**AbstractUrlHandlerMapping**实现),最后获取到**HandlerMethod**或者**controller**对象



![handleMapping](https://ws3.sinaimg.cn/large/006tNc79gy1g38rqy2o7bj30py0jx76p.jpg)

```java
@Nullable
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    if (this.handlerMappings != null) {
        for (HandlerMapping mapping : this.handlerMappings) {
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
    }
    return null;
}
```



```java
public class HandlerExecutionChain {
    // 关键信息
	private final Object handler;
    @Nullable
	private HandlerInterceptor[] interceptors;
}
```





### 5.HandlerAdapter

> **HandlerAdapter** 的主要作用是调用`handle`方法执行**Controller**中的业务代码获取 **ModelAndView** 返回给**DispatcherServlet**

```java
public interface HandlerAdapter {

   boolean supports(Object handler);

   @Nullable
   ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
    
   long getLastModified(HttpServletRequest request, Object handler);
}
```

**HandlerAdapter** 的几个实现类：

* **HttpRequestHandlerAdapter**  实现**HttpRequestHandler**处理请求
* **SimpleControllerHandlerAdapter**  实现**Controller**处理请求
* **SimpleServletHandlerAdapter**  继承至**HttpServlet** 处理请求
* **RequestMappingHandlerAdapter**



几种类处理请求的代码

```java
// 1. HttpRequestHandlerAdapter 
public class HttpRequestHandlerImp implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", "张三");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

// 2. SimpleControllerHandlerAdapter
public class myControllerEmp implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("message", "HelloWorld");
        return modelAndView;
    }
}

// 3. SimpleServletHandlerAdapter
public class DemoServlet extends HttpServlet{   
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {   
        response.setContentType(CONTENT_TYPE);  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        PrintWriter out = response.getWriter();  
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");    
        out.flush();  
        out.close();  
    }  
```



接着**HandlerMapping** 的执行步骤：

1. 得到 **HandlerExecutionChain**
2. 遍历 **DispatcherServlet** 的 **handlerAdapters**
3. 调用`supports(handle)`获取到 **handlerAdapter**
4. 调用`handle`获取**ModelAndView**

![handleAdapter](https://ws1.sinaimg.cn/large/006tNc79gy1g38tgyo24yj30th0hztbc.jpg)



### 6.HttpMessageConverter

> 我们知道，使用@RequestBody、@ResponseBody注解，可以直接将输入解析成Json、将输出解析成Json，HttpMessageConverter发挥着作用。



######6.1 @ResponseBody注解  处理输出报文

在**HandlerMapping**中**RequestMappingHandlerAdapter**执行`invoke(method)`的时候会对**Controller**中的返回值进行处理，处理的流程如下图：

![ResponseBody](https://ws4.sinaimg.cn/large/006tNc79gy1g3a5qpn6hqj30pk0iq76b.jpg)

######6.2 @requestBody注解  处理请求报文

![requestBody](https://ws4.sinaimg.cn/large/006tNc79gy1g3a8qkran4j30ov0ec75i.jpg)



###7.ViewResolver

> 把一个逻辑上的视图名称解析为一个真正的视图



**DisparcherServlet**收到**ModelAndView**后解析逻辑如下：

![viewResolver](https://ws3.sinaimg.cn/large/006tNc79gy1g3aaofqsd7j30j90ge3zi.jpg)



Spring配置文件中必须注册视图解析器

```xml
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/page/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```



###8.MultipartResolver

> 文件解析器，用于对客户端上传的文件进行解析。



1.配置了文件解析器，请求进入**DispatcherServlet**会使用文件解析器解析请求。

2.未配置文件解析器，在执行`InvokeMethod`的时候处理参数，会使用**StandardServletMultipartResolver**解析器解析文件。

![MultipartResolver](https://ws1.sinaimg.cn/large/006tNc79gy1g3b8zpajaxj30qc0ek404.jpg)





### 9.Handler

> Handler也就是我们的Controller，用于处理业务逻辑。这里我们只描述注解类型(RequestMappingHandlerAdapter)的Handler



###### 9.1 请求映射



* @RequestMapping(value = "path",method = RequestMethod.POST)
* @GetMapping
* @PostMapping
* @PutMapping
* @DeleteMapping
* @PatchMapping



URI：

```java
@GetMapping("/owners/{ownerId}/pets/{petId}")
public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
    // ...
}

@Controller
@RequestMapping("/owners/{ownerId}")
public class OwnerController {

    @GetMapping("/pets/{petId}")
    public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
        // ...
    }
}
```





















<https://blog.csdn.net/a745233700/article/details/80963758>



<https://docs.spring.io/spring/docs/5.2.0.M1/spring-framework-reference/web.html#mvc-config>



<https://segmentfault.com/q/1010000005139036>



异常处理











