package com.lixin.web.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lixin.model.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-26 23:58:34
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-26     张李鑫                     1.0         1.0 Version
 */
public class ClientJsonMessageDecoder extends ByteToMessageDecoder {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 获取接收到的 ByteBuf 的数据长度
        int length = in.readableBytes();

        // 读取接收到的数据，并转换为 byte 数组
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        // 将 byte 数组转换为 JSON 字符串
        String json = new String(bytes);

        // 将 JSON 字符串转换为指定的 Java 对象，并添加到输出列表中
        Object obj = objectMapper.readValue(json, Response.class);
        out.add(obj);
    }
}
