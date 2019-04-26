# Spring Resource



### 1.介绍

> Spring把其资源做了一个抽象，底层使用统一的资源访问接口来访问Spring的所有资源。也就是说，不管什么格式的文件，也不管文件在哪里，到Spring 底层，都只有一个访问接口，Resource。整个Spring的资源定义在spring-core 的包中.

![img](https://img-blog.csdn.net/20170912130741254?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZnl6THVja3kyMDE1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

#####InputStreamSource顶层接口

```Java
public interface InputStreamSource {
    // 找到并打开资源，返回InputStream从资源中读取的内容
    InputStream getInputStream() throws IOException;
}
```

##### Resource接口

```java
public interface Resource extends InputStreamSource {
    boolean exists();
    boolean isOpen();
    boolean isFile();
    boolean isReadable()
    String getFilename();
    // 返回此资源的描述，用于处理资源时的错误输出。这通常是完全限定的文件名或资源的实际URL
    String getDescription(); 
    URL getURL() 
    File getFile()
}
```

### 2.内置实现接口

| 实现类                 |              说明              |
| :--------------------- | :----------------------------: |
| ClassPathResource      |     通过类路径获取资源文件     |
| FileSystemResource     |      通过文件系统获取资源      |
| UrlResource            |      通过URL地址获取资源       |
| ServletContextResource | 获取ServletContext环境下的资源 |
| InputStreamResource    |      获取输入流封装的资源      |
| ByteArrayResource      |     获取字节数组封装的资源     |

#### 3.ResourceLoader

> 所有的   `ApplicationContext `  都实现了该  `ResourceLoader`  接口。
>
> 因此，可以使用所有应用程序上下文来获取`Resource`实例

```java
public interface ResourceLoader {
    Resource getResource(String location);
}
```

```java
Resource template = ctx.getResource("some/resource/path/myTemplate.txt");
```

