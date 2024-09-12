package com.axon.java.stack.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static void main(String[] args) {

        //接受连接的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理读写的线程组
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)  //设置接受线程组，和工作线程组
                    .channel(NioServerSocketChannel.class)  //表示用那种协议去处理
                    .childHandler(new HttpInitializerHandler());  //设置业务处理器

            try {
                ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
                channelFuture.channel().closeFuture().sync();  //关闭

            } catch (InterruptedException e) {


            }
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
