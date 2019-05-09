package com.boundless;

import com.boundless.user.user;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import com.boundless.userMapper.userMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class usertest {

    @Test
    public void finduserbyudT() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("META-INF/spring/camel-context.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sessionFactory.openSession();
//        user user =  session.selectOne("user.findUserById",1);

//        List<user> list =  session.selectList("user.findUserByName","a");
//        for (user u : list){
//            System.out.println(u);
//        }

//    session.delete("user.deleteUser",5);

//        user u = new user();
//        u.setId(6);
//        u.setUsername("张三");
//        u.setUserpass("123456");
//        u.setNickname("333333");
////        int i = session.insert("user.insertUser",u);
//        session.update("user.updateUser",u);
        userMapper userMapper = session.getMapper(userMapper.class);
        user u = userMapper.findUserById(2);
        System.out.println(u);
//        List<user> list = userMapper.findUserByName("a");
//        for(user u: list){
//            System.out.println(u);
//        }

//        user u = new user();
//        u.setId(11);
//        u.setUsername("王五");
//        u.setUserpass("123456");
//        u.setNickname("333333");
//        userMapper.insertUser(u);
        session.commit();
        session.close();
    }


    @Test
    public void xmlTest(){
        ApplicationContext context = new  ClassPathXmlApplicationContext("META-INF/spring/app.xml");
        userMapper mapper = context.getBean("userMapper",userMapper.class);
        user u = mapper.findUserById(4);
        System.out.println(u);

    }
}
