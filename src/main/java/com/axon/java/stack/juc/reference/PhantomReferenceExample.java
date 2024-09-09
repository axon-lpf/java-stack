package com.axon.java.stack.juc.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 *
 * 4. 虚引用（Phantom Reference）
 *
 * 定义：
 *
 * 虚引用是最弱的一种引用类型。一个对象是否有虚引用的存在，不会影响它的生命周期。虚引用主要用于跟踪对象的回收活动，必须与引用队列（ReferenceQueue）联合使用。
 *
 * 使用场景：
 *
 * 虚引用主要用于检测对象何时从内存中回收，以便在对象被回收时进行一些后续清理操作。
 *
 */
public class PhantomReferenceExample {

    public static void main(String[] args) {
        // 创建一个引用队列
        ReferenceQueue<Object> refQueue = new ReferenceQueue<>();

        // 创建一个虚引用对象，并将它与引用队列关联
        PhantomReference<Object> phantomRef = new PhantomReference<>(new Object(), refQueue);

        // 手动调用垃圾回收
        System.gc();

        // 虚引用对象在任何情况下都不会通过 get() 方法获取到
        if (phantomRef.get() != null) {
            System.out.println("Phantom reference is still alive.");
        } else {
            System.out.println("Phantom reference has no object associated with it.");
        }

        // 检查引用队列中是否有对象被回收
        if (refQueue.poll() != null) {
            System.out.println("Object associated with phantom reference has been collected.");
        } else {
            System.out.println("No objects have been collected yet.");
        }
    }
}
