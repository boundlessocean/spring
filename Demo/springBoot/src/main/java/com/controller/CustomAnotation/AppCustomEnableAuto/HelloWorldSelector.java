package com.controller.CustomAnotation.AppCustomEnableAuto;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class HelloWorldSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {HelloWorldConfiguration.class.getName()};
    }
}
