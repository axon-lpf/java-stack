package com.axon.java.stack.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import static io.netty.handler.timeout.IdleState.READER_IDLE;
import static io.netty.handler.timeout.IdleState.WRITER_IDLE;

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        if (o instanceof IdleStateEvent){
            IdleStateEvent stateEvent = (IdleStateEvent) o;

            String eventType=null;
            switch (stateEvent.state()){
                case READER_IDLE:
                    eventType="读取空闲";
                case WRITER_IDLE:
                    eventType="写空闲";
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(channelHandlerContext.channel().remoteAddress()+"超时时间"+eventType);
        }
    }
}
