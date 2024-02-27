package com.lixin.web.test;

import cn.hutool.core.bean.BeanUtil;
import com.lixin.entity.GroupChatMessage;
import com.lixin.entity.Users;
import com.lixin.model.notify.AddFriendNotify;
import com.lixin.model.request.*;
import com.lixin.web.jwt.JwtConstants;

import java.util.*;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-08-06 23:53:45
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-08-06     张李鑫                     1.0         1.0 Version
 */
public class FuncTest {
    public static Request<com.lixin.engine.model.request.SendMessageRequest> sendMessageRequest(String token, Integer userId, String message) {
        Request<com.lixin.engine.model.request.SendMessageRequest> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("chat/sendMessageToUser");
        com.lixin.engine.model.request.SendMessageRequest sendMessageRequest = new com.lixin.engine.model.request.SendMessageRequest();
        sendMessageRequest.setTo(userId);
        sendMessageRequest.setMessage(message);
        request.setModel(sendMessageRequest);
        return request;
    }


    public static HashMap<String, String> getHeader(String token) {
        HashMap<String, String> header = new HashMap<>();
        header.put(JwtConstants.AUTH_TOKEN_KEY, JwtConstants.AUTH_TOKEN_PRE + token);
        return header;
    }

    public Request<com.lixin.engine.model.request.AcceptFriendRequest> acceptFriendRequest(String token, Integer friendsId) {
        Request<com.lixin.engine.model.request.AcceptFriendRequest> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("friends/acceptFriendRequest");
        com.lixin.engine.model.request.AcceptFriendRequest acceptFriendRequest = new com.lixin.engine.model.request.AcceptFriendRequest();
        acceptFriendRequest.setFriendsId(friendsId);
        request.setModel(acceptFriendRequest);
        return request;
    }

    public static Request createGroupChat(String token){
        Request<Object> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("chat/createGroupChat");
        return request;
    }

    public static Request<com.lixin.engine.model.request.ShareGroupRequest> shareGroup(String token, Integer groupId){
        Request<com.lixin.engine.model.request.ShareGroupRequest> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("chat/shareGroup");
        com.lixin.engine.model.request.ShareGroupRequest shareGroupRequest = new com.lixin.engine.model.request.ShareGroupRequest();
        shareGroupRequest.setGroupId(groupId);
        request.setModel(shareGroupRequest);
        return request;
    }

    public static Request<com.lixin.engine.model.request.JoinGroupRequest> joinGroup(String token, String   qrCode){
        Request<com.lixin.engine.model.request.JoinGroupRequest> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("chat/joinGroup");
        com.lixin.engine.model.request.JoinGroupRequest joinGroupRequest = new com.lixin.engine.model.request.JoinGroupRequest();
        joinGroupRequest.setQrCode(qrCode);
        request.setModel(joinGroupRequest);
        return request;
    }
    public static Request<GroupChatMessage> sendGroupMessage(String token,Integer   groupId,Integer messageType,String content){
        Request<GroupChatMessage> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("chat/sendGroupMessage");
        GroupChatMessage groupChatMessage = new GroupChatMessage();
        groupChatMessage.setGroupId(groupId);
        groupChatMessage.setMessageType(messageType);
        groupChatMessage.setContent(content);
        groupChatMessage.setSendTime(new Date());
        request.setModel(groupChatMessage);
        return request;
    }

    //sendGroupMessage

    public static Request<AddFriendNotify> addFriend(String token, Integer recipientId) {

        Request<AddFriendNotify> request = new Request<>();
        request.setHeaders(getHeader(token));
        request.setEvent("friends/addFriend");
        AddFriendNotify addFriendNotify = new AddFriendNotify();
        addFriendNotify.setRecipientId(recipientId);
        request.setModel(addFriendNotify);
        return request;
    }

    public static Request findAllFriends(String token) {
        Request<Users> request = new Request<>();
        request.setEvent("friends/findAllFriends");
        request.setHeaders(getHeader(token));
        return request;
    }

    public static Request<Users> login(String email) {
        Request<Users> request = new Request<>();
        request.setEvent("system/login");
        Users users = new Users();
        users.setEmail(email);
        users.setPassword("10201224w");
        request.setModel(users);
        return request;
    }

    public static Request<com.lixin.engine.model.request.RefreshTokenRequest> refreshToken(String refreshToken) {
        Request<com.lixin.engine.model.request.RefreshTokenRequest> request = new Request<>();
        request.setEvent("system/refreshToken");
        request.setModel(new com.lixin.engine.model.request.RefreshTokenRequest(refreshToken));
        return request;
    }

}
