package com.controller.CustomAnotation.AppCustomEnableAuto;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(HelloWorldConfiguration.class) // 不可弹性判断
@Import(HelloWorldSelector.class)//可弹性判断Hello world的实现类
public @interface EnableHelloWorld {

}
