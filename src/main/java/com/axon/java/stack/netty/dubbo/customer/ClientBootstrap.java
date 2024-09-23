package com.axon.java.stack.netty.dubbo.customer;

import com.axon.java.stack.netty.dubbo.common.IHelloService;
import com.axon.java.stack.netty.dubbo.netty.NettyClient;

public class ClientBootstrap {

    public static final String providerName = "IHelloService#hello#";

    public static void main(String[] args) {

        try {
            NettyClient nettyClient = new NettyClient();

            IHelloService helloService = (IHelloService) nettyClient.getBean(IHelloService.class, providerName);

            String helloResult = helloService.hello("我是doubbo");

            System.out.println("服务端返回结果了" + helloResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
