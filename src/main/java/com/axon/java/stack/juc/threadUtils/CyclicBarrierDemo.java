package com.axon.java.stack.juc.threadUtils;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * CyclicBarrier:
 * 	•	用于多个线程相互等待，直到所有线程都到达某个同步点时再继续执行。它允许线程在执行到某个点时互相等待，直到所有线程都到达该点，再一起继续执行。
 * 	•	它是可重复使用的（“循环的”），一组线程继续执行后，计数器会被重置，可以再次使用。
 *
 * 	生活实际场景：
 * 	  打麻将三缺一，必须等到所有人到齐了之后才能打。
 *
 •	可重复使用: CyclicBarrier 可以在所有线程通过屏障后重新使用。
 •	同步点: 线程到达同步点时，必须等待其他线程也到达，才能继续执行。

 适用场景
 CyclicBarrier 适用于需要一组线程在同一时刻达到某个状态或点的情况，如多线程的分阶段计算、运动员准备比赛等。
 总结
 本示例展示了 CyclicBarrier 的基本使用方式，通过协调多个线程在一个同步点等待，确保它们在某个条件满足时同时继续执行。
 *
 *
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier  cycleBarrier = new CyclicBarrier(10,()->{
            System.out.println("所有的运动员都准备好了");
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行结束");
                try {
                    cycleBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
               // System.out.println("开始比赛了");
            },String.valueOf(i)).start();
        }

       // System.out.println("比赛结束");


    }
}
