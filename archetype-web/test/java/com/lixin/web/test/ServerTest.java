package com.lixin.web.test;

import com.lixin.web.chat.IMClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-27 00:39:38
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-27     张李鑫                     1.0         1.0 Version
 */
public class ServerTest {
    static EventLoopGroup group;
    static Channel channel;

    static CountDownLatch latch;
    static Bootstrap bootstrap = new Bootstrap();
    @BeforeAll
    static void run() throws InterruptedException {
        group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new IMClientInitializer());
        channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();
        latch = new CountDownLatch(1);
    }

    @Test
    public void client() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTY5MTgzNTIxNiwiaWF0IjoxNjkxNzQ4ODE2fQ.zi_gX6xiBVhHAr-F3mGhlX_GlDS3rZv3fc-DAVG5k6g";
        channel.writeAndFlush(FuncTest.sendGroupMessage(token,1,1,"你好111")).sync();
        latch.await();
    }

    @Test
    public void client1() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImV4cCI6MTY5MTc4NjE4OSwiaWF0IjoxNjkxNjk5Nzg5fQ.qKVKiefW49A23FbC5pArH6NEibKOZYiAFqeOTQvYNtI";
        channel.writeAndFlush(FuncTest.login("5140283811@163.com")).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token, "ae685db3-3d61-4a0a-bf46-bfed40a66c72")).sync();
        latch.await();
    }

    @Test
    public void client2() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwOTYyNCwiZXhwIjoxNjkxNzg2MjA5LCJpYXQiOjE2OTE2OTk4MDl9.VkowOzb4k2vxvOYv-uc5F4rvjZlUUGMlxQ7JN7koNjo";
        channel.writeAndFlush(FuncTest.login("ttjp88yg@example.com")).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token,1)).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token, "ae685db3-3d61-4a0a-bf46-bfed40a66c72")).sync();
        latch.await();
    }


    @Test
    public void client3() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwOTYyMywiZXhwIjoxNjkxNzg2MjI5LCJpYXQiOjE2OTE2OTk4Mjl9.M7f2JM0Bm2gX3-31tqZ-NtunezSCOUcqjv6GkAsPouU";
        channel.writeAndFlush(FuncTest.login("4jh48ju043@example.com")).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token,1)).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token, "ae685db3-3d61-4a0a-bf46-bfed40a66c72")).sync();
        latch.await();
    }

    @Test
    public void client4() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwOTYyNiwiZXhwIjoxNjkxNzg2MjUzLCJpYXQiOjE2OTE2OTk4NTN9.DN1AlVeScpKKmOH4MoroK-Vd5TjA8k1GPNwKVsK0Ewo";
        channel.writeAndFlush(FuncTest.login("axm219hp@outlook.com")).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token,1)).sync();
//        channel.writeAndFlush(FuncTest.joinGroup(token, "ae685db3-3d61-4a0a-bf46-bfed40a66c72")).sync();
        latch.await();
    }

}
