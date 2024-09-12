package com.axon.java.stack.netty.chatgroup;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatGroupClient {

    private String address;

    private int port;

    public ChatGroupClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run() {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ctx) throws Exception {
                        ctx.pipeline().addLast("myDecoder", new StringDecoder())
                                .addLast("myEncorder", new StringEncoder())
                                .addLast(new ChatGroupClientHandler());
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            Channel channel = channelFuture.channel();
            Scanner sample = new Scanner(System.in);
            while (sample.hasNextLine()) {
                String line = sample.nextLine();
                channel.writeAndFlush(line);
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {

        ChatGroupClient chatGroupClient = new ChatGroupClient("127.0.0.1", 9001);
        chatGroupClient.run();
    }
}
