package com.boundless;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.*;
import org.springframework.core.io.support.EncodedResource;

import java.io.*;

public class Test {
    @org.junit.Test
    public void test() throws IOException {
        System.out.println("开始初始化容器");
//        ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "META-INF/spring/camel-context.xml" });
//        System.out.println("xml加载完毕");
//        Person person1 = (Person) ac.getBean("person1");
//        person1.read1();
//        person1.read2();
//        System.out.println(person1);
//        System.out.println("关闭容器");

//        ResourceLoader loader = new DefaultResourceLoader();
//        Resource resource = loader.getResource("a.txt");
//        File file = resource.getFile();
//        InputStream inputStream = resource.getInputStream();
//        byte data[]= new byte[(int)file.length()];
//        inputStream.read(data);
//        inputStream.close();
//        System.out.println(new String(data));
          xiaowang wang = (xiaowang)context.getBean("wang");
          wang.say();
    }
}
