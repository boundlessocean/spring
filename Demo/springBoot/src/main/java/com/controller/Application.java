package com.controller;

import com.controller.Config.AppConfig;
import com.controller.Entity.User;
import com.controller.Listrner.AppListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;

import javax.annotation.security.RunAs;

@SpringBootApplication

public class Application {
//    public static void main(String[] args){
//
//        SpringApplication app = new SpringApplication(Application.class);
//        app.addListeners(event -> {
//            System.out.println("event - - " + event.toString());
//        });
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
//    }

//    @Bean
//    public ExitCodeGenerator exitCodeGenerator(){
//        return () -> 42;
//    }




    public static void main(String[] args){
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AppConfig.class)
                .web(WebApplicationType.NONE)
                .properties("user.id=99")
                .run(args);


        System.out.println(context.getEnvironment().getProperty("user.id"));

        for (PropertySource source : context.getEnvironment().getPropertySources()){

            System.out.println(source);
        }


        try {
            User u = context.getBean(User.class);
            System.out.println(u);
        }catch (Exception e){
            System.out.println(e);
        }
    };
}
