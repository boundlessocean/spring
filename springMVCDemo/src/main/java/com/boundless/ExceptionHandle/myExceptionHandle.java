package com.boundless.ExceptionHandle;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;

@ControllerAdvice
public class myExceptionHandle {

    @ExceptionHandler(myException.class)
    public void handle(myException ex){
        System.out.println(ex.getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleArg(MethodArgumentNotValidException ex){
        System.out.println(ex);
    }
}
