package com.axon.java.stack.juc.Volatile;


/**
 *   这块代码flag 没有使用Volatile， 没有保证线程的可见性。所以main线程修改之后，  AAA线程并不知晓
 */
public class VolatileTestDemo {

    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "当前flag的值" + flag);
        },"AAAA").start();

        Thread.sleep(2000);
        flag = false;
        System.out.println(Thread.currentThread().getName() + "修改flag的值为" + flag);

    }
}
