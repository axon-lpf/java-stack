package com.axon.java.stack.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInitializerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //得到一个管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty提供的httpServerCode codec=> coder=>decorder

        pipeline.addLast("myhttpServerCode", new HttpServerCodec());
        pipeline.addLast("myHttpHander", new HttpServerHandler());
    }
}
