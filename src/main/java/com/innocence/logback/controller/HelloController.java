package com.innocence.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;
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


    /**
     * 验证MDC , 在日志文件的appender中增加 traceId=[%red(%X{mdcTraceId})]
     * 打印各个线程的 traceId
     * @return
     */
    @RequestMapping("/test")
    public String test(){
        log.info("---------------------> test ");
        for (int i = 0; i < 10; i++) {
            final int m = i;
            new Thread(
                    ()-> {
                        MDC.put("mdcTraceId", UUID.randomUUID().toString());
                        log.info("当前数值是:{}"+m);
                        try {
                            Thread.sleep((long)new Random().nextInt(100));
                            log.info(Thread.currentThread().getName() + "睡了一会儿");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info(Thread.currentThread().getName() + "运行结束");
                    }

            ).start();
        }
        return "success";
    }
}
