package com.boundless.ExceptionHandle;

import com.boundless.controller.person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Component
public class myExceptionHandle  {

//    @ExceptionHandler(myException.class)
////    public void handle(myException ex){
////        System.out.println(ex.getErrorMsg());
////    }

    @ResponseBody
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public person handleArg(IndexOutOfBoundsException ex){
        person p = new person();
        p.setAge(20+10);
        p.setName("张三");
        return p;
    }


}
