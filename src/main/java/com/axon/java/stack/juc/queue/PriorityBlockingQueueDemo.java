package com.axon.java.stack.juc.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // Producer thread
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.put(5 - i);
                System.out.println("Produced: " + (5 - i));
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