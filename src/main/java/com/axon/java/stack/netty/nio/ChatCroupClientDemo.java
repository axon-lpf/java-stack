package com.axon.java.stack.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 聊天客户端
 * <p>
 * SocketChannel 客户端使用 SocketChannel 与服务端建立起链接
 * 也同样可以使用 Selector 进行注册事件
 */
public class ChatCroupClientDemo {

    private Selector selector;

    private final static int port = 7002;

    private final static String address = "127.0.0.1";

    private SocketChannel socketChannel;

    private String username;


    public ChatCroupClientDemo() {

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(address, port));
            socketChannel.configureBlocking(false);
            //当前客户端去注册一个读的事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ok ");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        // 发送消息的逻辑
        message = username + "说：" + message;
        socketChannel.write(ByteBuffer.wrap(message.getBytes()));
    }

    /**
     * 接收消息
     */
    public void receiveMessage() throws IOException {

        int readChannels = selector.select();
        if (readChannels > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //监听获取读事件，进行解析。
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    String s = new String(byteBuffer.array());
                    System.out.println(s.trim());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatCroupClientDemo chatCroupClientDemo = new ChatCroupClientDemo();

        new Thread(() -> {
            while (true) {
                try {
                    chatCroupClientDemo.receiveMessage();
                    Thread.sleep(3000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //扫描器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            chatCroupClientDemo.sendMessage(s);
        }

    }
}
