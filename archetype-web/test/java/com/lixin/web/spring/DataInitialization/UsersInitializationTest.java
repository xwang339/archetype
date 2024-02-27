package com.lixin.web.spring.DataInitialization;

import com.lixin.entity.Users;
import com.lixin.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UsersInitializationTest {

    @Autowired
    private IUsersService usersService;


    /**
     * 随机插入用户
     */
    @Test
    public void addUser() {
        long l = System.currentTimeMillis();
        Random random = new Random();
        ArrayList<Users> userList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Users users = new Users();
            users.setUsername(Generate.generateRandomName(random.nextInt(2) + 2));
            users.setPassword(Generate.generateRandomPassword(12));
            users.setEmail(Generate.generateRandomEmail());
            userList.add(users);
        }
        usersService.saveBatch(userList);
        log.info("耗时:{}", System.currentTimeMillis() - l);
    }


}
