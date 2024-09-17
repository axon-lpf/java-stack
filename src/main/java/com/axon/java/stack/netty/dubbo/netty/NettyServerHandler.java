package com.axon.java.stack.netty.dubbo.netty;

import com.axon.java.stack.netty.dubbo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 获取客户端发送的消息，然后调用指定的服务
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("接收到的消息" + msg);
        //定义协议，每次发消息，必须以每个字符串开头  HelloService#hello
        if (msg.toString().contains("#")) {
            String methodName = msg.toString().substring(msg.toString().lastIndexOf("#") + 1);
            String result = new HelloServiceImpl().hello(methodName);
            ctx.channel().writeAndFlush(result);
        } else {
            ctx.channel().writeAndFlush("Invalid message format");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
