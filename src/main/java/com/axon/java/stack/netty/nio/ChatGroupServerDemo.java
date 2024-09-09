package com.axon.java.stack.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 聊天的服务端
 *  ServerSocketChannel 服务端使用  ServerSocketChannel开启服务
 *
 *
 *
 */
public class ChatGroupServerDemo {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private final static int port = 7002;

    private final static String address = "127.0.0.1";

    public ChatGroupServerDemo() throws IOException {
        //选择器
        selector = Selector.open();
        //服务端的链接通道
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    /**
     * 监听消息
     *
     * @throws IOException
     */
    public void listenMessage() throws IOException {

        while (true) {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        //获取连接
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        //注册事件
                        sc.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        //打印
                        System.out.println(sc.getRemoteAddress() + "上线");
                    }
                    if (key.isReadable()) {
                        SocketChannel channel = null;
                        try {
                            channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                            channel.configureBlocking(false);
                            channel.read(byteBuffer);
                            byteBuffer.flip();
                            String message = new String(byteBuffer.array(), 0, byteBuffer.remaining());
                            System.out.println("from 客户端" + message);
                            // 向其它客户端转发送消息
                            sendMessageOtherClient(message, channel);
                        } catch (IOException e) {
                            try {
                                System.out.println(channel.getRemoteAddress() + "离线了");
                                //取消注册
                                key.cancel();
                                //关闭通道
                                channel.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    iterator.remove(); //手动移除，防止重复处理
                }
            }
        }
    }

    /**
     * 消息转发
     *
     * @param message
     * @param socketChannel
     * @throws IOException
     */
    private void sendMessageOtherClient(String message, SocketChannel socketChannel) throws IOException {
        System.out.println("服务器转发消息中");
        // selector.keys() 注意这里是获取到所有的key值的channel， 而不是selectedKey， 这个获取发生的事件
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();
            //剔除给自己转发，转发给别人
            if (channel instanceof SocketChannel && socketChannel != channel) {
                SocketChannel dest = (SocketChannel) channel;
                dest.configureBlocking(false);
                ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                dest.write(byteBuffer);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ChatGroupServerDemo chatGroupServerDemo = new ChatGroupServerDemo();
        chatGroupServerDemo.listenMessage();
    }
}
