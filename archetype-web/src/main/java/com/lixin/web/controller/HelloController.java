package com.lixin.web.controller;


import com.lixin.main.manager.HelloManager;
import com.lixin.dal.model.Result;
import com.lixin.main.redis.RedisUtils;
import com.lixin.main.redis.StringRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloManager helloManager;

    @Autowired
    private StringRedisUtil stringRedisUtil;

    @RequestMapping("/helloUser")
    public Result<String> helloUser() {
        return Result.success(helloManager.hello());
    }


    @RequestMapping("/redisTestGet")
    public Result<String> redisTestGet() {
        return Result.success((stringRedisUtil.get("test")));
    }


    @RequestMapping("/redisTestPut")
    public Result<String> redisTestPut(String val) {
        stringRedisUtil.set("test", val);
        return Result.success();
    }
}
