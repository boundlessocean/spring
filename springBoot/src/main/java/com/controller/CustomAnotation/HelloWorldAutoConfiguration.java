package com.controller.CustomAnotation;

import com.controller.CustomAnotation.AppCustomEnableAuto.EnableHelloWorld;
import com.controller.CustomAnotation.AppPropertyCondition.ConditionProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionProperty(name = "name",value = "xiaoming")
@EnableHelloWorld
public class HelloWorldAutoConfiguration {

    @Bean
    String helloWorld(){
        return "helloWorld,大佬";
    }
}
