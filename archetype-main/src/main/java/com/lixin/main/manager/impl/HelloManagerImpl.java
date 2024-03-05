package com.lixin.main.manager.impl;

import com.lixin.main.manager.HelloManager;
import org.mybatis.generator.dao.UsersMapper;
import org.mybatis.generator.domain.Users;
import org.mybatis.generator.domain.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloManagerImpl implements HelloManager {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public String hello() {
        return "hello world";
    }

}
