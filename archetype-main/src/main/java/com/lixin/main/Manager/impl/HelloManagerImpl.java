package com.lixin.main.Manager.impl;

import com.lixin.main.Manager.HelloManager;
import org.springframework.stereotype.Component;

@Component
public class HelloManagerImpl implements HelloManager {
    @Override
    public String hello(){
        return "hello world";
    }
}
