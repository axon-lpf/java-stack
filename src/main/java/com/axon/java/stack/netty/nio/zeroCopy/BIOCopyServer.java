package com.axon.java.stack.netty.nio.zeroCopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOCopyServer {

    /**
     * bio 接收处理文件的逻辑
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7001);
            System.out.println("服务器已启动，等待连接...");

            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("连接已建立：" + socket.getInetAddress());

                // 使用 try-with-resources 自动关闭资源
                try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                    byte[] bytes = new byte[4096];
                    int totalRead = 0;

                    // 接收数据
                    while (true) {
                        int read = dataInputStream.read(bytes);
                        if (read == -1) {
                            System.out.println("客户端已关闭连接，数据接收完毕。");
                            break;
                        }
                        totalRead += read;
                        System.out.println("正在接收数据，接收数据量：" + read + " 字节");
                    }

                    System.out.println("文件接收完成，总接收数据量：" + totalRead + " 字节");
                } catch (IOException e) {
                    System.out.println("读取数据时发生异常：" + e.getMessage());
                } finally {
                    // 关闭 Socket 连接
                    socket.close();
                    System.out.println("连接已关闭：" + socket.getInetAddress());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
