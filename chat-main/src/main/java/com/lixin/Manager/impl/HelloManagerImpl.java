package com.lixin.Manager.impl;

import com.lixin.Manager.HelloManager;
import org.springframework.stereotype.Component;

@Component
public class HelloManagerImpl implements HelloManager {
    @Override
    public String hello(){
        return "hello world";
    }
}
