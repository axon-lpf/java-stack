package com.axon.java.stack.juc.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * gc 回收频繁，超出限制
 * <p>
 * *  设置VM options
 * * -Xms10m  -Xmx10m  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * <p>
 * gc 频繁回收超过了限制
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 *
 *
 *  如何去解决？
 *
 *  当你遇到 JVM 中 GC 回收频繁、性能下降甚至出现 OutOfMemoryError 的问题时，排查和解决这个问题通常涉及以下几个步骤。你需要分析 GC 日志、了解内存使用情况、调整 GC 配置，并可能需要优化代码逻辑。下面是详细的排查步骤：
 *
 * 1. 查看 GC 日志
 *
 * 首先，开启并分析 GC 日志，这可以帮助你了解垃圾回收的频率、耗时和发生的区域。使用以下 JVM 参数启用 GC 日志：
 * -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/path/to/gc.log
 *
 * 	•	每次 GC 发生的时间。
 * 	•	GC 类型（如 Minor GC、Major GC、Full GC）。
 * 	•	回收了多少内存以及剩余内存情况。
 * 	•	每次 GC 花费的时间。
 *
 * 分析点：
 *
 * 	•	Minor GC：频繁的 Minor GC 通常意味着新生代内存不足。查看堆中 Eden 区域和 Survivor 区域的大小设置。
 * 	•	Full GC：如果 Full GC 频繁发生，通常意味着老年代内存不足或者内存碎片化严重，需特别关注堆的总容量和老年代容量。
 *
 *
 * 2. 使用工具监控内存使用
 *
 * 使用一些工具来监控堆内存使用情况，查看是否有内存泄漏或对象创建过于频繁：
 *
 * 	•	VisualVM：自带内存快照分析，展示堆内存、线程、GC 等详细信息。
 * 	•	JConsole：实时查看堆内存使用、线程和 GC 相关的指标。
 * 	•	MAT (Memory Analyzer Tool)：用于深度分析堆转储文件，找出占用内存过多的对象、泄漏点等。
 *
 * 关注点：
 *
 * 	•	查看堆内存分布，特别是老年代的使用情况。
 * 	•	检查内存泄漏：哪些对象没有被回收？是否有意外持有的引用？
 * 	•	观察 Eden 区是否被过多的小对象填满，导致频繁的 Minor GC。
 *
 *
 * 3. 检查并调整 JVM 内存设置
 *
 * 根据实际的内存使用情况，调整堆大小、年轻代和老年代的比例等 JVM 内存设置。常用参数包括：
 *
 * 	•	堆大小：-Xms（初始堆大小），-Xmx（最大堆大小）。
 *
 * 	    -Xms4G -Xmx4G
 *
 * 	•	新生代大小：-Xmn，用于控制新生代的大小（一般设置为堆大小的 1/4 到 1/3）。
 *
 * 	    -Xmn1G
 *
 * 	•	Survivor Ratio：新生代中 Eden 区和 Survivor 区的比例。
 * 	    -XX:SurvivorRatio=8
 *
 * 	优化点：
 *
 * 	•	如果 Minor GC 频繁，可能需要增大新生代的大小。
 * 	•	如果 Full GC 频繁，可能需要增大老年代的大小。
 *
 * 	4. 调整 GC 策略
 *
 * 不同的 GC 收集器适用于不同的应用场景。你可以根据系统的吞吐量、延迟需求选择合适的 GC 策略。
 *
 * 	•	Parallel GC（默认）：适合吞吐量优先的场景。适用于 CPU 核心较多、对暂停时间不敏感的场景。
 * 	•	CMS（Concurrent Mark-Sweep）：适合对响应时间要求较高的应用，减少 Full GC 产生的长暂停时间。适合老年代较大的场景。
 * 	•	G1 GC：适合堆较大，追求低延迟的应用（JDK 9 以后是默认垃圾收集器）。它能够根据需要调整不同代之间的回收。
 *
 * 	    -XX:+UseG1GC  或者  -XX:+UseConcMarkSweepGC
 *
 * 5. 减少对象创建频率
 *
 * 如果内存频繁被填满，检查代码是否创建了过多的短生命周期对象。这些对象很快会被垃圾收集器回收，造成频繁的 Minor GC。
 *
 * 	•	尽量复用对象，避免重复创建不必要的对象。
 * 	•	考虑使用对象池来减少对象频繁创建和销毁的开销。
 *
 * 优化方法：
 *
 * 	•	检查临时对象的创建：比如字符串拼接频繁时，使用 StringBuilder 代替 +。
 * 	•	检查集合类的使用，尽量避免不必要的扩容和拷贝。
 *
 *6. 监控和优化代码中的大对象
 *
 * 大对象（尤其是数组、文件缓存、图片等）直接进入老年代，如果频繁创建大对象会导致老年代很快填满，引发 Full GC。你可以：
 *
 * 	•	优化代码逻辑，避免大对象频繁创建。
 * 	•	减少大对象的生命周期，尽早让它们可以被垃圾收集。
 *
 * 7. 内存泄漏排查
 *
 * 内存泄漏会导致对象无法被 GC 回收，逐渐填满堆内存。排查内存泄漏的方式：
 *
 * 	•	使用 MAT（Memory Analyzer Tool）分析堆转储文件，查看哪些对象一直存在无法回收。
 * 	•	常见的内存泄漏来源：
 * 	•	静态变量持有对象的引用。
 * 	•	缓存未清理。
 * 	•	监听器、回调函数没有正确移除。
 *
 * 8. 检查第三方库或框架
 *
 * 如果你的应用使用了第三方库，确认它们没有引发内存泄漏或频繁分配内存的问题。某些库可能会缓存大量数据或持有对象的引用，导致内存无法释放。
 *
 * 总结
 *
 * 通过分析 GC 日志、调整 JVM 参数、优化代码逻辑和选择合适的 GC 策略，可以有效解决 GC 回收频繁、超出限制的问题。以下是一些主要的方向：
 *
 * 	1.	GC 日志分析：确认 GC 的类型、频率、耗时。
 * 	2.	工具监控：使用 VisualVM 等工具实时查看内存占用情况。
 * 	3.	调整 JVM 配置：根据内存使用情况调整堆大小、GC 策略。
 * 	4.	代码优化：减少短生命周期对象的创建，避免内存泄漏。
 *
 * 这些步骤可以帮助你有效找到问题的根源并进行优化。
 *
 *
 * 导出内存快照
 *
 * 1. 使用 jmap 命令
 *
 * jmap 是 JDK 提供的工具，用于生成 Java 应用的 Heap Dump。可以在运行中的 Java 进程中导出堆内存快照。
 *
 *  jmap -dump:live,format=b,file=heapdump.hprof <pid>
 *
 * 	•	live：表示只导出当前正在使用的对象（可选）。
 * 	•	format=b：表示二进制格式的堆转储文件。
 * 	•	file=heapdump.hprof：指定导出的 Heap Dump 文件路径和名称。
 * 	•	<pid>：正在运行的 Java 进程的进程 ID。你可以使用 jps 命令查看所有 Java 进程的 ID。
 *
 * 	2. JVM 启动参数
 *
 * 你可以通过在 JVM 启动时指定参数，自动在内存溢出（OutOfMemoryError）时生成 Heap Dump。
 *
 *  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/heapdump.hprof
 *
 *  	•	-XX:+HeapDumpOnOutOfMemoryError：在发生 OutOfMemoryError 时生成堆转储。
 * 	•	-XX:HeapDumpPath：指定堆转储文件的路径。
 *
 * 	3. 使用 jcmd 工具
 *
 * jcmd 是一个更强大的工具，能够提供更多的 JVM 诊断和管理功能。你可以使用它生成 Heap Dump：
 *
 *  jcmd <pid> GC.heap_dump /path/to/heapdump.hprof
 *
 *
 *  5. 使用 VisualVM 工具
 *
 * VisualVM 是一个 GUI 工具，可以通过图形界面轻松导出 Heap Dump：
 *
 * 	1.	打开 VisualVM。
 * 	2.	连接到正在运行的 Java 应用。
 * 	3.	在应用程序的上下文菜单中选择 “Heap Dump”。
 * 	4.	Heap Dump 文件会保存到默认路径，或在 GUI 中导出。
 *
 * 总结
 *
 * 	•	使用 jmap、jcmd 或 JVM 参数可在命令行生成 Heap Dump。
 * 	•	使用 Java 编程可以在代码中主动触发 Heap Dump。
 * 	•	VisualVM 提供了图形化的方式导出堆转储文件。
 *
 * 这些方法都是常见的导出 Heap Dump 文件（即 .hprof 文件）的方式，适用于不同的调试和分析场景。
 *
 *
 */

public class GCLimitOverflowTest {
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
    }
}
