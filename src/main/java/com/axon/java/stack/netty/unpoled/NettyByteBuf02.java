package com.axon.java.stack.netty.unpoled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", StandardCharsets.UTF_8);
        if (byteBuf.hasArray()) {
            //将byte在转换成字符穿
            byte[] array = byteBuf.array();
            System.out.println(new String(array, StandardCharsets.UTF_8));
            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset()); //0
            System.out.println(byteBuf.readerIndex()); //0
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println(byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 6, StandardCharsets.UTF_8));
        }
    }
}
