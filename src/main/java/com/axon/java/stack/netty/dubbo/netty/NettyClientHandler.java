package com.axon.java.stack.netty.dubbo.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;

    private String result;

    private String para;  //客户端调用方法时，传入的参数

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受到服务器消息了");
        result = msg.toString();
        notify();  /// 唤醒等待的线程

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.context.close();
    }

    // 被代理对象调用， 发送数据给服务器->wait->等待被唤醒-> 返回结果
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("开始执行发送消息了");
        context.writeAndFlush(para);
        System.out.println("发送消息结束");
        wait();
        System.out.println("已经返回结果了");
        return result;  // 服务方返回的结果

    }

    //设置参数
    public void setPara(String para) {
        this.para = para;
    }
}
