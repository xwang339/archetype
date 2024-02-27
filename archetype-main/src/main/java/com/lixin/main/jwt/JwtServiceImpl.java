package com.lixin.main.jwt;

import cn.hutool.core.util.StrUtil;
import com.lixin.utils.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 18:49:31
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
@Component
public class JwtServiceImpl implements JwtService {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenStorage tokenStorage;

    @Override
    public boolean checkTokenNeedsRefresh(String token, Integer userId) {
        if (StrUtil.isEmpty(token)) {
            throw new AuthException("token is empty");
        }
        //检查用户id是否一致 用户id对应的token是否一致
        if (!token.equals(tokenStorage.getAccessToken(userId))) {
            throw new AuthException("fake token");
        }
        return !jwtUtils.verify(token);
    }

    @Override
    public void clearToken(Integer userId) {
        tokenStorage.removeTokens(userId);
    }


    @Override
    public Integer getUserId(String token) {
        return jwtUtils.getUserIdByToken(token);
    }

    /**
     * 刷新token
     *
     * @param userId
     * @return
     */
    @Override
    public String refreshToken(Integer userId) {
        String newToken = jwtUtils.createToken(userId);
        tokenStorage.updateAccessToken(userId, newToken);
        return newToken;
    }

    @Override
    public boolean validateRefreshToken(String token,Integer userId) {
        if (StrUtil.isEmpty(token)) {
            throw new AuthException("token is empty");
        }
        //检查用户id是否一致 用户id对应的token是否一致
        if (!token.equals(tokenStorage.getRefreshToken(userId))) {
            throw new AuthException("fake token");
        }
        return jwtUtils.verify(token);
    }
}
