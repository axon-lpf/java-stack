package com.axon.java.stack.juc.queue;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueDemo {
    public static void main(String[] args) {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();

        // Producer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.transfer(i);
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