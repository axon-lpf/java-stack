package com.axon.java.stack.juc.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 2. 并行垃圾收集器 (Parallel GC)
 优点：
 •	多线程并行收集：在垃圾回收期间同时利用多个线程进行垃圾收集，适合多核处理器，提升了垃圾回收的吞吐量。
 •	高吞吐量：并行收集器专注于提高应用程序的吞吐量，适合长时间运行的后台计算型任务。
 缺点：
 •	较长的停顿时间：虽然是多线程回收，但同样会发生全局暂停，可能会导致较长的 GC 暂停时间。
 工作原理：
 •	并行垃圾收集器的年轻代和老年代垃圾收集都可以通过多个线程并行执行。JVM 会根据 CPU 核心数动态调整并行线程的数量，最大限度地利用 CPU 资源来减少垃圾回收时间。

 使用场景：
 •	适用于对吞吐量要求高的应用程序，如批处理任务、大规模数据处理应用程序，内存充足且 GC 暂停时间不敏感的场景。

 * 年轻代和老年代的组合方式：
 *   	年轻代：并行垃圾回收器 (Parallel Young GC)，使用多线程并行处理年轻代垃圾回收。
 * 	•	老年代：并行老年代垃圾回收器 (Parallel Old GC)，使用多线程并行处理老年代垃圾回收。
 * 	•	组合方式：Parallel Young GC + Parallel Old GC
 * 	•	适合场景：适合多核CPU、大内存场景，通常在服务端模式下使用，能够充分利用多核优势，提高吞吐量。
 *
 *  命令配置：

 * 3. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
 *    使用以上命令后,年轻代则是用的是 Parallel Scavenge GC
 *  Heap
 *  PSYoungGen      total 2560K, used 64K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 2048K, 3% used [0x00000007bfd00000,0x00000007bfd102a8,0x00000007bff00000)
 *   from space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
 *   to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
 *  ParOldGen       total 7168K, used 797K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
 *   object space 7168K, 11% used [0x00000007bf600000,0x00000007bf6c7430,0x00000007bfd00000)
 *  Metaspace       used 4010K, capacity 4572K, committed 4864K, reserved 1056768K
 *   class space    used 450K, capacity 460K, committed 512K, reserved 1048576K
 *
 *  执行后打印的 配置：
 *  -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails
 *  -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
 *



 */
public class ParalleGCTest {

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
