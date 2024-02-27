package com.lixin.web.spring.DataInitialization;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-08-10 18:44:02
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-08-10     张李鑫                     1.0         1.0 Version
 */
public class Generate {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String[] FIRST_NAMES = {
            "张", "王", "李", "赵", "刘", "陈", "杨", "黄", "吴", "周", "徐", "孙", "马", "朱", "胡", "郭", "何", "高", "林", "郑"
    };

    private static final String[] LAST_NAMES = {
            "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军", "洋", "勇", "艳", "杰", "娟", "涛", "明", "超", "秀兰", "霞"
    };

    public static String generateRandomName(int length) {
        if (length <= 1) {
            throw new IllegalArgumentException("姓名长度必须大于等于2");
        }

        StringBuilder name = new StringBuilder();
        Random random = new Random();

        // 随机选择一个姓氏
        String lastName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        name.append(lastName);

        // 随机生成剩余长度的名字
        for (int i = 1; i < length; i++) {
            String firstName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            name.append(firstName);
        }

        return name.toString();
    }

    public static String generateRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0");
        }

        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        String allChars = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS;

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        return password.toString();
    }



    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomEmail() {
        SecureRandom random = new SecureRandom();

        // Generate random username
        int usernameLength = random.nextInt(10) + 5; // Random length between 5 and 14
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < usernameLength; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARS.length());
            username.append(ALLOWED_CHARS.charAt(randomIndex));
        }

        // Select a random domain
        int randomDomainIndex = random.nextInt(DOMAINS.length);
        String domain = DOMAINS[randomDomainIndex];

        return username.toString() + "@" + domain;
    }

}
