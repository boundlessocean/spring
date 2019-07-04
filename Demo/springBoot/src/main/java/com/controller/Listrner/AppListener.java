package com.controller.Listrner;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppListener implements /**ApplicationListener<ContextRefreshedEvent> ,*/ServletContextListener,ApplicationListener<ApplicationEnvironmentPreparedEvent> {

//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        System.out.println("ContextRefreshedEvent = "+ event.getApplicationContext().getId()+" , time = "+ event.getTimestamp());
//    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("------- AppListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("------- AppListener contextDestroyed");
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

//        Map<String,Object> map = new HashMap<>();
//        map.put("user.id","25");
//        MapPropertySource mapPropertySource = new MapPropertySource("custom_Properties",map);
//        MutablePropertySources sources = event.getEnvironment().getPropertySources();
//        sources.addFirst(mapPropertySource);
    }
}
