package com.axon.java.stack.juc.threadlocal;

import java.util.Random;

class House {
    int saleCount;

    /**
     * 使用同步锁块区增加
     */
    public synchronized void saleHouse() {
        ++saleCount;
    }

    /**
     * 使用线程池去处理
     */
    ThreadLocal<Integer> saleNums = ThreadLocal.withInitial(() -> 0);

    public void saleHouseThreadLocal() {
        saleNums.set(saleNums.get() + 1);
    }
}


/**
 *
 * ThreadLocal 并不解决线程问共享数据的问题
 * ThreadLocal 适用于变量在线程间隔离且在方法间共享的场景
 * ThreadLocal通过隐式的在不同线程内创建独立实例副本避免了实例线程安全的问题每个线程持有一个只属于自己的专属Map并维护了ThreadLocal对象与具体实例的映射，
 * 该Map由于只被持有它的线程访问，故不存在线程安全以及锁的问题
 * ThreadLocalMap的Entry对ThreadLocal的引用为弱引用，避免了ThreadLocal对象无法被回收的问题都会通过expungeStaleEntry,cleanSomeSlots,replaceStaleEntry这三个方法回收键为 null 的 Entry对象的值（即为具体实例）以及 Entry 对象本身从而防止内存泄漏，属于安全加固的方法群雄逐鹿起纷争，人各一份天下安


 1. ThreadLocal 内存泄漏的问题如何解决？
 ThreadLocal 的内存泄漏问题主要发生在使用线程池的场景中，因为线程池中的线程是重复使用的。如果 ThreadLocal 中的变量没有及时清理，可能会导致内存泄漏。以下是 ThreadLocal 内存泄漏问题的原因及解决方法：

 原因
 1.	ThreadLocalMap 的生命周期与线程一致：
        ThreadLocal 变量会存储在 ThreadLocalMap 中，而这个 ThreadLocalMap 是存储在线程中的，因此它的生命周期与线程一致。如果使用线程池，由于线程是长时间存在的，ThreadLocalMap 也会长时间存在。如果 ThreadLocal 对象被设置但没有被清理，它们所引用的对象可能永远不会被垃圾回收，从而导致内存泄漏。
 2.	ThreadLocalMap 的键是弱引用：
    在 ThreadLocalMap 中，ThreadLocal 作为键存储，它们被实现为弱引用（WeakReference）。如果 ThreadLocal 对象本身被垃圾回收了，但 ThreadLocalMap 中的值仍然存在，则该值无法被回收，导致内存泄漏。

 解决方法

 1.	显式调用 remove() 方法：
    使用 ThreadLocal 后，在任务完成时显式调用 remove() 方法。这会删除 ThreadLocalMap 中当前线程的 ThreadLocal 键和值，确保引用能够及时释放。
    threadLocal.remove();

 2.	避免使用重用线程的场景：
    如果能够确定线程在使用完 ThreadLocal 后会终止，或者 ThreadLocal 的生命周期只与当前任务绑定，则不会存在内存泄漏问题。因此，可以通过避免在长时间运行的线程（如线程池中的线程）中使用 ThreadLocal 来减少内存泄漏的可能性。
 3.	在最终代码块中清理：
    为了保证 ThreadLocal 在所有情况下都能被清理，建议将 remove() 调用放在 try-finally 结构中：
     try {
     // 使用 ThreadLocal
     threadLocal.set(someValue);
     } finally {
     threadLocal.remove();
     }
 4.	设计合理的 ThreadLocal 变量使用范围：
     限制 ThreadLocal 变量的使用范围，不要让它们在不必要的上下文中存在太久。避免将 ThreadLocal 变量用作全局变量或在不同上下文之间传递。

 总结：
    ThreadLocal 内存泄漏的问题通常出现在长时间运行的线程（如线程池）中，因为 ThreadLocalMap 的生命周期与线程一致。在使用 ThreadLocal 时，确保在任务完成后显式调用 remove() 方法清理资源，或者在最终代码块中清理 ThreadLocal。这种方式可以有效避免内存泄漏问题。


 ThreadLocal 中的 key 是实现了弱引用，垃圾回收时，就立马回收， 但是回收之后， ThreadLocalMap中的键就变为null值，  但是value 是强引用， 不会被清理， 引用




 问题：

 1.	ThreadLocalMap 中的键和值：
 •	ThreadLocalMap 使用 ThreadLocal 对象作为键，并将与之关联的值存储在 ThreadLocalMap.Entry 中。
 •	ThreadLocalMap.Entry 是 ThreadLocal 的内部类，继承自 WeakReference<ThreadLocal<?>>，所以 Entry 中的键（即 ThreadLocal）是一个弱引用。
 2.	弱引用的行为：
 •	弱引用的对象在垃圾回收时如果没有其他强引用存在，就会被回收。当一个 ThreadLocal 对象没有强引用时，它会被垃圾回收。
 •	一旦 ThreadLocal 被垃圾回收，ThreadLocalMap 中对应的键就变成 null，但是与之关联的值依然是一个强引用，这个值不会被垃圾回收。
 3.	内存泄漏的风险：
 •	ThreadLocalMap 的生命周期与线程相同，只要线程还在运行，ThreadLocalMap 就不会被回收。
 •	如果 ThreadLocal 被垃圾回收，其对应的值仍然存在于 ThreadLocalMap 中，但由于没有对应的 ThreadLocal 键，这个值不会再被访问，但也不会被垃圾回收。这就导致了内存泄漏，特别是在长生命周期的线程（如线程池中的工作线程）中。

 解决方法

 为了防止这种内存泄漏，ThreadLocalMap 在执行 set()、get() 或 remove() 操作时，会进行额外的清理工作：

 1.	get() 操作：
 •	在执行 get() 操作时，如果发现某个键为 null（即 ThreadLocal 已被垃圾回收），ThreadLocalMap 会清除这个键对应的值。
 2.	set() 操作：
 •	类似地，在执行 set() 操作时，也会检查并清理无效的键值对。
 3.	remove() 操作：
 •	用户可以手动调用 ThreadLocal.remove() 方法清除当前线程中的线程局部变量。这不仅会移除 ThreadLocalMap 中的值，还能有效避免内存泄漏。

 总结

 虽然 ThreadLocal 本身使用了弱引用来避免线程间的强耦合，但 ThreadLocalMap 中的值仍然是强引用。由于 ThreadLocalMap 的生命周期与线程一致，如果不及时清理被垃圾回收的 ThreadLocal 对象对应的值，就会造成内存泄漏。通过在 get()、set() 和 remove() 操作中进行清理，可以有效地防止这种内存泄漏。


大白话理解：
 一个线程拥有自己的ThreadLoalMap，ThreadLoalMap 中的key 对应的是ThreadLocal， 当ThreadLocal被回收了，那当前线程中的ThreadLoalMap的可以就变为null, 但是value 存在， 由于线程在线程池下是复用的，
 线程是没有被释放的，那ThreadLoalMap 一种存在，没有被垃圾回收， 那这样的情况，则容易造成内存泄漏。


 chatGpt解释：
 1.	ThreadLocalMap 的生命周期：
 •	每个线程都有一个 ThreadLocalMap，用于存储该线程所有的 ThreadLocal 变量。
 •	ThreadLocalMap 的键是 ThreadLocal 对象，值是与之关联的线程局部变量。
 2.	ThreadLocal 对象被回收：
 •	当一个 ThreadLocal 对象不再被引用时，它会变成垃圾对象，可以被垃圾回收。
 •	但是，线程池中的线程是被复用的。即使 ThreadLocal 对象被回收了，线程池中的线程仍然持有其 ThreadLocalMap。
 3.	ThreadLocalMap 的键变为 null：
 •	如果 ThreadLocal 对象被回收，ThreadLocalMap 中的键变为 null，但相应的值（value）仍然存在，直到 ThreadLocalMap 被垃圾回收。
 •	这会导致内存泄漏，因为虽然 ThreadLocal 对象已经不再被使用，但 ThreadLocalMap 中仍然保持对其对应值的引用，从而阻止了垃圾回收。





 */
public class TheadLocalDemo {

    public static void main(String[] args) throws InterruptedException {

        House house = new House();

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                for (int j = 0; j < size; j++) {
                    house.saleHouse();
                    house.saleHouseThreadLocal();
                }
                System.out.println(Thread.currentThread().getName() + "当前线程共卖出" + house.saleNums.get());
            }, String.valueOf(i).toString()).start();
        }

        Thread.sleep(2000);
        System.out.println("saleCount共计数量" + house.saleCount);

        //System.out.println("HouseThreadLocal共计数量" + house.saleNums.);


    }
}
