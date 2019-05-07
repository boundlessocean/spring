package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("user")

public class user {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void txtest(){
        jdbcTemplate.update("update user set userpass = '1234567' where id = 5");
        jdbcTemplate.update("update user set userpass = '1234567' where id = 7");
        jdbcTemplate.execute("update user 'userpass' = '123456' where id = 8");
    }


    @Transactional(readOnly = false,propagation = Propagation.SUPPORTS)
    public void txtest1(){
        jdbcTemplate.update("update user set userpass = '7654321' where id = 5");
        jdbcTemplate.update("update user set userpass = '7654321' where id = 7");
        jdbcTemplate.execute("update user 'userpass' = '123456' where id = 8");
    }
}
