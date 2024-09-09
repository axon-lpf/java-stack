package com.axon.java.stack.juc.thread;


import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadAtomTestDemo {

    private static volatile AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (flag.get()) {
                System.out.println(Thread.currentThread().getName() + "正在打印");
            }
            System.out.println("跳出循环了。。。。。。。。。。。。。。。。。。");
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            flag.set(false);
        }).start();

        Thread.interrupted();
        Thread.sleep(3000);
        System.out.println(" 运行结束");

    }


}
