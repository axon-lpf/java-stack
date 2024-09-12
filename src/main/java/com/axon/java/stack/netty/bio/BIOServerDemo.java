package com.axon.java.stack.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 总结：
 * BIO 处理， 每个客户端连接都需要启用一个线程，当客户端不在发送消息时， 当客户端不在发送消息时，这些线程闲置，严重的浪费资源，
 * 如果在高并发的场景下，减少了其这个吞吐量。
 *
 * chatgpt总结：
 *  •	总结中提到每个客户端连接都需要一个线程处理，且当客户端不发送消息时，线程会闲置。这种情况是 BIO 的一个明显缺点。
 * 	•	在高并发场景下，BIO 的性能较差，因为每个连接都需要一个独立的线程，这会导致资源消耗和线程切换开销。
 *
 */
public class BIOServerDemo {

    private static int coreSize = Runtime.getRuntime().availableProcessors();

    private static ExecutorService customer = new ThreadPoolExecutor(coreSize, coreSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("服务器开始启动了");
            while (true) {
                //这里也是阻塞
                Socket socket = serverSocket.accept();
                System.out.println("当前连接处理线程id" + Thread.currentThread().getId());
                messageHandle(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


    public static void messageHandle(Socket socket) {

        customer.submit(() -> {
            try {
                // 这里阻塞状态
                System.out.println("当前消息处理线程id" + Thread.currentThread().getId());
                byte[] bytes = new byte[1024];
                // 这里是阻塞
                InputStream inputStream = socket.getInputStream();
                while (true) {
                    int read = inputStream.read(bytes);
                    if (read != -1) {
                        System.out.println("有消息过来了" + new String(bytes, 0, read, "UTF-8"));

                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
