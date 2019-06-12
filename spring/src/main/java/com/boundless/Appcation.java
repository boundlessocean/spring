package com.boundless;

import com.boundless.Bean.Person;
import com.boundless.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Appcation {
    public static void main(String[] args){
        SpringApplication.run(Appcation.class,args);
    }
}
