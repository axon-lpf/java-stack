package com.axon.java.stack.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class TcpProtocolServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol message) throws Exception {


        String result = new String(message.getContent(), Charset.forName("utf-8"));
        System.out.println("服务端接收的消息数据" + channelHandlerContext.channel().remoteAddress() + "   " + result);
        System.out.println("服务端接收数据的长度是" + message.getLength());
        System.out.println("服务端接收的消息数据数量" + channelHandlerContext.channel().remoteAddress() + "   " + (++this.count));

        //回送一个消息给客户端, 编码处理
        String response = UUID.randomUUID().toString();
        int responseLength = response.getBytes(Charset.forName("utf-8")).length;
        byte[] bytes = response.getBytes(Charset.forName("utf-8"));

        MessageProtocol responseMessage = new MessageProtocol();
        responseMessage.setLength(responseLength);
        responseMessage.setContent(bytes);
        channelHandlerContext.channel().writeAndFlush(responseMessage);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
    }
}
