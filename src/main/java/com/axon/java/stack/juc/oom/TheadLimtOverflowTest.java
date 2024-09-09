package com.axon.java.stack.juc.oom;

/**
 * 创建线程超过限制
 * <p>
 * 1.	操作系统限制：
 * •	操作系统对每个进程创建的线程数有一定的限制。这取决于操作系统和系统配置。  一般都是1024
 * •	在某些系统中，线程的总数可能会受到最大线程数限制的影响，这可能会在创建大量线程时造成问题。
 * 2.	JVM 配置：
 * •	JVM 本身对线程的创建有一定的限制，主要取决于每个线程的栈大小（由 -Xss 参数设置）。
 * •	每个线程的栈大小加上其他线程资源的总和可能超出系统可用内存，从而导致创建线程失败。
 * 3.	内存使用：
 * •	每个线程都需要一定的内存来分配栈空间。大量线程会迅速消耗掉可用的系统内存，导致系统无法继续分配资源。
 *
 *
 * <p>
 * 如果超过限制，则会报以下错误
 * Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
 * at java.lang.Thread.start0(Native Method)
 * at java.lang.Thread.start(Thread.java:717)
 * at com.axon.java.stack.juc.oom.TheadLimtOverflowTest.main(TheadLimtOverflowTest.java:21)
 * Error occurred during initialization of VM
 * java.lang.OutOfMemoryError: unable to create new native thread
 */
public class TheadLimtOverflowTest {


    public static void main(String[] args) {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            new Thread(() -> {
                System.out.println("我是线程" + Thread.currentThread().getName());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }

}
