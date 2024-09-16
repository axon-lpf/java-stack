package com.axon.java.stack.netty.tcp.protocol;

/**
 * 自定义消息协议
 */
public class MessageProtocol {

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    private int length;

    private byte[] content;
}
