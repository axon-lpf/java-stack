package com.axon.java.stack.netty.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 针对输出流的fieChanel 的使用， 以及 byteBuffer的使用
 */
public class FileOutputStreamChannelDemo {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("classpath:fileOutputStream.txt");

            String hello = "hello， 我是一只小小鸟啊";

            FileChannel fileChannel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            byteBuffer.put(hello.getBytes());

            // 反转策略
            byteBuffer.flip();
            //从缓存区中读取，写入到channel中。
            fileChannel.write(byteBuffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutputStream.close();
        }
    }
}
