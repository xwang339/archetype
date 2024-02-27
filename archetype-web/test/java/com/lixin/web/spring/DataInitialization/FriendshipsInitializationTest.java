package com.lixin.web.spring.DataInitialization;

import com.lixin.entity.Friendships;
import com.lixin.service.IFriendshipsService;
import com.lixin.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-08-11 01:40:25
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-08-11     张李鑫                     1.0         1.0 Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FriendshipsInitializationTest {


    @Autowired
    private IFriendshipsService iFriendshipsService;

    @Autowired
    private IUsersService iUsersService;

    @Test
    public void addFriends() {
        long l = System.currentTimeMillis();
        Random random = new Random();
        HashSet<Integer> ids = new HashSet<>();
        //模拟添加一千个好友
        while (ids.size()!=2000){
            ids.add(random.nextInt(400000)+209622);
        }
        ArrayList<Integer> list = new ArrayList<>(ids);

        Integer userIdCount = iUsersService.query().in("user_id", list).count();
        if (userIdCount!=list.size()){
            System.out.println("error");
        }

        List<Friendships>batchList=new ArrayList<>();

        Date now = new Date();
        for (int i = 0; i < 1000; i++) {
            Friendships me = new Friendships();
            Friendships u = new Friendships();
            batchList.add(me);
            batchList.add(u);
            me.setFriendId(list.get(i));
            me.setUserId(2);
            me.setTimestamp(now);

            u.setUserId(list.get(i));
            u.setFriendId(2);
            u.setTimestamp(now);
        }
        iFriendshipsService.saveBatch(batchList);
        log.info("耗时:{}",System.currentTimeMillis()-l);
    }


    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(random.nextInt(400000) + 209622);
        }
    }

}
