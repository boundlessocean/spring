package com.boundless.Bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Animal implements  InitializingBean,BeanNameAware,BeanFactoryAware {

    @Value("Lucy")
    private String name;


    @PostConstruct
    public void init(){
        System.out.println("animal 初始化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet - animal");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory --- "+beanFactory);
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName --- "+s);
    }
}
