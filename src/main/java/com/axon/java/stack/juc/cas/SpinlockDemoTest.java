package com.axon.java.stack.juc.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 * CAS  底层是用CAS机制
 *
 * 自旋锁实现总结
 *
 * 	1.	基本原理：
 * 	•	自旋锁是一种忙等待的锁，当一个线程尝试获取锁时，如果锁已经被其他线程持有，当前线程不会被阻塞，而是持续尝试获取锁。这种忙等待的过程称为“自旋”。
 * 	2.	代码说明：
 * 	•	MyLock 类使用 AtomicReference<Thread> 来保存当前持有锁的线程。AtomicReference 提供了原子操作方法，可以保证多线程环境下操作的安全性。
 * 	•	lock() 方法：尝试通过 compareAndSet(null, Thread.currentThread()) 方法将 atomicReference 设置为当前线程。如果 atomicReference 为 null，则说明锁空闲，设置成功即表示锁被当前线程持有；否则，自旋等待直到成功获取锁。
 * 	•	unlock() 方法：通过 compareAndSet(Thread.currentThread(), null) 方法将 atomicReference 置为 null，表示当前线程释放锁。
 * 	3.	优缺点：
 * 	•	优点：自旋锁避免了线程上下文切换的开销，适合锁定时间短的场景。
 * 	•	缺点：如果锁的持有时间较长，自旋会浪费大量 CPU 资源，导致系统性能下降。
 * 	4.	注意事项：
 * 	•	自旋锁通常只适用于多核处理器环境，因为单核处理器中自旋等待不会让出 CPU 资源，导致其它线程无法运行。
 * 	•	由于自旋锁的不可重入性，同一线程如果尝试多次获取锁，会导致死锁。
 *
 * 代码准确性和优化建议
 *
 * 你的代码已经准确地实现了自旋锁的基本功能和原理。以下是一些可能的优化和改进建议：
 *
 * 	1.	增加超时机制：
 * 为了防止某些情况下锁无法释放（如发生死锁等问题），可以增加超时机制，避免线程无限制自旋等待。
 * 	2.	扩展功能：
 * 可以增加计数器等扩展功能来支持重入锁的场景。
 * 	3.	性能优化：
 * 在多核处理器上，自旋锁的效率高于阻塞锁，但在单核处理器或高并发场景中可能导致性能问题，因此使用时需谨慎。
 *
 */

class MyLock {


    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 获取锁
     */
    public void lock() {
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
        }
        System.out.println(Thread.currentThread().getName() + "枷锁成功");
    }

    /**
     * 释放锁的
     */
    public void unlock() {
        atomicReference.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + "释放锁成功");
    }


}

public class SpinlockDemoTest {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                myLock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "正在执行中");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myLock.unlock();
                }

            }, String.valueOf(i)).start();
        }

    }
}
