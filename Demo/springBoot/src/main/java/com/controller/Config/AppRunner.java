package com.controller.Config;

import com.controller.Application;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class AppRunner implements SpringApplicationRunListener {


    public AppRunner(SpringApplication application, String[] args) {

    }

    @Override
    public void starting() {
        System.out.println("---------   starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

//        Map<String,Object> map = new HashMap<>();
//        map.put("user.id","15");
//        MapPropertySource mapPropertySource = new MapPropertySource("custom_Properties",map);
//
//        MutablePropertySources sources = environment.getPropertySources();
//        sources.addFirst(mapPropertySource );
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        Map<String,Object> map = new HashMap<>();
        map.put("user.id","45");
        MapPropertySource mapPropertySource = new MapPropertySource("custom_Properties",map);

        MutablePropertySources sources = context.getEnvironment().getPropertySources();
        sources.addFirst(mapPropertySource );
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
    }
}
