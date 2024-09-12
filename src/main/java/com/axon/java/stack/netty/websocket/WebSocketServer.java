package com.axon.java.stack.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup=new NioEventLoopGroup();

        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //基于http协议的编解码处理器
                            pipeline.addLast(new HttpServerCodec())
                                    //以块的方式写的处理器
                                    .addLast(new ChunkedWriteHandler())
                                    //http数据在传输的过程中是分段的， HttpObjectAggregator可以进行分段聚合
                                    .addLast(new HttpObjectAggregator(8192))
                                    //说明
                                    //1.对应websocket, 它的数据是以帧的形式传递
                                    //2.可以看到 webSocket下面有六个子类
                                    //3.请求方式ws://localhost:9001
                                    .addLast(new WebSocketServerProtocolHandler("/hello"))
                                    //自定义处理
                                    .addLast(new WebSocketHandler());
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
