package com.axon.java.stack.netty.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 针对输入流的fieChanel 的使用， 以及 byteBuffer的使用
 */
public class FileInputStreamChannelDemo {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("classpath:fileOutputStream.txt");

            FileChannel fileChannel = fileInputStream.getChannel();

            // 注意若是文本中的字符小于当前的字节长度， 输出可能会有很多的空格符
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //从channel中读取到byteBuffer中,即写入到 byteBuffer
            fileChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array(), "UTF-8"));

            // 正确的方式  反转
            byteBuffer.flip();

            //byteBuffer.remaining() 该方法表示当前能读取到的字节数
            byte[] data = new byte[byteBuffer.remaining()];
            byteBuffer.get(data);

            System.out.println(new String(data, "UTF-8"));



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileInputStream.close();
        }
    }
}
