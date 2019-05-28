package com.boundless.ExceptionHandle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;


@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户名和密码不匹配!")
public class UserNameNotMatchPasswordException extends RuntimeException{
//    ResponseStatusExceptionResolver
}

