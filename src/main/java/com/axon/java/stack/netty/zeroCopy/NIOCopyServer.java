package com.axon.java.stack.netty.zeroCopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * nio接收文件的客户端
 */
public class NIOCopyServer {


    public static void main(String[] args) {

        try {
            // 打开ServerSocketChannel并绑定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(7002));

            // 文件输出流和文件通道，用于保存接收到的文件
            String outputFileName = "received_file.zip";
            FileOutputStream fos = new FileOutputStream(outputFileName);
            FileChannel fileChannel = fos.getChannel();

            // 分配一个ByteBuffer用于数据的传输
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096); // 更大一些的buffer，避免频繁读取

            while (true) {
                // 接收客户端连接
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("客户端连接成功: " + socketChannel.getRemoteAddress());

                // 读取数据
                int readCount;
                while ((readCount = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println("读取的数据量" + readCount);
                    byteBuffer.flip(); // 切换Buffer为读模式
                    fileChannel.write(byteBuffer); // 将Buffer写入到文件
                    byteBuffer.clear(); // 清空Buffer以便下一次读操作
                }

                if (readCount == -1) {
                    System.out.println("文件传输完成，关闭连接。");
                    socketChannel.close(); // 读取完毕，关闭连接
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
