package com.axon.java.stack.netty.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorServerTestDemo {

    public static void main(String[] args) throws IOException {

        //打开一个渠道 创建一个新的 ServerSocketChannel，它类似于传统的 ServerSocket，用于侦听客户端的连接。
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        //绑定服务器到端口 9001，以便等待客户端的连接。
        socketChannel.socket().bind(new InetSocketAddress(9001));

        //建立一个选择器
        Selector selector = Selector.open();
        // 设置非阻塞的模式, 将通道设置为非阻塞模式。意味着在等待连接和数据时，不会阻塞线程。这样即使没有任何事件发生，服务器也可以继续执行其他任务。
        socketChannel.configureBlocking(false);

        //注册一个链接的选择器  创建一个选择器（Selector）。选择器是 Java NIO 的核心部分，它允许单个线程检查多个通道的状态。
        //将服务器通道注册到选择器上，并对连接（OP_ACCEPT）事件感兴趣。当有客户端试图连接时，选择器将触发事件。
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 检查是否有事件发生，如果没发生则返回一个0
            if (selector.select(1000) == 0) {
                System.out.println("目前处于空闲状态");
                continue;
            }
            //获取所有的链接
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            //循环遍历去处理他们的请求
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                //这里检测到有新的客户端连接，注册对应的事件
                if (selectionKey.isAcceptable()) {

                    //受客户端的连接并返回一个 SocketChannel，这个通道代表了与客户端的连接。
                    SocketChannel channel = socketChannel.accept();
                    //将客户端的通道也设置为非阻塞。
                    channel.configureBlocking(false);

                    //将这个通道注册到选择器上，并对读事件（OP_READ）感兴趣，同时为每个通道分配一个 ByteBuffer 来存储从客户端接收的数据。
                    channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                //检查当前通道是否有数据可以读取。
                if (selectionKey.isReadable()) {
                    SocketChannel selectableChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();
//从通道中读取数据并存储到 ByteBuffer 中。bytesRead 返回读取的字节数。
//	•	如果 bytesRead > 0，表示有数据被读取。通过 flip() 切换 ByteBuffer 到读模式，打印收到的数据。然后通过 clear() 清空缓冲区，为下次读取做准备。
                    int bytesRead = selectableChannel.read(attachment);
                    if (bytesRead > 0) {
                        attachment.flip();  // 切换为读模式
                        System.out.println("from 客户端: " + new String(attachment.array(), 0, attachment.limit()));
                        attachment.clear(); // 清除缓冲区以便下次读取
                    } else if (bytesRead == -1) {
                        selectableChannel.close(); // 处理客户端断开连接
                        System.out.println("客户端断开连接");
                    }
                }
                //// 检查是否有事件发生，如果没发生则返回一个0
                iterator.remove();

            }


        }
    }
}
