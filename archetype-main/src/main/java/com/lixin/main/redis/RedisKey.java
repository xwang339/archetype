package com.lixin.main.redis;

/**
 * @author zhongzb create on 2021/06/10
 */
public class RedisKey {
    private static final String BASE_KEY = "chat:";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN:USERID_%d";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN:USERID_%d";

    /**
     * 用户通知
     */
    public static final String USER_NOTIFY = "USER_NOTIFY:USERID_%d:ACK_CODE:_%s";

    public static final String GROUP_QR_CODE = "GROUP_QR_CODE:%s";

    public static final String POST_LIST="POST_LIST:POST_ID_%D";

    public static final String CREATE_QR_CODE = "CREATE_QR_CODE:USER_ID_%d";

    public static final String GROUP_MEMBER = "GROUP_MEMBER:GROUP_ID_%d";

    public static final String UNREAD_MESSAGES_ACK="UNREAD_MESSAGES_ACK:USER_ID_%d";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }

}
