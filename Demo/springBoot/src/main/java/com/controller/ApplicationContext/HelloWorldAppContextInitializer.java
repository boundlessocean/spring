package com.controller.ApplicationContext;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.validation.constraints.Max;


@Order(Ordered.HIGHEST_PRECEDENCE)
public class HelloWorldAppContextInitializer<C extends ConfigurableApplicationContext>
        implements ApplicationContextInitializer<C> {
    @Override
    public void initialize(C applicationContext) {
        System.out.println("applicationContext ID = "+applicationContext.getId());
    }
}
