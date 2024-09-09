package com.axon.java.stack.juc.lockUpgrade;

/**
 * 锁粗化案例
 *  锁粗化是指将多个紧密相连的、对同一对象进行操作的同步块合并成一个更大的同步块，从而减少锁的频繁获取和释放。
 */
public class LockCoarseningExample {

    private static Object lock = new Object();

    public static void main(String[] args) {

        synchronized (lock) {
            System.out.println("锁粗化1");
        }

        synchronized (lock) {
            System.out.println("锁粗化2");
        }

        synchronized (lock) {
            System.out.println("锁粗化3");
        }

    }
}
