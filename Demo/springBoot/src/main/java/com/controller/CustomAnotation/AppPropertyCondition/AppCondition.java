package com.controller.CustomAnotation.AppPropertyCondition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class AppCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(ConditionProperty.class.getName());

        return attributes.get("name").equals("name") &&
                attributes.get("value").equals("xiaoming");
    }
}
