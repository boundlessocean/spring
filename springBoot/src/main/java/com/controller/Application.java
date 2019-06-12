package com.controller;

import com.controller.Listrner.AppListener;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args){

        SpringApplication app = new SpringApplication(Application.class);
//        app.addListeners(new AppListener());
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        SpringApplication.run(Application.class,args);
    }

//    @Bean
//    public ExitCodeGenerator exitCodeGenerator(){
//        return () -> 42;
//    }
}
