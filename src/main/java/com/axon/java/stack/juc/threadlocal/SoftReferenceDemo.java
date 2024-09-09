package com.axon.java.stack.juc.threadlocal;

import java.lang.ref.SoftReference;


/**
 *
 * 2. 软引用（SoftReference）
 *
 * 定义： 软引用在内存不足时会被回收，通常用于实现缓存。
 *
 * 特点：
 *
 * 	•	只有在内存不足时，垃圾回收器才会回收软引用的对象。
 * 	•	在内存充足的情况下，软引用对象可以长期存在。
 * 	•	get() 方法可以返回引用的对象，除非对象已经被回收。
 *
 * 使用场景：
 *
 * 	•	缓存： 用于实现内存敏感的缓存。如果内存充足，缓存对象会一直保留；如果内存不足，缓存对象会被回收释放内存空间。
 * 	•	图片缓存： 在 Android 开发中，常用于图片缓存，减少内存使用。
 *
 *
 *
 * 软引用通常用于实现内存敏感的缓存，当 JVM 内存不足时，会回收这些软引用对象。
 * 软引用对象在内存足够时不会被垃圾回收，softReference.get() 应该返回非空值。
 * 当内存不足时，软引用对象可能会被回收，softReference.get() 返回 null。
 * <p>
 * <p>
 * *  设置VM options  后在测试
 * * -Xms10m  -Xmx10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 */
public class SoftReferenceDemo {

    public static void main(String[] args) {

        //内存足够的情况下
        memeryNormal();

        System.out.println("开始执行内存不足的情况下");
        // 内存不足的情况下
        memeryOver();


    }

    private static void memeryNormal() {
        MyReferenceObject myReferenceObject = new MyReferenceObject();
        SoftReference<MyReferenceObject> softReference = new SoftReference<>(myReferenceObject);

        System.out.println("Before GC: Soft Reference get() - " + softReference.get());

        myReferenceObject = null;
        System.gc();

        System.out.println("After GC: Soft Reference get() - " + softReference.get());
    }

    private static void memeryOver() {
        MyReferenceObject myReferenceObject = new MyReferenceObject();
        SoftReference<MyReferenceObject> softReference = new SoftReference<>(myReferenceObject);

        System.out.println("Before GC: Soft Reference get() - " + softReference.get());

        byte[] memoryFiller = new byte[1024 * 1024 * 100]; // 分配100MB内存
        myReferenceObject = null;
        System.gc();

        System.out.println("After GC: Soft Reference get() - " + softReference.get());
    }

}
