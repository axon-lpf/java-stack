package com.axon.java.stack.juc.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ZGC
 *
 * 优点：
 *
 * 	1.	低暂停时间：
 * 	•	ZGC的设计目标是将GC暂停时间控制在10毫秒以内，且与堆大小几乎无关，这使得它非常适合需要极低暂停时间的应用程序。
 * 	2.	大内存支持：
 * 	•	ZGC可以处理非常大的堆（TB级别），而不显著增加暂停时间，这在大数据处理和高并发系统中非常有用。
 * 	3.	并发性高：
 * 	•	大部分GC工作与应用线程并发进行，不会影响应用的吞吐量。
 * 	4.	无需停止整个应用：
 * 	•	ZGC的大部分工作（如标记、重定位）都是并发进行的，应用程序不需要停顿，从而减少了GC的干扰。
 *
 * 缺点：
 * 	1.	CPU开销：
 * 	•	由于ZGC在很大程度上依赖并发处理，这可能会对CPU资源产生较大压力。
 * 	2.	内存开销：
 * 	•	ZGC使用了颜色标记指针和其他复杂的技术，这些可能会略微增加内存开销。
 * 	3.	兼容性：
 * 	•	虽然ZGC的兼容性已经有了显著提升，但在某些特殊情况下，可能仍需要进行额外的调优或排除。
 * 	4.	成熟度：
 * 	•	相较于传统GC，ZGC的成熟度和广泛使用程度还在逐步提升中。
 *
 * 工作原理：
 * 	1.	分区和颜色标记指针：
 * 	•	ZGC将堆划分为多个小的分区，这些分区通过颜色标记指针来管理对象的生命周期。每个指针使用若干比特位来存储对象状态（如标记、转发、重定位状态）。
 * 	2.	并发标记和重定位：
 * 	•	标记和重定位阶段是并发进行的，应用程序可以在GC工作时继续运行。ZGC通过染色指针和并发处理来确保对象引用的正确性。
 * 	3.	垃圾收集的阶段：
 * 	•	ZGC主要分为并发标记（Concurrent Marking）、并发准备重定位（Concurrent Prepare for Relocation）、并发重定位（Concurrent Relocation）等阶段。
 * 	4.	染色指针（Colored Pointers）：
 * 	•	ZGC使用染色指针来管理内存中的对象。这些指针通过位图或位标记来表示对象的状态，以确保在垃圾收集中处理对象的安全性和效率。
 *
 * 使用场景：
 *
 * 	1.	低延迟应用：
 * 	•	ZGC非常适合金融交易、实时数据处理、在线游戏等对延迟非常敏感的应用场景。
 * 	2.	大内存应用：
 * 	•	在需要处理大量数据的应用场景（如大数据、机器学习、内存数据库等）中，ZGC的性能优势尤为明显。
 * 	3.	高并发系统：
 * 	•	ZGC能够在高并发系统中保持稳定的低暂停时间，使得它成为对响应时间要求严格的高并发应用的理想选择。
 *
 * <p>
 * 年轻代和老年代：ZGC 采用区域化内存管理策略，所有代的回收都是并发进行的，ZGC不再严格区分年轻代和老年代，而是将整个堆视为一个整体进行回收。回收机制通过读屏障、并发标记等技术，来最小化GC停顿时间。
 * 组合方式：ZGC统一负责整个堆空间的垃圾回收。
 * 适合场景：适用于需要极低停顿时间的大型应用，适合响应时间要求极高的场景。
 * <p>
 * 命令配置：
 * 6. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseZGC
 *
 *ZGC 是从 JDK 11 开始引入的，因此请确保你使用的是 Java 11 或更高版本，  JDK8下是不能执行的。
 */
public class ZGCTest {

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
