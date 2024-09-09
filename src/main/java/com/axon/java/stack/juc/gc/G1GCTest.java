package com.axon.java.stack.juc.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 4. G1垃圾收集器 (G1 GC)
 *
 * 优点：
 * 	•	可预测的低停顿：G1 旨在替代 CMS，并提供了更加可预测的垃圾回收停顿时间，适用于大内存环境下的低延迟应用。
 * 	•	区域化内存管理：将堆内存分成多个独立的区域（Region），可以在年轻代和老年代之间自由分配，灵活性更高。
 * 	•	并发执行：G1 的标记、清理过程可以并发执行，减少了应用线程的停顿时间。
 * 缺点：
 * 	•	复杂性高：G1 的实现复杂，调优难度较大。
 * 	•	高开销：G1 的内存占用和处理开销相对较高。
 *
 * 工作原理：
 * 	•	G1 将堆内存分成若干个大小相等的区域 (Region)，这些区域可以被用作年轻代、老年代或作为 GC 的其他用途。G1 使用“暂停时间模型”来控制垃圾回收的停顿时间，并通过“全局标记-清除”和“局部复制”相结合的方式进行垃圾收集。G1 会先标记对象，然后根据应用程序的内存使用模式来选择最合适的回收区域，优先回收垃圾最多的区域。
 * 	    Region 的空间大小一般是1-32M
 * 使用场景：
 * 	•	适用于对响应时间要求高的大型内存应用程序，如大数据处理、大型企业级应用等。G1 GC 提供了在低延迟和高吞吐量之间的平衡，特别适用于堆内存较大的应用场景。
 *
 * 年轻代和老年代的组合方式：
 *      年轻代和老年代：G1垃圾回收器同时处理年轻代和老年代，G1 不再严格区分年轻代和老年代，而是将堆分成多个大小相同的区域 (Region)，每个区域可以属于年轻代或老年代。G1 使用了并发标记和复制算法，回收那些垃圾最多的区域。
 * 	•	组合方式：G1垃圾回收器单独负责年轻代和老年代的垃圾回收。
 * 	•	适合场景：适合大内存、多核CPU的服务器环境，目标是在可控的停顿时间内实现高吞吐量和响应时间。
 *
 * 命令配置：
 *  * 6. -Xms10m -Xmx10m  -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 *
 *  Heap
 *  garbage-first heap   total 10240K, used 854K [0x00000007bf600000, 0x00000007bf700050, 0x00000007c0000000)
 *   region size 1024K, 1 young (1024K), 0 survivors (0K)
 *  Metaspace       used 4180K, capacity 4604K, committed 4864K, reserved 1056768K
 *   class space    used 468K, capacity 492K, committed 512K, reserved 1048576K
 *
 * 执行后打印的配置：
 *    * -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails
 *    -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC
 *
 *
 * -XX:+UseG1GC
 * XX:G1HeRpRegionSize=n：设置的G1区域的大小。值是2的幂，范围是1MB到32MB。目标是根据量小的
 * -XX:MaxGCPauseMillis=n：最大GC停顿时间，这是个软目标，IM将尽可能（但不保证）停顿小于这个
 * -XX:InitiatingHeapOccupancyPercent-n：堆占用了多少的时候就触发GC，默认为45
 * -XX:ConcGCThreads=n： 并发GC使用的线程数
 * XX:G1ReservePercent=n：设置作为空闲空间的预留内存百分比，以降低目标空间溢出的风险，默认
 *
 *
 * G1垃圾回收流程：
 *  1>.初始标记：只标记GC Roots能直接关联到的对象
 *  2>.并发标记：进行GC Roots Tracing的过程
 *  3>.最终标记：修正并发标记期间，因程序运行导致标记发生变化的那一部分对象
 *  4>.筛选回收：根据时间来进行价值最大化的回收
 *
 *  这个回收流程与CMS的差不多一致，只不过最后一步CMS是标记清除，产生很多碎片。
 *
 *
 *  G1垃圾回收器的特点：
 *  1>.G1能充分利用多CPU、多核环境硬件优势，尽量缩短STW。
 *  2>.G1整体上采用标记-整理算法，局部是通过复制算法，不会产生内存碎片。
 *  3>.宏观上看G1之中不再区分年轻代和老年代。把内存划分成多个独立的子区域（Region），可以近似理解为一个围棋的棋盘。
 *  4>.G1收集器里面讲熬个的内存区都混合在一起了，但其本身依然在小范围内要进行年轻代和老年代的区分，保留了新生代和老年代，但它们不再是物理隔离的，而是一部分Region的集合且不需要Region是连续的，也就是说依然会采用不同的GC方式来处理不同的区域。
 *  5>.G1虽然也是分代收集器，但整个内存分区不存在物理上的年轻代与老年代的区别，也不需要完全独立的survivor（to space）堆做复制准备。G1只有逻辑上的分代概念，或者说每个分区都可能随G1的运行在不同代之间前后切换：
 *
 * G1整体上采用的是标记整理算法， 局部使用的是复制算法。
 *
 *
 *
 *


 */
public class G1GCTest {

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
