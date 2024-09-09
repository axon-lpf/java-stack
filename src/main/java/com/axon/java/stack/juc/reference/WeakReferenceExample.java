package com.axon.java.stack.juc.reference;

import java.lang.ref.WeakReference;

/**
 * 3. 弱引用（Weak Reference）
 * <p>
 * 定义：
 * <p>
 * 弱引用比软引用更弱。无论内存是否充足，垃圾回收器都会回收被弱引用指向的对象。
 * <p>
 * 使用场景：
 * <p>
 * 弱引用常用于 WeakHashMap 等场景中，键的生命周期不由显式管理，而由垃圾回收机制决定。
 */
public class WeakReferenceExample {

    public static void main(String[] args) {
        // 创建一个弱引用对象
        WeakReference<Object> weakRef = new WeakReference<>(new Object());

        // 手动调用垃圾回收
        System.gc();

        // 弱引用对象可能会被回收
        if (weakRef.get() != null) {
            System.out.println("Weak reference is still alive.");
        } else {
            System.out.println("Weak reference has been collected.");
        }
    }
}
