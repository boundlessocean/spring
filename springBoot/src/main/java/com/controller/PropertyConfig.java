package com.controller;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyConfig extends PropertyPlaceholderConfigurer {
    @Override
    public void setLocation(Resource location) {
        ClassPathResource path = new ClassPathResource("application.properties");
        super.setLocation(path);
    }

    @Override
    public void setFileEncoding(String encoding) {
        super.setFileEncoding("UTF-8");
    }
}
