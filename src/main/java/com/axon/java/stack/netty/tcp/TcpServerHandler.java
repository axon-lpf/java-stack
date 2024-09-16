package com.axon.java.stack.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf message) throws Exception {

        byte[] bytes = new byte[message.readableBytes()];
        message.readBytes(bytes);
        String result = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收的消息数据" + channelHandlerContext.channel().remoteAddress() + "   " + result);
        System.out.println("服务端接收的消息数据数量" + channelHandlerContext.channel().remoteAddress() + "   " + (++this.count));

        //回送一个消息给客户端
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        channelHandlerContext.writeAndFlush(byteBuf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
    }
}
