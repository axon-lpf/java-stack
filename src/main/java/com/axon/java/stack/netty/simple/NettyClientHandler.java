package com.axon.java.stack.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道链接就绪，就会触发该方法
     *
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {

        System.out.println("client" + channelHandlerContext);
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello服务端，我是小狗汪汪", CharsetUtil.UTF_8));
    }


    /**
     * 读取服务端消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务端发来消息了，服务器" + ctx);
        System.out.println("服务器地址" + ctx.channel().remoteAddress());
        //ctx.fireChannelRead(msg);
    }

    /**
     * 异常则关闭链接
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
