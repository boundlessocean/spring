package com.controller.Listrner;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

//@Controller
@RestController
public class  FristController {
    @RequestMapping("1")
    public Callable<String> home(){
        System.out.println("Thread --- "+Thread.currentThread().getName());
        return ()->{
            System.out.println("Thread --- "+Thread.currentThread().getName());
            return "hello world";
        };
    }

    @RequestMapping("2")
    public CompletionStage<String> two(){
        System.out.println("Thread --- "+Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(()->{
            System.out.println("Thread --- "+Thread.currentThread().getName());
            return "hello world";
        });
    }
}
