package com.axon.java.stack.netty.simple;

import com.axon.java.stack.juc.oom.TheadLimtOverflowTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Thread.sleep(1000*20);
        //比如我们这里有一个非常耗时的业务-》异步执行，提交该channel对应的NIOEventLoop的taskQueue中
        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(10*1000);
                System.out.println("我是异步任务哦");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("server cxt=" + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕的处理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));
        super.channelReadComplete(ctx);
    }

    /**
     * 出现异常则关闭
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
