package com.boundless;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

//public class MyAppContextInit implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        XmlWebApplicationContext xac = new XmlWebApplicationContext();
//        xac.setConfigLocation("WEB-INF/DispatcherServlet-servlet.xml");
//
//        DispatcherServlet servlet = new DispatcherServlet(xac);
//        ServletRegistration.Dynamic registration = servletContext.addServlet("app",servlet);
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");
//    }
//}
