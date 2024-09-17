package com.axon.java.stack.netty.dubbo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {


    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;


    /**
     * 客户端每调用一次就会反复执行
     *
     *
     *
     * @param serviceClass
     * @param providerName
     * @return
     */
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, (proxy, method, args) -> {
            // 这里必须要加这个代理方法，不然会创建代理失败   在 Java 的动态代理机制中，代理类会拦截所有方法调用，包括来自 Object 的方法，例如 toString()、equals() 和 hashCode()。如果代理类未专门处理这些方法，那么默认的代理实现可能会由于方法查找失败而导致异常。
            //例如，如果没有对 toString() 进行特殊处理，那么当你尝试打印代理对象时，toString() 方法会被代理机制拦截。此时，由于 toString() 没有被正确代理，可能会导致 NullPointerException 或其他未定义行为。
            if (method.getName().equals("toString")) {
                return "Proxy for " + serviceClass.getName();
            }
            if (client == null) {
                initClient();
            }
            client.setPara(providerName + args[0]);
            return executorService.submit(client).get();  // 获取执行的结果
        });
    }


    private static void initClient() {
        client = new NettyClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(client);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9001).sync();
            System.out.println("客户端已经准备就绪了。。。。");
            // 测试中遇到的坑
            // channelFuture.channel().closeFuture().sync();  这里不能写，这里是阻塞的，会导致后面的流程无法执行
        } catch (InterruptedException e) {

        } finally {
            // eventExecutors.shutdownGracefully();  这句也不能写，因为没有上面channelFuture.channel().closeFuture().sync() 进行阻塞，则会提前关闭的
        }
    }


}
