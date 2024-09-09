package com.axon.java.stack.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);


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