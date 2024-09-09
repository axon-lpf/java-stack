package com.axon.java.stack.juc.locksuport;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步块 (synchronized)：synchronized 用于确保线程安全，只有一个线程可以在任意时间持有锁对象 (lock)。
 * •	等待和通知：
 * •	wait() 让当前线程进入等待状态，同时释放所持有的锁。
 * •	notifyAll() 唤醒在该锁对象上等待的所有线程。
 * •	注意事项：必须在相同的锁对象上调用 wait() 和 notifyAll()，否则会导致异常。
 */
class SyncTest {

    private Object lock = new Object();

    public void run() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + "comme in.......");
                lock.wait();
                System.out.println((Thread.currentThread().getName() + " 结束了"));
            } catch (InterruptedException e) {

            } finally {
            }
        }
    }

    public void run2() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + "comme in.......");
                lock.notifyAll();
                System.out.println((Thread.currentThread().getName() + " 结束了"));
            } finally {
            }
        }
    }

}


/**
 * ReentrantLock: 是一种可重入锁，与 synchronized 相比，它提供了更灵活的锁操作，支持条件变量 Condition。
 * •	Condition: 用于线程间通信，可以实现更精确的线程控制，如定向唤醒特定线程。
 * •	condition.await()：当前线程等待，并释放锁。
 * •	condition.signal()：唤醒等待队列中的一个线程（signalAll() 唤醒所有等待线程）。
 * •	注意：必须在 lock.lock() 和 lock.unlock() 配对使用的情况下调用 condition.await() 和 condition.signal()。
 */
class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "come in  等待了.......");
            condition.await();
            System.out.println((Thread.currentThread().getName() + " 等待结束了"));
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }

    }

    public void run2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "comme in.......");
            condition.signal();
            System.out.println((Thread.currentThread().getName() + " 开始通知了"));
        } finally {
            lock.unlock();
        }
    }

}


/**
 * 分析：
 * <p>
 * •	LockSupport: 是一种更底层的线程阻塞工具，相比 wait/notify 和 Condition，它更为灵活。
 * •	LockSupport.park()：挂起当前线程，类似于进入等待状态。
 * •	LockSupport.unpark(Thread thread)：唤醒指定线程。
 * •	特点:
 * •	park() 和 unpark() 不需要配合锁使用，可以在任意位置调用。
 * •	unpark() 操作可以先于 park() 调用，这样被 unpark() 的线程会立即返回。
 */
class LockSupportTest {

    public void run() {

        System.out.println(Thread.currentThread().getName() + "come in  等待了.......");
        LockSupport.park();
        System.out.println((Thread.currentThread().getName() + " 等待结束了"));

    }

    public void run2(Thread thread) {
        System.out.println(Thread.currentThread().getName() + "comme in.......");
        LockSupport.unpark(thread);
        System.out.println((Thread.currentThread().getName() + " 开始通知了"));

    }

}


public class LockSupportTestDemo {

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        // sync同步锁
        SyncTest syncTest = new SyncTest();
        // reentrantLock
        ReentrantLockTest reeantLockTest = new ReentrantLockTest();

        LockSupportTest lockSupportTest = new LockSupportTest();

        Thread t1 = new Thread(() -> {
            //syncTest.run();
            // reeantLockTest.run();
            lockSupportTest.run();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "AAA");

        t1.start();
        Thread.sleep(200);

        new Thread(() -> {
            //syncTest.run2();
            //reeantLockTest.run2();
            lockSupportTest.run2(t1);
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

        System.out.println(Thread.currentThread().getName() + "运行结束了");

    }
}
