package com.axon.java.stack.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class TcpProtocolClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    /**
     * 发送数据
     *
     * @param channelHandlerContext
     * @param message
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol message) throws Exception {

        String content = new String(message.getContent(), Charset.forName("utf-8"));

        System.out.println("客户端接收到的消息" + content);
        System.out.println("客户端接收消息数量" + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 5; i++) {
            String message = "今天天气好热啊，阿西吧啊。。。";
            int length = message.getBytes(Charset.forName("utf-8")).length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLength(length);
            messageProtocol.setContent(message.getBytes(Charset.forName("utf-8")));
            ctx.writeAndFlush(messageProtocol);
        }

        System.out.println("TcpClientHandler 发送数据");
        //ctx.channel().writeAndFlush(123456L); // 发送一个 long
    }
}
