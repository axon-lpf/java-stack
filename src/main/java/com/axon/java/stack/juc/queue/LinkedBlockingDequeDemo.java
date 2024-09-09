package com.axon.java.stack.juc.queue;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeDemo {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(10);

        // Producer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    deque.putFirst(i);
                    System.out.println("Produced at head: " + i);
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
                    Integer value = deque.takeLast();
                    System.out.println("Consumed from tail: " + value);
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}