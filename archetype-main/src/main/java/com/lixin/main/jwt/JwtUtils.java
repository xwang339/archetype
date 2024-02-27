package com.lixin.main.jwt;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateUnit;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 16:53:46
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
@Component
public class JwtUtils {
    /**
     * 创建token Access Token
     *
     * @param object 可以是简单的用户信息 或者用户id
     * @return
     */
    public String createToken(Object object) {
        return createToken(object, JwtConstants.EXPIRATION_TIME);
    }


    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        return this.createToken(this.getUserIdByToken(token));
    }

    /**
     * 创建token 用户创建Refresh Token
     *
     * @param object
     * @return
     */
    public String createToken(Object object, long expirationTime) {
        return JWT.create()
                .setPayload(JwtConstants.UID_CLAIM, object)
                .setExpiresAt(new Date(System.currentTimeMillis() + expirationTime * DateUnit.MINUTE.getMillis()))
                .setIssuedAt(new Date()).setSigner(JWTSignerUtil.hs256(JwtConstants.SECRET_KEY.getBytes()))
                .sign();
    }

    /**
     * 验证token
     *
     * @param token token
     * @return
     */
    public boolean verify(String token) {
        try {
            return JWT.of(token).setKey(JwtConstants.SECRET_KEY.getBytes()).validate(0);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token里的存储
     *
     * @param token
     * @return
     */
    public Integer getUserIdByToken(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        return ((NumberWithFormat) jwt.getPayload(JwtConstants.UID_CLAIM)).intValue();
    }

}
