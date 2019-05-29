package com.boundless.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.MultipartConfig;

public class MyAppContextInit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext xac = new XmlWebApplicationContext();
        xac.setConfigLocation("WEB-INF/DispatcherServlet-servlet.xml");

        DispatcherServlet servlet = new DispatcherServlet(xac);
        servlet.setThrowExceptionIfNoHandlerFound(false);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app",servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        MultipartConfigElement multipartConfig = new MultipartConfigElement("",200000,400000,30000);
        registration.setMultipartConfig(multipartConfig);
    }
}
