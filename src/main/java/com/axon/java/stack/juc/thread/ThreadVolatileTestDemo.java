package com.axon.java.stack.juc.thread;



public class ThreadVolatileTestDemo {

    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (flag) {
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
            flag = false;
        }).start();


        Thread.sleep(3000);
        System.out.println(" 运行结束");

    }


}
