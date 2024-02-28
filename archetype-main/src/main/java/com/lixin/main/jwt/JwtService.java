package com.lixin.main.jwt;


public interface JwtService {


    boolean checkTokenNeedsRefresh(String token,Integer userId);

    void clearToken(Integer userId);


    Integer getUserId(String token);

    String refreshToken(Integer userId);

    boolean validateRefreshToken(String token,Integer userId);
}
