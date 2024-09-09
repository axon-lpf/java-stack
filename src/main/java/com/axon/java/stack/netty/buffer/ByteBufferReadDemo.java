package com.axon.java.stack.netty.buffer;

import java.nio.ByteBuffer;

public class ByteBufferReadDemo {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // put
        byteBuffer.putInt(1);
        byteBuffer.putLong(100L);
        byteBuffer.putChar('刘');
        byteBuffer.putShort((short) 4);

        // 反转一下
        byteBuffer.flip();

        // 注意获取值的时候， 必须按照put的类型位置获取，否则就会报错
        System.out.println("以下是正确的类型-----");
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());

        System.out.println("以下是错误的类型案例-----");
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());

    }
}
