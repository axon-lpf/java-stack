package com.axon.java.stack.netty.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 把一个文件的内容拷贝到另一个文件
 */
public class FileOutPutAndFileInPutChannelDemo {

    public static void main(String[] args) throws IOException {

        // 读取对应的文件
        FileInputStream filterInputStream = new FileInputStream("fileOutputStream.txt");

        // 写入对应的文件
        FileOutputStream fileOutputStream = new FileOutputStream("fileOutputStream02.txt");

        //获取读取的channel
        FileChannel inputStreamChannel = filterInputStream.getChannel();

        //获取输出的channel
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //缓存中写一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);

        while (true) {
            //读取到 ByteBuffer 中
            byteBuffer.clear();
            int read = inputStreamChannel.read(byteBuffer);
            //等于-1表示读取完毕
            if (read == -1) {
                break;
            }
            //反转， 之后进行读取写入
            byteBuffer.flip();
            //每读取一次，就写一次
            outputStreamChannel.write(byteBuffer);
        }
        //关闭对应的流
        fileOutputStream.close();
        filterInputStream.close();
    }
}
