package com.axon.java.stack.juc.queue;


import java.util.concurrent.*;

/**
 * BlockArraryListDemo
 *
 * ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>();
 * ArrayBlockingQueue 是一个基于数组的有界阻塞队列，它按 FIFO（先进先出）原则进行排序。由于是有界的，它具有固定的容量，因此它不会无限制地增长。
 *
 * LinkedBlockingQueue<Integer> linkedBlockingQeque = new LinkedBlockingQueue();
 * LinkedBlockingQueue 是一个基于链表的阻塞队列，通常用于生产者-消费者问题。它是一个无界队列（默认情况下），但可以指定容量。
 *
 * SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue();
 * SynchronousQueue 是一个特殊的队列，每个插入操作必须等待另一个线程的相应移除操作才能完成。这是一种“零容量”队列，每个插入操作都与移除操作相对应。
 *
 *DelayQueue<Integer>  delayQueue=new DelayQueue<>();  // 延迟队列
 * DelayQueue 是一个实现了 BlockingQueue 接口的队列，其中的元素在特定的延迟时间到期之前无法被取出。它用于需要在某个延迟时间后处理的任务。
 *
 * LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
 * LinkedBlockingDeque 是一个双端阻塞队列，支持在两端插入和移除元素。它是一个线程安全的双端队列，适用于需要在两端进行操作的场景。
 *
 * LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();
 * LinkedTransferQueue 是一个高效的无界阻塞队列，支持线程之间的高效数据传输。它提供了额外的 transfer 方法，可以用来实现生产者-消费者模式中的数据传输。
 *
 *PriorityBlockingQueue<Integer> priorityBlockingQueue=new PriorityBlockingQueue<>();  // 排序队列
 * PriorityBlockingQueue 是一个支持优先级排序的阻塞队列，它按照元素的优先级进行排序而不是按照 FIFO 顺序。队列中的元素必须实现 Comparable 接口，或者在创建队列时提供一个 Comparator。
 *
 *
 *
 */
public class BlockArraryListDemo {

    public static void main(String[] args) {


    }
}
