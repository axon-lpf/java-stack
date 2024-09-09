package com.axon.java.stack.juc.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 1. 串行垃圾收集器 (Serial GC)
 * 优点：
 * •	简单高效：串行收集器的实现简单，适用于单线程环境，开销较低。
 * •	适合小内存：在单核 CPU 和小内存环境中性能良好。
 * 缺点：
 * •	单线程执行：垃圾收集时会暂停所有应用线程（“Stop The World” 事件），只能使用一个 CPU 进行垃圾回收，导致长时间的应用暂停。
 * 工作原理：
 * •	串行垃圾收集器在进行垃圾收集时会暂停所有应用线程，并且使用一个单独的线程进行垃圾回收。包括年轻代的 Minor GC 和老年代的 Full GC。
 * 使用场景：
 * •	适用于单核处理器或小型应用程序，内存占用较少且对应用响应时间要求不高的环境。
 *
 * 年轻代和老年代的组合方式：
 *      年轻代：串行垃圾回收器 (Serial Young GC)，使用单线程进行垃圾回收。
 * 	•	老年代：串行垃圾回收器 (Serial Old GC)，同样使用单线程进行垃圾回收。
 * 	•	组合方式：Serial Young GC + Serial Old GC
 * 	•	适合场景：适用于单核或小型内存环境，通常在客户端模式下使用。
 *
 * 	命令配置：
 * 	 * 1.-Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
 *
 * 	 Heap
 *  def new generation   total 3072K, used 82K [0x00000007bf600000, 0x00000007bf950000, 0x00000007bf950000)
 *   eden space 2752K,   3% used [0x00000007bf600000, 0x00000007bf614b48, 0x00000007bf8b0000)
 *   from space 320K,   0% used [0x00000007bf900000, 0x00000007bf900000, 0x00000007bf950000)
 *   to   space 320K,   0% used [0x00000007bf8b0000, 0x00000007bf8b0000, 0x00000007bf900000)
 *  tenured generation   total 6848K, used 855K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
 *    the space 6848K,  12% used [0x00000007bf950000, 0x00000007bfa25ee8, 0x00000007bfa26000, 0x00000007c0000000)
 *  Metaspace       used 4190K, capacity 4604K, committed 4864K, reserved 1056768K
 *   class space    used 467K, capacity 492K, committed 512K, reserved 1048576K
 *
 *  执行后打印的配置：
 *  -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails
 *  -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC
 *
 *2. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  执行这个命令后， oldGC默认使用 SerialOldGC 垃圾回收器
 *

 */
public class SerialGCTest {

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
