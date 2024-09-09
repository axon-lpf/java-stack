package com.axon.java.stack.juc.reference;

import java.lang.ref.SoftReference;

/**
 *
 * 2. 软引用（Soft Reference）
 *
 * 定义：
 *
 * 软引用是一种相对强引用更弱的引用类型。当内存不足时，垃圾回收器会回收软引用指向的对象，但在内存充足的情况下不会回收这些对象。
 *
 * 使用场景：
 *
 * 软引用适用于实现缓存。当内存足够时，缓存可以保留；当内存不足时，可以回收这些缓存以腾出空间。
 *
 * 如：图片的加载等
 *
 */
public class SoftReferenceExample {


    public static void main(String[] args) {
        // 创建一个软引用对象
        SoftReference<Object> softRef = new SoftReference<>(new Object());

        // 手动调用垃圾回收
        System.gc();

        // 软引用对象仍然存活（假设内存充足）
        if (softRef.get() != null) {
            System.out.println("Soft reference is still alive.");
        } else {
            System.out.println("Soft reference has been collected.");
        }

        // 模拟内存不足的情况（可能需要大量分配内存）
        try {
            byte[] memoryFiller = new byte[1024 * 1024 * 100]; // 分配100MB内存
        } catch (OutOfMemoryError e) {
            System.out.println("Out of memory error!");
        }

        // 在内存不足的情况下，软引用对象可能会被回收
        if (softRef.get() != null) {
            System.out.println("Soft reference is still alive.");
        } else {
            System.out.println("Soft reference has been collected.");
        }
    }
}
