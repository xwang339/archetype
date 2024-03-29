package com.lixin.main.jwt;

import com.lixin.main.redis.RedisKey;
import com.lixin.main.redis.StringRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class TokenStorage {

    @Autowired
    private StringRedisUtil stringRedisUtil;

    /**
     * 内存存储，模拟存储令牌的HashMap
     */

    /**
     * 存储访问令牌和刷新令牌
     *
     * @param accessToken  访问令牌
     * @param refreshToken 刷新令牌
     */
    public void storeTokens(Integer userId, String accessToken, String refreshToken) {
        setAccessToken(userId, accessToken);
        setRefreshToken(userId, refreshToken);
    }


    private void setAccessToken(Integer userId, String accessToken) {
        stringRedisUtil.set(RedisKey.getKey(RedisKey.ACCESS_TOKEN, userId), accessToken);
    }

    private void setRefreshToken(Integer userId, String refreshToken) {
        stringRedisUtil.set(RedisKey.getKey(RedisKey.REFRESH_TOKEN, userId), refreshToken, JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME * 60);
    }

    /**
     * 根据用户id获取访问令牌
     *
     * @param userId 用户id
     * @return
     */
    public String getAccessToken(Integer userId) {
        return Optional.ofNullable(stringRedisUtil.get(RedisKey.getKey(RedisKey.ACCESS_TOKEN, userId))).map(Object::toString).orElse("");
    }

    /**
     * 根据用户id获取刷新令牌
     *
     * @param userId
     * @return
     */
    public String getRefreshToken(Integer userId) {
        return Optional.ofNullable(stringRedisUtil.get(RedisKey.getKey(RedisKey.REFRESH_TOKEN, userId))).map(Object::toString).orElse("");
    }

    public void updateAccessToken(Integer userId, String token) {
        setAccessToken(userId, token);
    }

    /**
     * 删除令牌
     *
     * @param userId 用户id
     */
    public void removeTokens(Integer userId) {
        stringRedisUtil.delete(RedisKey.getKey(RedisKey.REFRESH_TOKEN, userId));
        stringRedisUtil.delete(RedisKey.getKey(RedisKey.ACCESS_TOKEN, userId));
    }
}