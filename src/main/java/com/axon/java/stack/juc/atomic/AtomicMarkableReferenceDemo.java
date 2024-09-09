package com.axon.java.stack.juc.atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {


    static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {

        CyclicBarrier cycleBarrier = new CyclicBarrier(2);

        new Thread(() -> {

            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "获取的marke默认值是" + marked);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicMarkableReference.compareAndSet(100, 200, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "当前获取的值是" + atomicMarkableReference.getReference());
            try {
                cycleBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "获取的marke默认值是" + marked);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicMarkableReference.compareAndSet(100, 300, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "当前获取的值是" + atomicMarkableReference.getReference());

            try {
                cycleBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("运行结束");
    }
}
