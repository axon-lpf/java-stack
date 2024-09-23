package com.axon.java.stack.netty.dubbo.provider;

import com.axon.java.stack.netty.dubbo.common.IHelloService;

public class HelloServiceImpl implements IHelloService {

    private static int helloCount;

    @Override
    public String hello(String message) {
        String reuslt;
        System.out.println("收到客户端消息" + message);
        if (message != null) {
            reuslt = "收到客户端消息 " + message + ",第" + (++helloCount) + "次";

        } else {
            reuslt = "我已经收到客户端消息了";
        }

        return reuslt;
    }
}
