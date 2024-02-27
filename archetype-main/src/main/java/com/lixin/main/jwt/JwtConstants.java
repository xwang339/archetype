package com.lixin.main.jwt;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 17:53:09
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
public class JwtConstants {
    public static final String SECRET_KEY = "lixin"; // 替换为自己的密钥
    public static final long EXPIRATION_TIME = 60*24;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60*24*7;
    public static final String UID_CLAIM = "userId";
    public static final String AUTH_TOKEN_KEY = "Authorization";
    public static final String AUTH_TOKEN_PRE = "Bearer";
}
