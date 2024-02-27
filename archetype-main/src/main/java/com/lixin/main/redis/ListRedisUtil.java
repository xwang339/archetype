package com.lixin.main.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 23:59:57
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
@Component
public class ListRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void lPush(String key, Object value, long timeout) {
        redisTemplate.opsForList().leftPush(key, value);
    }


    public void lPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }


    public void rPush(String key, Object value, long timeout) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void rPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }


    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public List<Object> lRange(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void lTrim(String key,int start,int end){
        redisTemplate.opsForList().trim(key,start,end);
    }
    public long lLen(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size;
    }
    public void lDelete(String key, long count, Object value) {
        redisTemplate.opsForList().remove(key, count, value);
    }

    // 其他列表操作方法可以根据需要继续添加...
}