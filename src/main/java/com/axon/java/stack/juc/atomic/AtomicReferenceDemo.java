package com.axon.java.stack.juc.atomic;



import java.util.concurrent.atomic.AtomicReference;

class  ReferenceDemo{


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


public class AtomicReferenceDemo {


    public static void main(String[] args) {


        ReferenceDemo myLock = new ReferenceDemo();

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
