package com.axon.java.stack.juc.threadlocal;


/**
 *
 * 1. 强引用（Strong Reference）
 *
 * 定义： 强引用是 Java 中最常见的引用类型。任何通过直接赋值创建的对象引用都是强引用。
 *
 * 特点：
 *
 * 	•	只要强引用存在，垃圾回收器就不会回收被引用的对象。
 * 	•	对象即使在内存不足时也不会被回收，可能会导致 OutOfMemoryError。
 *
 * 使用场景：
 *
 * 	•	默认情况： 所有普通的对象引用都是强引用，比如 Object obj = new Object();。
 * 	•	关键数据： 用于关键数据的持有，比如核心业务逻辑中的数据结构、配置对象等，必须确保这些对象不会被垃圾回收。
 *
 *
 */
public class ReferenceDemo {


    public static void main(String[] args) {
        MyReferenceObject myReferenceObject = new MyReferenceObject();

        System.out.println("GC回收参数设置  before object,内存够用" + myReferenceObject);

        myReferenceObject = null;

        System.gc();

        System.out.println("GC回收参数设置 after object，内存够用" + myReferenceObject);
    }
}
