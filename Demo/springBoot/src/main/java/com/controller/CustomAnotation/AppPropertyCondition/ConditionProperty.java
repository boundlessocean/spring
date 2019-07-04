package com.controller.CustomAnotation.AppPropertyCondition;


import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Conditional(AppCondition.class)
public @interface ConditionProperty {

    String name();
    String value();
}
