package com.lixin.main.jwt;


public class JwtConstants {
    public static final String SECRET_KEY = "lixin"; // 替换为自己的密钥
    public static final long EXPIRATION_TIME = 60*24;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60*24*7;
    public static final String UID_CLAIM = "userId";
    public static final String AUTH_TOKEN_KEY = "Authorization";
    public static final String AUTH_TOKEN_PRE = "Bearer";
}
