package com.boundless.config;

import com.boundless.Bean.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AppBeanProcess implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person") || beanName.equals("animal")) {
            System.out.println("postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("person") || beanName.equals("animal")) {
            System.out.println("postProcessAfterInitialization");
        }
        return bean;
    }
}
