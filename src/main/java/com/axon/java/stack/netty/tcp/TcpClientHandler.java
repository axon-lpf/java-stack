package com.axon.java.stack.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    /**
     * 发送数据
     *
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String message = new String(bytes, Charset.forName("utf-8"));

        System.out.println("客户端接收到的消息" + message);
        System.out.println("客户端接收消息数量" + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,Server   " + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }

        System.out.println("TcpClientHandler 发送数据");
        //ctx.channel().writeAndFlush(123456L); // 发送一个 long
    }
}
