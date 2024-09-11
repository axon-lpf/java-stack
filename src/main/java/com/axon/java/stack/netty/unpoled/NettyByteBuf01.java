package com.axon.java.stack.netty.unpoled;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class NettyByteBuf01 {

    /**
     *  在netty的buffer中是不需要反转的 ，因为底层维护了一个WriteIndex 和readIndex
     * @param args
     */
    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {

            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {

            System.out.println(buffer.readByte());
        }
    }
}
