package com.axon.java.stack.netty.inboundandoutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class InboudAndOutboundClientHandler extends SimpleChannelInboundHandler<Long> {

    /**
     * 发送数据
     *
     * @param channelHandlerContext
     * @param aLong
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {

        System.out.println("服务器的ip=" + channelHandlerContext.channel().remoteAddress());
        System.out.println("收到服务器返回数据" + aLong);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("InboudAndOutboundClientHandler 发送数据");
        ctx.channel().writeAndFlush(123456L); // 发送一个 long
    }
}
