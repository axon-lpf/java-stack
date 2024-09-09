package com.axon.java.stack.juc.thread;


/**
 * interrupted(): 返回中断状态后， 清除状态
 *
 * chatGpt 解释总结：
 *
 * 	•	Thread.interrupted() 是一个静态方法，用于检查当前线程是否被中断，同时会清除线程的中断状态。换句话说，调用 Thread.interrupted() 后，如果线程之前被中断，它会返回 true，但中断状态会被清除，所以后续调用 Thread.interrupted() 会返回 false。
 * 	•	Thread.currentThread().interrupt(); 用于设置当前线程的中断状态。
 *
 * 基于你的代码：
 *
 * 	1.	System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted());  // false
 * 这条语句会检查当前线程的中断状态，并返回 false，因为当前线程没有被中断。
 * 	2.	System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted());  // false
 * 这次检查仍然返回 false，因为中断状态已经在上一次调用时被清除或没有被设置。
 * 	3.	Thread.currentThread().interrupt();
 * 这行代码设置当前线程的中断状态。
 * 	4.	System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted());  // true
 * 这次检查会返回 true，因为线程刚刚被中断。
 * 	5.	System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted());  // false
 * 因为前一次调用 Thread.interrupted() 清除了中断状态，所以这次会返回 false。
 *
 * 总结：Thread.interrupted() 会返回当前线程的中断状态，同时清除这个状态。每次调用 Thread.interrupted() 后，如果返回 true，下一次调用它会返回 false。
 *
 *
 * Thread.interrupted()  和   Thread.currentThread().isInterrupt()  一个是实例方法， 一个是静态法，  底层代码是通过传true 或false来是否确认清楚状态。
 */
public class ThreadInterruptedDemo {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted());  //false
        System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted()); // false
        System.out.println(",................");
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted()); //true
        System.out.println(Thread.currentThread().getName() + "...." + Thread.interrupted()); // false
    }
}
