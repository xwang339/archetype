package com.lixin.main.jwt;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-30 17:56:49
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-30     张李鑫                     1.0         1.0 Version
 */
public interface JwtService {


    boolean checkTokenNeedsRefresh(String token,Integer userId);

    void clearToken(Integer userId);


    Integer getUserId(String token);

    String refreshToken(Integer userId);

    boolean validateRefreshToken(String token,Integer userId);
}
