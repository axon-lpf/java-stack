package com.axon.java.stack.juc.reference;

/**
 * Reference）。它们在垃圾回收机制中的行为各不相同。下面是对这些引用类型的详细解释、使用场景以及代码案例。
 * <p>
 * 1. 强引用（Strong Reference）
 * <p>
 * 定义：
 * <p>
 * 强引用是 Java 中最常见的引用类型。只要一个对象有强引用存在，垃圾回收器绝不会回收该对象。
 * <p>
 * 使用场景：
 * <p>
 * 通常情况下，程序中的对象都是强引用。如果一个对象是强引用，说明它非常重要，系统不会轻易回收它。
 */
public class StrongReferenceExample {


    public static void main(String[] args) {
        // 创建一个强引用对象
        Object strongRef = new Object();

        // 调用垃圾回收器，但 strongRef 指向的对象不会被回收
        System.gc();

        // 强引用对象仍然存活
        System.out.println("Strong reference is still alive: " + strongRef);
    }

}
