package com.axon.java.stack.netty.nio.buffer;

import java.nio.IntBuffer;

/**
 *
 * 1. mark (标记)
 *
 * 	•	作用：mark 用于记录当前的 position，可以通过 reset() 方法将 position 恢复到 mark 处。
 * 	•	初始值：默认值是 -1，表示没有标记。
 * 	•	使用场景：当你想暂时保存某个位置，之后可以随时回到这个位置时，可以使用 mark() 方法来标记当前的 position，然后通过 reset() 方法回到这个标记。
 * 	•	示例：
 *      buffer.mark();   // 记录当前 position
 *      buffer.get();    // 读取一个字节，position 增加
 *      buffer.reset();  // 恢复到 mark 时的位置
 *
 * 2. position (当前位置)
 *
 * 	•	作用：position 表示下一个读或写操作的索引位置，每次读/写操作都会使 position 自动前进。
 * 	•	初始值：position 默认是 0，表示从起始位置开始读/写。
 * 	•	使用场景：position 是你当前读/写数据的位置。每次调用 get() 或 put() 等操作时，position 会自动加1，表示你向前移动到下一个位置。
 *      •	示例：
 *      buffer.put((byte) 1);  // 向 position=0 的位置写入数据，position 移动到 1
 *      buffer.get();          // 从 position=1 读取数据，position 移动到 2
 *
 * 3. limit (限制)
 *
 * 	•	作用：limit 表示你能读取或写入的上界，position 不能超过 limit。对于读模式，limit 表示可以读取数据的最大数量；对于写模式，limit 表示可以写入数据的最大数量。
 * 	•	初始值：在写模式下，limit 等于 capacity；在读模式下，limit 表示实际可读数据的数量。
 * 	•	使用场景：
 * 	•	当你想要切换 Buffer 的读写模式时，通常会通过 flip() 方法将 limit 设置为当前的 position，并将 position 重置为 0，以便准备从头读取数据。
 * 	•	示例：
 * 	    buffer.limit(10);  // 设置 limit 为 10，表示最多能读/写 10 个字节
 *
 * 4. capacity (容量)
 *
 * 	•	作用：capacity 是 Buffer 中可以容纳的最大数据量，是固定的，不会改变。它代表 Buffer 的总大小。
 * 	•	初始值：在 Buffer 创建时指定，并且不能改变。
 * 	•	使用场景：capacity 决定了 Buffer 中最多能存储多少数据。你不能向 Buffer 写入超过 capacity 的数据。
 * 	•	示例：
 * 	    ByteBuffer buffer = ByteBuffer.allocate(1024);  // 创建容量为 1024 字节的 buffer
 *
 *
 * 总结
 *
 * 	•	mark：用来标记某个 position，可以通过 reset() 回到该位置。
 * 	•	position：当前读写的索引位置，每次读写操作后都会自动递增。
 * 	•	limit：限制你能读写的范围，读模式下表示能读的最大位置，写模式下表示能写的最大位置。
 * 	•	capacity：Buffer 的容量，代表能够存储的最大数据量，是固定的。
 *
 * 通过这四个属性的配合，Buffer 可以非常灵活地进行数据的读写操作。
 *
 *
 * allocate.flip() 中的 flip() 方法是 Java NIO Buffer 类中的一个重要方法，主要作用是将 Buffer 从写模式切换到读模式。
 *
 * flip() 的作用
 *
 * 在 Buffer 中，写入和读取数据是两种不同的操作模式。flip() 方法用于在这两种模式之间切换。
 *
 * 具体来说，flip() 的作用是：
 *
 * 	1.	将当前的 position 设置为 0：
 * 	•	这样可以从 Buffer 的开头开始读取数据，因为 position 表示下一个要读或写的位置。
 * 	2.	将 limit 设置为当前的 position：
 * 	•	这意味着可以读取的数据的上限被设置为你写入的数据量。此时的 limit 是写模式下的 position，表示可以读取多少数据。
 * 	3.	清除 mark：
 * 	•	mark 会被清除，因为我们正在准备重新读取数据，之前的标记已经无效。
 *
 * 使用场景
 *
 * flip() 通常在你完成向 Buffer 写入数据后调用，准备开始从 Buffer 中读取数据时使用。写模式下，position 随着数据写入不断增加，但读模式需要从头开始读取，所以需要通过 flip() 方法重置 position 并设置 limit。
 *
 */
public class BufferDemo {

    public static void main(String[] args) {

        IntBuffer allocate = IntBuffer.allocate(5);
        System.out.println("开始插入-----------");
        for (int i = 0; i < allocate.limit(); i++) {
            allocate.put(i * 2);
        }
        System.out.println("插入结束----------");
        // 反转一下，由写转为读取
        allocate.flip();
        // 开始读取
        System.out.println("开始读取-----------");
        for (int i = 0; i < allocate.limit(); i++) {
            int i1 = allocate.get();
            System.out.println("当前数值" + i1);
        }


    }
}
