package com.axon.java.stack.juc.gc;

/**
 * GC回收参数设置 的总结
 *
 * 	串行垃圾收集器 (Serial GC回收参数设置)：适用于单线程、小内存环境，简单但暂停时间长。
 * 	并行垃圾收集器 (Parallel GC回收参数设置)：适用于多核、高吞吐量场景，暂停时间较长但整体效率高。
 * 	并发标记清除垃圾收集器 (CMS GC回收参数设置)：适用于对响应时间敏感的场景，低停顿但可能存在内存碎片。
 * 	G1垃圾收集器 (G1 GC回收参数设置)：适用于大内存环境下对响应时间有较高要求的场景，具有可预测的低停顿时间
 *
 * 配置命令：
 * 1. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
 *
 * 2. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
 *
 * 3. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
 *
 * 4. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC
 *
 * 5. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
 *
 * 6. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 *
 * 7. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC(理论知道即可，实际已经优化没有这个GCl )
 *
 * -XX:+PrintGCDetails :意思是打印 GC回收详情的日志
 * -XX:+PrintCommandLineFlags : 意思是用于在启动时打印出所有使用的 JVM 命令行标志的详细信息。这些标志包括你设置的所有 JVM 参数以及 JVM 默认的设置。
 *
 *
 *
 *
 */
public class GCSummaryTest {
}
