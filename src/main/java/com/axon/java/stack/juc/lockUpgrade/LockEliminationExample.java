package com.axon.java.stack.juc.lockUpgrade;


/**
 * 锁消除案例：
 * <p>
 * 锁消除是指在编译时，JVM 检测到代码中的锁对象不会被多个线程共享，从而消除掉同步块的锁操作。常见的场景是局部变量或不可逃逸的对象。
 */
public class LockEliminationExample {

    public static void main(String[] args) {
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("锁消除案例");
        }
    }
}
