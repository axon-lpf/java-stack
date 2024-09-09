package com.axon.java.stack.juc.threadUtils;

import java.util.concurrent.Semaphore;

/**
 * 在多线程编程中，如果有多个线程需要争夺有限数量的资源，通常使用 信号量（Semaphore） 作为工具类来管理这种资源争夺。
 * <p>
 * Semaphore 介绍
 * <p>
 * 1.	概念:
 * •	Semaphore 是一个计数信号量。它管理一组资源的访问权限，可以控制同时访问特定资源的线程数量。每个信号量都有一个计数器，表示当前可用资源的数量。
 * 2.	主要方法:
 * •	acquire(): 获取一个许可，如果没有许可可用，则线程进入等待状态。
 * •	release(): 释放一个许可，将其归还给信号量。
 * 3.	使用场景:
 * •	控制对资源的访问，如数据库连接池、文件读写、网络连接等。
 * •	限制访问共享资源的线程数量，以防止系统过载。
 * <p>
 * <p>
 * 这个使用 Semaphore 控制资源访问的案例可以对应到一个现实生活中的停车场管理场景。设想一个小型停车场只有三个停车位，而有六辆车想要停进去，这时就需要一种机制来控制车的进出，确保不会超出停车场的容量。
 * <p>
 * 生活场景示例
 * <p>
 * 场景描述
 * <p>
 * 一个小型停车场只有三个车位（资源），而有六辆车（线程）需要停车。每次只能有三辆车停在停车场内，其它车辆必须等待，直到有车位空出来（资源释放）。
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {

            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "开始处理了");
                    Thread.sleep(300);
                    System.out.println(Thread.currentThread().getName() + "处理结束");
                } catch (InterruptedException e) {
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
        System.out.println("运行结束");

    }
}
