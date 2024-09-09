package com.axon.java.stack.juc.atomic;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar {

    public volatile Boolean isInit = Boolean.FALSE;


    AtomicReferenceFieldUpdater updateUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    public void init(MyVar myVar) throws InterruptedException {
        if (updateUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + "start init");

            Thread.sleep(2000);

            System.out.println(Thread.currentThread().getName() + "init over");
        } else {
            System.out.println(Thread.currentThread().getName() + "已经有对应的线程进行初始化工作了 ");
        }

    }
}


public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {

        MyVar myVar = new MyVar();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {

            new Thread(() -> {
                try {
                    myVar.init(myVar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();

        }

        System.out.println("运行结束");

    }
}
