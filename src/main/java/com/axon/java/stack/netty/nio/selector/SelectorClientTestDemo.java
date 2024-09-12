package com.axon.java.stack.netty.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SelectorClientTestDemo {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9001);

        if (!socketChannel.connect(address)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端不会阻塞，可以做其它工作");
            }
        }
        String str = "我是nio" + Thread.currentThread().getName();

        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(byteBuffer);

        System.out.println("链接成功");

        Scanner scanner = new Scanner(System.in);

        System.out.println("输入'exit'以外的任何内容继续运行，输入'exit'结束程序。");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.println("程序已经结束。");
                break;
            }

            System.out.println("你输入了: " + input);
            // 这里可以添加更多的处理逻辑
        }

        scanner.close();

    }
}
