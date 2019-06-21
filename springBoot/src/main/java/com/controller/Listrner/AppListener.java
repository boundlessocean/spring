package com.controller.Listrner;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppListener implements ApplicationListener<ContextRefreshedEvent> ,ServletContextListener {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedEvent = "+ event.getApplicationContext().getId()+" , time = "+ event.getTimestamp());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("------- AppListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("------- AppListener contextDestroyed");
    }
}
