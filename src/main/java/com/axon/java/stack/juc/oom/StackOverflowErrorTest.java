package com.axon.java.stack.juc.oom;


/**
 * 栈的大小一般在512-1024K之间
 *
 *
 * 手动设置栈内存的大小
 * -Xss 512k  设置栈的大小
 *
 *  设置VM options
 * -Xss512k -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 *
 * 错误信息：
 * 	at com.axon.java.stack.juc.oom.StackOverflowError.testStackOver(StackOverflowError.java:21)
 *
 */
public class StackOverflowErrorTest {

    public static void main(String[] args) {

        //递归调用会发生栈溢出
        testStackOver();
    }


    public static void testStackOver() {
        testStackOver();
    }
}
