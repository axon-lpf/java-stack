package com.axon.java.stack.netty.protobuf.messageProtobuf;

import com.axon.java.stack.netty.protobuf.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufMessageServerHandler extends SimpleChannelInboundHandler<MessageDataInfo.MessageData> {


// 重写了 channelRead0 就不能重写 channelRead ， 如果也重写了 channelRead ， 那channelRead0不会被调用
/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageDataInfo.MessageData messageData =(MessageDataInfo.MessageData) msg;

   *//*     Student.Person person = (Student.Person) msg;
        System.out.println("客户端发送的数据    " + person.getName() + "  " + person.getEmail());*//*

    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageDataInfo.MessageData messageData) throws Exception {

        MessageDataInfo.MessageData.DataType dataType = messageData.getDataType();
        if (dataType == MessageDataInfo.MessageData.DataType.StudentType) {
            System.out.println("学生id" + messageData.getStudent().getId());

        } else if (dataType == MessageDataInfo.MessageData.DataType.WorkerType) {
            System.out.println("工人名称" + messageData.getWorker().getName());

        } else {
            System.out.println("传输类型不正确");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
    }
}
