package com.axon.java.stack.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HeartbeatServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup=new NioEventLoopGroup();

        EventLoopGroup workGroup=new NioEventLoopGroup();

     try{

         ServerBootstrap serverBootstrap=new ServerBootstrap();

         serverBootstrap.group(bossGroup,workGroup)
                 .channel(NioServerSocketChannel.class)
                 .handler(new LoggingHandler())  //添加日志记录
                 .option(ChannelOption.SO_BACKLOG,128)
                 .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel socketChannel) throws Exception {

                         ChannelPipeline pipeline = socketChannel.pipeline();
                         // IdleStateHandler  是netty提供空闲状态的处理器
                         // long  readerIdleTime 表示多长时间没有读取，就会发送一个心跳检测包，检测是否连接
                         // long  writeIdeTime  表示多长时间没有写入， 就会发送一个心跳检测包，检测是否连接
                         // long  allIdeTime     表示多长时间没有读写， 就会发送一个心跳检测包，检测是否连接
                         pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS))
                                 .addLast(new HeartbeatServerHandler());  // 自定义，对空闲状态进一步检测处理
                     }
                 });


         try {
             ChannelFuture channelFuture = serverBootstrap.bind(9001).sync();
             channelFuture.channel().closeFuture().sync();
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }

     }finally {
         bossGroup.shutdownGracefully();
         workGroup.shutdownGracefully();
     }

    }
}
