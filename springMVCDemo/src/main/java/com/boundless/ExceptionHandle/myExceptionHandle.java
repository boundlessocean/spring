package com.boundless.ExceptionHandle;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class myExceptionHandle  {

    @ExceptionHandler(myException.class)
    public void handle(myException ex){
        System.out.println(ex.getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleArg(MethodArgumentNotValidException ex){
        System.out.println(ex);
    }


}
