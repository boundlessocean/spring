package com.controller.CustomAnotation.AppCustomEnableAuto;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String helloWorld(){
        return "hello world,2019";
    }
}
