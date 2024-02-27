package com.lixin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.lixin"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ChatWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatWebApplication.class, args);
    }
}
