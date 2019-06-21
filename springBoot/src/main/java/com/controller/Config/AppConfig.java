package com.controller.Config;

import com.controller.CustomAnotation.AppPropertyCondition.ConditionProperty;
import com.controller.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
@EnableAutoConfiguration


@EnableConfigurationProperties(User.class)
//@ConditionalOnProperty(name = "user.city.code",matchIfMissing = false,havingValue = "200")

@ComponentScan(basePackages = "com.controller")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

//    @Bean
//    public User user(@Value("${user.id}") String id,@Value("${user.name}") String name){
//        return new User(id,name);
//    }


//    @Bean
//    public User user(){
//        return new User();
//    }
}
