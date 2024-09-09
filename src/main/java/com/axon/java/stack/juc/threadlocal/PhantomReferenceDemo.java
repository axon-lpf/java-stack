package com.axon.java.stack.juc.threadlocal;


import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 4. 虚引用（PhantomReference）
 *
 * 定义： 虚引用无法通过 get() 方法获取对象引用，它的存在仅仅是为了追踪对象的垃圾回收过程，必须与 ReferenceQueue 结合使用。
 *
 * 特点：
 *
 * 	•	虚引用对象本身随时可能被回收。
 * 	•	用于在对象被回收时执行某些操作，比如清理或释放资源。
 * 	•	与 ReferenceQueue 一起使用，追踪对象的回收过程。
 *
 * 使用场景：
 *
 * 	•	堆外内存的回收： 比如 DirectByteBuffer 使用虚引用来管理堆外内存的释放。
 * 	•	资源清理： 用于在对象被回收前后执行一些特定的清理操作，确保资源（如文件句柄、数据库连接等）能够在对象回收时得到释放。
 *
 *
 * 虚引用（幽灵引用）仅用于跟踪对象被垃圾回收的时间，无法通过 get() 方法获取引用对象，需要与 ReferenceQueue 一起使用。
 * <p>
 * phantomReference.get() 总是返回 null，因为虚引用对象永远不可通过虚引用访问。
 * 当对象被垃圾回收后，虚引用会被放入 referenceQueue 中，可通过 referenceQueue.poll() 检查是否被回收。
 * <p>
 * 用于管理堆外资源或跟踪对象的垃圾回收，必须与 ReferenceQueue 结合使用。
 */
public class PhantomReferenceDemo {

    public static void main(String[] args) throws InterruptedException {

        MyReferenceObject myReferenceObject = new MyReferenceObject();

        ReferenceQueue<MyReferenceObject> myReferenceQueue = new ReferenceQueue<>();

        PhantomReference<MyReferenceObject> phantomReference = new PhantomReference<>(myReferenceObject, myReferenceQueue);

        List<byte[]> list = new ArrayList<>();


        new Thread(() -> {
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get() + "    list  add ok ");
            }

        }, "T1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyReferenceObject> poll = myReferenceQueue.poll();
                if (poll != null) {
                    System.out.println("有需对象新加入队列");
                    break;
                }
            }
        }, "t2").start();

        Thread.sleep(10000);
        System.out.println("运行结束");
    }
}
