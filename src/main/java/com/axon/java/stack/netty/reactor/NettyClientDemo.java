package com.axon.java.stack.netty.reactor;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientDemo {

    public static void main(String[] args) {

        try {
            NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)  //设置客户端实现类
                    .handler(new ChannelInitializer<SocketChannel>() {  //加入自己的处理器
                @Override
                protected void initChannel(SocketChannel ct) throws Exception {
                    ct.pipeline().addLast(new NettyClientHandler());
                }
            });

            System.out.println("客户端准备OK了 ");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
