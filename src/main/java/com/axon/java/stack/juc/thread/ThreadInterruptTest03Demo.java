package com.axon.java.stack.juc.thread;

public class ThreadInterruptTest03Demo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程中断了，中断标志位 " + Thread.currentThread().isInterrupted());
                    break;
                } else {
                    for (int i = 0; i < 300; i++) {
                        System.out.println(Thread.currentThread().getName() + " 正在运行 " + i);
                    }
                }
            }

        }, "AAA");

        t1.start();

        Thread.sleep(10);

        new Thread(() -> {
            t1.interrupt();
            System.out.println("已经设置标志位中断了");

        }, "BBB").start();

        System.out.println("运行结束了");
    }
}
