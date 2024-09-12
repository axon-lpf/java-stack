package com.axon.java.stack.netty.chatgroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * 1.	ChannelGroup：
 * 	•	ChannelGroup 是 Netty 提供的一个容器类，它可以存储多个 Channel 实例。当你有多个客户端连接时，你可以将这些连接的 Channel 对象放到 ChannelGroup 中，这样可以方便地进行批量操作，例如广播消息给所有连接的客户端、关闭所有连接等。
 * 	•	ChannelGroup 支持并发访问，线程安全的，可以在多个线程中共享和操作。
 * 	2.	DefaultChannelGroup：
 * 	•	DefaultChannelGroup 是 ChannelGroup 的一个实现类，它提供了默认的存储和操作 Channel 的机制。该类通过内部维护一个 Set<Channel> 来管理多个 Channel 实例。
 * 	•	你可以对 DefaultChannelGroup 执行类似于添加 Channel、移除 Channel 以及广播消息等操作。
 * 	3.	GlobalEventExecutor.INSTANCE：
 * 	•	GlobalEventExecutor 是 Netty 中的全局单线程 EventExecutor，它提供了一个默认的执行器来处理任务。在 ChannelGroup 中，这个执行器用于处理异步操作，如将消息发送给所有 Channel、关闭所有 Channel 等。
 * 	4.	作用：
 * 	•	管理多个 Channel：你可以将所有的连接（即 Channel 实例）添加到这个 ChannelGroup 中，方便统一管理。
 * 	•	批量操作：通过 channelGroup，可以对所有 Channel 执行批量操作，比如：
 * 	•	广播消息：发送消息给所有连接的客户端。
 * 	•	关闭所有连接：当需要停止服务器时，可以通过 channelGroup 批量关闭所有客户端连接。
 * 	•	线程安全：由于 ChannelGroup 是线程安全的，可以在多线程环境下安全地操作。
 *
 */

public class ChatGroupServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     * •	作用：当 Channel 注册到 EventLoop 上时触发，即通道关联到 EventLoop，可以开始处理 I/O 操作。
     * •	场景：当通道刚刚被注册时，可以执行一些初始化操作。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * •	作用：当 Channel 从 EventLoop 上取消注册时触发，表示通道将不再处理 I/O 操作。
     * •	场景：可以清理资源或进行连接终止前的操作。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //添加到用户组中去
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //移除对应的用户连接
        channelGroup.remove(ctx.channel());
    }


    /**
     * •	作用：当通道激活时触发，即当连接建立后可以发送或接收数据时被调用。
     * •	场景：可以在这里进行数据流启动的操作，如发送欢迎消息等。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 已上线\r\n");
        channelGroup.writeAndFlush("[客户] " + ctx.channel().remoteAddress() + " 已上线\r\n");
    }

    /**
     * •	作用：当通道变为非活跃状态时触发，表示连接已被关闭。
     * •	场景：可以在这里进行关闭连接后资源清理工作。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户]" + ctx.channel().remoteAddress() + " 已离线\r\n");
        channelGroup.writeAndFlush("[客户] " + ctx.channel().remoteAddress() + " 已离线\n");

    }

    /**
     * 读取消息
     *
     * @param ctx
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach((f) -> {
            if (channel == f) {

                f.writeAndFlush("[自己]" + ctx.channel().remoteAddress() + "  发送了  " + s + "\r\n");
            } else {
                f.writeAndFlush("[客户]" + ctx.channel().remoteAddress() + "  发送了 " + s + "\r\n");
            }
        });

    }

    /**
     * •	作用：当处理过程中出现异常时调用。
     * •	场景：在此方法中可以处理异常，如关闭连接或记录错误。
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
