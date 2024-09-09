package com.axon.java.stack.juc.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        // Producer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Consumer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Integer value = queue.take();
                    System.out.println("Consumed: " + value);
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}