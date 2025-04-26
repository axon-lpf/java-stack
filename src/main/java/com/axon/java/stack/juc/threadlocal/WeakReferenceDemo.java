package com.axon.java.stack.juc.threadlocal;

import java.lang.ref.WeakReference;


/**
 *
 * 3. 弱引用（WeakReference）
 *
 * 定义： 弱引用在下一次垃圾回收时，无论内存是否充足，都会被回收。
 *
 * 特点：
 *
 * 	•	弱引用对象通常在下一个垃圾回收周期中被回收。
 * 	•	get() 方法可以返回引用的对象，除非对象已经被回收。
 * 	•	可以用于检测对象是否已经被回收，以避免内存泄漏。
 *
 * 使用场景：
 *
 * 	•	弱引用缓存： 实现一些较为松散的缓存，不希望对象强制保留，允许垃圾回收器在下一次回收中回收它们。
 * 	•	避免内存泄漏： 当引用对象的生命周期与某些管理对象不一致时，可以使用弱引用防止内存泄漏。
 *
 *
 *
 * 弱引用在下一次垃圾回收时，无论内存是否足够，都会被回收。
 *
 *
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {

        MyReferenceObject myReferenceObject = new MyReferenceObject();

        WeakReference<MyReferenceObject> weakReference = new WeakReference<>(myReferenceObject);

        System.out.println("Before GC回收参数设置: Weak Reference get() - " + weakReference.get());

        myReferenceObject = null;

        System.gc();

        System.out.println("After GC回收参数设置: Weak Reference get() - " + weakReference.get());

        //弱引用对象在下一次垃圾回收时会被回收，因此 weakReference.get() 可能会返回 null。

    }
}
