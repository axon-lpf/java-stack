package com.axon.java.stack.juc.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


class DelayedElement implements Delayed {
    private final String name;
    private final long delayTime;
    private final long startTime;

    public DelayedElement(String name, long delayTime) {
        this.name = name;
        this.delayTime = delayTime;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime + delayTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return name;
    }
}

class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedElement> queue = new DelayQueue<>();

        queue.put(new DelayedElement("Task1", 3000));
        queue.put(new DelayedElement("Task2", 5000));

        // Consumer thread
        new Thread(() -> {
            try {
                while (true) {
                    DelayedElement element = queue.take();
                    System.out.println("Taken: " + element);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}