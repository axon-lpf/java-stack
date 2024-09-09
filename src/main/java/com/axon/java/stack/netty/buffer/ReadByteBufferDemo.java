package com.axon.java.stack.netty.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 这段代码展示了Java NIO（非阻塞IO）中的Scatter/Gather概念。代码的主要目的是将数据从一个Socket读取到多个ByteBuffer中（scatter），然后将它们组合写回到Socket（gather）。这种方式在处理固定大小的消息时非常有用，特别是在需要分段处理数据的场景下。
 */
public class ReadByteBufferDemo {


    public static void main(String[] args) throws IOException {
        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress address = new InetSocketAddress(7001);
        serverSocketChannel.socket().bind(address);

        //建立连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 100;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {

                long r = socketChannel.read(byteBuffers);
                if (r == -1) {
                    break; // 客户端断开连
                }
                byteRead += r;   // 累加读取到的字节数
                System.out.println("byteRead" + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> "postion=" + buffer.position() + "limit=" + buffer.limit()).forEach(System.out::println);

                //将所有的buffer进行反转
                Arrays.asList(byteBuffers).forEach(f -> f.flip());
                // 打印接收的消息内容
                StringBuilder message = new StringBuilder();
                for (ByteBuffer buffer : byteBuffers) {
                    while (buffer.hasRemaining()) {
                        message.append((char) buffer.get()); // 逐字节读取
                    }
                }
                System.out.println("Received message: " + message.toString());
                // 读取到后又发送过去
                long write = socketChannel.write(byteBuffers);
                Arrays.asList(byteBuffers).forEach(f -> f.clear());

                System.out.println("byteRead");
            }
        }


    }
}
