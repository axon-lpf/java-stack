package com.axon.java.stack.juc.thread;


/**
 * 中断状态的清除：
 * * 当线程执行一些阻塞操作（如 Thread.sleep()、wait() 或 join() 等）时，如果线程在等待期间被中断，这些操作会抛出 InterruptedException，并清除线程的中断状态。在这种情况下，再次调用 isInterrupted() 会返回 false。
 */
public class ThreadInterruptTest04Demo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程中断了，中断标志位" + Thread.currentThread().isInterrupted());
                    break;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    //y因为遇到了sleep()，会自动清除线程中断状态， 所以这里需要再次调用，否则会进入死循环
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在运行");
            }

        }, "AAA");

        t1.start();
        Thread.sleep(20);

        new Thread(() -> {
            t1.interrupt();
            System.out.println("已经设置标志位中断了");
        }, "BBB").start();

        System.out.println("运行结束了");

    }
}
