package com.axon.java.stack.netty.chatgroup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 1. ChannelOption.SO_BACKLOG
 * <p>
 * SO_BACKLOG 是为服务器端通道设置的选项，用于指定内核为此通道维护的连接队列的最大长度。它对应的是 TCP 的 backlog 参数。
 * <p>
 * •	作用：当一个客户端发起连接请求时，如果服务器没有及时调用 accept() 方法来处理该连接，连接请求将会在队列中等待。SO_BACKLOG 就是定义这个等待队列的最大长度。如果队列满了，新连接将会被拒绝。
 * •	128：在这个例子中，128 是队列的最大长度。即最多可以有 128 个等待处理的连接请求。如果超过 128，新的连接请求会被拒绝。
 * <p>
 * 简化说明：控制有多少个客户端连接可以在未被处理的情况下等待。
 * <p>
 * <p>
 * <p>
 * 2. SO_KEEPALIVE 是为子通道（即处理每个客户端连接的通道）设置的 TCP 保活选项。它对应的是 TCP 层的 SO_KEEPALIVE 选项。
 * <p>
 * •	作用：启用 TCP 的保活机制。保活机制会在连接长时间空闲时（没有数据传输），周期性地发送心跳包以检测连接的活跃状态。如果一方失去响应，另一方就可以及时发现并关闭连接。
 * •	true：在这里，true 表示开启 TCP 的 keepalive 选项。
 * <p>
 * 简化说明：确保空闲的连接不会意外断开，可以通过保活包检测远程连接的状态。
 */
public class ChatGroupServer {


    private int port;


    public ChatGroupServer(int port) {
        this.port = port;
    }

    public void run() {
        //设置一个线程去接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        //设置默认获取服务器的核心数*2 作为线程工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //控制有多少个客户端连接可以在未被处理的情况下等待。
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //于子通道，启用 TCP 保活，确保连接长时间空闲时不被意外断开。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ctx) throws Exception {

                            ctx.pipeline().addLast("myDecoder", new StringDecoder())
                                    .addLast("myEncoder", new StringEncoder())
                                    .addLast(new ChatGroupServerHandler());  //处理器
                        }
                    });

            //监听启动
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务端已启动就绪。。。。。。");
            //关闭操作
            channelFuture.channel().closeFuture().sync();

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {

        ChatGroupServer chatGroupServer = new ChatGroupServer(9001);
        chatGroupServer.run();
    }
}
