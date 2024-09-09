package com.axon.java.stack.juc.oom;


import java.nio.ByteBuffer;

/**
 * 配置参数：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 * -XX:MaxDirectMemorySize=5m
 *
 * 	•	含义：设置 JVM 最大直接内存大小为 5MB。
 * 	•	作用：控制 JVM 可以分配的最大直接内存（直接缓冲区的大小）。直接内存是 JVM 之外的内存区域，用于处理 NIO（New Input/Output）操作或其他需要直接操作操作系统内存的场景。如果分配的直接内存超过了这个限制，也会抛出 OutOfMemoryError。
 *
 *
 * <p>
 * Exception in thread "main" java. Lang.OutOfMemoryError: Direct buffer memory
 * <p>
 * 导敏原因：
 * * 写NIO程序经常使用ByteBuffer来读取或者写入数据，这是一种基于適道（ChanneL）与缓冲区（Buffer）的工/0方式，
 * * 它可以使用Native函数库直接分配维外内存，然后通过一个存体在Java维里面的DirectByteBuffer对象作为这统内存的引用进行操作。
 * 这样能在一些场景中显署提高性能，因为避免了在Java控和Native 雄中来回复制数据。
 * *
 * *
 * * ByteBuffer.allocate（capability）第一种方式是分 JVM維内存，属于GC管辖范園，由于篇要烤贝所以速度相对按授
 * * ByteBuffer.allocteDirect（capability）第一种方式是分配DS本地内存，不属予GC管错范围，由子不篇更内存拷贝所以速度相可航快。
 * *但如與不断分配本地内存，维内存很少使用，那么了VM就不麻要执行GC，DirectByteBuffer可象们就不会被回收，这时候堆内存充足，但本地内存可能已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError，那程序就直接扇赏了。
 *
 *
 */
public class DirectBufferMemeryDemoTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("配置的MaxDirectMemorySize" + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024));
        Thread.sleep(3000);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);

        System.out.println("运行结束");

    }
}
