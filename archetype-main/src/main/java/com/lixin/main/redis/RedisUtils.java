package com.lixin.main.redis;

import com.lixin.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 23:35:24
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
public abstract class RedisUtils {

    @Value("${spring.redis.default.timeout}")
    public long DEFAULT_TIMEOUT;

    public static <T> T toBeanOrNull(Object json, Class<T> tClass) {
        return json == null ? null : JsonUtils.toObject(json.toString(), tClass);
    }
}
