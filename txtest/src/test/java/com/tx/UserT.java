package com.tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserT {

    @Test
    public void usert(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
        user u = applicationContext.getBean("user",user.class);
        u.txtest1();
    }
}
