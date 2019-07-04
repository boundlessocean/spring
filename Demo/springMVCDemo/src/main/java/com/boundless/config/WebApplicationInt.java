package com.boundless.config;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class WebApplicationInt extends AbstractDispatcherServletInitializer {


    @Override
    protected WebApplicationContext createServletApplicationContext() {
//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        ac.register(AppConfig.class);
//        return ac;
         XmlWebApplicationContext cxt = new XmlWebApplicationContext();
         cxt.setConfigLocation("WEB-INF/DispatcherServlet-servlet.xml");
         return cxt;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
