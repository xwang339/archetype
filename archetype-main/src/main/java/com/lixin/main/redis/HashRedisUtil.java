package com.lixin.main.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class HashRedisUtil extends RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);

    }

    public void hSet(String key, String field, Object value, long timeout) {
        redisTemplate.opsForHash().put(key, field, value);
    }


    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hDelete(String key, String... fields) {
        redisTemplate.opsForHash().delete(key, (Object[]) fields);
    }

    public boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }
}
