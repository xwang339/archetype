package com.lixin.main.manager.impl;

import com.lixin.main.manager.HelloManager;
import org.springframework.stereotype.Component;

@Component
public class HelloManagerImpl implements HelloManager {
    @Override
    public String hello(){
        return "hello world";
    }
}
