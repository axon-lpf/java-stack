package com.axon.java.stack.netty.protobuf.messageProtobuf;

import com.axon.java.stack.netty.protobuf.Student;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;


/**
 * ReplayingDecoder 和 ByteToMessageDecoder 都是 Netty 中用于处理字节流到消息对象转换的解码器。它们的作用类似，但工作方式和使用场景有所不同。以下是它们的主要区别：
 * <p>
 * 1. 基本概念
 * <p>
 * •	ByteToMessageDecoder：
 * •	是 Netty 提供的抽象类，用于从字节流中解码消息。
 * •	需要显式处理读取和解析字节的逻辑，并且需要开发者管理解码过程中数据的读取状态。
 * •	如果缓冲区中的数据不足以构建一个完整的消息，ByteToMessageDecoder 需要手动管理剩余的数据并等待下次接收更多数据。
 * •	ReplayingDecoder：
 * •	继承自 ByteToMessageDecoder，用于简化解码过程。
 * •	提供了“回放”机制，允许在解码过程中失败时自动重新读取缓冲区中的数据，而无需显式处理缓冲区状态。
 * •	它通过抛出 Signal 异常的方式让解码器回放当前读取的缓冲区，避免在手动管理读取位置和状态时引入错误。
 */
public class ProtobufMessageServer {

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline//.addLast(new TcpByteToStringDecoderDemo())  // 加入解码器
                                    //.addLast(new TcpReplayingDecoderDemo())
                                    //.addLast(new TcpByteToStringEncorderDemo())、
                                    .addLast(new ProtobufDecoder(MessageDataInfo.MessageData.getDefaultInstance()))
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new ProtobufMessageServerHandler()); // 加入编码器
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(9001).sync();
            System.out.println("服务器已就绪");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
