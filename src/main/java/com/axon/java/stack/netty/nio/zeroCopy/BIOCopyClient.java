package com.axon.java.stack.netty.nio.zeroCopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * BIO接收文件的处理逻辑
 */
public class BIOCopyClient {


    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 7001);

            String fileName = "mindmaster_cn_full5764.dmg";

            FileInputStream fileInputStream = new FileInputStream(fileName);

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            byte[] bytes = new byte[4026];

            long readCount = 0;
            long totalCount = 0;

            long beginTime = System.currentTimeMillis();

            while ((readCount = fileInputStream.read(bytes)) > 0) {
                totalCount += readCount;
                outputStream.write(bytes, 0, (int) readCount); // 使用 readCount 而不是 bytes.length
            }
            System.out.println("总共发送字节数量" + totalCount + "总耗时：" + (System.currentTimeMillis() - beginTime));
            outputStream.close();
            fileInputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
