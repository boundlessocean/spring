package com.boundless.Bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component()
public class Person implements  InitializingBean ,BeanFactoryAware,BeanNameAware {

    @Value("Lucy")
    private String name;
    @Value("20")
    private int age;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init(){
        System.out.println("person - 初始化");
        System.out.println("context - "+context);
    }


    @PreDestroy
    public void destroy(){
        System.out.println("销毁");
    }


//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("setApplicationContext");
//        context = applicationContext;
//    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet -- person");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory---"+beanFactory);
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName----"+s);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
