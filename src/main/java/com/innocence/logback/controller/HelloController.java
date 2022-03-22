package com.innocence.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: 姚飞虎
 * @Date: 2022/3/22 2:51 下午
 * @Description:
 */
@Slf4j
@RestController
public class HelloController {


    @RequestMapping("/test")
    public String test(){
        log.info("---------------------> test ");
        return "success";
    }

    @RequestMapping("/Future")
    public String testFuture() throws ExecutionException, InterruptedException, TimeoutException {
        log.info("---------------------> testFuture ");
        FutureTask<String> futureTask = new FutureTask<>(
                ()-> {
                    int a = 3/0;
                    log.info("future -----------> done");
                    return "futureTask success";
                }
        );
        new Thread(futureTask).start();
        String s = futureTask.get();
        log.info(s);
        return "success";
    }

    @RequestMapping("/completableFutureJoin")
    public String completableFutureJoin(){
        log.info("---------------------> completableFutureJoin ");
        CompletableFuture<String> futureTask1 = CompletableFuture.supplyAsync(
                ()->{
                    int a = 3/0;
                    return "success";
                }
        );
        CompletableFuture<String> futureTask2 = CompletableFuture.supplyAsync(
                ()->{
                    String str = null;
                    System.out.println(str.equals(""));
                    return "success";
                }
        );
        CompletableFuture.allOf(futureTask1,futureTask2).join();
        log.info("main ---------------->");
        return "success";
    }
}
