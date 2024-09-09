package com.axon.java.stack.juc;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    /**
     * 第一步
     *         final void lock() {
     *             if (compareAndSetState(0, 1))
     *                 setExclusiveOwnerThread(Thread.currentThread());
     *             else
     *                 acquire(1);
     *         }
     *
     *
     *      第二步
     *        public final void acquire(int arg) {
     *         if (!tryAcquire(arg) &&
     *             acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
     *             selfInterrupt();
     *     }
     *
     *
     *             final boolean nonfairTryAcquire(int acquires) {
     *             final Thread current = Thread.currentThread();
     *             int c = getState();
     *             if (c == 0) {
     *                 if (compareAndSetState(0, acquires)) {
     *                     setExclusiveOwnerThread(current);
     *                     return true;
     *                 }
     *             }
     *             else if (current == getExclusiveOwnerThread()) {
     *                 int nextc = c + acquires;
     *                 if (nextc < 0) // overflow
     *                     throw new Error("Maximum lock count exceeded");
     *                 setState(nextc);
     *                 return true;
     *             }
     *             return false;
     *         }
     *
     * @param args
     */
    public static void main(String[] args) {
        // 同时有三个线程处理的案例
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            Thread.sleep(10000);
            System.out.println("测试");
        } catch (Exception exception) {

        } finally {
            reentrantLock.unlock();

        }

    }
}
