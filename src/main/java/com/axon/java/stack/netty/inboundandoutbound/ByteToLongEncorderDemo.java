package com.axon.java.stack.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;


/**
 * 解码器
 */
public class ByteToLongEncorderDemo extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("ByteToLongEncorderDemo 被调用");
        System.out.println("msg" + aLong);
        byteBuf.writeLong(aLong);
    }
}
