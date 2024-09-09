package com.axon.java.stack.juc.queue;


import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用锁构建一个队列
 */
class MyLockQueue {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private volatile int number = 0;

    public void putQueue() {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "推进当前数量" + number);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void getQueue() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "获取当前的数值为" + number);
            number--;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 不使用锁的方式，构建一个队列
 */

class MyBlockQueue {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private volatile boolean flag = true;

    private BlockingQueue<Integer> queue = null;

    public MyBlockQueue(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void product() throws InterruptedException {
        Integer value = null;
        while (flag) {
            value = atomicInteger.incrementAndGet();
            boolean offer = queue.offer(value, 2, TimeUnit.SECONDS);
            if (offer) {
                System.out.println(Thread.currentThread().getName() + "获得值" + value + "进入队列");
            } else {
                System.out.println(Thread.currentThread().getName() + "插入队列失败");
            }
            Thread.sleep(1000);
        }
    }

    public void consumer() throws InterruptedException {
        while (flag) {
            Integer take = queue.take();
            if (Objects.isNull(take)) {
                flag = false;
            } else {
                System.out.println(Thread.currentThread().getName() + "获取的值为" + take);
            }
        }
    }

    public void stop() {
        flag = false;
    }


}


/**
 * 简易版本的消息队列
 *
 *
 * 	•	MyLockQueue：通过 ReentrantLock 和 Condition 实现了基本的生产者-消费者模式，适合学习和理解锁和条件变量的使用。
 * 	•	MyBlockQueue：通过 BlockingQueue 实现了更简洁和高效的生产者-消费者模式，适合实际应用场景。
 * 	•	代码结构清晰：展示了两种不同的实现方式，并通过主类演示了它们的使用。
 * 	•	改进空间：可以考虑优化 MyLockQueue 的性能，减少 signalAll 的使用；在实际应用中，可以结合使用更多的高级队列实现。
 *
 *
 */
public class MyBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        MyLockQueue myQueue = new MyLockQueue();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                myQueue.putQueue();
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                myQueue.getQueue();
            }
        }, "BBB").start();


        Thread.sleep(5000);
        System.out.println(" 执行结束");


        MyBlockQueue myBlockQueue = new MyBlockQueue(new ArrayBlockingQueue<Integer>(3));
        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                try {
                    myBlockQueue.product();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "ccc").start();

        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                try {
                    myBlockQueue.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "ddd").start();


        Thread.sleep(10000);
        myBlockQueue.stop();
        System.out.println(" 执行结束");
    }
}
