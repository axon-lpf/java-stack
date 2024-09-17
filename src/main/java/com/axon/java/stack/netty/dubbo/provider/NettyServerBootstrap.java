package com.axon.java.stack.netty.dubbo.provider;

import com.axon.java.stack.netty.dubbo.netty.NettyServer;


/**
 * 服务端是server启动
 */
public class NettyServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 9001);
    }
}
