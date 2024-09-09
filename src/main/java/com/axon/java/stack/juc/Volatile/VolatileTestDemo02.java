package com.axon.java.stack.juc.Volatile;


/**
 *  这块代码 flag 使用volatile去修饰，保证了线程之间的可见性。
 *  其底层原理： 每个线程都会从主内存copy一份值到线程自己的内存工作空间修改， 被volatile修饰后， 会立马提交到主内存，并通知其他线程，已经修改了。 并获取最新值。
 *
 */
public class VolatileTestDemo02 {

    private static volatile  boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "当前flag的值" + flag);
        }).start();

        Thread.sleep(2000);
        flag = false;
        System.out.println(Thread.currentThread().getName() + "修改flag的值为" + flag);

    }
}
