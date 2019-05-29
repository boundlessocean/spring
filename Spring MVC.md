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



| Bean                                  | 说明             |
| ------------------------------------- | ---------------- |
| HandlerMapping                        | 处理器映射器     |
| HandlerAdapter                        | 处理器适配器     |
| HandlerExceptionResolver              | 处理器异常解析器 |
| ViewResolver                          | 视图解析器       |
| LocaleResolver、LocaleContextResolver | 本地化解析器     |
| ThemeResolver                         | 主题解析器       |
| MultipartResolver                     | 文件解析器       |
| FlashMapManager                       | 重定向属性传递   |



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

- **BeanNameUrlHandlerMapping** ：通过Spring 配置Bean Name的方式
- **SimpleUrlHandlerMapping** ：通过Spring 配置SimpleUrlHandlerMapping的方式
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

> 需要注意：最后一步，`body.flish()`之后，**DispatcherServlet**得到的**ModelAndView**是`null`,最后一个箭头是不需要的。

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



### 8.HandlerExceptionResolver

> 上一节中**DisparcherServlet**对**ModelAndView**渲染之前会坚持是否有异常，存在异常是会使用**HandlerExceptionResolver**处理异常



**HandlerExceptionResolver**是一个接口：

```java
public interface HandlerExceptionResolver {
    @Nullable
	ModelAndView resolveException(
			HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex);
}
```



SpringMVC中提供的 **HandlerExceptionResolver**的实现类：

| HandlerExceptionResolver          | 描述                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| SimpleMappingExceptionResolver    | 将异常映射到配置好的错误页面                                 |
| DefaultHandlerExceptionResolver   | 默认的异常处理解析器（未配置异常任何异常处理时会使用它）     |
| ResponseStatusExceptionResolver   | 使用`@ResponseStatus`注释解析异常，并根据注释中的值将它们映射到HTTP状态代码。 |
| ExceptionHandlerExceptionResolver | 通过`@ExceptionHandler`解决异常的都会使用它来解析            |



**DisparcherServlet**中关于**HandlerExceptionResolver**的部分代码：

```java
public class DispatcherServlet extends FrameworkServlet {
	/** List of HandlerExceptionResolvers used by this servlet. */
	@Nullable
	private List<HandlerExceptionResolver> handlerExceptionResolvers;
    
    /**
	 * Initialize the strategy objects that this servlet uses.
	 * <p>May be overridden in subclasses in order to initialize further strategy objects.
	 */
    protected void initStrategies(ApplicationContext context) {
        ...
		initHandlerExceptionResolvers(context);
        ...
	}
    ...
}
```

我们可以看到在服务器启动时会根据**ApplicationContext(Spring配置)**初始化属性**handlerExceptionResolvers**,而`<mvc:annotation-driven>`注解默认会为我们装配以下**HandlerExceptionResolver**：

* 1.ExceptionHandlerExceptionResolver	
* 2.ResponseStatusExceptionResolver 
* 3.DefaultHandlerExceptionResolver

我们发先少了**SimpleMappingExceptionResolver**，如过要使用它需要在Spring配置文件中配置，配置之后**handlerExceptionResolvers**就会多一个**SimpleMappingExceptionResolver**，如下：

```Xml
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <prop key="java.lang.IndexOutOfBoundsException">error</prop>
        </props>
    </property>
</bean>
```

当出现**IndexOutOfBoundsException**（数组越界）异常时，并且没有其他处理器处理此异常，就回返回**error.jsp**页面



######8.1 ExceptionHandlerExceptionResolver

> 使用@ExceptionHandler注解的控制器都会被**ExceptionHandlerExceptionResolver**解析

而@ExceptionHandler注解方法的控制器，只能接受当前控制器的异常处理，所以我们要配合@ControllerAdvice注解做到全局处理异常。

```java
@ControllerAdvice
@Component
public class myExceptionHandle  {
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public String handleArg(IndexOutOfBoundsException ex){
        return "error";
    }
}
```

以上代码与Spring配置文件中配置**SimpleMappingExceptionResolver**达到同样的效果。



SpringMVC 还提供了一个异常处理抽象类**ResponseEntityExceptionHandler**，代码如下：

```java
public abstract class ResponseEntityExceptionHandler {
    @ExceptionHandler({
          HttpRequestMethodNotSupportedException.class,
          HttpMediaTypeNotSupportedException.class,
          HttpMediaTypeNotAcceptableException.class,
          MissingPathVariableException.class,
          MissingServletRequestParameterException.class,
          ServletRequestBindingException.class,
          ConversionNotSupportedException.class,
          TypeMismatchException.class,
          HttpMessageNotReadableException.class,
          HttpMessageNotWritableException.class,
          MethodArgumentNotValidException.class,
          MissingServletRequestPartException.class,
          BindException.class,
          NoHandlerFoundException.class,
          AsyncRequestTimeoutException.class
       })
    @Nullable
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {
       ...
    }
}
```

P

可以看出，这个类对特定的一些异常进行了处理，我们如果需要使用它，代码如下：

```java
@ControllerAdvice
@Component
public class subResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 如果需要重写某个异常处理,
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 对异常处理重写
        String body = "请求错误";
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
```

加上@ControllerAdvice后，就配合ResponseEntityExceptionHandler中的@ExceptionHandler全局处理了这些异常。



1. **@ExceptionHandler**注解的方法支持一下参数：

| 参数                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Exception                                                    | 用于访问引发的异常。                                         |
| HandlerMethod                                                | 用于访问引发异常的控制器方法。                               |
| WebRequest、NativeWebRequest                                 | 无需直接使用Servlet API即可访问请求参数以及请求和会话属性。  |
| javax.servlet.ServletRequest、 javax.servlet.ServletResponse | 请求或响应（`ServletRequest` 、  `HttpServletRequest`、 `MultipartRequest` 、 `MultipartHttpServletRequest`） |
| javax.servlet.http.HttpSession                               | 会话                                                         |
| HttpMethod                                                   | 请求的HTTP方法。                                             |
| java.util.Locale                                             | 当前请求区域设置，由最具体的`LocaleResolver`可用区域确定     |
| java.util.TimeZone、java.time.ZoneId                         | 与当前请求关联的时区，由`LocaleContextResolver`确定          |
| java.io.OutputStream、java.io.Writer                         | 用于访问原始响应主体，由Servlet API公开。                    |
| java.util.Map、org.springframework.ui.Model、org.springframework.ui.ModelMap | 用于访问模型                                                 |
| RedirectAttributes                                           | 指定在重定向的情况下使用的属性                               |
| @SessionAttribute                                            | 用于访问任何会话属性                                         |
| @RequestAttribute                                            | 用于访问请求属性                                             |

2. **@ExceptionHandler**方法支持以下返回值：

| 返回值                                      | 描述                                                         |
| ------------------------------------------- | ------------------------------------------------------------ |
| @ResponseBody                               | 返回值通过HttpMessageConverter实例转换并写入响应             |
| HttpEntity<B>、ResponseEntity<B>            | 返回值指定完整响应（包括HTTP标头和正文）通过`HttpMessageConverter`实例转换并写入响应 |
| String                                      | 使用`ViewResolver`实现解析的视图名称                         |
| View                                        | 返回渲染的View                                               |
| java.util.Map、org.springframework.ui.Model | 模型                                                         |
| @ModelAttribute                             | 要添加到模型的属性                                           |
| ModelAndView                                | ModelAndView                                                 |
| void                                        | 用的方法`void`返回类型被认为已经完全处理的响应               |

###### 8.2 ResponseStatusExceptionResolver  

> 使用**@ResponseStatus** 会被 **ResponseStatusExceptionResolver** 解析

```java
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户名和密码不匹配!")
public class UserNameNotMatchPasswordException extends Exception{

}
```

```java
@GetMapping("login")
public void login() throws UserNameNotMatchPasswordException {
    throw new UserNameNotMatchPasswordException();
}
```



###### 8.3 DefaultHandlerExceptionResolver

> 在未配置异常处理的情况下，默认会使用它来处理，处理的异常类与ResponseEntityExceptionHandler相同，
>
> 不同的是，DefaultHandlerExceptionResolver把body错误信息填充好，ResponseEntityExceptionHandler没有填充body错误信息。



######8.4 实现HandlerExceptionResolver处理异常

> 我们知道SpringMVC提供的这几个类都是实现了HandlerExceptionResolver接口来处理异常。
>
> 如果我们不需要SpringMVC提供的这几个类来处理可以直接实现HandlerExceptionResolver接口来处理异常。

```java
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, 
                                         Object handler, Exception ex) {
        // handle exception ...
        return new ModelAndView();
    }
}
```







###9.MultipartResolver

> 文件解析器，用于对客户端上传的文件进行解析。



1.配置了文件解析器，请求进入**DispatcherServlet**会使用文件解析器解析请求。

2.未配置文件解析器，在执行`InvokeMethod`的时候处理参数，会使用**StandardServletMultipartResolver**解析器解析文件。

![MultipartResolver](https://ws1.sinaimg.cn/large/006tNc79gy1g3b8zpajaxj30qc0ek404.jpg)





### 10.Handler

> Handler也就是我们的Controller，用于处理业务逻辑。这里我们只描述注解类型(RequestMappingHandlerAdapter)的Handler



###### 10.1 请求映射

注解：

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
    
	// 1.通过 produces（Accept）缩小匹配范围
    @GetMapping(path = "/pets/{petId}",
                produces = "application/json;charset=UTF-8")
    public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
        // ...
    }
    
    // 2.通过 consumes（Content-Type）缩小匹配范围
    @GetMapping(path = "/pets/{petId}",
                consumes = "application/json")
    public Pet findPet(@PathVariable Long ownerId, @PathVariable Long petId) {
        // ...
    }
    
    // 3.通过 参数是否等于某个值、是否包含某个参数 来匹配
    @GetMapping(path = "/pets/{petId}",
                params = "param=value",
               // params = "param",
               // params = "!param"
               ) 
	public void findPet(@PathVariable String petId) {
   		 	// ...
	}
    
    // 4.通过 header是否等于某个值 来匹配
    @GetMapping(path = "/pets", 
                headers = "header=value") 
    public void findPet(@PathVariable String petId) {
        // ...
    }
}
```



######10.2 处理器方法

1. 支持的方法参数

| 控制器方法参数                                               | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| javax.servlet.ServletRequest、 javax.servlet.ServletResponse | 选择任何特定的请求或响应类型`ServletRequest`，`HttpServletRequest`或Spring的`MultipartRequest`，`MultipartHttpServletRequest`。 |
| javax.servlet.http.HttpSession                               | HTTP会话，请注意，会话访问不是线程安全的。如果允许多个请求同时访问会话，请考虑将`RequestMappingHandlerAdapter`实例的`synchronizeOnSession`标志设置为 `true`。 |
| HttpMethod                                                   | 请求的HTTP方法。                                             |
| java.util.Locale                                             | 当前请求区域设置，由最具体确定`LocaleResolver`可用的（实际上，配置的`LocaleResolver`或`LocaleContextResolver`）。 |
| java.util.TimeZone + java.time.ZoneId                        | 与当前请求关联的时区，由a确定`LocaleContextResolver`。       |
| java.io.InputStream， java.io.Reader                         | 用于访问Servlet API公开的原始请求主体。                      |
| java.io.OutputStream， java.io.Writer                        | 用于访问Servlet API公开的原始响应主体。                      |
| @PathVariable                                                | 用于访问URI模板变量。                                        |
| @RequestParam                                                | 用于访问Servlet请求参数，包括多部分文件。参数值将转换为声明的方法参数类型。请注意，`@RequestParam`对于简单参数值，使用是可选的。 |
| @RequestHeader                                               | 用于访问请求标头。标头值将转换为声明的方法参数类型。         |
| @CookieValue                                                 | 用于访问cookie。Cookie值将转换为声明的方法参数类型。         |
| @RequestBody                                                 | 用于访问HTTP请求正文。通过使用`HttpMessageConverter`实现将正文内容转换为声明的方法参数类型。 |
| @RequestPart                                                 | 要访问`multipart/form-data`请求中的某个部分，请使用。转换部件的主体`HttpMessageConverter` |
| RedirectAttributes                                           | 指定在重定向（即，要附加到查询字符串）的情况下使用的属性，以及临时存储的flash属性，直到重定向后的请求为止。 |
| @ModelAttribute                                              | 用于访问模型中的现有属性（如果不存在则实例化），并应用数据绑定和验证。 |
| Errors，BindingResult                                        | 用于访问命令对象（即`@ModelAttribute`参数）的验证和数据绑定中的错误或来自`@RequestBody`或 `@RequestPart`参数验证的错误。您必须在经过验证的方法参数之后立即声明一个`Errors`或`BindingResult`参数。 |
| @SessionAttribute                                            | 用于访问任何会话属性                                         |
| @RequestAttribute                                            | 用于访问请求属性,多是在过滤器或拦截器创建的、预先存在的请求属性 |

2. 返回值

下表描述了支持的控制器方法返回值。

| 控制器方法返回值      | 描述                                                         |
| --------------------- | ------------------------------------------------------------ |
| @ResponseBody         | 返回值通过`HttpMessageConverter`实现转换并写入响应。         |
| HttpHeaders           | 用于返回带标题且没有正文的响应。                             |
| String                | 要使用`ViewResolver`实现解析的视图名称                       |
| View                  | ViewAndModel渲染后的视图                                     |
| @ModelAttribute       | 要添加到模型的属性                                           |
| ModelAndView          | 要使用的视图和模型属性，以及（可选）响应状态。               |
| void                  | 具有`void`返回类型（或`null`返回值）的方法被认为已完全处理响应。 |
| StreamingResponseBody | `OutputStream`异步写入响应。也支持作为一个体 `ResponseEntity` |

例子：

```java
@Controller
@RequestMapping("person")
public class personController  {

    @GetMapping("requestHeader")
    public String requestHeader(@RequestHeader(value = "Accept-Language") String language,int id){
        System.out.println(language +" id = "+id);
        return "name";
    }


    @GetMapping("cookieValue")
    public String cookieValue(@CookieValue(value = "JSESSIONID") String cookie){
        System.out.println(cookie);
        return "name";
    }


    @GetMapping("modelAttribute")
    public String modelAttribute(person p){
        System.out.println("传入用户："+p);
        return "name";
    }

    
    @ModelAttribute()
    public person testModelAttribute(){    
        person p = findPersonByID(1);
        return p;
    }

    
    @GetMapping("flashMap")
    public String flashMap(RedirectAttributes attr){
        person p = findPersonByID(1);
//        attr.addAttribute("name","xialu");         //这里传入的参数会出现在重定向后的url中，相当于get方式。
        attr.addFlashAttribute("person",p);    //这里传入的参数会用flashmap保存
        return "redirect:modelAttribute";
    }


    @GetMapping("sendRedirect")
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("success").forward(request,response);
        response.sendRedirect("success");
    }


    @GetMapping("forword")
    public String forword(){
        return "redirect:testVoid";
    }


    @PostMapping("jsonBody")
    @ResponseBody
    public person jsonBody(@RequestBody person p,
                           Integer id) throws IOException {
        person u = findPersonByID(id);
        u.setName(p.getName());
        return u;
    }
```

```java
@Controller
@SessionAttributes(value = "p")
@RequestMapping("job")
public class JobController {

    @GetMapping("applyJob")
    public String apply(@ModelAttribute("p") person p,
                        @RequestAttribute("name") String name){
        System.out.println("找工作的人 ="+"person = " +p.toString()+" name = "+name);
        return "success";
    }


    @GetMapping("userName")
    public  String  user(Model model){
        Address address = new Address();
        address.setArea("高新区");
        address.setStreet("1901号");

        person p = new person();
        p.setAge(10);
        p.setName("张三");
        p.setAddress(address);
        model.addAttribute("p",p);
        return "success";
    }
}
```













