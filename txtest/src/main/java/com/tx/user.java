package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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


    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void txtest1(){


        jdbcTemplate.update("update user set userpass = '765432eqeq221' where id = 5");
        jdbcTemplate.update("update user set userpass = '765432eqe2221' where id = 7");


//        jdbcTemplate.execute("update user 'userpass' = '123456' where id = 8");
    }
}

@Component
class MyTransactionListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void hanldeOrderCreatedEvent(Object o) {
        System.out.println("transactionEventListener finish");
    }
}

