package com.axon.java.stack.netty.protobuf.messageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ProtobufMessageClientHandler extends SimpleChannelInboundHandler<MessageDataInfo.MessageData> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = new Random().nextInt(3);
        MessageDataInfo.MessageData messageData = null;
        if (i == 0) {
            MessageDataInfo.Student student = new MessageDataInfo.Student().newBuilderForType().setId(1).setName("松江").build();
            messageData = MessageDataInfo.MessageData.newBuilder().setDataType(MessageDataInfo.MessageData.DataType.StudentType).setStudent(student).build();
        } else {
            MessageDataInfo.Worker worker = new MessageDataInfo.Worker().newBuilderForType().setName("黑精钢").setAge(20).build();
            messageData = MessageDataInfo.MessageData.newBuilder().setDataType(MessageDataInfo.MessageData.DataType.WorkerType).setWorker(worker).build();
        }
        ctx.writeAndFlush(messageData);
    }


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
}
