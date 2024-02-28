package com.lixin.main.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;


@Component
public class SetRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public boolean sIsMember(String key, Object value) {
        Boolean member = redisTemplate.opsForSet().isMember(key, value);
        return member != null && member;
    }
    public long sSize(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }

    public void sRemove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    // 其他集合操作方法可以根据需要继续添加...
}