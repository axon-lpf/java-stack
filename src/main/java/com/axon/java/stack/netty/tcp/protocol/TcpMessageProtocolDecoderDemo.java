package com.axon.java.stack.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class TcpMessageProtocolDecoderDemo extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("TcpMessageProtocolDecoderDemo 被调用");

        int length = in.readInt();

        byte[] content = new byte[length];

        in.readBytes(content);

        // 这里封装成MessageProtocol 交给下一个业务区处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(content);
        messageProtocol.setLength(length);

        out.add(messageProtocol);

    }
}
