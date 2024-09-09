package com.axon.java.stack.juc.threadUtils;


import java.util.concurrent.CountDownLatch;

/**
 * 线程计数
 * <p>
 * CountDownLatch
 * <p>
 * CountDownLatch 是 Java 并发工具包（java.util.concurrent）中的一个同步辅助类。它允许一个或多个线程等待，直到在其他线程中执行的一组操作完成。
 * <p>
 * CountDownLatch 是一种灵活的同步工具，适用于需要等待多个线程完成任务的场景。
 * •	它的核心思想是通过计数器的减法操作，实现主线程对其他线程的等待协调。通过 await() 和 countDown() 方法，可以方便地控制线程执行的顺序和依赖关系。
 * <p>
 * <p>
 * <p>
 * 与 CountDownLatch 相反的同步工具是 CyclicBarrier。虽然它们都有协调多个线程的功能，但使用方式和目的不同：
 * <p>
 * 1.	CountDownLatch:
 * •	用于一个或多个线程等待其他多个线程完成某个操作。每当一个线程完成任务后，计数器减 1，所有线程完成后，计数器变为 0，等待的线程才能继续执行。
 * •	它是一次性的，计数器到达零后无法重置。
 * 2.	CyclicBarrier:
 * •	用于多个线程相互等待，直到所有线程都到达某个同步点时再继续执行。它允许线程在执行到某个点时互相等待，直到所有线程都到达该点，再一起继续执行。
 * •	它是可重复使用的（“循环的”），一组线程继续执行后，计数器会被重置，可以再次使用。
 * <p>
 * <p>
 * 生活实际场景：
 * 打扫办公室，等所有的人出去之后，才能打扫。
 */
public class CountDownLauchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);


        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行结束");
                countDownLatch.countDown(); // 执行完一次减少一个，直至为0

            }, String.valueOf(i)).start();
        }

        countDownLatch.await();  // 等待所有线程执行结束

        System.out.println("执行结束");

    }
}
