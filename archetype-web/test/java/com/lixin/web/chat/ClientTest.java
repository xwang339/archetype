package com.lixin.web.chat;

import com.lixin.entity.Users;
import com.lixin.model.request.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-25 00:00:00
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-25     张李鑫                     1.0         1.0 Version
 */

public class ClientTest {
    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new com.lixin.web.chat.IMClientInitializer());

            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();

            // 模拟用户登录"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImV4cCI6MTY5MTMzMTE2OCwiaWF0IjoxNjkxMjQ0NzY4fQ.Hgt9PZPxy6mRFNoZCkYTS2wn-cp9g-ic7jUZFeWgYVg"

            Request<Users> request = new Request<>();
            request.setEvent("users/l1ogin");
            Users users = new Users();
            users.setUsername("zlx");
            users.setPassword("10201224w");
            request.setModel(users);
            channel.writeAndFlush(request);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
