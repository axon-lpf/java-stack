package com.axon.java.stack.juc.Volatile;


class MyNumber {

   volatile int number = 10;

    public void addTo1205() {
        this.number = 1205;
    }

}

/**
 * JMM  test
 *
 * volatile 保证线程之间的可见性， 线程之间需要通讯。
 *
 * 每个线程都会动主物理内存copy 一份变量，到自己的工作空间，即栈空间，然后修改完毕后，在提交到主物理内存。
 *
 * 内存屏障
 *
 * <p>
 * 可见性（通知机制）
 */
public class JMMTest {

    public static void main(String[] args) {
        System.out.println("come in");
        MyNumber myNumber = new MyNumber();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myNumber.addTo1205();
            System.out.println("修改完成了，数字是" + myNumber.number);
        }, "aaa").start();

        while (myNumber.number == 10) {

        }

        System.out.println("应用程序结束");

    }


}
