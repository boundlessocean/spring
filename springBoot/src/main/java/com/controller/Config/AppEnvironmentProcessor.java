package com.controller.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppEnvironmentProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String,Object> map = new HashMap<>();
        map.put("user.id","35");
        MapPropertySource mapPropertySource = new MapPropertySource("custom_Properties",map);

        MutablePropertySources sources = environment.getPropertySources();
        sources.addFirst(mapPropertySource );
    }
}
