package com.axon.java.stack.juc.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 3. 并发标记清除垃圾收集器 (CMS GC回收参数设置)
 * 优点：
 * •	低停顿时间：CMS 是一种低延迟的垃圾收集器，适用于对响应时间要求高的应用程序，通过并发方式减少 GC回收参数设置 暂停时间。
 * •	并发标记和清除：年轻代的回收采用多线程并行处理，老年代使用并发标记-清除算法，减少了 Full GC回收参数设置 的停顿时间。
 * 缺点：
 * •	内存碎片：CMS 采用“标记-清除”算法，可能会导致内存碎片问题，从而影响性能。
 * •	CPU 开销大：CMS 需要占用更多的 CPU 资源，以并发方式执行垃圾收集，可能导致应用程序和 GC回收参数设置 之间的 CPU 竞争。
 * 工作原理：
 * •	CMS 使用的算法是“标记-清除”算法，垃圾收集过程分为初始标记、并发标记、重新标记和并发清除四个阶段。初始标记和重新标记需要暂停所有应用线程，其他阶段则是并发执行。
 * <p>
 * 使用场景：
 * •	适用于对响应时间敏感的应用场景，如 Web 服务器、交易系统等，需要低延迟和高响应速度。
 *
 * 年轻代和老年代的组合方式：
 *      年轻代：并行垃圾回收器 (Parallel Young GC回收参数设置)，使用多线程并行处理年轻代垃圾回收。
 * 	•	老年代：CMS垃圾回收器 (Concurrent Mark-Sweep GC回收参数设置)，通过并发标记清除算法处理老年代垃圾回收，旨在减少老年代的停顿时间。
 * 	•	组合方式：Parallel Young GC回收参数设置 + CMS GC回收参数设置
 * 	•	适合场景：适合对响应时间有高要求的应用，如互联网应用、在线交易系统，目标是减少长时间的停顿。
 *
 * 命令配置：
 *
 *  * 5. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
 *
 *  Heap
 *  par new generation   total 3072K, used 83K [0x00000007bf600000, 0x00000007bf950000, 0x00000007bf950000)
 *   eden space 2752K,   3% used [0x00000007bf600000, 0x00000007bf614e00, 0x00000007bf8b0000)
 *   from space 320K,   0% used [0x00000007bf900000, 0x00000007bf900000, 0x00000007bf950000)
 *   to   space 320K,   0% used [0x00000007bf8b0000, 0x00000007bf8b0000, 0x00000007bf900000)
 *  concurrent mark-sweep generation total 6848K, used 803K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
 *  Metaspace       used 4184K, capacity 4604K, committed 4864K, reserved 1056768K
 *   class space    used 468K, capacity 492K, committed 512K, reserved 1048576K
 *
 *
 * 执行后打印的配置
 * -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=3497984 -XX:MaxTenuringThreshold=6 -XX:NewSize=3497984 -XX:OldPLABSize=16 -XX:OldSize=6987776
 * -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 *
 */
public class CMSGCTest {

    public static void main(String[] args) {
        try {
            boolean flag = true;
            List<String> result = new ArrayList<>();
            while (flag) {
                result.add(UUID.randomUUID().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("执行结束");

        System.out.println("执行完成");
    }
}
