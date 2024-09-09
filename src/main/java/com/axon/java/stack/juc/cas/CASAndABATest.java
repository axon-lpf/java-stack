package com.axon.java.stack.juc.cas;

/**
 * CAS
 * 1.什么是CAS?
 * 1.CAS即比较并交换，在并发情况下，每个线程从主内存copy一份原始数据到自己工作空间去修改，修改完成之后提交到主内存，提交时，会与主内存的值进行比较，比较当初拷贝的值是否与主内存的一致，一致则修改，
 * 不一致则继续拷贝修改，直至成功为止。 底层do while循环。
 * 2.底层是使用unsafe类， 调用的是汇编类型的接口，即jvm底层的方法，保证了原子性。
 * 3.CAS是一种自旋锁。
 * 优缺点：和枷锁的情况比较，提高了并发性，但长时间自旋，占用cpu。
 * 4.什么是ABA问题？ 如何解决
 * ABA问题就是：
 * 当前主物理内存的值是A ,  线程一和线程二各拷贝了一份值到自己的工作空间去修改为B。 由于cpu调度的策略， 可能线程A修改完成需要 10ms, 线程二修改需要20ms.
 * 那此时由于线程一修改速度过快，可能修改完提交到主内存后，主内存的值变B， 然后线程A又去修改为A, 那此时线程二去修改，也修改成功了。但是线程二是不知道这中间的变化的。 可能导致程序bug .
 * <p>
 * 解决方案：  AtomicStampedReference  在修改时添加版本号
 * AtomicStampedReference  stampedReference=new AtomicStampedReference();
 * <p>
 * <p>
 * 你的总结基本上准确地描述了 CAS (Compare-And-Swap) 机制及其相关问题，特别是 ABA 问题。以下是一些进一步的解释和细化，以确保对概念的准确理解：
 * <p>
 * CAS（Compare-And-Swap）
 * <p>
 * 1.	基本概念：CAS 是一种用于实现多线程同步的机制，它通过比较一个内存位置的当前值和一个期望的值，如果它们相等，则更新该内存位置为新的值。这个过程是原子的，即在多个线程并发操作时，CAS 能够保证只有一个线程能够成功修改值，避免了数据竞争。
 * 2.	工作原理：
 * •	线程从主内存中获取一个变量的值，并保存在本地（工作内存）。
 * •	线程尝试使用 CAS 指令将这个值更新为一个新值。
 * •	如果主内存中的值在这段时间内没有被其他线程修改，则更新成功；否则，更新失败，线程可以选择重试（一般是通过自旋实现）。
 * 3.	底层实现：CAS 通常由硬件提供支持，比如在 x86 架构上使用 cmpxchg 指令。Java 中通过 Unsafe 类和 JNI（Java Native Interface）调用底层的硬件支持，保证了操作的原子性。
 * 4.	优缺点：
 * •	优点：避免了锁的开销，提高了并发性能，适合高并发场景。
 * •	缺点：在高竞争情况下，CAS 可能导致大量的重试（自旋），从而占用大量 CPU 资源。此外，它也无法解决 ABA 问题。
 * <p>
 * ABA 问题
 * <p>
 * 1.	定义：ABA 问题是指在一个线程执行 CAS 操作时，变量的值被另一个线程从 A 改为 B，然后又改回 A。尽管值看起来没有变化，但实际上已经经历了变化，这可能导致逻辑错误。
 * 2.	示例：假设线程 1 读取到一个值 A，准备更新为 B。同一时间，线程 2 将这个值从 A 改为 B，又改回 A。当线程 1 进行 CAS 操作时，看到的值依然是 A，因此认为可以成功更新，但实际情况中，这个值已经被其他线程修改过了。
 * 3.	解决方案：使用带版本号的原子引用，例如 AtomicStampedReference。它在 CAS 操作时不仅比较值，还比较一个版本号，确保即使值相同，但版本号不同，也会检测到变化。
 * <p>
 * 代码示例总结
 * <p>
 * 你的代码正确演示了 ABA 问题的发生和 CAS 的基本使用。你创建了两个线程，分别模拟了值的更改和 ABA 问题的发生。注意事项：
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA 问题案例
 */
public class CASAndABATest {

    public static void main(String[] args) throws InterruptedException {
        // ABATest();
        ABASuccessTest();
    }

    private static void ABASuccessTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference(10, 1);
        new Thread(() -> {
            int stamp = atomicReference.getStamp();
            boolean andSet = atomicReference.compareAndSet(10, 20, 1, stamp + 1);
            System.out.println(andSet);
            System.out.println("1当前的值是" + atomicReference.getReference());
            stamp = atomicReference.getStamp();
            boolean compareAndSet = atomicReference.compareAndSet(20, 10, stamp, stamp + 1);
            System.out.println(compareAndSet);
            System.out.println("2当前的值是" + atomicReference.getReference());
            latch.countDown();
        }, "AAA").start();

        Thread.sleep(1000);

        new Thread(() -> {
            boolean compareAndSet = atomicReference.compareAndSet(10, 20, 1, 2);
            System.out.println(compareAndSet);
            System.out.println("3当前的值是" + atomicReference.getReference());
            latch.countDown();
        }, "AAA").start();

        latch.await();
        System.out.println("运行结束");
    }

    private static void ABATest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicReference<Integer> atomicReference = new AtomicReference(10);
        new Thread(() -> {
            boolean andSet = atomicReference.compareAndSet(10, 20);
            System.out.println(andSet);
            System.out.println("1当前的值是" + atomicReference.get());
            boolean compareAndSet = atomicReference.compareAndSet(20, 10);
            System.out.println(compareAndSet);
            System.out.println("2当前的值是" + atomicReference.get());
            latch.countDown();
        }, "AAA").start();

        Thread.sleep(1000);

        new Thread(() -> {
            boolean compareAndSet = atomicReference.compareAndSet(10, 20);
            System.out.println(compareAndSet);
            System.out.println("3当前的值是" + atomicReference.get());
            latch.countDown();
        }, "AAA").start();

        latch.await();
        System.out.println("运行结束");
    }
}
