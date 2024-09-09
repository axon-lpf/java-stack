package com.axon.java.stack.netty.zeroCopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NIOCopyClient {

    public static void main(String[] args) {


        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 7002));
            String fileName = "mindmaster_cn_full5764.dmg";
            long timeMillis = System.currentTimeMillis();
            FileChannel fileChannel = new FileInputStream(fileName).getChannel();
            long fileSize = fileChannel.size();
            long   transferred = fileChannel.transferTo(0, fileSize, socketChannel);

            System.out.println("文件发送完成，总字节数: " + transferred + "，总耗时: " + (System.currentTimeMillis() - timeMillis) + " ms");

            // 关闭通道
            fileChannel.close();
            socketChannel.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
