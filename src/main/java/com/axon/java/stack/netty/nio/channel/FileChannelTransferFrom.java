package com.axon.java.stack.netty.nio.channel;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * TransferFrom的使用说明
 */
public class FileChannelTransferFrom {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("fileChannel说明.JPG");

        FileOutputStream fileOutputStream = new FileOutputStream("fileChannel说明copy.JPG");

        FileChannel fileInputChannel = fileInputStream.getChannel();

        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        //拷贝机制
        fileOutputStreamChannel.transferFrom(fileInputChannel, 0, fileInputChannel.size());

        fileOutputStreamChannel.close();
        fileInputChannel.close();
        fileOutputStream.close();
        fileInputStream.close();

        System.out.println("拷贝结束了");

    }

}
