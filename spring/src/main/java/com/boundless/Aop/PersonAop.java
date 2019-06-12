package com.boundless.Aop;

import com.boundless.Bean.AppObject;
import com.boundless.Bean.AppObjectIMP;
import com.boundless.Bean.Person;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PersonAop {
    @DeclareParents(value = "com.boundless.Bean.Person",defaultImpl = AppObjectIMP.class)
    private AppObject aop;
}
