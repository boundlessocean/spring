package com.boundless;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Person implements BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean, ResourceLoaderAware {

    private ApplicationContext applicationContext;
    private ResourceLoader resourceLoader;

    private String name;

    public Person() {
        System.out.println("PersonService类构造方法");
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        System.out.println("set方法被调用");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext");
        this.applicationContext = applicationContext;
    }

    //自定义的初始化函数
    public void myInit() {
        System.out.println("myInit被调用");
    }

    //自定义的销毁方法
    public void myDestroy() {
        System.out.println("myDestroy被调用");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public void read1() throws IOException {
        Resource resource = applicationContext.getResource("a.txt");
        File file = resource.getFile();
        InputStream inputStream = resource.getInputStream();
        byte data[] = new byte[(int) file.length()];
        inputStream.read(data);
        inputStream.close();
        System.out.println(new String(data));
    }

    public void read2() throws IOException {
        Resource resource = this.resourceLoader.getResource("a.txt");
        File file = resource.getFile();
        InputStream inputStream = resource.getInputStream();
        byte data[] = new byte[(int) file.length()];
        inputStream.read(data);
        inputStream.close();
        System.out.println(new String(data));
    }
}
