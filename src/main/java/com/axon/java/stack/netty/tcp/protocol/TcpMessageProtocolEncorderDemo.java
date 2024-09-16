package com.axon.java.stack.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 解码器
 */
public class TcpMessageProtocolEncorderDemo extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol message, ByteBuf out) throws Exception {
        System.out.println("TcpMessageProtocolEncorderDemo 被调用");
        out.writeInt(message.getLength());
        out.writeBytes(message.getContent());
/*        System.out.println("msg" + aLong);
        out.writeLong(aLong);*/
    }
}
