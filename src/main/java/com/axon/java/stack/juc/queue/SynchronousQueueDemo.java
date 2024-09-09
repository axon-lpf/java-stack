package com.axon.java.stack.juc.queue;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        // Producer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}