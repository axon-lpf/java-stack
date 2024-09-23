package com.axon.java.stack.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("ProtobufClientHandler 发送数据");
        Student.Person.Builder builder = Student.Person.newBuilder().setId(1).setName("阿西吧").setEmail("1255852992@qq.com");
        ctx.writeAndFlush(builder);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回送的消息" + byteBuf.toString(Charset.forName("utf-8")));
        System.out.println("服务器的地址" + ctx.channel().remoteAddress());
    }
}
