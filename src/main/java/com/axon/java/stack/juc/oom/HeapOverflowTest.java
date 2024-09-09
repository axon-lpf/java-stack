package com.axon.java.stack.juc.oom;

/**
 *  堆内存的溢出
 *
 *  设置VM options
 * -Xms10m  -Xmx10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 * 输出错误信息：
 *
 * 1604K->480K(2560K)
 * 回收前占用大小-> 回收后占用多大小（总内存大小）
 *
 * 0.111: [GC (Allocation Failure) [PSYoungGen: 1604K->480K(2560K)] 1604K->630K(9728K), 0.0023516 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
 * 0.114: [GC (Allocation Failure) [PSYoungGen: 480K->512K(2560K)] 630K->670K(9728K), 0.0004696 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 0.114: [Full GC (Allocation Failure) [PSYoungGen: 512K->0K(2560K)] [ParOldGen: 158K->445K(7168K)] 670K->445K(9728K), [Metaspace: 3097K->3097K(1056768K)], 0.0024831 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
 * 0.117: [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 445K->445K(9728K), 0.0002625 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 0.117: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 445K->428K(7168K)] 445K->428K(9728K), [Metaspace: 3097K->3097K(1056768K)], 0.0021278 secs] [Times: user=0.01 sys=0.01, real=0.00 secs]
 * Heap
 *  PSYoungGen      total 2560K, used 104K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 2048K, 5% used [0x00000007bfd00000,0x00000007bfd1a038,0x00000007bff00000)
 *   from space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
 *   to   space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
 *  ParOldGen       total 7168K, used 428K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
 *   object space 7168K, 5% used [0x00000007bf600000,0x00000007bf66b198,0x00000007bfd00000)
 *  Metaspace       used 3188K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 351K, capacity 388K, committed 512K, reserved 1048576K
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at com.axon.java.stack.juc.oom.HeapOverflowTest.main(HeapOverflowTest.java:14)
 *
 *
 * 	日志逐句分析：
 * 	0.115: [GC (Allocation Failure) [PSYoungGen: 1596K->512K(2560K)] 1596K->622K(9728K), 0.0028234 secs] [Times: user=0.00 sys=0.01, real=0.00 secs]
 * 		0.115: GC事件发生的时间点，单位是秒。表示从 JVM 启动到这个 GC 事件发生时经过的时间。
 * 	•	[GC (Allocation Failure)]: 表示这是一次年轻代（Young Generation）GC，由于内存分配失败（Allocation Failure）而触发。
 * 	•	[PSYoungGen: 1596K->512K(2560K)]: 表示在GC后，年轻代的内存变化：
 * 	•	1596K: GC 前年轻代使用的内存量。
 * 	•	512K: GC 后年轻代使用的内存量。
 * 	•	(2560K): 年轻代内存的总大小。
 * 	•	1596K->622K(9728K): 表示堆内存总使用量的变化：
 * 	•	1596K: GC 前堆内存总使用量。
 * 	•	622K: GC 后堆内存总使用量。
 * 	•	(9728K): 堆内存的总大小。
 * 	•	0.0028234 secs: 这次 GC 事件花费的总时间，单位为秒。
 * 	•	[Times: user=0.00 sys=0.01, real=0.00 secs]: 表示这次 GC 的时间分布：
 * 	•	user=0.00: 用户态 CPU 时间。
 * 	•	sys=0.01: 内核态 CPU 时间。
 * 	•	real=0.00: 实际消耗的时间（墙钟时间）。
 *
 *
 * 	0.118: [GC (Allocation Failure) [PSYoungGen: 512K->512K(2560K)] 622K->622K(9728K), 0.0005131 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
 *
 * 	0.118: GC 事件发生的时间点。
 * 	•	[GC (Allocation Failure)]: 依然是年轻代 GC 事件，原因是内存分配失败。
 * 	•	[PSYoungGen: 512K->512K(2560K)]: 表示 GC 前后，年轻代的内存使用量没有变化，依然是 512K。
 * 	•	622K->622K(9728K): 表示 GC 前后堆内存总使用量没有变化，依然是 622K。
 * 	•	0.0005131 secs: 这次 GC 事件花费的总时间。
 * 	•	[Times: user=0.01 sys=0.00, real=0.00 secs]: 时间分布，用户态 CPU 时间为 0.01 秒。
 *
 * 0.119: [Full GC (Allocation Failure) [PSYoungGen: 512K->0K(2560K)] [ParOldGen: 110K->424K(7168K)] 622K->424K(9728K), [Metaspace: 3087K->3087K(1056768K)], 0.0024155 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *  	0.119: GC 事件发生的时间点。
 * 	•	[Full GC (Allocation Failure)]: 这是一场 Full GC 事件，原因是内存分配失败。Full GC 会清理整个堆，包括年轻代和老年代。
 * 	•	[PSYoungGen: 512K->0K(2560K)]: 年轻代在 GC 后从 512K 减少到 0K。
 * 	•	[ParOldGen: 110K->424K(7168K)]: 老年代在 GC 后从 110K 增加到 424K。
 * 	•	622K->424K(9728K): 堆内存总使用量从 622K 减少到 424K。
 * 	•	[Metaspace: 3087K->3087K(1056768K)]: Metaspace 没有变化，依然使用 3087K。
 * 	•	0.0024155 secs: Full GC 所花费的时间。
 * 	•	[Times: user=0.00 sys=0.00, real=0.00 secs]: 时间分布。
 *
 * 	0.121: [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 424K->424K(9728K), 0.0002709 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *
 * 	    0.121: GC 事件发生的时间点。
 * 	•	[GC (Allocation Failure)]: 年轻代 GC 事件，原因是内存分配失败。
 * 	•	[PSYoungGen: 0K->0K(2560K)]: 年轻代内存没有变化，依然是 0K。
 * 	•	424K->424K(9728K): 堆内存总使用量没有变化，依然是 424K。
 * 	•	0.0002709 secs: 这次 GC 事件花费的总时间。
 * 	•	[Times: user=0.00 sys=0.00, real=0.00 secs]: 时间分布。
 *
 * 	0.121: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 424K->407K(7168K)] 424K->407K(9728K), [Metaspace: 3087K->3087K(1056768K)], 0.0023894 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
 *
 * 	0.121: GC 事件发生的时间点。
 * 	•	[Full GC (Allocation Failure)]: Full GC 事件，原因是内存分配失败。
 * 	•	[PSYoungGen: 0K->0K(2560K)]: 年轻代内存没有变化，依然是 0K。
 * 	•	[ParOldGen: 424K->407K(7168K)]: 老年代内存从 424K 减少到 407K。
 * 	•	424K->407K(9728K): 堆内存总使用量从 424K 减少到 407K。
 * 	•	[Metaspace: 3087K->3087K(1056768K)]: Metaspace 没有变化，依然是 3087K。
 * 	•	0.0023894 secs: Full GC 所花费的时间。
 * 	•	[Times: user=0.01 sys=0.00, real=0.01 secs]: 时间分布。
 *
 *
 * 	Heap
 *  PSYoungGen      total 2560K, used 120K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 2048K, 5% used [0x00000007bfd00000,0x00000007bfd1e348,0x00000007bff00000)
 *   from space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
 *   to   space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
 *  ParOldGen       total 7168K, used 407K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
 *   object space 7168K, 5% used [0x00000007bf600000,0x00000007bf665cf0,0x00000007bfd00000)
 *  Metaspace       used 3175K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 349K, capacity 388K, committed 512K, reserved 1048576K
 *
 * Heap: 堆内存的整体信息。
 * 	•	PSYoungGen total 2560K, used 120K: 年轻代的总大小是 2560K，使用了 120K。
 * 	•	eden space 2048K, 5% used: Eden 区的总大小是2048K，使用了 5%。
 *      from space 512K, 0% used: From 区的总大小是 512K，没有使用。
 * 	•	to space 512K, 0% used: To 区的总大小是 512K，没有使用。
 * 	•	ParOldGen total 7168K, used 407K: 老年代的总大小是 7168K，使用了 407K。
 * 	•	Metaspace used 3175K, capacity 4496K, committed 4864K, reserved 1056768K: Metaspace 的详细信息：
 * 	•	used 3175K: 使用了 3175K。
 * 	•	capacity 4496K: 容量是 4496K。
 * 	•	committed 4864K: 已分配的内存是 4864K。
 * 	•	reserved 1056768K: 保留的内存是 1056768K。
 * 	•	class space used 349K: 类空间使用了 349K。
 *
 *  根据输出的 老年代的总大小是 7168K，使用了 407K
 * 即使在老年代有空间未被完全使用的情况下，如果 JVM 无法为新分配的对象找到合适的内存区域（例如，新生代空间不足或者老年代内存碎片化严重），仍然会导致 OutOfMemoryError。
 *
 *
 */
public class HeapOverflowTest {

    public static void main(String[] args) {

        try {
            byte[] memoryFiller = new byte[1024 * 1024 * 100]; // 分配100MB内存
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        System.out.println("执行完成");

    }
}
