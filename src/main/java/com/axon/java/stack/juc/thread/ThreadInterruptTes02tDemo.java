package com.axon.java.stack.juc.thread;

/**
 *
 * Thread 中  interrupt() 和 isInterrupted（）
 *
 * interrupt(): 中断线程执行
 * isInterrupted（）： 标志线程是否被中断过。
 *
 * 1.调用interrupt() , 线程不会立马结束，而是会去协商处理。
 * 2.调用interrupt() 后， isInterrupted() ，返回的状态是true, 但是该线程一旦运行结束就为flase。
 *  当线程被中断后，如果线程已经运行结束了，那么此时调用 isInterrupted() 会返回 false。
 *
 * 这是因为 isInterrupted() 返回的是当前线程的中断状态，但当线程结束时，JVM 会清除线程的中断状态。因此，即使在线程运行期间它曾被中断过，但一旦线程运行结束并终止后，再检查它的中断状态，结果会是 false。
 *
 *
 *  chatGpt总结：
 * 	1.interrupt() 方法：
 * 	•	interrupt() 方法不会立刻终止线程，而是向线程发送一个中断信号。线程在收到中断信号后，可以根据中断状态决定如何处理。通常情况下，线程会在下一个检查点或被阻塞的操作时响应中断信号。
 * 	2.isInterrupted() 方法：
 * 	•	isInterrupted() 方法用于检查线程是否被中断。如果线程被中断过，它将返回 true。
 * 	•	需要注意的是，isInterrupted() 不会清除线程的中断状态，即多次调用 isInterrupted() 后，只要线程未清除中断状态，返回的值依然是 true。
 * 	3.	中断状态的清除：
 * 	•	当线程执行一些阻塞操作（如 Thread.sleep()、wait() 或 join() 等）时，如果线程在等待期间被中断，这些操作会抛出 InterruptedException，并清除线程的中断状态。在这种情况下，再次调用 isInterrupted() 会返回 false。
 *
 */
public class ThreadInterruptTes02tDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 300; i++) {

                System.out.println("------------" + i);
            }

        }, "AAA");

        t1.start();
        System.out.println("t1线程的默认中断标识位" + t1.isInterrupted());
        Thread.sleep(2);
        t1.interrupt();
        System.out.println("t1线程设置中断后的识位" + t1.isInterrupted());

        System.out.println("运行结束了");
        //Thread.sleep(3000);

    }
}
