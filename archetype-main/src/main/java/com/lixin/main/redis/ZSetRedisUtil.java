package com.lixin.main.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;


@Component
public class ZSetRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void zAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public long zSize(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        return size == null ? 0 : size;
    }

    public boolean isMemberExists(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value) != null;
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public void zRemove(String key, Object... values) {
        redisTemplate.opsForZSet().remove(key, values);
    }

    // 其他有序集合操作方法可以根据需要继续添加...
}